package Modelo;

import Modelo.Figuras.Figura;
import Modelo.Figuras.SinFigura;
import observador.Observable;
import java.util.ArrayList;
import java.util.List;

public class ManoRonda extends Observable {

    private static int contadorId = 1; // Contador estático para IDs únicos
    private int id;
    private EstadoMano estado;
    private MesaPoker mesaPoker;
    private ArrayList<Jugador> jugadoresEnMano;
    private Figura figuraGanadora;
    private Pozo pozo;
    private Jugador ganador;
    private Mazo mazo;
    private ApuestaJugador apuestaActual;

    public ManoRonda(MesaPoker mesa) throws PokerException {
        this.id = contadorId++;
        this.mesaPoker = mesa;
        this.estado = EstadoMano.ESPERANDO_APUESTA;
        this.jugadoresEnMano = new ArrayList<>(mesa.getJugadoresMesa());
        this.figuraGanadora = new SinFigura();
        this.ganador = null;
        this.mazo = new Mazo();
        this.apuestaActual = null;
        this.pozo = new Pozo(mesaPoker.getPorcentajeComision());
        inicializarMano();
    }

    public void inicializarMano() throws PokerException {
        repartirCartasAJugadores();
        for (Jugador jugador : jugadoresEnMano) {
            jugador.decrementarSaldo(mesaPoker.getLuz());
        }
    }

    public Pozo getPozo() {
        return pozo;
    }

    public void setPozo(Pozo pozo) {
        this.pozo = pozo;
    }

    public void iniciarMano() throws PokerException {
        if (estado != EstadoMano.ESPERANDO_APUESTA) {
            throw new IllegalStateException("La ronda no puede iniciarse desde el estado actual.");
        }
        repartirCartasAJugadores();
        estado = EstadoMano.ESPERANDO_APUESTA;
        avisar(EventosGenerales.Eventos.INICIA_MANO);
    }

    public void repartirCartasAJugadores() throws PokerException {
        for (Jugador jugador : jugadoresEnMano) {
            jugador.setCartas(mazo.repartirCartas(5));
            System.out.println("CARTAS JUGADOR" + jugador.getCartas());
        }
    }

    public void cambiarCartas(Jugador jugador, ArrayList<Integer> indicesSeleccionados) throws PokerException {
        validarEstado(EstadoMano.PIDIENDO_CARTAS);
        validarJugadorActivo(jugador);

        if (indicesSeleccionados == null || indicesSeleccionados.isEmpty()) {
            throw new PokerException("Debe seleccionar al menos una carta para cambiar.");
        }

        ArrayList<Carta> nuevasCartas = mazo.repartirCartas(indicesSeleccionados.size());
        jugador.cambiarCartas(indicesSeleccionados, nuevasCartas);
        avisar(EstadoMano.PIDIENDO_CARTAS);
    }

    public void determinarGanador() throws PokerException {
        validarEstado(EstadoMano.PIDIENDO_CARTAS);

        Figura mejorFigura = new SinFigura();
        for (Jugador jugador : jugadoresEnMano) {
            Figura figuraJugador = Figura.obtenerFiguraGanadora(jugador.getCartas());
            if (figuraJugador.compararCon(mejorFigura) > 0) {
                mejorFigura = figuraJugador;
                ganador = jugador;
            }
        }
        if (ganador != null) {
            System.out.println("GANADOR" + ganador);
            figuraGanadora = mejorFigura;
        }
    }

    public void registrarApuesta(Jugador jugador, double monto) throws PokerException {
        validarEstado(EstadoMano.ESPERANDO_APUESTA);
        validarJugadorActivo(jugador);

        jugador.decrementarSaldo(monto);
        pozo.incrementarPozo(monto);
        avisar(EventosGenerales.Eventos.CAMBIA_POZO);
    }

    //valida que uno de los dos estados sea el actual
    private void validarEstado(EstadoMano... estadosPermitidos) throws PokerException {
        for (EstadoMano estadoPermitido : estadosPermitidos) {
            if (estado == estadoPermitido) {
                return;
            }
        }
        throw new PokerException("La acción no es válida en el estado actual: " + estado);
    }

    private void validarJugadorActivo(Jugador jugador) throws PokerException {
        if (!jugadoresEnMano.contains(jugador)) {
            throw new PokerException("El jugador no está en esta mano.");
        }
    }

    public void eliminarJugador(Jugador jugador) {
        jugadoresEnMano.remove(jugador);
    }

    public int getId() {
        return id;
    }

    public EstadoMano getEstado() {
        return estado;
    }

    public List<Jugador> getJugadoresEnMano() {
        return jugadoresEnMano;
    }

    public Figura getFiguraGanadora() {
        return figuraGanadora;
    }

    public Jugador getGanador() {
        return ganador;
    }

    public Mazo getMazo() {
        return mazo;
    }

    public ApuestaJugador getApuestaActual() {
        return apuestaActual;
    }

    public void setEstado(EstadoMano estado) {
        this.estado = estado;
    }

    public void finalizarMano() throws PokerException {
        validarEstado(EstadoMano.PIDIENDO_CARTAS, EstadoMano.ESPERANDO_APUESTA);
        determinarGanador();
        pozo.calcularComision();
        estado = EstadoMano.TERMINADA;
        avisar(EventosGenerales.Eventos.FINALIZA_MANO);
        pozo.reiniciarPozo();
    }

    public boolean todosHanPasado() {
        return jugadoresEnMano.isEmpty();
    }

    public boolean tieneGanador() {
        return ganador != null;
    }

    public void pasarApuesta(Jugador jugador, MesaPoker mesaPoker) throws PokerException {
        validarEstado(EstadoMano.ESPERANDO_APUESTA);
        validarJugadorActivo(jugador);
        if (apuestaActual != null && apuestaActual.jugadorPago(jugador)) {
            throw new PokerException("No puedes pasar porque ya pagaste la apuesta.");
        } else if (apuestaActual == null || !apuestaActual.esActiva()) {
            throw new PokerException("No hay una apuesta inciadad para pasar.");
        } else {
            System.out.println(jugador.getNombreCompleto() + " ha pasado.");
        }
    }

    public void iniciarApuesta(double monto, Jugador iniciador) throws PokerException {
        validarEstado(EstadoMano.ESPERANDO_APUESTA);
        validarJugadorActivo(iniciador);

        if (apuestaActual != null && apuestaActual.esActiva()) {
            throw new PokerException("Ya existe una apuesta activa.");
        }
        apuestaActual = new ApuestaJugador(monto, iniciador);
        pozo.incrementarPozo(monto);
        iniciador.decrementarSaldo(monto);
        estado = EstadoMano.APUESTA_INICIADA;

        avisar(EventosGenerales.Eventos.CAMBIA_POZO);
        System.out.println("Apuesta inicial de $" + monto + " iniciada por " + iniciador.getNombreCompleto());

    }

    public void pagarApuesta(Jugador jugador) throws PokerException {
        validarEstado(EstadoMano.APUESTA_INICIADA);
        validarJugadorActivo(jugador);

        if (apuestaActual == null || !apuestaActual.esActiva()) {
            throw new PokerException("No hay una apuesta iniciada para pagar.");
            
        } else if (apuestaActual.jugadorPago(jugador)) {
            throw new PokerException("Ya has pagado la apuesta.");
            
        } else {
            double montoApuesta = apuestaActual.getMonto();
            if (jugador.getSaldo() < montoApuesta) {
                throw new PokerException("Saldo insuficiente para pagar esta apuesta.");
            }
            apuestaActual.procesarPago(jugador);
            pozo.incrementarPozo(montoApuesta);
            avisar(EventosGenerales.Eventos.CAMBIA_POZO);

            if (apuestaActual.todosPagaron(jugadoresEnMano)) {
                estado = EstadoMano.PIDIENDO_CARTAS;
                avisar(EstadoMano.PIDIENDO_CARTAS);
            }
        }
    }

    @Override
    public String toString() {
        return "ManoRonda{"
                + "id=" + id
                + ", estado=" + estado
                + ", figuraGanadora=" + figuraGanadora
                + ", ganador=" + (ganador != null ? ganador.getNombreCompleto() : "Ninguno")
                + '}';
    }
}

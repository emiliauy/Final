package Modelo;

import Modelo.EventosGenerales.Eventos;
import Modelo.Figuras.*;
import observador.Observable;
import java.util.ArrayList;

public class MesaPoker extends Observable {

    private static int contadorId = 1;
    private int id;
    private Mazo mazo;
    private ArrayList<Jugador> jugadoresMesa;
    private int jugadoresRequeridos;
    private EstadoMesa estado;
    private double porcentajeComision;  //sobre el pozo recaudado la mesa cobra un comision y lo debita automatico antes de pagarle al ganador
    private double luz; //costo de la mesa
    private ArrayList<ManoRonda> manos;
    private ManoRonda manoActual;

    private double totalRecaudado;
    private int idManoActual;
    private ArrayList<Figura> figuras;

    public MesaPoker(int jugadoresRequeridos, double luz, double porcentajeComision) throws PokerException {
        validarJugadoresRequeridos(jugadoresRequeridos);
        validarApuestaBase(luz);

        this.id = contadorId++;
        this.mazo = new Mazo();
        this.jugadoresMesa = new ArrayList<>();
        this.jugadoresRequeridos = jugadoresRequeridos;
        this.estado = EstadoMesa.ABIERTA;
        this.figuras = inicializarFiguras();
        this.porcentajeComision = porcentajeComision;
        this.luz = luz;
        this.totalRecaudado = 0;
        this.manos = new ArrayList<>();
        this.manoActual = null;
        this.idManoActual = -1;
    }

    public int getId() {
        return id;
    }

    public ManoRonda getManoActual() throws PokerException {
        if (manoActual == null) {
            throw new PokerException("No hay una mano activa en esta mesa.");
        }
        return manoActual;
    }

    public Mazo getMazo() {
        return mazo;
    }

    public ArrayList<Jugador> getJugadoresMesa() {
        return jugadoresMesa;
    }

    public int getJugadoresRequeridos() {
        return jugadoresRequeridos;
    }

    public EstadoMesa getEstado() {
        return estado;
    }

    public ArrayList<Figura> getFiguras() {
        return figuras;
    }

    public double getPorcentajeComision() {
        return porcentajeComision;
    }

    public double getLuz() {
        return luz;
    }

    public ArrayList<ManoRonda> getManos() {
        return manos;
    }

    public int getIdManoActual() {
        return idManoActual;
    }

    public double getTotalRecaudado() {
        return totalRecaudado;
    }

    public void setTotalRecaudado(double totalRecaudado) {
        this.totalRecaudado = totalRecaudado;
    }

    public void setManoActual(ManoRonda manoActual) {
        this.manoActual = manoActual;
    }

    public void setEstado(EstadoMesa estado) {
        this.estado = estado;
    }

    public double totalRecaudado() {
        double total = 0;
        if (manos == null) {
            return total;
        } else {
            for (ManoRonda m : manos) {
                total += m.getPozo().getTotalRecaudado();
            }
            return total;
        }
    }

    private void validarJugadoresRequeridos(int jugadoresRequeridos) throws PokerException {
        if (jugadoresRequeridos < 2 || jugadoresRequeridos > 5) {
            throw new PokerException("Cantidad de jugadores no válida. Debe estar entre 2 y 5.");
        }
    }

    private void validarApuestaBase(double luz) throws PokerException {
        if (luz < 1) {
            throw new PokerException("Luz inválida. Debe ser igual o mayor a 1.");
        }
    }

    private ArrayList<Figura> inicializarFiguras() {
        ArrayList<Figura> figuras = new ArrayList<>();
        figuras.add(new Par());
        figuras.add(new Pierna());
        figuras.add(new Escalera());
        figuras.add(new Poker());
        figuras.add(new SinFigura());
        return figuras;
    }

    public void iniciarMesa() throws PokerException {
        System.out.println("Estado de la mesa " + estado);
        if (estado != EstadoMesa.ABIERTA) {
            throw new PokerException("La mesa no puede iniciarse porque no está en estado ABIERTA."); // Excepción correcta
        }

       
        System.out.println("Estado de la mesa antes de iniciarMano " + estado);

        iniciarMano();
        avisar(Eventos.INICIA_JUEGO);
    }

    public void cerrarMesa() {
        if (estado != EstadoMesa.FINALIZADA) {
            this.estado = EstadoMesa.FINALIZADA;
            jugadoresMesa.clear();
            manos.clear();
            avisar(Eventos.FINALIZA_JUEGO);
            System.out.println("La mesa " + id + " se ha cerrado.");
        }
    }

    public void finalizarMesa() throws PokerException {
        if (estado != EstadoMesa.INICIADA) {
            throw new PokerException("La mesa no puede finalizar porque no está en estado INICIADA.");
        }
        jugadoresMesa.clear();
        manoActual = null;
        estado = EstadoMesa.FINALIZADA;
    }

    public void iniciarJuego() throws PokerException {
        if (estado != EstadoMesa.INICIADA) {
            throw new PokerException("La mesa no está en estado INICIADA.");
        }
        if (manoActual == null) {
            iniciarMano();
        }
        manoActual.iniciarMano();
    }

    public void agregarJugador(Jugador jugador) throws PokerException {
        if (estado == EstadoMesa.INICIADA) {
            throw new PokerException("La mesa ya está en juego, no se puede ingresar.");
        }
        if (jugadoresMesa.size() >= jugadoresRequeridos) {
            throw new PokerException("La mesa ya tiene el número máximo de jugadores.");
        }
        if (jugador.getSaldo() < luz) {
            throw new PokerException("El jugador no tiene suficiente saldo para cubrir la luz.");
        }
        jugador.decrementarSaldo(luz);
        jugadoresMesa.add(jugador);
        actualizarEstadoMesa();
        avisar(EventosGenerales.Eventos.CAMBIA_CANTIDAD_JUGADORES);
    }

    public void iniciarMano() throws PokerException {
        System.out.println("Estado de la mesa en iniciar Mano " + estado);
        
            estado = EstadoMesa.INICIADA;
            manoActual = new ManoRonda(this);
            manos.add(manoActual);
            idManoActual = manoActual.getId();
            avisar(Eventos.INICIA_MANO);

    }

    public void finalizarMano() throws PokerException {
        manoActual.finalizarMano();
    }

    public void actualizarEstadoMesa() throws PokerException {
        System.out.println("Actualizando estado de la mesa: Estado = " + estado + ", Jugadores = " + jugadoresMesa.size());

        if (jugadoresMesa.size() == jugadoresRequeridos && estado == EstadoMesa.ABIERTA) {
            System.out.println("Jugadores suficientes, iniciando la mesa...");
            iniciarMesa(); 
            return;
        }

        if (manoActual == null) {
            // Si manoActual es null, no se puede actualizar el estado relacionado con ella
            return;
        }

        //si todos los jugadores de la mano actual pagaron se avanza
        if (manoActual.getApuestaActual().todosPagaron(jugadoresMesa)) {
            manoActual.setEstado(EstadoMano.PIDIENDO_CARTAS);
            avisar(EstadoMano.PIDIENDO_CARTAS);

            //si queda solo 1 jugador en la mesa se termina y gana ese
        } else if (jugadoresMesa.size() == 1 && estado == EstadoMesa.INICIADA) {
            cerrarMesa();
            avisar(Eventos.FINALIZA_JUEGO);

            //arranca el juego
        } else if (jugadoresMesa.size() == jugadoresRequeridos) {
            this.estado = EstadoMesa.INICIADA;
            iniciarJuego();
            avisar(Eventos.INICIA_JUEGO);

            //si todos abandonan se termina
        } else if (jugadoresMesa.isEmpty() && this.estado == EstadoMesa.INICIADA) {
            this.estado = EstadoMesa.FINALIZADA;
            avisar(Eventos.FINALIZA_JUEGO);

            //solo actualiza cuando entra o abandona un jugador
        } else {
            if (estado != EstadoMesa.ABIERTA) {
                avisar(Eventos.CAMBIA_CANTIDAD_JUGADORES);
            }
        }
    }

    public void abandonarMesa(Jugador jugador) throws PokerException {
        this.jugadoresMesa.remove(jugador);
        if (manoActual != null) {
            this.manoActual.eliminarJugador(jugador);
        }
        actualizarEstadoMesa();
    }

    public Jugador obtenerGanador() throws PokerException {
        if (jugadoresMesa.isEmpty()) {
            throw new PokerException("No hay jugadores en la mesa.");
        }
        Jugador ganador = jugadoresMesa.get(0);
        Figura mejorFigura = ganador.evaluarFigura();
        for (Jugador jugador : jugadoresMesa) {
            Figura figuraActual = jugador.evaluarFigura();
            if (figuraActual.compararCartas(mejorFigura) > 0) {
                ganador = jugador;
                mejorFigura = figuraActual;
            }
        }
        return ganador;
    }

}

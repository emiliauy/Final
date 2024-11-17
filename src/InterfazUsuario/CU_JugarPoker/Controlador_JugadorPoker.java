package InterfazUsuario.CU_JugarPoker;

import Modelo.Carta;
import Modelo.EstadoMesa;
import Modelo.EventosGenerales.Eventos;
import Modelo.Fachada;
import Modelo.Jugador;
import Modelo.MesaPoker;
import Modelo.PokerException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import observador.Observable;
import observador.Observador;
import panelCartasPoker.CartaPoker;

/**
 *
 * @author Emilia
 */
public class Controlador_JugadorPoker implements Observador {

    private Vista_JugadorPoker vista;
    private Jugador jugador;
    private MesaPoker mesa;
    private ArrayList<Integer> indicesSeleccionados = new ArrayList<>();

    public Controlador_JugadorPoker(Vista_JugadorPoker vista, Jugador jugador, MesaPoker mesa) throws PokerException {
        this.vista = vista;
        this.jugador = jugador;
        this.mesa = mesa;

        mesa.agregarObservador(this);
        inicializarVista();
    }

    private void inicializarVista() throws PokerException {
        if (mesa.getEstado() != EstadoMesa.INICIADA) {
            int jugadoresActuales = mesa.getJugadoresMesa().size();
            int jugadoresRequeridos = mesa.getJugadoresRequeridos();
            vista.mostrarMensajeEnEtiqueta("Esperando inicio del juego. Hay " + jugadoresActuales + " de " + jugadoresRequeridos + " jugadores en la mesa.");
        //    vista.habilitarBotones(false);

        } else if (jugador.getCartas() == null || jugador.getCartas().isEmpty()) {
            vista.mostrarMensajeEnEtiqueta("Esperando que se repartan las cartas.");
        } else {
            mostrarCartasJugador();
         //   vista.habilitarBotones(true);
        }
    }

  public void mostrarCartasJugador() throws PokerException {
          System.out.println("Intentando mostrar cartas del jugador...");

        ArrayList<Carta> cartas = jugador.getCartas();
        
            System.out.println("Cartas obtenidas: " + cartas);

        if (cartas == null || cartas.isEmpty()) {
            throw new PokerException("No se han repartido cartas al jugador.");
        }
        vista.mostrarCartas(cartas);
        vista.mostrarMensaje("Cartas repartidas. Puedes iniciar tu apuesta o pedir cartas.");
    }

    public void iniciarApuesta(double apuestaInicial) {
        try {
            Fachada.getInstancia().iniciarApuesta(mesa.getId(), jugador, apuestaInicial);
            vista.actualizarPozo(mesa.getManoActual().getPozo().getTotalRecaudado());
            vista.mostrarMensajeEnEtiqueta("Has iniciado una apuesta de $" + apuestaInicial);
        } catch (PokerException e) {
            vista.mostrarError("Error al iniciar apuesta: " + e.getMessage());
        }
    }

    public void pagarApuesta() {
        try {
            Fachada.getInstancia().pagarApuesta(mesa.getId(), jugador);
            vista.actualizarPozo(mesa.getManoActual().getPozo().getTotalRecaudado());
            vista.mostrarMensajeEnEtiqueta("Has pagado la apuesta.");
        } catch (PokerException e) {
            vista.mostrarError("Error al pagar apuesta: " + e.getMessage());
        }
    }

    public void pasarApuesta() throws PokerException {
        try {
            Fachada.getInstancia().pasarApuesta(mesa.getId(), jugador);
            vista.mostrarMensaje("Has pasado. Esperando acción de otros jugadores...");
        } catch (PokerException e) {
            vista.mostrarError("No se pudo pasar: " + e.getMessage());
        }
    }

    public void cambiarCartas() throws PokerException {
        if (indicesSeleccionados.isEmpty()) {
            vista.mostrarError("No has seleccionado cartas para cambiar.");
            return;
        }
        mesa.getManoActual().cambiarCartas(jugador, indicesSeleccionados);
        ArrayList<Carta> cartasActualizadas = jugador.getCartas();
        vista.mostrarCartas(cartasActualizadas);
        vista.mostrarMensajeEnEtiqueta("Cartas cambiadas. Revisa tus cartas actualizadas.");
        indicesSeleccionados.clear();
    }

   public void gestionarClickEnCarta(CartaPoker carta) throws PokerException {
        try {
            int index = obtenerIndiceCarta(carta);
            if (indicesSeleccionados.contains(index)) {
                indicesSeleccionados.remove((Integer) index);
            } else {
                indicesSeleccionados.add(index);
            }
            vista.mostrarMensajeEnEtiqueta("Cartas seleccionadas: " + indicesSeleccionados.size());
        } catch (PokerException e) {
            vista.mostrarError("Error al seleccionar carta: " + e.getMessage());
        }
    }

    private int obtenerIndiceCarta(CartaPoker carta) throws PokerException {
        ArrayList<Carta> cartasJugador = jugador.getCartas();
        for (int i = 0; i < cartasJugador.size(); i++) {
            if (cartasJugador.get(i).equals(carta)) {
                return i;
            }
        }
        return -1;
    }

    public void abandonarMesa() throws PokerException {
        mesa.abandonarMesa(jugador);
        vista.mostrarMensaje("Usted ha abandonado la mesa.");
        vista.mesasAbiertas();
    }

     @Override
    public void actualizar(Object evento, Observable origen) {
            System.out.println("Evento recibido: " + evento + " desde: " + origen.getClass().getSimpleName());

        if (evento.equals(Eventos.CAMBIA_CANTIDAD_JUGADORES)) {
            vista.actualizarJugadores(mesa.getJugadoresMesa());
        } else if (evento.equals(Eventos.CAMBIA_POZO)) {
            vista.actualizarPozo(0);
        } else if (evento.equals(Eventos.INICIA_JUEGO)) {
            try {
                mostrarCartasJugador();
                vista.mostrarMensajeEnEtiqueta("El juego ha comenzado.");
            } catch (PokerException e) {
                vista.mostrarError("Error al mostrar las cartas: " + e.getMessage());
            }
        } else if (evento.equals(Eventos.FINALIZA_JUEGO)) {
            try {
                Jugador ganador = mesa.obtenerGanador();
                vista.mostrarGanador(ganador);
                vista.mostrarMensajeEnEtiqueta("El ganador es " + ganador.getNombreCompleto());
            } catch (PokerException ex) {
                Logger.getLogger(Controlador_JugadorPoker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    private Jugador obtenerGanador() throws PokerException {
        return mesa.obtenerGanador(); // Suponiendo que `obtenerGanador` está implementado en la clase `MesaPoker`.
    }

    public String obtenerTitulo() {
        return jugador.getNombreCompleto() + " • Mesa Nº " + mesa.getId();
    }
}

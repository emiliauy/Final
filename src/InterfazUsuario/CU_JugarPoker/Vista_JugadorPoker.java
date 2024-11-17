package InterfazUsuario.CU_JugarPoker;

import Modelo.Carta;
import Modelo.EventosGenerales.Eventos;
import Modelo.Jugador;
import java.util.ArrayList;

/**
 *
 * @author Emilia
 */
public interface Vista_JugadorPoker {
    
    public void mostrarCartas(ArrayList<Carta> cartas);
    
    public void actualizarVista(Eventos evento, Object data);

    public void actualizarPozo(double totalPozo);

    public void mostrarGanador(Jugador ganador);

    public void mostrarMensajeEnEtiqueta(String mensaje);

    public void mostrarMensaje(String mensaje);

    public void mostrarError(String mensaje);

    public void mostrarApuestaActual(double apuesta);

    public void finalizarRonda();

    public void mesasAbiertas();

    public void salir();

    public void habilitarBotones(boolean habilitado);

    public void actualizarEstadoJugadores(Object evento, ArrayList<Jugador> jugadoresMesa);

    public void actualizarJugadores(ArrayList<Jugador> jugadoresMesa);
    

}

package InterfazUsuario.CU_IngresarAunaMesa;

import Modelo.EventosGenerales.Eventos;
import Modelo.Fachada;
import Modelo.Jugador;
import Modelo.MesaPoker;
import Modelo.PokerException;
import Modelo.Sesion;
import java.util.ArrayList;
import observador.Observable;
import observador.Observador;

/**
 *
 * @author Emilia
 */
public class Controlador_MesasAbiertas implements Observador {

    private Vista_MesasAbiertas vista;
    private Jugador jugador;
    private ArrayList<MesaPoker> mesasAbiertas = new ArrayList<>();
    private Fachada fachada;
    private Sesion sesion;

    public Controlador_MesasAbiertas(Vista_MesasAbiertas vista, Jugador jugador, Sesion sesion) {
        this.vista = vista;
        this.jugador = jugador;
        this.fachada = Fachada.getInstancia();
        this.sesion = sesion;

        inicializarVista();

    }

    public String obtenerTituloVentana() {
        return "Jugador: " + jugador.getNombreCompleto() + " Saldo: $" + jugador.getSaldo();
    }

    private void inicializarVista() {
        actualizarMesasAbiertas();
        fachada.agregarObservador(this);
    }

    private void actualizarMesasAbiertas() {
        mesasAbiertas = Fachada.getInstancia().getMesasPokerAbiertas();
        if (mesasAbiertas == null) {
            mesasAbiertas = new ArrayList<>(); // Inicializa como lista vacía si es null
        }
        vista.mostrarMesasAbiertas(mesasAbiertas); // Actualiza la ventana
    }

    public void ingresarMesaSeleccionada(int idMesa) {
        try {
            MesaPoker mesa = fachada.obtenerMesaPorId(idMesa);
            fachada.agregarJugadorAMesa(jugador, idMesa);
            vista.mostrarMensaje(jugador.getNombreCompleto() + " : Te has unido a la mesa No " + idMesa);
            vista.cerrarVentanaSinLogout();
            vista.jugarAlPoker(mesa);
        } catch (PokerException e) {
            vista.mostrarError("Error al ingresar a la mesa: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
        if (evento.equals(Eventos.CAMBIA_CANTIDAD_JUGADORES) || evento.equals(Eventos.INICIA_JUEGO)) {
            actualizarMesasAbiertas();
            vista.mostrarMensaje("Un jugador se ha unido a una mesa.");

        } else if (evento.equals(Eventos.MESA_CREADA)) {
            actualizarMesasAbiertas();
            vista.mostrarMensaje("Se ha creado una nueva mesa.");
        }
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void salir() {
        Fachada.getInstancia().logout(sesion);

    }
}

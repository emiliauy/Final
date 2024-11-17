package InterfazUsuario.CU_AdministrarMesas;

import Modelo.Administrador;
import Modelo.Fachada;
import Modelo.ManoRonda;
import Modelo.MesaPoker;
import observador.Observable;
import observador.Observador;
import java.util.ArrayList;
import Modelo.EventosGenerales.Eventos;
import Modelo.PokerException;
import Modelo.Sesion;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Emilia
 */
public class Controlador_AdministrarMesas implements Observador {

    private Vista_AdministrarMesas vista;
    private Administrador usuario;
    private Sesion sesion;

    public Controlador_AdministrarMesas(Vista_AdministrarMesas vista, Sesion sesion) throws PokerException {
        this.vista = vista;
        this.sesion = sesion;
        this.usuario = (Administrador) sesion.getUsuario();
        configurarObservadoresMesas();
        inicializarVista();
    }

    //EVENTOS DEL USUARIO
    private void inicializarVista() throws PokerException {
        Fachada.getInstancia().agregarObservador(this);
        actualizarVistaMesas();
        actualizarTotalRecaudado();
    }

    private ArrayList<MesaPoker> obtenerMesas() {
        return Fachada.getInstancia().getMesas();
    }

    public void verManosDeMesa(int op) {
        ArrayList<MesaPoker> mesas = Fachada.getInstancia().getMesas();
        if (op >= 0 && op < mesas.size()) {
            MesaPoker mesaSeleccionada = mesas.get(op);
            cargarManos(mesaSeleccionada);
        } else {
            vista.mostrarError("Debe seleccionar una mesa válida para ver las manos.");
        }
    }

    public void cargarManos(MesaPoker mesa) {
        ArrayList<ManoRonda> manos = mesa.getManos();
        vista.mostrarManos(manos);
    }

    public void crearNuevaMesa() {
        vista.mostrarVentanaCrearMesa(usuario);
    }

    public void salir() {
        Fachada.getInstancia().quitarObservador(this);
        Fachada.getInstancia().logout(sesion);

    }

    public String obtenerNombreCompletoAdministrador() {
        return usuario.getNombreCompleto();
    }

    public void actualizarTotalRecaudado() throws PokerException {
        double total = Fachada.getInstancia().totalRecaudado();
        vista.cargarTotalRecaudado(total);
    }

    private void actualizarVistaMesas() {
        ArrayList<MesaPoker> mesas = obtenerMesas();
        vista.mostrarMesas(mesas);
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
        if (evento.equals(Eventos.MESA_CREADA)) {
            actualizarVistaMesas();
            try {
                actualizarTotalRecaudado();
            } catch (PokerException ex) {
                Logger.getLogger(Controlador_AdministrarMesas.class.getName()).log(Level.SEVERE, null, ex);
            }
            vista.mostrarMensaje("Se ha creado una nueva mesa.");
        } else if (evento.equals(Eventos.CAMBIA_CANTIDAD_JUGADORES)) {
            actualizarVistaMesas();
            try {
                actualizarTotalRecaudado();
            } catch (PokerException ex) {
                Logger.getLogger(Controlador_AdministrarMesas.class.getName()).log(Level.SEVERE, null, ex);
            }
            vista.mostrarMensaje("Ingresó un jugador a la mesa.");
        } else if (evento.equals(Eventos.CAMBIA_POZO)) {
            vista.mostrarMensaje("El pozo ha aumentado.");
        } else if (evento.equals(Eventos.INICIA_JUEGO)) {
            vista.mostrarMensaje("La partida ha comenzado.");
        } else if (evento.equals(Eventos.FINALIZA_JUEGO)) {
            vista.mostrarMensaje("La partida ha finalizado.");
        }
    }

    private void configurarObservadoresMesas() {
        ArrayList<MesaPoker> mesas = Fachada.getInstancia().getMesas();
        for (MesaPoker mesa : mesas) {
            mesa.agregarObservador(this);
        }
    }
}

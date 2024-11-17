package InterfazUsuario.CU_CrearMesa;

import Modelo.Administrador;
import Modelo.EventosGenerales.Eventos;
import Modelo.Fachada;
import Modelo.PokerException;
import observador.Observable;
import observador.Observador;

/**
 *
 * @author Emilia
 */
public class Controlador_CrearMesa implements Observador {

    private Vista_CrearMesa vista;

    public Controlador_CrearMesa(Vista_CrearMesa vista, Administrador usuario) {
        this.vista = vista;
        inicializarVista();
    }

    //EVENTOS DEL USUARIO
    public void crearMesa(int jugadoresRequeridos, double apuestaBase, double comision) {
        try {
            Fachada.getInstancia().crearMesaPoker(jugadoresRequeridos, comision, comision);
            vista.mostrarMensaje("Mesa creada con éxitooooooooo.");
        } catch (PokerException e) {
            vista.mostrarError(e.getMessage());
        }
    }

    //EVENTOS DEL MODELO
    private void inicializarVista() {
        Fachada.getInstancia().agregarObservador(this);
        vista.prepararProximoIngreso();
    }

    public void salir() {
        Fachada.getInstancia().quitarObservador(this);
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
        if (evento.equals(Eventos.MESA_CREADA)) {
            vista.mostrarMensaje("Mesa creada con éxito y actualizada en el sistema.");
            vista.prepararProximoIngreso();
        } else if (evento.equals("errorMesa")) {
            vista.mostrarError("Ocurrió un error al intentar crear la mesa.");
        }
    }
}

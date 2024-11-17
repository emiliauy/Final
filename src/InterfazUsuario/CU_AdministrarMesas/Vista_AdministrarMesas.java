package InterfazUsuario.CU_AdministrarMesas;

import Modelo.Administrador;
import Modelo.ManoRonda;
import Modelo.MesaPoker;
import java.util.ArrayList;

/**
 *
 * @author Emilia
 */
public interface Vista_AdministrarMesas {

    public void cargarTotalRecaudado(double totalRecaudadoMesas);

    public void mostrarMesas(ArrayList<MesaPoker> mesasPoker);

    public void mostrarManos(ArrayList<ManoRonda> manos);

    public void mostrarMensaje(String mensaje);

    public void mostrarError(String message);

    public void mostrarVentanaCrearMesa(Administrador usuario);
    
    public void salir();

}

package InterfazUsuario.CU_IngresarAunaMesa;

import Modelo.MesaPoker;
import java.util.ArrayList;

/**
 *
 * @author Emilia
 */
public interface Vista_MesasAbiertas {

    public void mostrarMesasAbiertas(ArrayList<MesaPoker> mesasPoker);

    public void mostrarMensaje(String mensaje);

    public void mostrarError(String message);

    public void jugarAlPoker(MesaPoker mesa);

    public void salir();

    public void cerrarVentanaSinLogout();

}

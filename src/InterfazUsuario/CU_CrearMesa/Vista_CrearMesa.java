package InterfazUsuario.CU_CrearMesa;

/**
 *
 * @author Emilia
 */
public interface Vista_CrearMesa {

    public void mostrarError(String mensaje);

    public void mostrarMensaje(String mensaje);

    public void prepararProximoIngreso();

    public int getJugadoresRequeridos();

    public double getApuestaBase();

    public double getComision();
    
    public void salir();
}

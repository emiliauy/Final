package Modelo;

/**
 *
 * @author Emilia
 */
public abstract class Usuario {

    private String cedula;
    private String password;
    private String nombreCompleto;
    private boolean logueado;

    public Usuario(String cedula, String password, String nombreCompleto) {
        this.cedula = cedula;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.logueado = false;
    }

    public String getCedula() {
        return cedula;
    }

    public String getPassword() {
        return password;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public boolean isLogueado() {
        return logueado;
    }

    public void setLogueado(boolean logueado) {
        this.logueado = logueado;
    }
    
     public boolean validarCredenciales(String cedula, String password) {
        return this.cedula.equals(cedula)&& this.password.equals(password);

    }

    @Override
    public String toString() {
        return "Usuario{" + "cedula=" + cedula + ", password=" + password + ", nombreCompleto=" + nombreCompleto + ", logueado=" + logueado + '}';
    }

   
}

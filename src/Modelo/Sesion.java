package Modelo;

import java.util.Date;

/**
 *
 * @author Emilia
 */
public class Sesion {

    private Date fechaIngreso = new Date();
    private Usuario usuario;

    public Sesion(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public String toString() {
        return "Sesion{"
                + "fechaIngreso=" + fechaIngreso
                + ", usuario=" + usuario.getNombreCompleto()
                + // O cualquier información relevante del usuario
                '}';
    }
}

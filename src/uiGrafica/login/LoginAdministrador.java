package uiGrafica.login;

import uiGrafica.administrador.AdministracionMesas;
import java.awt.Frame;
import Modelo.Fachada;
import Modelo.PokerException;
import Modelo.Sesion;
import Modelo.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emilia
 */
public class LoginAdministrador extends LoginAbstracto{
    
    public LoginAdministrador(Frame parent, boolean modal) {
        super(parent, modal, "Ingreso Administrador");
    }

    @Override
    public Usuario llamarLogin(String cedula, String password) throws PokerException {
        return Fachada.getInstancia().loginAdministrador(cedula, password);
    }

    @Override
    public void proximoCasoUso(Usuario usuario) {
        Sesion sesion= new Sesion(usuario);
        try {
            new AdministracionMesas(null,false, sesion).setVisible(true);
        } catch (PokerException ex) {
            Logger.getLogger(LoginAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
}

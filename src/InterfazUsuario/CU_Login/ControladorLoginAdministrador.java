/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InterfazUsuario.CU_Login;

import Modelo.Fachada;
import Modelo.PokerException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emilia
 */
public class ControladorLoginAdministrador extends ControladorLogin{

    public ControladorLoginAdministrador(VistaLogin vista) {
        super(vista);
    }

    @Override
    public Object llamarLogin(String usuario, String pwd) {
        try {
            return Fachada.getInstancia().loginAdministrador(usuario, pwd);
        } catch (PokerException ex) {
            Logger.getLogger(ControladorLoginAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}

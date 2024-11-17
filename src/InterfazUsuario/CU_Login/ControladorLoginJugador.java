package InterfazUsuario.CU_Login;

import Modelo.Fachada;
import Modelo.PokerException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emilia
 */
public class ControladorLoginJugador extends ControladorLogin{

    public ControladorLoginJugador(VistaLogin vista) {
        super(vista);
    }

    @Override
    public Object llamarLogin(String usuario, String pwd) {
        try {
            return Fachada.getInstancia().loginJugador(usuario, pwd);
        } catch (PokerException ex) {
            Logger.getLogger(ControladorLoginJugador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

package uiGrafica.login;

import uiGrafica.jugador.MesasAbiertas;
import java.awt.Frame;
import Modelo.Fachada;
import Modelo.Jugador;
import Modelo.PokerException;
import Modelo.Sesion;
import Modelo.Usuario;

/**
 *
 * @author Emilia
 */
public class LoginJugador extends LoginAbstracto {

    public LoginJugador(Frame parent, boolean modal) {
        super(parent, modal, "Ingreso Jugador");
    }

    @Override
    public Usuario llamarLogin(String cedula, String password) throws PokerException {
        return Fachada.getInstancia().loginJugador(cedula, password);
    }

    @Override
    public void proximoCasoUso(Usuario usuario) {
        Sesion sesion = new Sesion(usuario);

        new MesasAbiertas(null, false, sesion).setVisible(true);
    }

}

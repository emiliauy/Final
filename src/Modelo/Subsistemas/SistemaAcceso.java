package Modelo.Subsistemas;

import java.util.ArrayList;
import Modelo.Administrador;
import Modelo.EventosGenerales;
import Modelo.Fachada;
import Modelo.Jugador;
import Modelo.PokerException;
import Modelo.Sesion;
import Modelo.Usuario;

/**
 *
 * @author Emilia
 */
public class SistemaAcceso {

    private ArrayList<Jugador> usuariosJugador = new ArrayList();
    private ArrayList<Administrador> usuariosAdministrador = new ArrayList();
    private ArrayList<Sesion> sesiones = new ArrayList();

    public void agregarUsarioPoker(String cedula, String password, String nombreCompleto, double saldoInicial) throws PokerException {
        usuariosJugador.add(new Jugador(cedula, password, nombreCompleto, saldoInicial));
    }

    public void agregarAdministrador(String cedula, String password, String nombreCompleto) {
        usuariosAdministrador.add(new Administrador(cedula, password, nombreCompleto));
    }

    public Administrador loginAdministrador(String ci, String pwd) throws PokerException {
        return (Administrador) login(ci, pwd, usuariosAdministrador);
    }

    public Jugador loginJugador(String ci, String pwd) throws PokerException {
        return (Jugador) login(ci, pwd, usuariosJugador);
    }

    private Usuario login(String ci, String pwd, ArrayList<? extends Usuario> listaUsuarios) throws PokerException {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getCedula().equals(ci)) {
                if (usuario.validarCredenciales(ci, pwd)) {
                    if (sesionActiva(usuario)) {
                        throw new PokerException("Acceso denegado. El usuario ya está logueado.");
                    }
                    iniciarSesion(usuario);
                    return usuario;
                } else {
                    throw new PokerException("Credenciales incorrectas.");
                }
            }
        }
        throw new PokerException("Credenciales incorrectas.");
    }

    public void logout(Sesion sesion) {
        if (sesiones.removeIf(s -> s.getUsuario().equals(sesion.getUsuario()))) {
            Fachada.getInstancia().avisar(EventosGenerales.Eventos.CAMBIO_LISTA_SESIONES);
            System.out.println("se elimino la sesion");

        } else {
            System.out.println("La sesión no se encontró en la lista activa.");
        }
    }

    private boolean sesionActiva(Usuario usuario) {
        return sesiones.stream().anyMatch(sesion -> sesion.getUsuario().equals(usuario));
    }

    private void iniciarSesion(Usuario usuario) {
        Sesion sesion = new Sesion(usuario);
        sesiones.add(sesion);
        Fachada.getInstancia().avisar(EventosGenerales.Eventos.CAMBIO_LISTA_SESIONES);
    }

    public ArrayList<Sesion> getSesiones() {
        return sesiones;
    }
}

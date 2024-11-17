package Modelo;

import Modelo.Subsistemas.SistemaJuego;
import Modelo.Subsistemas.SistemaAcceso;
import observador.Observable;
import java.util.ArrayList;

/**
 *
 * @author Emilia
 */
public class Fachada extends Observable {

    private static Fachada instancia = new Fachada();

    private SistemaAcceso sAcceso = new SistemaAcceso();
    private SistemaJuego sJuego = new SistemaJuego();

    public static Fachada getInstancia() {
        return instancia;
    }

    private Fachada() {
    }

    //DELEGACIONES SISTEMA JUEGO
    public MesaPoker crearMesaPoker(int jugadoresRequeridos, double luz, double porcentajeComision) throws PokerException {
        return sJuego.crearMesaPoker(jugadoresRequeridos, luz, porcentajeComision);
    }

    public void agregarJugadorAMesa(Jugador jugador, int idMesa) throws PokerException {
        sJuego.agregarJugadorAMesa(jugador, idMesa);
    }

    public void iniciarManoEnMesa(int idMesa) throws PokerException {
        sJuego.iniciarManoEnMesa(idMesa);
    }

    public void finalizarManoEnMesa(int idMesa) throws PokerException {
        sJuego.finalizarManoEnMesa(idMesa);
    }

    public void cerrarMesa(int idMesa) throws PokerException {
        sJuego.cerrarMesa(idMesa);
    }

    public ArrayList<MesaPoker> getMesas() {
        return sJuego.getMesas();
    }

    public double totalRecaudado() throws PokerException {
        return sJuego.totalRecaudado();
    }

    public ArrayList<MesaPoker> getMesasPokerAbiertas() {
        return sJuego.getMesasPokerAbiertas();
    }

    public MesaPoker obtenerMesaPorId(int idMesa) {
        return sJuego.obtenerMesaPorId(idMesa);
    }

    public void iniciarApuesta(int idMesa, Jugador jugador, double monto) throws PokerException {
        sJuego.iniciarApuesta(idMesa, jugador, monto);
    }
    
    public void pagarApuesta(int idMesa, Jugador jugador) throws PokerException {
    sJuego.pagarApuesta(idMesa, jugador);
}

    public void pasarApuesta(int idMesa, Jugador jugador) throws PokerException {
        sJuego.pasarApuesta(idMesa, jugador);
    }

    //DELEGACIONES SISTEMA ACCESO
    public ArrayList<Jugador> getUsuarios() {
        return sJuego.getUsuarios();
    }

    public void agregarUsarioPoker(String cedula, String password, String nombreCompleto, double saldoInicial) throws PokerException {
        sAcceso.agregarUsarioPoker(cedula, password, nombreCompleto, saldoInicial);
    }

    public void agregarAdministrador(String cedula, String password, String nombreCompleto) {
        sAcceso.agregarAdministrador(cedula, password, nombreCompleto);
    }

    public Administrador loginAdministrador(String ci, String pwd) throws PokerException {
        return sAcceso.loginAdministrador(ci, pwd);
    }

    public Jugador loginJugador(String ci, String pwd) throws PokerException {
        return sAcceso.loginJugador(ci, pwd);
    }

    public void logout(Sesion sesion) {
        sAcceso.logout(sesion);
    }

    public ArrayList<Sesion> getSesiones() {
        return sAcceso.getSesiones();
    }

}

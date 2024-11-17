package Modelo;

import java.util.ArrayList;

/**
 *
 * @author Emilia
 */
public class ApuestaJugador {

    private double monto;
    private Jugador iniciador;
    private boolean activa;
    private ArrayList<Jugador> jugadoresQuePagaron = new ArrayList<>();

    public ApuestaJugador(double monto, Jugador iniciador) {
        this.monto = monto;
        this.iniciador = iniciador;
        this.activa = true;
        this.jugadoresQuePagaron.add(iniciador);
    }

    public void procesarPago(Jugador jugador) throws PokerException {
        if (!activa) {
            throw new PokerException("No se puede procesar una apuesta ya finalizada.");
        }
        if (jugadoresQuePagaron.contains(jugador)) {
            throw new PokerException("El jugador ya ha pagado esta apuesta.");
        }
        if (jugador.getSaldo() < monto) {
            throw new PokerException("Saldo insuficiente para realizar esta apuesta.");
        }
        jugadoresQuePagaron.add(jugador);
        System.out.println("Jugador va a pagar" + jugador.getNombreCompleto() + monto+ " Saldo " + jugador.getSaldo());
        jugador.decrementarSaldo(monto);
        System.out.println("Jugador pago" + jugador.getNombreCompleto() + monto + " Saldo " + jugador.getSaldo());
    }

   

    public void finalizar() {
        activa = false;
    }

    public double getMonto() {
        return monto;
    }

    public Jugador getIniciador() {
        return iniciador;
    }

    public ArrayList<Jugador> getJugadoresQuePagaron() {
        return jugadoresQuePagaron;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setIniciador(Jugador iniciador) {
        this.iniciador = iniciador;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public boolean esActiva() {
        return activa;
    }

    public boolean esIniciador(Jugador jugador) {
        return jugador.equals(iniciador);
    }

    public void registrarApuesta(double monto, Jugador jugador) {
        setMonto(monto);
        setActiva(true);
        setIniciador(jugador);
    }

    public boolean jugadorPago(Jugador jugador) {
        return jugadoresQuePagaron.contains(jugador);
    }

    public boolean todosPagaron(ArrayList<Jugador> jugadores) {
        return jugadoresQuePagaron.containsAll(jugadores);
    }
}

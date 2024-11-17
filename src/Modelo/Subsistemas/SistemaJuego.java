package Modelo.Subsistemas;

import Modelo.*;
import static Modelo.EstadoMesa.ABIERTA;
import Modelo.EventosGenerales.Eventos;
import Modelo.Figuras.Figura;
import Modelo.Figuras.SinFigura;
import observador.Observable;
import java.util.ArrayList;

public class SistemaJuego extends Observable {

    private ArrayList<MesaPoker> mesas = new ArrayList<>();
    private ArrayList<Jugador> usuarios;

    public MesaPoker crearMesaPoker(int jugadoresRequeridos, double luz, double porcentajeComision) throws PokerException {
        MesaPoker nuevaMesa = new MesaPoker(jugadoresRequeridos, luz, porcentajeComision);
        mesas.add(nuevaMesa);
        avisar(Eventos.MESA_CREADA);
        return nuevaMesa;
    }

    public void iniciarMesa(int idMesa) throws PokerException {
        MesaPoker mesa = obtenerMesaPorId(idMesa);
        if (mesa == null) {
            throw new PokerException("La mesa no existe.");
        }
        mesa.iniciarMesa();
        avisar(Eventos.INICIA_JUEGO);
    }

    public void finalizarMesa(int idMesa) throws PokerException {
        MesaPoker mesa = obtenerMesaPorId(idMesa);
        if (mesa == null) {
            throw new PokerException("La mesa no existe.");
        }
        mesa.finalizarMesa();
        avisar(Eventos.FINALIZA_JUEGO);
    }

    public void iniciarApuesta(int idMesa, Jugador jugador, double monto) throws PokerException {
        MesaPoker mesa = obtenerMesaPorId(idMesa);
        mesa.getManoActual().iniciarApuesta(monto, jugador);
    }

    public void pagarApuesta(int idMesa, Jugador jugador) throws PokerException {
        MesaPoker mesa = obtenerMesaPorId(idMesa);
        mesa.getManoActual().pagarApuesta(jugador);
       
    }

    public void pasarApuesta(int idMesa, Jugador jugador) throws PokerException {
        MesaPoker mesa = obtenerMesaPorId(idMesa);
        mesa.getManoActual().pasarApuesta(jugador, mesa);
    }

    public void agregarJugadorAMesa(Jugador jugador, int idMesa) throws PokerException {
        MesaPoker mesa = obtenerMesaPorId(idMesa);
        if (mesa == null) {
            throw new PokerException("La mesa no existe.");
        }
        mesa.agregarJugador(jugador); // Delegado a la mesa
    }

    public void iniciarManoEnMesa(int idMesa) throws PokerException {
        MesaPoker mesa = obtenerMesaPorId(idMesa);
        if (mesa == null) {
            throw new PokerException("La mesa no existe.");
        }
        mesa.getManoActual().iniciarMano();
        avisar(Eventos.INICIA_MANO);
    }

    public void finalizarManoEnMesa(int idMesa) throws PokerException {
        MesaPoker mesa = obtenerMesaPorId(idMesa);
        if (mesa == null) {
            throw new PokerException("La mesa no existe.");
        }
        mesa.getManoActual().finalizarMano();
        avisar(Eventos.FINALIZA_MANO);
    }

    public Figura evaluarFigura(ArrayList<Carta> cartas) {
        return Figura.obtenerFiguraGanadora(cartas);
    }

    public int compararFiguras(Figura figura1, Figura figura2) {
        return figura1.compararCon(figura2);
    }

    public void determinarGanador(MesaPoker mesa) throws PokerException {
        if (mesa.getManoActual() == null) {
            throw new PokerException("No hay una mano activa en esta mesa.");
        }

        ArrayList<Jugador> jugadores = mesa.getJugadoresMesa();
        Jugador ganador = null;
        Figura mejorFigura = new SinFigura();

        for (Jugador jugador : jugadores) {
            Figura figura = evaluarFigura(jugador.getCartas());
            if (compararFiguras(figura, mejorFigura) > 0) {
                mejorFigura = figura;
                ganador = jugador;
            }
        }

        if (ganador != null) {
            // Actualiza el saldo del ganador y finaliza la mano
            mesa.getManoActual().finalizarMano();
        }
    }

    public void cerrarMesa(int idMesa) throws PokerException {
        MesaPoker mesa = obtenerMesaPorId(idMesa);
        if (mesa == null) {
            throw new PokerException("La mesa no existe.");
        }
        mesa.finalizarMesa();
        mesas.remove(mesa);
        avisar(Eventos.FINALIZA_JUEGO);
    }

    public MesaPoker obtenerMesaPorId(int idMesa) {
        for (MesaPoker mesa : mesas) {
            if (mesa.getId() == idMesa) {
                return mesa;
            }
        }
        return null;
    }

    public double totalRecaudado() throws PokerException {
        double total = 0;

        for (MesaPoker mesa : mesas) {
            if (mesa.getManos().size() != 0) {
                total += mesa.getManoActual().getPozo().getTotalRecaudado();
            }
        }
        return total;
    }

    public ArrayList<MesaPoker> getMesas() {
        return mesas;
    }

    public ArrayList<Jugador> getUsuarios() {
        return usuarios;
    }

    public ArrayList<MesaPoker> getMesasPokerAbiertas() {
        ArrayList<MesaPoker> mesasAbiertas = new ArrayList<>();
        for (MesaPoker mesa : mesas) {
            if (mesa.getEstado().equals(ABIERTA)) {
                mesasAbiertas.add(mesa);
            }
        }
        return mesasAbiertas;
    }
}

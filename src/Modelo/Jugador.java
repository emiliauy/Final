package Modelo;

import Modelo.Figuras.Figura;
import java.util.ArrayList;

public class Jugador extends Usuario {

    private double saldo;
    private ArrayList<Carta> cartas;

    public Jugador(String cedula, String password, String nombreCompleto, double saldoInicial) throws PokerException {
        super(cedula, password, nombreCompleto);
        if (saldoInicial < 0) {
            throw new PokerException("El saldo inicial debe ser positivo.");
        }
        this.saldo = saldoInicial;
        this.cartas = new ArrayList<>();
    }

    public void setCartas(ArrayList<Carta> nuevasCartas) throws PokerException {
        if (nuevasCartas == null || nuevasCartas.size() != 5) {
            throw new PokerException("La mano debe contener exactamente 5 cartas.");
        }
        this.cartas = new ArrayList<>(nuevasCartas);
    }

    
    public ArrayList<Carta> getCartas() {
        return new ArrayList<>(cartas);
    }

    public void limpiarCartas() {
        cartas.clear();
    }

    public void cambiarCartas(ArrayList<Integer> posiciones, ArrayList<Carta> nuevasCartas) throws PokerException {
        if (posiciones.size() != nuevasCartas.size()) {
            throw new PokerException("El número de posiciones y nuevas cartas no coincide.");
        }
        for (int i = 0; i < posiciones.size(); i++) {
            int posicion = posiciones.get(i);
            if (posicion < 0 || posicion >= cartas.size()) {
                throw new PokerException("Posición de carta inválida: " + posicion);
            }
            cartas.set(posicion, nuevasCartas.get(i));
        }
    }

    public double getSaldo() {
        return saldo;
    }

    public void ajustarSaldo(double cantidad) throws PokerException {
        if (saldo + cantidad < 0) {
            throw new PokerException("No hay suficiente saldo para realizar esta operación.");
        }
        saldo += cantidad;
    }

    public void decrementarSaldo(double monto) throws PokerException {
        ajustarSaldo(-monto);
    }

    public void incrementarSaldo(double monto) {
        saldo += monto;
    }

    public void puedePagarApuesta(double monto) throws PokerException {
        if (monto > saldo) {
            throw new PokerException("Saldo insuficiente para pagar esta apuesta.");
        }
        decrementarSaldo(monto);
    }

    public Figura evaluarFigura() {
        return Figura.obtenerFiguraGanadora(cartas);
    }

    @Override
    public String toString() {
        return "Jugador{nombre=" + getNombreCompleto() + ", saldo=" + saldo + '}';
    }
}

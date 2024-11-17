package Modelo;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Emilia
 */
public class Mazo {

    private ArrayList<Carta> cartas = new ArrayList<>();

    public Mazo() {
        inicializarMazo();
    }

    private void inicializarMazo() {
        String[] valores = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        for (Palo palo : Palo.values()) {
            for (String valor : valores) {
                cartas.add(new Carta(palo, valor));
            }
        }
        barajar();
    }

    public void barajar() {
        Collections.shuffle(cartas);
    }

    public Carta sacarCarta() {
        if (cartas.isEmpty()) {
            throw new IllegalStateException("El mazo no tiene mas cartas..");
        }
        return cartas.remove(cartas.size() - 1);
    }

    //reparte las cartas y retorna una exc si no hay cartas suficientes
    public ArrayList<Carta> repartirCartas(int cantidad) throws IllegalArgumentException {
        if (cantidad > cartas.size()) {
            throw new IllegalArgumentException("No alcanzan las cartasdel mazo para repartir.");
        }

        ArrayList<Carta> cartasRepartidas = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            cartasRepartidas.add(sacarCarta()); //la saca del mazo y la agrega aca
        }
        return cartasRepartidas;
    }

    @Override
    public String toString() {
        return "Mazo{" + "cartas=" + cartas + '}';
    }

}

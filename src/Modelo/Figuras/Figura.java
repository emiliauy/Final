package Modelo.Figuras;

import Modelo.Carta;
import java.util.ArrayList;

/**
 *
 * @author Emilia
 */
public abstract class Figura {

    private String nombre;
    private int nivel;  //poker 4, escalera 3  ,Pierna 2 y par 1 y sinfigura 0
    private ArrayList<Carta> cartasFigura;

    public Figura(String nombre, int nivel) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.cartasFigura = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public ArrayList<Carta> getCartasFigura() {
        return cartasFigura;
    }

    public abstract boolean esFigura(ArrayList<Carta> cartas);

    public int compararCon(Figura otraFigura) {//comparo una figura con otra figura
        if (this.nivel != otraFigura.getNivel()) {
            return Integer.compare(this.nivel, otraFigura.getNivel());
        }
        return compararCartas(otraFigura);//cuando hay empate se comparan las cartas
    }

    public int compararCartas(Figura otraFigura) {
        ArrayList<Carta> cartasOtraFigura = otraFigura.getCartasFigura();

        for (int i = 0; i < cartasFigura.size(); i++) {
            int valorEsta = cartasFigura.get(i).getValorUnico();
            int valorOtra = cartasOtraFigura.get(i).getValorUnico();
            if (valorEsta != valorOtra) {
                return Integer.compare(valorEsta, valorOtra);
            }
        }
        return 0;
    }

    public static Figura obtenerFiguraGanadora(ArrayList<Carta> cartas) {
        for (Figura figura : obtenerFigurasEnOrden()) {
            if (figura.esFigura(cartas)) {
                return figura;//retorna la primera figura que encuentra
            }
        }
        return new SinFigura();// si no se encuentra ninguna figura retorna SinFigura
    }

    private static ArrayList<Figura> obtenerFigurasEnOrden() {
        ArrayList<Figura> figuras = new ArrayList<>();
        figuras.add(new Poker());
        figuras.add(new Escalera());
        figuras.add(new Pierna());
        figuras.add(new Par());
        figuras.add(new SinFigura());
        return figuras;
    }
}

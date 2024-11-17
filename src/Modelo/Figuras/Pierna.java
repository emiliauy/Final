package Modelo.Figuras;

import java.util.ArrayList;
import Modelo.Carta;

/**
 * @author Emilia
 */
public class Pierna extends Figura {

    // Constructor de Pierna
    public Pierna() {
        super("Pierna", 2);  //nivel de 2 de figuras
    }

    @Override
    public boolean esFigura(ArrayList<Carta> cartas) {
        if (cartas.size() < 3) {
            return false;
        }

        for (Carta carta : cartas) {
            int contador = 0;
            for (Carta otraCarta : cartas) {
                if (carta.getValorUnico() == otraCarta.getValorUnico()) {
                    contador++;
                }
            }
            // si hay 3 cartas con = valor califica como Pierna
            if (contador == 3) {
                this.getCartasFigura().clear();
                for (Carta c : cartas) {
                    if (c.getValorUnico() == carta.getValorUnico()) {
                        this.getCartasFigura().add(c);
                    }
                }
                return true;
            }
        }
        return false;
    }

    //compara esta figura Pierna con otra figura pierna
    public int compararCon(Figura otraFigura) {
        if (!this.getNombre().equals(otraFigura.getNombre())) {
            return Integer.compare(this.getNivel(), otraFigura.getNivel());
        }
        return compararCartas(otraFigura);
    }

    @Override
    public int compararCartas(Figura otraFigura) {
        ArrayList<Carta> cartasOtraFigura = otraFigura.getCartasFigura();

        for (int i = 0; i < this.getCartasFigura().size(); i++) {
            int valorEsta = getCartasFigura().get(i).getValorUnico();
            int valorOtra = cartasOtraFigura.get(i).getValorUnico();
            if (valorEsta != valorOtra) {
                return Integer.compare(valorEsta, valorOtra);
            }
        }
        return 0;
    }
}

package Modelo.Figuras;

import java.util.ArrayList;
import Modelo.Carta;

/**
 *
 * @author Emilia
 */
public class Par extends Figura {

    public Par() {
        super("Par", 1);//Par tiene nivel de 1 de figuras, es casi el mas bajo
    }

    @Override
    public boolean esFigura(ArrayList<Carta> cartas) {
        if (cartas.size() < 2) {
            return false;
        }

        for (Carta carta : cartas) {
            int contador = 0;
            for (Carta otraCarta : cartas) {
                if (carta.getValorUnico() == otraCarta.getValorUnico()) {
                    contador++;
                }
            }
            
            // si hay 2 cartas iguales etnonces es Par
            if (contador == 2) {
                this.getCartasFigura().clear();
                for (Carta c : cartas) {
                    if (c.getValorUnico() == carta.getValorUnico()) {
                        this.getCartasFigura().add(c);
                    }
                }
                return true;
            }
        }
        return false;  // No es un Par
    }

    @Override
    public int compararCon(Figura otraFigura) {
        // Comparación basada en el nombre de la figura
        if (!this.getNombre().equals(otraFigura.getNombre())) {
            return Integer.compare(this.getNivel(), otraFigura.getNivel());
        }
        // Si ambas figuras son Pares, compara las cartas para desempatar
        return compararCartas(otraFigura);
    }

    @Override
    public int compararCartas(Figura otraFigura) {
        ArrayList<Carta> cartasOtraFigura = otraFigura.getCartasFigura();

        for (int i = 0; i < this.getCartasFigura().size(); i++) {
            int valorEsta = this.getCartasFigura().get(i).getValorUnico();
            int valorOtra = cartasOtraFigura.get(i).getValorUnico();
            if (valorEsta != valorOtra) {
                return Integer.compare(valorEsta, valorOtra);
            }
        }
        return 0;
    }
}
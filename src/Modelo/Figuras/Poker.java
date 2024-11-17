package Modelo.Figuras;

import Modelo.Carta;
import java.util.ArrayList;

/**
 *
 * @author Emilia
 */
public class Poker extends Figura {

    public Poker() {
        super("Poker", 4);  //nivel 1 de figura, es el mas alto
    }

    @Override
    public boolean esFigura(ArrayList<Carta> cartas) {
        for (Carta carta : cartas) {
            int contador = 0;
            for (Carta otraCarta : cartas) {
                if (carta.getValorUnico() == otraCarta.getValorUnico()) {
                    contador++;
                }
            }
            if (contador == 4) {
                this.getCartasFigura().clear();
                this.getCartasFigura().addAll(cartas);
                return true;
            }
        }
        return false;
    }

    // cuando tengo dos pokers los comparo para saber cual vale mas
    @Override
    public int compararCon(Figura otraFigura) {
        if (!this.getNombre().equals(otraFigura.getNombre())) {
            return Integer.compare(this.getNivel(), otraFigura.getNivel());  // 1 A > B , -1 A < B y 0 A = B
        }
        return compararCartas(otraFigura);
    }

    @Override
    public int compararCartas(Figura otraFigura) {
        ArrayList<Carta> cartasOtraFigura = otraFigura.getCartasFigura();

        for (int i = 0; i < this.getCartasFigura().size(); i++) {
            int valorCartaEstaFigura = this.getCartasFigura().get(i).getValorUnico();
            int valorCartaOtraFigura = cartasOtraFigura.get(i).getValorUnico();
            if (valorCartaEstaFigura != valorCartaOtraFigura) {
                return Integer.compare(valorCartaEstaFigura, valorCartaOtraFigura);
            }
        }
        return 0; //todas las cartas tiene el mismo valor
    }
}

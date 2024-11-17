package Modelo.Figuras;

import java.util.ArrayList;
import Modelo.Carta;
import java.util.Collections;

/**
 *
 * @author Emilia
 */
public class Escalera extends Figura {

    public Escalera() {
        super("Escalera", 3);//nivel 3 de figuras
    }

    @Override
    public boolean esFigura(ArrayList<Carta> cartas) {
        if (cartas.size() != 5) {
            return false;
        }

        // las ordeno por valor ASC
        ArrayList<Integer> valores = new ArrayList<>();
        for (Carta carta : cartas) {
            valores.add(carta.getValorUnico());
        }
        Collections.sort(valores);

        for (int i = 0; i < valores.size() - 1; i++) {
            if (valores.get(i) + 1 != valores.get(i + 1)) {
                return false;  // no serian secuenciales
            }
        }

        this.getCartasFigura().clear();
        this.getCartasFigura().addAll(cartas);
        return true;
    }

    // comparo una escalera con otra escalera 
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
            int valorEsta = this.getCartasFigura().get(i).getValorUnico();
            int valorOtra = cartasOtraFigura.get(i).getValorUnico();
            if (valorEsta != valorOtra) {
                return Integer.compare(valorEsta, valorOtra);
            }
        }
        return 0;
    }
}
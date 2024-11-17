package Modelo.Figuras;

import java.util.ArrayList;
import Modelo.Carta;

/**
 *
 * @author Emilia
 */
public class SinFigura extends Figura {

    public SinFigura() {
        super("SinFigura", 0);  // SinFigura tiene el nivel  en figuras, es el peor
    }

    @Override
    public boolean esFigura(ArrayList<Carta> cartas) {
        // Siempre retorna true porque "SinFigura" representa una mano sin combinación
        this.getCartasFigura().clear();
        this.getCartasFigura().addAll(cartas);
        return true;
    }

    //comparto una figura SinFigura con otra 
    @Override
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

package inicio;

import uiGrafica.AplicacionPoker;
import Modelo.PokerException;

public class Inicio {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws PokerException {
        // TODO code application logic here
        DatosDePrueba.cargar();
        new AplicacionPoker().setVisible(true);


    }
}

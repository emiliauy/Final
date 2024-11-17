package inicio;

import Modelo.Carta;
import Modelo.Fachada;
import Modelo.Figuras.Poker;
import Modelo.Jugador;
import Modelo.Palo;
import Modelo.PokerException;
import java.util.ArrayList;

/**
 *
 * @author Emilia
 */
public class DatosDePrueba {

    public static void cargar() throws PokerException {
        Fachada logica = Fachada.getInstancia();

        // Crear usuarios y jugadores de prueba
        logica.agregarUsarioPoker("0", "0", "J0", 0);
        logica.agregarUsarioPoker("1", "1", "J1", 1000);
        logica.agregarUsarioPoker("2", "2", "J2", 2000);
        logica.agregarUsarioPoker("3", "3", "J3", 3000);
        logica.agregarUsarioPoker("4", "4", "J4", 4000);

        logica.agregarUsarioPoker("b", "a", "Bad Bunny", 20000);
        logica.agregarUsarioPoker("c", "a", "Cazzu", 1000000);
        logica.agregarUsarioPoker("d", "a", "Drake", 250000);
        logica.agregarUsarioPoker("j", "a", "Juan", 500);
        logica.agregarUsarioPoker("jd", "a", "Jorge Drexler", 5000);

        logica.agregarAdministrador("y", "a", "Young Miko");
        logica.agregarAdministrador("t", "a", "Tini");

        // Crear mesas de prueba directamente
        logica.crearMesaPoker(2, 1000, 5.5);
        logica.crearMesaPoker(3, 2000, 10.0);

        // Prueba de figuras en cartas
        Jugador j1 = new Jugador("10", "0", "J10", 10000);
        verificarFiguraPoker(j1);

        System.out.println("Datos de prueba cargados correctamente.");

        verificarCartas();
    }

    private static void verificarFiguraPoker(Jugador jugador) {
        ArrayList<Carta> cartasPokerJ1 = new ArrayList<>();
        cartasPokerJ1.add(new Carta(Palo.CORAZON, "9"));
        cartasPokerJ1.add(new Carta(Palo.DIAMANTE, "8"));
        cartasPokerJ1.add(new Carta(Palo.TREBOL, "9"));
        cartasPokerJ1.add(new Carta(Palo.PIQUE, "9"));
        cartasPokerJ1.add(new Carta(Palo.CORAZON, "2"));

        Poker pokerJ1 = new Poker();
        boolean esPokerJ1 = pokerJ1.esFigura(cartasPokerJ1);
        if (esPokerJ1) {
            System.out.println("El jugador " + jugador.getNombreCompleto() + " tiene un Poker: " + cartasPokerJ1);
        } else {
            System.out.println("El jugador " + jugador.getNombreCompleto() + " no tiene un Poker.");
        }
    }

    private static void verificarCartas() {
        Carta carta = new Carta(Palo.CORAZON, "A");
        System.out.println("Palo: " + carta.getPaloCarta()); // Esperado: "C"
        System.out.println("Valor: " + carta.getValorCarta()); // Esperado: 14
    }

}

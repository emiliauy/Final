package Modelo;

import panelCartasPoker.CartaPoker;

/**
 *
 * @author Emilia
 */
public class Carta implements CartaPoker {

    private Palo palo;
    private String valorString;
    private int valor; // valor en numeros de la carta del 2 al 14 si A=14)
    private int valorUnico; // valor unico de 1 a 52
    private boolean visible;

    public Carta(Palo palo, String numero) {
        this.palo = palo;
        this.valorString = numero;
        this.valor = pasarANumero(numero);
        this.valorUnico = asignarValorUnico();
        this.visible = true;

    }

    public String getNumero() {
        return valorString;
    }

    private int asignarValorUnico() {
        switch (palo) {
            case CORAZON:
                return valor;               // Corazones: 1 a 13
            case DIAMANTE:
                return valor + 13;          // Diamantes: 14 a 26
            case TREBOL:
                return valor + 26;          // Tréboles: 27 a 39
            case PIQUE:
                return valor + 39;          // Picas: 40 a 52
            default:
                return 0;
        }
    }

    public int pasarANumero(String numero) {
        switch (numero) {
            case "A":
                return 1;
            case "K":
                return 13;
            case "Q":
                return 12;
            case "J":
                return 11;
            case "10":
                return 10;
            case "9":
                return 9;
            case "8":
                return 8;
            case "7":
                return 7;
            case "6":
                return 6;
            case "5":
                return 5;
            case "4":
                return 4;
            case "3":
                return 3;
            case "2":
                return 2;
            default:
                return 0;
        }
    }

    public int getValorUnico() {
        return valorUnico;
    }

    @Override
    public int getValorCarta() {
        return valor;
    }

    @Override
    public String getPaloCarta() {
        switch (palo) {
            case CORAZON:
                return CORAZON;
            case DIAMANTE:
                return DIAMANTE;
            case TREBOL:
                return TREBOL;
            case PIQUE:
                return PIQUE;
            default:
                return "";
        }
    }

    @Override
    public boolean estaVisible() {
        return visible;
    }

    @Override
    public void setVisible(boolean b) {
        this.visible = b;
        System.out.println(toString());

    }

    @Override
    public String toString() {
        return "Carta{" + "palo=" + palo + ", valorString=" + valorString + ", valor=" + valor + ", valorCarta=" + valorUnico + '}';
    }

}

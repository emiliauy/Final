package observador;

import java.util.ArrayList;

/**
 *
 * @author Emilia
 */
public class Observable {

    private ArrayList<Observador> observadores = new ArrayList();

    public void agregarObservador(Observador obs) {
        if (!observadores.contains(obs)) {
            observadores.add(obs);
        }
    }

    public void quitarObservador(Observador obs) {
        observadores.remove(obs);
    }

    public void avisar(Object evento) {
        ArrayList<Observador> copia = new ArrayList(observadores);
        for (Observador obs : copia) {
            obs.actualizar(evento, this);
        }
    }

    public boolean tieneObservador(Observador obs) {
        return observadores.contains(obs);
    }

}

package emergencias;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class ColaPrioridadEmergencias {     /** Envoltorio interno que combina la emergencia, su prioridad y un
 *  contador de secuencia para desempatar respetando el orden de llegada. */
private static class Entrada implements Comparable<Entrada> {
    Emergencia emergencia;
    int prioridad;
    long secuencia;

    Entrada(Emergencia emergencia, int prioridad, long secuencia) {
        this.emergencia = emergencia;
        this.prioridad = prioridad;
        this.secuencia = secuencia;
    }

    @Override
    public int compareTo(Entrada otra) {
        if (this.prioridad != otra.prioridad) {
            return Integer.compare(this.prioridad, otra.prioridad);
        }
        return Long.compare(this.secuencia, otra.secuencia);
    }
}

    private PriorityQueue<Entrada> heap;
    private long contador;

    public ColaPrioridadEmergencias() {
        this.heap = new PriorityQueue<>();
        this.contador = 0;
    }

    public void encolar(Emergencia emergencia, int prioridad) {
        heap.add(new Entrada(emergencia, prioridad, contador++));
    }

    public Emergencia desencolar() {
        Entrada entrada = heap.poll();
        return (entrada == null) ? null : entrada.emergencia;
    }

    public Emergencia verProximo() {
        Entrada entrada = heap.peek();
        return (entrada == null) ? null : entrada.emergencia;
    }

    public boolean estaVacia() {
        return heap.isEmpty();
    }

    public int tamanio() {
        return heap.size();
    }

    /** Devuelve las emergencias ordenadas por prioridad sin modificar la cola. */
    public List<Emergencia> listar() {
        List<Entrada> copia = new ArrayList<>(heap);
        copia.sort(null);
        List<Emergencia> resultado = new ArrayList<>();
        for (Entrada e : copia) {
            resultado.add(e.emergencia);
        }
        return resultado;
    }
}


package emergencias;

import java.util.ArrayList;
import java.util.List;

/*
 * Cola con Prioridad
 * Menor numero de gravedad = mayor prioridad (1=CRITICA es la mas urgente).
 * El arreglo se mantiene siempre ordenado de menor a mayor gravedad,
 * En caso de igual gravedad, se respeta el orden de llegada (FIFO).
 */
public class ColaPrioridadEmergencias {

    private static class Entrada {
        Emergencia emergencia;
        int prioridad;
        int secuencia; // para desempatar por orden de llegada

        Entrada(Emergencia emergencia, int prioridad, int secuencia) {
            this.emergencia = emergencia;
            this.prioridad  = prioridad;
            this.secuencia  = secuencia;
        }
    }

    private static final int MAX = 100;

    private Entrada[] cola;
    private int cantidad;
    private int contador; // numero de secuencia creciente

    public ColaPrioridadEmergencias() {
        this.cola     = new Entrada[MAX];
        this.cantidad = 0;
        this.contador = 0;
    }

    public boolean estaVacia() {
        return cantidad == 0;
    }

    public boolean estaLlena() {
        return cantidad == MAX;
    }

    /*
     * Inserta la emergencia manteniendo el arreglo ordenado.
     * Desplaza hacia la derecha los elementos de menor prioridad
     * (mayor numero de gravedad) para hacer lugar al nuevo.
     * Si dos emergencias tienen la misma gravedad, la que llego
     * antes queda primero (orden de secuencia ascendente).
     */
    public boolean encolar(Emergencia emergencia, int prioridad) {
        if (estaLlena()) {
            return false;
        }

        Entrada nueva = new Entrada(emergencia, prioridad, contador++);

        // Busca la posicion correcta y desplaza hacia la derecha
        int i = cantidad - 1;
        while (i >= 0 && debeIrDespues(cola[i], nueva)) {
            cola[i + 1] = cola[i];
            i--;
        }
        cola[i + 1] = nueva;
        cantidad++;
        return true;
    }

    /*
     * Devuelve true si 'existente' debe ir despues de 'nueva',
     * es decir si existente tiene menor prioridad (mayor numero)
     * o igual prioridad pero llego despues.
     */
    private boolean debeIrDespues(Entrada existente, Entrada nueva) {
        if (existente.prioridad != nueva.prioridad) {
            return existente.prioridad > nueva.prioridad;
        }
        return existente.secuencia > nueva.secuencia;
    }

    /*
     * Elimina y devuelve la emergencia de mayor prioridad (posicion 0).
     * Desplaza todos los elementos una posicion hacia la izquierda.
     */
    public Emergencia desencolar() {
        if (estaVacia()) {
            return null;
        }
        Emergencia eliminada = cola[0].emergencia;
        for (int i = 0; i < cantidad - 1; i++) {
            cola[i] = cola[i + 1];
        }
        cola[cantidad - 1] = null;
        cantidad--;
        return eliminada;
    }

    /* Devuelve las emergencias en orden de prioridad sin modificar la cola */
    public List<Emergencia> listar() {
        List<Emergencia> resultado = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            resultado.add(cola[i].emergencia);
        }
        return resultado;
    }
}

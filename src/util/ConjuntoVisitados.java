package util;

/**
 * Estructura de datos: Conjunto de visitados
 * Implementacion propia con arreglo fijo y busqueda lineal
 */
public class ConjuntoVisitados {

    private String[] elementos;
    private int cantidad;

    public ConjuntoVisitados(int capacidad) {
        this.elementos = new String[capacidad];
        this.cantidad  = 0;
    }

    /** Agrega el elemento si no estaba */
    public void agregar(String elemento) {
        if (!contiene(elemento) && cantidad < elementos.length) {
            elementos[cantidad] = elemento;
            cantidad++;
        }
    }

    /** Devuelve true si el elemento ya fue agregado */
    public boolean contiene(String elemento) {
        for (int i = 0; i < cantidad; i++) {
            if (elementos[i].equals(elemento)) {
                return true;
            }
        }
        return false;
    }

    /** Elimina el elemento del conjunto */
    public void eliminar(String elemento) {
        for (int i = 0; i < cantidad; i++) {
            if (elementos[i].equals(elemento)) {
                for (int j = i; j < cantidad - 1; j++) {
                    elementos[j] = elementos[j + 1];
                }
                elementos[cantidad - 1] = null;
                cantidad--;
                return;
            }
        }
    }
}

package vehicular;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Gestiona los vehiculos que esperan en UNA interseccion especifica.
 * Los vehiculos son liberados estrictamente en el orden en que
 * llegaron a la interseccion (primero en llegar, primero en salir).
 */
public class ColaVehicular {

    private final Queue<Vehiculo> cola;
    private final String nombreInterseccion;

    public ColaVehicular(String nombreInterseccion) {
        this.nombreInterseccion = nombreInterseccion;
        this.cola = new LinkedList<>();
    }

    /**
     * Registra un vehiculo al final de la cola de esta interseccion.
     * Su posicion en la cola determina el orden en que sera liberado
     */
    public void encolar(Vehiculo vehiculo) {
        cola.add(vehiculo);
    }

    /**
     * Libera el primer vehiculo de la cola (el que llego primero).
     * Devuelve null si no hay vehiculos esperando.
     */
    public Vehiculo liberar() {
        return cola.poll();
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }

    public int cantidadEsperando() {
        return cola.size();
    }

    /** Devuelve la lista de vehiculos en orden de llegada (sin modificar la cola). */
    public List<Vehiculo> listarEnOrden() {
        return new LinkedList<>(cola);
    }
}

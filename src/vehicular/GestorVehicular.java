package vehicular;

import util.Diccionario;
import java.util.ArrayList;
import java.util.List;

/**
 * Modulo: Flujo Vehicular
 * Administra las colas de vehiculos de TODAS las intersecciones
 * de la ciudad. Cada interseccion tiene su propia Cola FIFO: los
 * vehiculos son liberados en el orden exacto en que llegaron,
 * sin distincion de tipo ni prioridad.
 */
public class GestorVehicular {

    // Diccionario: nombre de interseccion -> cola de vehiculos de esa interseccion
    private final Diccionario<String, ColaVehicular> colasPorInterseccion;

    public GestorVehicular() {
        this.colasPorInterseccion = new Diccionario<>();
    }

    /**
     * Registra el arribo de un vehiculo a una interseccion. Si la
     * interseccion no tenia cola, se crea automaticamente.
     */
    public void registrarArribo(String interseccion, String patente, String tipoVehiculo) {
        ColaVehicular cola = colasPorInterseccion.get(interseccion);
        if (cola == null) {
            cola = new ColaVehicular(interseccion);
            colasPorInterseccion.put(interseccion, cola);
        }

        // Verifica que la patente no este duplicada en ESA interseccion
        for (Vehiculo v : cola.listarEnOrden()) {
            if (v.getPatente().equalsIgnoreCase(patente)) {
                System.out.println("-> La patente '" + patente
                        + "' ya esta en la cola de '" + interseccion + "'.");
                return;
            }
        }

        Vehiculo vehiculo = new Vehiculo(patente, tipoVehiculo);
        cola.encolar(vehiculo);
        System.out.println("-> " + vehiculo + " ingreso a la cola de '"
                + interseccion + "'. Posicion: " + cola.cantidadEsperando() + ".");
    }

    /**
     * Libera el primer vehiculo de la cola de una interseccion
     * (el que llego primero). Simula el verde del semaforo.
     */
    public void liberarSiguiente(String interseccion) {
        ColaVehicular cola = colasPorInterseccion.get(interseccion);

        if (cola == null || cola.estaVacia()) {
            System.out.println("-> No hay vehiculos esperando en '"
                    + interseccion + "'.");
            return;
        }

        Vehiculo liberado = cola.liberar();
        System.out.println("-> Vehiculo liberado de '" + interseccion
                + "': " + liberado
                + ". Quedan: " + cola.cantidadEsperando() + " vehiculo(s).");
    }

    /**
     * Libera todos los vehiculos de la cola de una interseccion en
     * orden de llegada. Simula el ciclo completo de un semaforo verde.
     */
    public void liberarTodos(String interseccion) {
        ColaVehicular cola = colasPorInterseccion.get(interseccion);

        if (cola == null || cola.estaVacia()) {
            System.out.println("-> No hay vehiculos esperando en '"
                    + interseccion + "'.");
            return;
        }

        System.out.println("-> Liberando todos los vehiculos de '"
                + interseccion + "' en orden de llegada:");
        int orden = 1;
        while (!cola.estaVacia()) {
            System.out.println("   " + orden + ". " + cola.liberar());
            orden++;
        }
    }

    /** Muestra el estado de la cola de una interseccion. */
    public void mostrarCola(String interseccion) {
        ColaVehicular cola = colasPorInterseccion.get(interseccion);

        if (cola == null || cola.estaVacia()) {
            System.out.println("-> No hay vehiculos esperando en '"
                    + interseccion + "'.");
            return;
        }

        List<Vehiculo> vehiculos = cola.listarEnOrden();
        System.out.println("-> Cola de '" + interseccion + "' ("
                + vehiculos.size() + " vehiculo(s), orden de llegada):");
        for (int i = 0; i < vehiculos.size(); i++) {
            System.out.println("   " + (i + 1) + ". " + vehiculos.get(i));
        }
    }

    /** Muestra el estado de todas las intersecciones con vehiculos en espera. */
    public void mostrarTodasLasColas() {
        if (colasPorInterseccion.isEmpty()) {
            System.out.println("-> No hay vehiculos en ninguna interseccion.");
            return;
        }

        boolean hayAlguno = false;
        for (Object[] entrada : colasPorInterseccion.entradas()) {
            String nombre = (String) entrada[0];
            ColaVehicular cola = (ColaVehicular) entrada[1];
            if (!cola.estaVacia()) {
                hayAlguno = true;
                mostrarCola(nombre);
            }
        }

        if (!hayAlguno) {
            System.out.println("-> No hay vehiculos en ninguna interseccion.");
        }
    }

    /** Lista los nombres de todas las intersecciones con cola activa. */
    public List<String> interseccionesActivas() {
        List<String> activas = new ArrayList<>();
        for (Object[] entrada : colasPorInterseccion.entradas()) {
            String nombre = (String) entrada[0];
            ColaVehicular cola = (ColaVehicular) entrada[1];
            if (!cola.estaVacia()) {
                activas.add(nombre);
            }
        }
        return activas;
    }
}

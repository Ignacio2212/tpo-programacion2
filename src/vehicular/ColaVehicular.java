package vehicular;

import java.util.ArrayList;
import java.util.List;


public class ColaVehicular {

    private NodoVehiculo cabeza;
    private NodoVehiculo cola;
    private final String nombreInterseccion;
    private int cantidad;

    public ColaVehicular(String nombreInterseccion) {
        this.nombreInterseccion = nombreInterseccion;
        this.cabeza = null;
        this.cola = null;
        this.cantidad = 0;
    }

    public String getNombreInterseccion() {
        return nombreInterseccion;
    }

    public void encolar(Vehiculo vehiculo) {
        NodoVehiculo nuevoNodo = new NodoVehiculo(vehiculo);

        if (cabeza == null) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            cola.siguiente = nuevoNodo;
            cola = nuevoNodo;
        }
        cantidad++;
    }

    /**
     * Libera el primer vehiculo de la cola (el que llego primero).
     * Devuelve null si no hay vehiculos esperando.
     */
    public Vehiculo liberar() {
        if (cabeza == null) {
            return null;
        }

        Vehiculo vehiculo = cabeza.vehiculo;
        cabeza = cabeza.siguiente;
        cantidad--;

        if (cabeza == null) {
            cola = null;
        }

        return vehiculo;
    }


    public Vehiculo verPrimero() {
        if (cabeza == null) {
            return null;
        }
        return cabeza.vehiculo;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public int cantidadEsperando() {
        return cantidad;
    }

    /** Devuelve la lista de vehiculos en orden de llegada (sin modificar la cola). */
    public List<Vehiculo> listarEnOrden() {
        List<Vehiculo> lista = new ArrayList<>();

        NodoVehiculo actual = cabeza;
        while (actual != null) {
            lista.add(actual.vehiculo);
            actual = actual.siguiente;
        }

        return lista;
    }


    private static class NodoVehiculo {
        Vehiculo vehiculo;
        NodoVehiculo siguiente;

        NodoVehiculo(Vehiculo vehiculo) {
            this.vehiculo = vehiculo;
            this.siguiente = null;
        }
    }
}
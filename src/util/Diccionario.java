package util;

/*
 * Estructura de datos: Diccionario
 * Implementacion propia con arreglo fijo de pares clave-valor
 * y busqueda lineal secuencial
 *
 */
public class Diccionario<K, V> implements IDiccionario<K, V> {
    // Par interno: una clave y su valor asociado
    private static class Dato<K, V> {
        K clave;
        V valor;

        Dato(K clave, V valor) {
            this.clave = clave;
            this.valor = valor;
        }
    }

    private static final int DIMENSION_DEFECTO = 100;

    private Dato<K, V>[] datos;
    private int cantidad;
    private int dimension;

    // Constructores
    public Diccionario(int dimension) {
        this.dimension = dimension;
        this.datos     = new Dato[dimension];
        this.cantidad  = 0;
    }

    public Diccionario() {
        this.dimension = DIMENSION_DEFECTO;
        this.datos     = new Dato[dimension];
        this.cantidad  = 0;
    }

    /** Devuelve true si el diccionario no tiene ningun elemento */
    public boolean estaVacio() {
        return cantidad == 0;
    }

    /** Devuelve true si el diccionario esta lleno */
    public boolean estaLleno() {
        return cantidad == dimension;
    }


    public boolean insertar(K clave, V valor) {
        if (estaLleno()) {
            return false;
        }
        if (existe(clave) != -1) {
            return false;   // clave duplicada
        }
        datos[cantidad] = new Dato<>(clave, valor);
        cantidad++;
        return true;
    }


    public boolean eliminar(K clave) {
        int posicion = existe(clave);
        if (posicion == -1) {
            return false;
        }
        for (int i = posicion; i < cantidad - 1; i++) {
            datos[i] = datos[i + 1];
        }
        datos[cantidad - 1] = null;
        cantidad--;
        return true;
    }


    public boolean modificar(K clave, V valor) {
        int posicion = existe(clave);
        if (posicion != -1) {
            datos[posicion].valor = valor;
            return true;
        }
        return false;
    }

    /**
     * Devuelve el valor asociado a la clave, o null si no existe
     */
    public V get(K clave) {
        int posicion = existe(clave);
        if (posicion != -1) {
            return datos[posicion].valor;
        }
        return null;
    }

    /**
     * Inserta o actualiza: si la clave no existe la inserta,
     * si ya existe modifica su valor
     */
    public void put(K clave, V valor) {
        if (!modificar(clave, valor)) {
            insertar(clave, valor);
        }
    }

    /** Devuelve true si la clave existe en el diccionario */
    public boolean containsKey(K clave) {
        return existe(clave) != -1;
    }

    /**
     * Busca la clave de forma lineal
     * Devuelve la posicion en el arreglo si existe, o -1 si no
     */
    public int existe(K clave) {
        if (estaVacio()) {
            return -1;
        }
        for (int i = 0; i < cantidad; i++) {
            if (datos[i].clave.equals(clave)) {
                return i;
            }
        }
        return -1;
    }

    public V getOrDefault(K clave, V valorDefecto) {
        V valor = get(clave);
        return (valor != null) ? valor : valorDefecto;
    }

    public int size() {
        return cantidad;
    }

    public boolean isEmpty() {
        return estaVacio();
    }

    public V remove(K clave) {
        int posicion = existe(clave);
        if (posicion == -1) return null;
        V valor = datos[posicion].valor;
        eliminar(clave);
        return valor;
    }


    public java.util.List<V> values() {
        java.util.List<V> lista = new java.util.ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            lista.add(datos[i].valor);
        }
        return lista;
    }


    public java.util.List<K> keys() {
        java.util.List<K> lista = new java.util.ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            lista.add(datos[i].clave);
        }
        return lista;
    }


    public java.util.List<Object[]> entradas() {
        java.util.List<Object[]> lista = new java.util.ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            lista.add(new Object[]{ datos[i].clave, datos[i].valor });
        }
        return lista;
    }


    public void mostrar() {
        if (!estaVacio()) {
            System.out.println("Diccionario:");
            for (int i = 0; i < cantidad; i++) {
                System.out.println(datos[i].clave + ": " + datos[i].valor);
            }
        } else {
            System.out.println("No existe elementos en el diccionario");
        }
    }
}
package util;

import java.util.List;

/**
 * Interfaz que define el contrato para un Diccionario genérico
 * Almacena pares clave-valor de cualquier tipo
 */
public interface IDiccionario<K, V> {

    boolean estaVacio();
    boolean estaLleno();
    boolean insertar(K clave, V valor);
    void put(K clave, V valor);
    V get(K clave);
    boolean containsKey(K clave);
    boolean eliminar(K clave);
    boolean modificar(K clave, V valor);
    V getOrDefault(K clave, V valorDefecto);
    int size();
    boolean isEmpty();
    List<V> values();
}
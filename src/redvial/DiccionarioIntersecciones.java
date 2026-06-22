package redvial;

import util.Diccionario;
import java.util.ArrayList;
import java.util.List;

/*
 * Indexa las intersecciones de la ciudad por su ID UNICO interno,
 * permitiendo busquedas en tiempo constante O(1)
 *
 * Tambien permite buscar una interseccion a partir del nombre de sus dos calles (ej: "Independencia" y "Lima"), que es
 * como el usuario la identifica. El ID es un detalle administrativo: el sistema lo
 * genera y lo usa internamente, pero el usuario solo trabaja con nombres de calles
 */
public class DiccionarioIntersecciones {

    private Diccionario<String, Interseccion> porId;
    // Contador para generar IDs unicos automaticamente
    private int contador;

    public DiccionarioIntersecciones() {
        this.porId = new Diccionario<>();
        this.contador = 0;
    }

    /* Genera un ID unico para una nueva interseccion (ej: "INT-001") */
    private String generarId() {
        contador++;
        return String.format("INT-%03d", contador);
    }

    /*
     * Crea e indexa una nueva interseccion a partir del nombre de sus
     * dos calles. El ID se genera automaticamente. Si ya existe una
     * interseccion con esas mismas dos calles (en cualquier orden),
     * se devuelve la existente en lugar de crear una duplicada
     */
    public Interseccion crearOConseguir(String calleUno, String calleDos) {
        Interseccion existente = buscarPorCalles(calleUno, calleDos);
        if (existente != null) {
            return existente;
        }
        String id = generarId();
        Interseccion interseccion = new Interseccion(id, calleUno, calleDos);
        porId.put(id, interseccion);
        return interseccion;
    }

    /* Busca una interseccion por su ID. Operacion O(1) */
    public Interseccion buscarPorId(String id) {
        return porId.get(id);
    }

    /*
     * Busca una interseccion a partir del nombre de sus dos calles
     *Devuelve null si no existe ninguna interseccion entre esas dos calles
     */
    public Interseccion buscarPorCalles(String calleUno, String calleDos) {
        for (Interseccion interseccion : porId.values()) {
            boolean coincideDirecto = interseccion.getCalleUno().equalsIgnoreCase(calleUno)
                    && interseccion.getCalleDos().equalsIgnoreCase(calleDos);
            boolean coincideInvertido = interseccion.getCalleUno().equalsIgnoreCase(calleDos)
                    && interseccion.getCalleDos().equalsIgnoreCase(calleUno);
            if (coincideDirecto || coincideInvertido) {
                return interseccion;
            }
        }
        return null;
    }

    /* Devuelve todas las intersecciones que involucran a una calle dada */
    public List<Interseccion> buscarPorCalle(String nombreCalle) {
        List<Interseccion> resultado = new ArrayList<>();
        for (Interseccion interseccion : porId.values()) {
            if (interseccion.involucraCalle(nombreCalle)) {
                resultado.add(interseccion);
            }
        }
        return resultado;
    }

    public List<Interseccion> listar() {
        return new ArrayList<>(porId.values());
    }

    public int tamanio() {
        return porId.size();
    }
}

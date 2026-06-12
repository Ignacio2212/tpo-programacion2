package redvial;

/*
 * Representa una interseccion (esquina) de la ciudad: el punto donde se cruzan dos calles.
 *
 * Cada interseccion tiene:
 *  - un ID unico, usado internamente por el sistema para administrar
 *    el grafo (vertices, busquedas, etc.). El usuario no necesita
 *    conocer ni usar este ID.
 *  - el nombre de las dos calles que se cruzan (ej: "Independencia"
 *    y "Lima"). Para el usuario, una interseccion se identifica y se
 *    muestra como "Independencia y Lima".
 *
 * El usuario se maneja siempre por nombres de calles; el ID es un
 * detalle interno del sistema.
 */
public class Interseccion {

    private String id;
    private String calleUno;
    private String calleDos;

    public Interseccion(String id, String calleUno, String calleDos) {
        this.id = id;
        this.calleUno = calleUno;
        this.calleDos = calleDos;
    }

    public String getId() {
        return id;
    }

    public String getCalleUno() {
        return calleUno;
    }

    public String getCalleDos() {
        return calleDos;
    }

    /*
     * Indica si esta interseccion involucra a la calle dada (en
     * cualquiera de sus dos calles), sin distinguir mayusculas/minusculas
     */
    public boolean involucraCalle(String nombreCalle) {
        return calleUno.equalsIgnoreCase(nombreCalle) || calleDos.equalsIgnoreCase(nombreCalle);
    }

    /*
     * Nombre con el que el usuario identifica esta interseccion:
     * "CalleUno y CalleDos".
     */
    public String getNombreVisible() {
        return calleUno + " y " + calleDos;
    }

    @Override
    public String toString() {
        return getNombreVisible() + " [id interno: " + id + "]";
    }
}

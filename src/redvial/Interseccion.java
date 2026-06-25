package redvial;

/*
 * Representa una interseccion de la ciudad
 *
 * Cada interseccion tiene:
 *  - un ID unico, . El usuario no necesita conocer ni usar este ID.
 *  - el nombre de las dos calles que se cruzan (ej: "Independencia"
 *    y "Lima"). Para el usuario, una interseccion se identifica y se
 *    muestra como "Independencia y Lima".
 *
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

    public String getNombreVisible() {
        return calleUno + " y " + calleDos;
    }

    @Override
    public String toString() {
        return getNombreVisible() + " [id interno: " + id + "]";
    }
}

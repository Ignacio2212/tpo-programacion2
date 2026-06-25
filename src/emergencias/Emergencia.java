package emergencias;

import util.Diccionario;

/**
 * Representa una emergencia reportada en la ciudad
 */
public class Emergencia {

    private static final Diccionario<Integer, String> NIVELES = new Diccionario<>();
    static {
        NIVELES.put(1, "CRITICA");
        NIVELES.put(2, "ALTA");
        NIVELES.put(3, "MEDIA");
        NIVELES.put(4, "BAJA");
    }

    private String codigo;
    private String descripcion;
    private int gravedad;       // 1 = mas grave, 4 = menos grave
    private String ubicacion;
    private String estado;      // pendiente, en_atencion, resuelta

    public Emergencia(String codigo, String descripcion, int gravedad, String ubicacion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.gravedad = gravedad;
        this.ubicacion = ubicacion;
        this.estado = "pendiente";
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        String nivel = NIVELES.getOrDefault(gravedad, "DESCONOCIDA");
        return "[" + codigo + "] " + descripcion
                + " - Gravedad: " + nivel
                + " - Ubicacion: " + ubicacion
                + " - Estado: " + estado;
    }
}

package redvial;

/*
 * Tipos de afectacion que pueden impactar el transito de una calle
 * Por ejemplo, una calle con tiempo base 2 y un semaforo roto
 * (factor 1.5) pasa a tener un tiempo efectivo de 3.
 */
public class TipoAfectacion {

    // ===== CONSTANTES PREDEFINIDAS =====
    public static TipoAfectacion SIN_AFECTACION =
            new TipoAfectacion("Sin afectacion", 1.0);

    public static TipoAfectacion TRAFICO_MODERADO =
            new TipoAfectacion("Trafico moderado", 1.5);

    public static TipoAfectacion TRAFICO_PESADO =
            new TipoAfectacion("Trafico pesado", 2.5);

    public static TipoAfectacion SEMAFORO_ROTO =
            new TipoAfectacion("Semaforo fuera de servicio", 1.8);

    public static TipoAfectacion OBRA_EN_VIA =
            new TipoAfectacion("Obra en la via", 2.0);

    public static TipoAfectacion ACCIDENTE =
            new TipoAfectacion("Accidente de transito", 3.0);

    public static TipoAfectacion CORTE_PARCIAL =
            new TipoAfectacion("Corte parcial de calle", 4.0);

    public static TipoAfectacion CORTE_TOTAL =
            new TipoAfectacion("Corte total de calle", 1000000.0);

    // ===== ATRIBUTOS =====
    private String descripcion;
    private double factorPonderacion;

    // ===== CONSTRUCTOR =====
    public TipoAfectacion(String descripcion, double factorPonderacion) {
        this.descripcion = descripcion;
        this.factorPonderacion = factorPonderacion;
    }

    // ===== MÉTODOS =====
    public String getDescripcion() {
        return descripcion;
    }

    public double getFactorPonderacion() {
        return factorPonderacion;
    }

    public boolean esCorteTotal() {
        return this == CORTE_TOTAL;
    }

    @Override
    public String toString() {
        return descripcion + " (x" + factorPonderacion + ")";
    }
}
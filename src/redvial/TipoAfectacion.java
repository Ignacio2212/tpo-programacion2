package redvial;

/*
 * Tipos de afectacion que pueden impactar el transito de una calle
 *
 * Cada tipo tiene asociado un factor de ponderacion: el tiempo base
 * de la calle se multiplica por este factor para obtener el tiempo
 * efectivo cuando la afectacion esta activa.
 *
 * Por ejemplo, una calle con tiempo base 2 y un semaforo roto
 * (factor 1.5) pasa a tener un tiempo efectivo de 3.
 */
public enum TipoAfectacion {

    /* Trafico normal / sin incidentes. No penaliza el tiempo */
    SIN_AFECTACION("Sin afectacion", 1.0),

    /* Trafico denso pero la calle sigue transitable */
    TRAFICO_MODERADO("Trafico moderado", 1.5),

    /* Trafico muy denso (hora pico, embotellamiento) */
    TRAFICO_PESADO("Trafico pesado", 2.5),

    /* Semaforo de la interseccion/calle fuera de servicio: demoras adicionales */
    SEMAFORO_ROTO("Semaforo fuera de servicio", 1.8),

    /* Obra en la via: reduce carriles disponibles */
    OBRA_EN_VIA("Obra en la via", 2.0),

    /* Accidente de transito reportado en la calle */
    ACCIDENTE("Accidente de transito", 3.0),

    /* Manifestacion, evento o corte parcial que demora el paso */
    CORTE_PARCIAL("Corte parcial de calle", 4.0),

    /*
     * Corte total de la calle: queda intransitable. Se representa con
     * un factor muy alto para que nunca se elija
     */
    CORTE_TOTAL("Corte total de calle", 1_000_000.0);

    private final String descripcion;
    private final double factorPonderacion;

    TipoAfectacion(String descripcion, double factorPonderacion) {
        this.descripcion = descripcion;
        this.factorPonderacion = factorPonderacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getFactorPonderacion() {
        return factorPonderacion;
    }

    public boolean esCorteTotal() {
        return this == CORTE_TOTAL;
    }
}

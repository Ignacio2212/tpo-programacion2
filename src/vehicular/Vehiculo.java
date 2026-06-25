package vehicular;

/**
 * Representa un vehiculo que espera ser liberado en una interseccion.
 * Se identifica por su patente y lleva registro del orden de llegada.
 */
public class Vehiculo {

    private String patente;
    private String tipoVehiculo; // auto, moto, camion, etc.

    public Vehiculo(String patente, String tipoVehiculo) {
        this.patente = patente;
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getPatente()      { return patente; }

    @Override
    public String toString() {
        return "[" + patente + " - " + tipoVehiculo + "]";
    }
}

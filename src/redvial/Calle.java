package redvial;

/*
 * Representa una calle que conecta dos intersecciones en un sentido determinado
 *
 * Cada calle tiene:
 *  - un nombre propio (ej: "Calle 1", "Av. Libertador"),
 *  - un tiempo base de recorrido (cuanto tarda en condiciones normales, sin ningun incidente),
 *  - una afectacion actual (corte, semaforo roto, obra, accidente, trafico pesado, etc.), que pondera ese tiempo base
 *
 * El tiempo efectivo de la calle (el que realmente se usa para calcular la ruta mas rapida) es:
 *      tiempoEfectivo = tiempoBase * afectacion.getFactorPonderacion()
 */
public class Calle {
    private String nombre;
    private String origen;
    private String destino;
    private double tiempoBase;
    private TipoAfectacion afectacion;

    public Calle(String nombre, String origen, String destino, double tiempoBase) {
        this(nombre, origen, destino, tiempoBase, TipoAfectacion.SIN_AFECTACION);
    }

    public Calle(String nombre, String origen, String destino, double tiempoBase, TipoAfectacion afectacion) {
        this.nombre = nombre;
        this.origen = origen;
        this.destino = destino;
        this.tiempoBase = tiempoBase;
        this.afectacion = afectacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public double getTiempoBase() {
        return tiempoBase;
    }

    public TipoAfectacion getAfectacion() {
        return afectacion;
    }

    public void setAfectacion(TipoAfectacion afectacion) {
        this.afectacion = afectacion;
    }

    public void quitarAfectacion() {
        this.afectacion = TipoAfectacion.SIN_AFECTACION;
    }

    public double getTiempoEfectivo() {
        return tiempoBase * afectacion.getFactorPonderacion();
    }

    public boolean estaCortada() {
        return afectacion.esCorteTotal();
    }

    public boolean tieneAfectacion() {
        return afectacion != TipoAfectacion.SIN_AFECTACION;
    }

    @Override
    public String toString() {
        String baseStr = formatear(tiempoBase);
        String efectivoStr = formatear(getTiempoEfectivo());
        String estado = (afectacion == TipoAfectacion.SIN_AFECTACION)
                ? "sin afectaciones"
                : afectacion.getDescripcion() + " (x" + formatear(afectacion.getFactorPonderacion()) + ")";

        if (afectacion == TipoAfectacion.SIN_AFECTACION) {
            return nombre + " [" + origen + " -> " + destino + "] "
                    + "(tiempo: " + baseStr + ", " + estado + ")";
        }
        return nombre + " [" + origen + " -> " + destino + "] "
                + "(tiempo base: " + baseStr + ", " + estado
                + ", tiempo efectivo: " + efectivoStr + ")";
    }

    private String formatear(double valor) {
        if (valor == Math.floor(valor) && !Double.isInfinite(valor)) {
            return String.valueOf((long) valor);
        }
        return String.valueOf(valor);
    }
}

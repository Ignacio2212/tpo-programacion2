package dispositivos;

/**
 * Representa un dispositivo urbano: semaforo o camara.
 */
public class Dispositivo {
    private String codigo;
    private String tipo;       // "semaforo" o "camara"
    private String ubicacion;
    private String estado;     // "activo", "inactivo", "en_mantenimiento"

    public Dispositivo(String codigo, String tipo, String ubicacion, String estado) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.estado = estado;
    }

    public Dispositivo(String codigo, String tipo, String ubicacion) {
        this(codigo, tipo, ubicacion, "activo");
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "[" + codigo + "] " + tipo.toUpperCase()
                + " - Ubicacion: " + ubicacion
                + " - Estado: " + estado;
    }
}

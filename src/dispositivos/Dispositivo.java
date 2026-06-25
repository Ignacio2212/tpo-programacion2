package dispositivos;

public class Dispositivo {
    private String codigo;
    private String tipo;
    private String ubicacion;
    private String estado;

    public Dispositivo(String codigo, String tipo, String ubicacion, String estado) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.estado = estado;
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

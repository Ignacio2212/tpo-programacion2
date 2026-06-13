package dispositivos;

public class GestorDispositivos {

    private DiccionarioDispositivos dispositivos;

    public GestorDispositivos() {
        this.dispositivos = new DiccionarioDispositivos();
    }

    public boolean registrarDispositivo(String codigo, String tipo, String ubicacion, String estado) {
        if (dispositivos.existe(codigo)) {
            System.out.println("-> Ya existe un dispositivo con el codigo '" + codigo + "'.");
            return false;
        }
        Dispositivo dispositivo = new Dispositivo(codigo, tipo, ubicacion, estado);
        dispositivos.insertar(codigo, dispositivo);
        System.out.println("-> Dispositivo registrado: " + dispositivo);
        return true;
    }

    public boolean registrarDispositivo(String codigo, String tipo, String ubicacion) {
        return registrarDispositivo(codigo, tipo, ubicacion, "activo");
    }

    public Dispositivo buscarDispositivo(String codigo) {
        Dispositivo dispositivo = dispositivos.buscar(codigo);
        if (dispositivo == null) {
            System.out.println("-> No se encontro dispositivo con codigo '" + codigo + "'.");
        } else {
            System.out.println("-> Encontrado: " + dispositivo);
        }
        return dispositivo;
    }

    public boolean actualizarEstado(String codigo, String nuevoEstado) {
        if (dispositivos.actualizarEstado(codigo, nuevoEstado)) {
            System.out.println("-> Estado de '" + codigo + "' actualizado a '" + nuevoEstado + "'.");
            return true;
        }
        System.out.println("-> No se pudo actualizar: dispositivo '" + codigo + "' no existe.");
        return false;
    }

    public void listarDispositivos() {
        var lista = dispositivos.listar();
        if (lista.isEmpty()) {
            System.out.println("-> No hay dispositivos registrados.");
            return;
        }
        System.out.println("-> Total de dispositivos: " + dispositivos.tamanio());
        for (Dispositivo d : lista) {
            System.out.println("   " + d);
        }
    }
}

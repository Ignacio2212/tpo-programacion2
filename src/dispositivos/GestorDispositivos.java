package dispositivos;

import util.IDiccionario;
import util.Diccionario;
import java.util.List;

public class GestorDispositivos {

    private IDiccionario<String, Dispositivo> dispositivos;

    public GestorDispositivos() {
        this.dispositivos = new Diccionario<>();
    }

    public boolean registrarDispositivo(String codigo, String tipo, String ubicacion, String estado) {
        if (dispositivos.containsKey(codigo)) {
            System.out.println("-> Ya existe un dispositivo con el codigo '" + codigo + "'.");
            return false;
        }
        Dispositivo dispositivo = new Dispositivo(codigo, tipo, ubicacion, estado);
        dispositivos.put(codigo, dispositivo);
        System.out.println("-> Dispositivo registrado: " + dispositivo);
        return true;
    }

    public boolean registrarDispositivo(String codigo, String tipo, String ubicacion) {
        return registrarDispositivo(codigo, tipo, ubicacion, "activo");
    }

    public Dispositivo buscarDispositivo(String codigo) {
        Dispositivo dispositivo = dispositivos.get(codigo);
        if (dispositivo == null) {
            System.out.println("-> No se encontro dispositivo con codigo '" + codigo + "'.");
        } else {
            System.out.println("-> Encontrado: " + dispositivo);
        }
        return dispositivo;
    }

    public boolean actualizarEstado(String codigo, String nuevoEstado) {
        Dispositivo d = dispositivos.get(codigo);
        if (d == null) {
            System.out.println("-> No se pudo actualizar: dispositivo '" + codigo + "' no existe.");
            return false;
        }
        d.setEstado(nuevoEstado);
        System.out.println("-> Estado de '" + codigo + "' actualizado a '" + nuevoEstado + "'.");
        return true;
    }

    public void listarDispositivos() {
        List<Dispositivo> lista = dispositivos.values();
        if (lista.isEmpty()) {
            System.out.println("-> No hay dispositivos registrados.");
            return;
        }
        System.out.println("-> Total de dispositivos: " + dispositivos.size());
        for (Dispositivo d : lista) {
            System.out.println("   " + d);
        }
    }

    public int tamanio() {
        return dispositivos.size();
    }
}
package dispositivos;

import util.Diccionario;
import java.util.ArrayList;
import java.util.List;

public class DiccionarioDispositivos {

    private Diccionario<String, Dispositivo> tabla;

    public DiccionarioDispositivos() {
        this.tabla = new Diccionario<>();
    }

    public void insertar(String codigo, Dispositivo dispositivo) {
        tabla.put(codigo, dispositivo);
    }

    public Dispositivo buscar(String codigo) {
        return tabla.get(codigo);
    }

    public boolean existe(String codigo) {
        return tabla.containsKey(codigo);
    }

    public boolean actualizarEstado(String codigo, String nuevoEstado) {
        Dispositivo d = tabla.get(codigo);
        if (d == null) {
            return false;
        }
        d.setEstado(nuevoEstado);
        return true;
    }

    public boolean eliminar(String codigo) {
        return tabla.remove(codigo) != null;
    }

    public List<Dispositivo> listar() {
        return new ArrayList<>(tabla.values());
    }

    public int tamanio() {
        return tabla.size();
    }
}

package emergencias;

import util.IDiccionario;
import util.Diccionario;
import java.util.List;

public class GestorEmergencias {

    private IDiccionario<String, Emergencia> emergencias;
    private ColaPrioridadEmergencias colaEmergencias;

    public GestorEmergencias() {
        this.emergencias = new Diccionario<>();
        this.colaEmergencias = new ColaPrioridadEmergencias();
    }

    public boolean registrarEmergencia(String codigo, String descripcion, int gravedad, String ubicacion) {
        if (emergencias.containsKey(codigo)) {
            System.out.println("-> Ya existe una emergencia con el codigo '" + codigo + "'.");
            return false;
        }
        Emergencia emergencia = new Emergencia(codigo, descripcion, gravedad, ubicacion);
        emergencias.put(codigo, emergencia);
        colaEmergencias.encolar(emergencia, gravedad);
        System.out.println("-> Emergencia registrada: " + emergencia);
        return true;
    }

    public boolean actualizarEstado(String codigo, String nuevoEstado) {
        Emergencia emergencia = emergencias.get(codigo);
        if (emergencia == null) {
            System.out.println("-> No existe emergencia con codigo '" + codigo + "'.");
            return false;
        }
        emergencia.setEstado(nuevoEstado);
        System.out.println("-> Estado de emergencia '" + codigo + "' actualizado a '" + nuevoEstado + "'.");
        return true;
    }

    public Emergencia atenderSiguiente() {
        if (colaEmergencias.estaVacia()) {
            System.out.println("-> No hay emergencias pendientes en cola.");
            return null;
        }
        Emergencia emergencia = colaEmergencias.desencolar();
        emergencia.setEstado("en_atencion");
        System.out.println("-> Atendiendo ahora: " + emergencia);
        return emergencia;
    }

    public void listarPendientes() {
        List<Emergencia> pendientes = colaEmergencias.listar();
        if (pendientes.isEmpty()) {
            System.out.println("-> No hay emergencias pendientes.");
            return;
        }
        System.out.println("-> Emergencias pendientes (ordenadas por prioridad):");
        for (Emergencia e : pendientes) {
            System.out.println("   " + e);
        }
    }

    public void listarTodas() {
        List<Emergencia> todas = emergencias.values();
        if (todas.isEmpty()) {
            System.out.println("-> No hay emergencias registradas.");
            return;
        }
        System.out.println("-> Todas las emergencias:");
        for (Emergencia e : todas) {
            System.out.println("   " + e);
        }
    }
}
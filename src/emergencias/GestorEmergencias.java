package emergencias;

import util.Diccionario;

/*
 * Modulo: Despacho de Emergencias
 * Funcionalidad implementada: Registro, actualizacion y atencion de
 * emergencias priorizando la gravedad del evento mediante una Cola de
 * Prioridad, sobre el orden cronologico de reporte
 */
public class GestorEmergencias {

    private ColaPrioridadEmergencias colaEmergencias;
    private Diccionario<String, Emergencia> registro;

    public GestorEmergencias() {
        this.colaEmergencias = new ColaPrioridadEmergencias();
        this.registro = new Diccionario<>();
    }

    public boolean registrarEmergencia(String codigo, String descripcion, int gravedad, String ubicacion) {
        if (registro.containsKey(codigo)) {
            System.out.println("-> Ya existe una emergencia con el codigo '" + codigo + "'.");
            return false;
        }
        Emergencia emergencia = new Emergencia(codigo, descripcion, gravedad, ubicacion);
        registro.put(codigo, emergencia);
        colaEmergencias.encolar(emergencia, gravedad);
        System.out.println("-> Emergencia registrada: " + emergencia);
        return true;
    }

    public boolean actualizarEstado(String codigo, String nuevoEstado) {
        Emergencia emergencia = registro.get(codigo);
        if (emergencia == null) {
            System.out.println("-> No existe emergencia con codigo '" + codigo + "'.");
            return false;
        }
        emergencia.setEstado(nuevoEstado);
        System.out.println("-> Estado de emergencia '" + codigo + "' actualizado a '" + nuevoEstado + "'.");
        return true;
    }

    /* Extrae y atiende la emergencia de mayor gravedad pendiente */
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
        var pendientes = colaEmergencias.listar();
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
        if (registro.isEmpty()) {
            System.out.println("-> No hay emergencias registradas.");
            return;
        }
        for (Emergencia e : registro.values()) {
            System.out.println("   " + e);
        }
    }
}

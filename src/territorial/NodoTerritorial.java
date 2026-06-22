package territorial;

import java.util.ArrayList;
import java.util.List;

public class NodoTerritorial {

    private String nombre;
    private String tipo;           // "ciudad", "zona", "barrio", "manzana"
    private int cantDispositivos;  // dispositivos instalados en esta unidad
    private List<NodoTerritorial> hijos;

    public NodoTerritorial(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.cantDispositivos = 0;
        this.hijos = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public String getTipo()   { return tipo; }
    public int getCantDispositivos() { return cantDispositivos; }
    public List<NodoTerritorial> getHijos() { return hijos; }

    public void agregarHijo(NodoTerritorial hijo) {
        hijos.add(hijo);
    }

    public void incrementarDispositivos() {
        cantDispositivos++;
    }

    public NodoTerritorial buscar(String nombre) {
        if (this.nombre.equalsIgnoreCase(nombre)) {
            return this;
        }
        for (NodoTerritorial hijo : hijos) {
            NodoTerritorial encontrado = hijo.buscar(nombre);
            if (encontrado != null) {
                return encontrado;
            }
        }
        return null;
    }

    /**
     * Cuenta el total de dispositivos en este nodo y en todos sus
     * descendientes
     */
    public int totalDispositivosAcumulado() {
        int total = cantDispositivos;
        for (NodoTerritorial hijo : hijos) {
            total += hijo.totalDispositivosAcumulado();
        }
        return total;
    }

    /**
     * Genera el reporte de este nodo y sus descendientes
     */
    public String generarReporte(int nivel) {
        String sangria = "  ".repeat(nivel);
        StringBuilder sb = new StringBuilder();
        sb.append(sangria)
                .append("[").append(tipo.toUpperCase()).append("] ")
                .append(nombre)
                .append("  (dispositivos propios: ").append(cantDispositivos)
                .append(", total acumulado: ").append(totalDispositivosAcumulado())
                .append(")\n");
        for (NodoTerritorial hijo : hijos) {
            sb.append(hijo.generarReporte(nivel + 1));
        }
        return sb.toString();
    }
}

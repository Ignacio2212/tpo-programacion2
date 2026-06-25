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

    public String getTipo() {
        return tipo;
    }

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
        String reporte = "";

        reporte += sangria
                + "[" + tipo.toUpperCase() + "] "
                + nombre
                + "  (dispositivos propios: " + cantDispositivos
                + ", total acumulado: " + totalDispositivosAcumulado()
                + ")\n";

        for (NodoTerritorial hijo : hijos) {
            reporte += hijo.generarReporte(nivel + 1);
        }

        return reporte;
    }
}

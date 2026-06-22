package territorial;

public class ArbolTerritorial {

    private NodoTerritorial raiz;

    public ArbolTerritorial(String nombreCiudad) {
        this.raiz = new NodoTerritorial(nombreCiudad, "ciudad");
    }

    // Operaciones de construccion de la jerarquia

    /**
     * Agrega una zona a la ciudad.
     * Si la zona ya existe, no la duplica.
     */
    public boolean agregarZona(String nombreZona) {
        if (raiz.buscar(nombreZona) != null) {
            return false; // ya existe
        }
        raiz.agregarHijo(new NodoTerritorial(nombreZona, "zona"));
        return true;
    }

    /**
     * Agrega un barrio a una zona existente.
     * Devuelve false si la zona no existe o el barrio ya existe.
     */
    public boolean agregarBarrio(String nombreZona, String nombreBarrio) {
        NodoTerritorial zona = raiz.buscar(nombreZona);
        if (zona == null || !zona.getTipo().equals("zona")) {
            return false;
        }
        if (raiz.buscar(nombreBarrio) != null) {
            return false; // ya existe
        }
        zona.agregarHijo(new NodoTerritorial(nombreBarrio, "barrio"));
        return true;
    }

    /**
     * Agrega una manzana a un barrio existente.
     * Devuelve false si el barrio no existe o la manzana ya existe.
     */
    public boolean agregarManzana(String nombreBarrio, String nombreManzana) {
        NodoTerritorial barrio = raiz.buscar(nombreBarrio);
        if (barrio == null || !barrio.getTipo().equals("barrio")) {
            return false;
        }
        if (raiz.buscar(nombreManzana) != null) {
            return false; // ya existe
        }
        barrio.agregarHijo(new NodoTerritorial(nombreManzana, "manzana"));
        return true;
    }

    /**
     * Registra un dispositivo en una unidad territorial (zona, barrio
     * o manzana). Se usa para llevar la estadistica de dispositivos
     * instalados en cada sector de la ciudad.
     */
    public boolean registrarDispositivoEn(String nombreUnidad) {
        NodoTerritorial nodo = raiz.buscar(nombreUnidad);
        if (nodo == null) {
            return false;
        }
        nodo.incrementarDispositivos();
        return true;
    }

    // Reportes estadisticos

    /**
     * Genera el reporte estadistico completo de la ciudad, mostrando
     * la jerarquia de unidades territoriales con la cantidad de
     * dispositivos en cada nivel.
     */
    public String generarReporteCompleto() {
        return raiz.generarReporte(0);
    }

    /**
     * Genera el reporte de una unidad territorial especifica
     * (zona, barrio o manzana) y sus sub-unidades.
     */
    public String generarReporteDe(String nombreUnidad) {
        NodoTerritorial nodo = raiz.buscar(nombreUnidad);
        if (nodo == null) {
            return null;
        }
        return nodo.generarReporte(0);
    }

    /** Busca una unidad territorial por nombre. */
    public NodoTerritorial buscar(String nombre) {
        return raiz.buscar(nombre);
    }
}

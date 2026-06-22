package territorial;

/**
 * Modulo: Organizacion Territorial
 * La ciudad se divide en zonas, barrios y manzanas.
 * Jerarquia:  Ciudad  >  Zona  >  Barrio  >  Manzana
 */
public class GestorTerritorial {

    private ArbolTerritorial arbol;

    public GestorTerritorial(String nombreCiudad) {
        this.arbol = new ArbolTerritorial(nombreCiudad);
    }

    // Alta de unidades territoriales

    public void agregarZona(String nombreZona) {
        if (arbol.agregarZona(nombreZona)) {
            System.out.println("-> Zona '" + nombreZona + "' registrada.");
        } else {
            System.out.println("-> La zona '" + nombreZona + "' ya existe.");
        }
    }

    public void agregarBarrio(String nombreZona, String nombreBarrio) {
        if (arbol.agregarBarrio(nombreZona, nombreBarrio)) {
            System.out.println("-> Barrio '" + nombreBarrio
                    + "' agregado a la zona '" + nombreZona + "'.");
        } else {
            System.out.println("-> No se pudo agregar el barrio: la zona '"
                    + nombreZona + "' no existe o el barrio ya existe.");
        }
    }

    public void agregarManzana(String nombreBarrio, String nombreManzana) {
        if (arbol.agregarManzana(nombreBarrio, nombreManzana)) {
            System.out.println("-> Manzana '" + nombreManzana
                    + "' agregada al barrio '" + nombreBarrio + "'.");
        } else {
            System.out.println("-> No se pudo agregar la manzana: el barrio '"
                    + nombreBarrio + "' no existe o la manzana ya existe.");
        }
    }

    /**
     * Registra un dispositivo en una unidad territorial. Esto actualiza
     * el contador de dispositivos de esa unidad, afectando los reportes
     * estadisticos de toda la jerarquia que la contiene.
     */
    public void registrarDispositivoEn(String nombreUnidad) {
        if (arbol.registrarDispositivoEn(nombreUnidad)) {
            System.out.println("-> Dispositivo registrado en '"
                    + nombreUnidad + "'.");
        } else {
            System.out.println("-> No se encontro la unidad territorial '"
                    + nombreUnidad + "'.");
        }
    }

    // Reportes estadisticos
    /** Muestra el reporte estadistico completo de toda la ciudad. */
    public void reporteCompleto() {
        System.out.println("-> Reporte estadistico de la ciudad:");
        System.out.println(arbol.generarReporteCompleto());
    }

    /**
     * Muestra el reporte estadistico de una unidad territorial
     * especifica (zona, barrio o manzana) y sus sub-unidades.
     */
    public void reporteDe(String nombreUnidad) {
        String reporte = arbol.generarReporteDe(nombreUnidad);
        if (reporte == null) {
            System.out.println("-> No se encontro la unidad territorial '"
                    + nombreUnidad + "'.");
        } else {
            System.out.println("-> Reporte de '" + nombreUnidad + "':");
            System.out.println(reporte);
        }
    }

    /** Informa si una unidad territorial existe en la jerarquia. */
    public boolean existe(String nombreUnidad) {
        return arbol.buscar(nombreUnidad) != null;
    }
}

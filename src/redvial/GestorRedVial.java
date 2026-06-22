package redvial;

import redvial.*;

import java.util.List;

/*
 * Funcionalidad implementada:
 *  - Una interseccion es el cruce de dos calles (ej: "Independencia y
 *    Lima"). Cada interseccion tiene un ID unico interno para que el
 *    sistema la administre, pero el usuario la
 *    identifica unicamente por el nombre de sus dos calles. nunca
 *    necesita conocer ni escribir el ID.
 *  - Cada calle conecta dos intersecciones en un sentido
 *    determinado, tiene un nombre propio, un tiempo base de recorrido
 *    y puede tener una afectacion (corte, semaforo roto, obra,
 *    accidente, trafico pesado, etc.) que pondera ese tiempo.
 *  - Para ir de una interseccion a otra, el sistema indica la
 *    secuencia exacta de calles a recorrer (ej: Independencia ->
 *    Lima -> ... hasta el destino).
 *  - a mejor ruta es la que minimiza el tiemop total
 *    de viaje, sin importar la cantidad de cuadras
 */
public class GestorRedVial {

    private Grafo red;
    private DiccionarioIntersecciones intersecciones;

    public GestorRedVial() {
        this.red = new Grafo();
        this.intersecciones = new DiccionarioIntersecciones();
    }

    /*
     * Registra la interseccion formada por
     * dos calles, ej: "Independencia" y "Lima". El sistema le asigna
     * automaticamente un ID interno (ej: INT-001) para administrarla;
     * el usuario solo necesita recordar el nombre de las dos calles
     */
    public Interseccion registrarInterseccion(String calleUno, String calleDos) {
        Interseccion interseccion = intersecciones.crearOConseguir(calleUno, calleDos);
        red.agregarVertice(interseccion.getId());
        System.out.println("--> Interseccion registrada: " + interseccion.getNombreVisible()
                + " (id interno: " + interseccion.getId() + ")");
        return interseccion;
    }

    /*
     * Agrega una calle (cuadra) que conecta dos intersecciones, en el
     * sentido origen -> destino. Cada interseccion se identifica por
     * el nombre de sus dos calles (ej: "Independencia" + "Lima").
     *
     * @param nombreCalle  nombre de la calle que se recorre en este tramo
     * @param origenA, origenB     calles que forman la interseccion de origen
     * @param destinoA, destinoB   calles que forman la interseccion de destino
     * @param tiempoBase   tiempo de recorrido en condiciones normales
     */
    public boolean agregarCalle(String nombreCalle,
                                String origenA, String origenB,
                                String destinoA, String destinoB,
                                double tiempoBase) {
        return agregarCalle(nombreCalle, origenA, origenB, destinoA, destinoB, tiempoBase, TipoAfectacion.SIN_AFECTACION);
    }

    /* Igual que {@link #agregarCalle}, pero permite indicar una afectacion inicial */
    public boolean agregarCalle(String nombreCalle,
                                String origenA, String origenB,
                                String destinoA, String destinoB,
                                double tiempoBase, TipoAfectacion afectacion) {
        Interseccion origen = intersecciones.buscarPorCalles(origenA, origenB);
        Interseccion destino = intersecciones.buscarPorCalles(destinoA, destinoB);

        if (origen == null) {
            System.out.println("--> No existe la interseccion '" + origenA + " y " + origenB
                    + "'. Registrela primero.");
            return false;
        }
        if (destino == null) {
            System.out.println("--> No existe la interseccion '" + destinoA + " y " + destinoB
                    + "'. Registrela primero.");
            return false;
        }

        Calle calle = new Calle(nombreCalle, origen.getId(), destino.getId(), tiempoBase, afectacion);
        red.agregarCalle(calle);
        System.out.println("-> Calle " + nombreCalle + ": '" + origen.getNombreVisible()
                + "' --> '" + destino.getNombreVisible() + "' agregada"
                + " (tiempo: " + formatear(tiempoBase)
                + (afectacion == TipoAfectacion.SIN_AFECTACION ? "" : ", " + afectacion.getDescripcion())
                + ").");
        return true;
    }

    /*
     * Agrega una calle de doble mano entre dos intersecciones (crea
     * ambos sentidos, sin afectaciones iniciales)
     */
    public boolean agregarCalleDobleMano(String nombreCalle,
                                         String interseccionUnoA, String interseccionUnoB,
                                         String interseccionDosA, String interseccionDosB,
                                         double tiempoBase) {
        boolean ok1 = agregarCalle(nombreCalle, interseccionUnoA, interseccionUnoB,
                interseccionDosA, interseccionDosB, tiempoBase);
        boolean ok2 = agregarCalle(nombreCalle, interseccionDosA, interseccionDosB,
                interseccionUnoA, interseccionUnoB, tiempoBase);
        return ok1 && ok2;
    }

    /*
     * Verifica si existe una ruta transitable entre dos intersecciones,
     * identificadas por el nombre de sus calles. Si hay mas de una,
     * muestra la cantidad y el detalle de cada una.
     */
    public boolean existeRuta(String origenA, String origenB, String destinoA, String destinoB) {
        Interseccion origen = intersecciones.buscarPorCalles(origenA, origenB);
        Interseccion destino = intersecciones.buscarPorCalles(destinoA, destinoB);

        if (origen == null || destino == null) {
            System.out.println("--> No se encontro alguna de las intersecciones indicadas.");
            return false;
        }

        List<List<Calle>> todasLasRutas = red.todasLasRutas(origen.getId(), destino.getId());

        if (todasLasRutas.isEmpty()) {
            System.out.println("--> No existe ninguna ruta entre '" + origen.getNombreVisible()
                    + "' y '" + destino.getNombreVisible() + "'.");
            return false;
        }

        // Ordenar rutas de menor a mayor tiempo efectivo total
        todasLasRutas.sort((a, b) -> Double.compare(calcularTiempoTotal(a), calcularTiempoTotal(b)));

        System.out.println("--> Hay " + todasLasRutas.size() + " ruta(s) entre '"
                + origen.getNombreVisible() + "' y '" + destino.getNombreVisible() + "':");

        for (int i = 0; i < todasLasRutas.size(); i++) {
            List<Calle> ruta = todasLasRutas.get(i);

            StringBuilder secuencia = new StringBuilder(origen.getNombreVisible());
            double tiempoTotal = 0;
            for (Calle calle : ruta) {
                Interseccion destTramo = intersecciones.buscarPorId(calle.getDestino());
                String nombreDest = (destTramo != null) ? destTramo.getNombreVisible() : calle.getDestino();
                secuencia.append(" -(").append(calle.getNombre()).append(")-> ").append(nombreDest);
                tiempoTotal += calle.getTiempoEfectivo();
            }

            String marcaMasRapida = (i == 0 && todasLasRutas.size() > 1) ? " (*)" : "";
            System.out.println("   Ruta " + (i + 1) + marcaMasRapida + ": " + secuencia);
            System.out.println("           Tiempo estimado: " + formatear(tiempoTotal));
        }

        if (todasLasRutas.size() > 1) {
            System.out.println("   (*) = ruta mas rapida segun tiempo efectivo");
        }

        return true;
    }

    private double calcularTiempoTotal(List<Calle> ruta) {
        double t = 0;
        for (Calle c : ruta) t += c.getTiempoEfectivo();
        return t;
    }

    /*
     * Calcula y muestra la ruta mas rapida entre dos intersecciones,
     * identificadas por el nombre de sus calles (ej: "Independencia y
     * Lima" hasta "Independencia y Salta"). Indica la secuencia exacta
     * de calles a recorrer y el tiempo total de viaje
     */
    public Grafo.ResultadoRuta calcularRuta(String origenA, String origenB, String destinoA, String destinoB) {
        Interseccion origen = intersecciones.buscarPorCalles(origenA, origenB);
        Interseccion destino = intersecciones.buscarPorCalles(destinoA, destinoB);

        if (origen == null || destino == null) {
            System.out.println("--> No se encontro alguna de las intersecciones indicadas.");
            return new Grafo.ResultadoRuta(null, Double.POSITIVE_INFINITY);
        }

        Grafo.ResultadoRuta resultado = red.rutaMasRapida(origen.getId(), destino.getId());

        if (!resultado.existeCamino()) {
            System.out.println("--> No se encontro una ruta transitable entre '" + origen.getNombreVisible()
                    + "' y '" + destino.getNombreVisible() + "' (puede que todos los caminos esten cortados).");
            return resultado;
        }

        if (resultado.calles.isEmpty()) {
            System.out.println("--> El origen y el destino son la misma interseccion.");
            return resultado;
        }

        System.out.println("--> Ruta mas rapida de '" + origen.getNombreVisible()
                + "' a '" + destino.getNombreVisible() + "':");

        // Secuencia de calles: Calle1 --> Calle2 --> ... --> destino
        StringBuilder secuencia = new StringBuilder(origen.getNombreVisible());
        for (Calle calle : resultado.calles) {
            Interseccion destinoTramo = intersecciones.buscarPorId(calle.getDestino());
            String nombreDestino = (destinoTramo != null) ? destinoTramo.getNombreVisible() : calle.getDestino();
            secuencia.append(" -(").append(calle.getNombre()).append(")--> ").append(nombreDestino);
        }
        System.out.println("   Recorrido: " + secuencia);

        // Detalle calle por calle, con su tiempo y afectacion
        Interseccion actual = origen;
        int paso = 1;
        for (Calle calle : resultado.calles) {
            Interseccion siguiente = intersecciones.buscarPorId(calle.getDestino());
            String nombreSiguiente = (siguiente != null) ? siguiente.getNombreVisible() : calle.getDestino();

            String detalleAfectacion = calle.tieneAfectacion()
                    ? " [" + calle.getAfectacion().getDescripcion()
                    + ", x" + formatear(calle.getAfectacion().getFactorPonderacion()) + "]"
                    : " [sin afectaciones]";
            System.out.println("   " + paso + ". Tomar " + calle.getNombre()
                    + ": '" + actual.getNombreVisible() + "' --> '" + nombreSiguiente + "'"
                    + " (tiempo base: " + formatear(calle.getTiempoBase())
                    + ", tiempo efectivo: " + formatear(calle.getTiempoEfectivo()) + ")"
                    + detalleAfectacion);
            actual = siguiente != null ? siguiente : actual;
            paso++;
        }

        System.out.println("--> Tiempo total estimado del viaje: " + formatear(resultado.tiempoTotal));
        return resultado;
    }

    /*
     * Muestra el estado de todas las calles registradas: tiempo base,
     * afectacion actual y tiempo efectivo resultante. Las
     * intersecciones se muestran por el nombre de sus calles, no por
     * su ID interno
     */
    public void mostrarEstadoCalles() {
        List<Calle> calles = red.todasLasCalles();
        if (calles.isEmpty()) {
            System.out.println("--> No hay calles registradas.");
            return;
        }
        System.out.println("--> Estado actual de las calles de la red vial:");
        for (Calle calle : calles) {
            String marca = calle.estaCortada() ? "  <-- CORTADA"
                    : (calle.tieneAfectacion() ? "  <-- AFECTADA" : "");
            System.out.println("   " + describirCalle(calle) + marca);
        }
    }

    /*
     * Aplica una afectacion (corte, semaforo roto, obra, accidente,
     * trafico pesado, etc.) sobre una calle especifica que conecta dos
     * intersecciones (identificadas por sus calles). Esto modifica su
     * tiempo efectivo y, en consecuencia, las rutas que el sistema
     * recomendara.
     */
    public boolean aplicarAfectacion(String nombreCalle,
                                     String origenA, String origenB,
                                     String destinoA, String destinoB,
                                     TipoAfectacion afectacion) {
        Calle calle = buscarCalle(nombreCalle, origenA, origenB, destinoA, destinoB);
        if (calle == null) {
            return false;
        }
        calle.setAfectacion(afectacion);
        System.out.println("--> " + describirCalle(calle) + " ahora tiene: "
                + afectacion.getDescripcion() + " (factor x" + formatear(afectacion.getFactorPonderacion()) + ").");
        System.out.println("   Nuevo tiempo efectivo de la calle: " + formatear(calle.getTiempoEfectivo()));
        return true;
    }

    /* Quita cualquier afectacion de una calle, dejandola en trafico normal */
    public boolean quitarAfectacion(String nombreCalle,
                                    String origenA, String origenB,
                                    String destinoA, String destinoB) {
        Calle calle = buscarCalle(nombreCalle, origenA, origenB, destinoA, destinoB);
        if (calle == null) {
            return false;
        }
        calle.quitarAfectacion();
        System.out.println("--> " + describirCalle(calle) + " vuelve a estar sin afectaciones.");
        return true;
    }

    /* Lista todas las intersecciones registradas (por nombre de calles) */
    public void mostrarIntersecciones() {
        List<Interseccion> lista = intersecciones.listar();
        if (lista.isEmpty()) {
            System.out.println("--> No hay intersecciones registradas.");
            return;
        }
        System.out.println("--> Intersecciones registradas:");
        for (Interseccion interseccion : lista) {
            System.out.println("   " + interseccion.getNombreVisible()
                    + " (id interno: " + interseccion.getId() + ")");
        }
    }

    public void mostrarRed() {
        List<Calle> calles = red.todasLasCalles();
        if (calles.isEmpty()) {
            System.out.println("--> Red vial actual: (sin calles registradas)");
            return;
        }
        System.out.println("--> Red vial actual:");
        for (Calle calle : calles) {
            System.out.println("   " + describirCalle(calle));
        }
    }

    /*
     * Busca una calle especifica entre dos intersecciones (dadas por
     * el nombre de sus calles). Imprime un mensaje de error y devuelve
     * null si no se encuentra
     */
    private Calle buscarCalle(String nombreCalle, String origenA, String origenB, String destinoA, String destinoB) {
        Interseccion origen = intersecciones.buscarPorCalles(origenA, origenB);
        Interseccion destino = intersecciones.buscarPorCalles(destinoA, destinoB);

        if (origen == null || destino == null) {
            System.out.println("--> No se encontro alguna de las intersecciones indicadas.");
            return null;
        }

        for (Calle calle : red.callesDesde(origen.getId())) {
            if (calle.getNombre().equals(nombreCalle) && calle.getDestino().equals(destino.getId())) {
                return calle;
            }
        }

        System.out.println("--> No se encontro la calle '" + nombreCalle + "' entre '"
                + origen.getNombreVisible() + "' y '" + destino.getNombreVisible() + "'.");
        return null;
    }

    /* Describe una calle reemplazando los IDs internos por nombres visibles */
    private String describirCalle(Calle calle) {
        Interseccion origen = intersecciones.buscarPorId(calle.getOrigen());
        Interseccion destino = intersecciones.buscarPorId(calle.getDestino());
        String nombreOrigen = (origen != null) ? origen.getNombreVisible() : calle.getOrigen();
        String nombreDestino = (destino != null) ? destino.getNombreVisible() : calle.getDestino();

        String estado = (calle.getAfectacion() == TipoAfectacion.SIN_AFECTACION)
                ? "sin afectaciones"
                : calle.getAfectacion().getDescripcion() + " (x" + formatear(calle.getAfectacion().getFactorPonderacion()) + ")";

        if (calle.getAfectacion() == TipoAfectacion.SIN_AFECTACION) {
            return calle.getNombre() + " [" + nombreOrigen + " --> " + nombreDestino + "] "
                    + "(tiempo: " + formatear(calle.getTiempoBase()) + ", " + estado + ")";
        }
        return calle.getNombre() + " [" + nombreOrigen + " --> " + nombreDestino + "] "
                + "(tiempo base: " + formatear(calle.getTiempoBase()) + ", " + estado
                + ", tiempo efectivo: " + formatear(calle.getTiempoEfectivo()) + ")";
    }

    private String formatear(double valor) {
        if (valor == Math.floor(valor) && !Double.isInfinite(valor)) {
            return String.valueOf((long) valor);
        }
        return String.valueOf(valor);
    }
}

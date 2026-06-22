package redvial;

import util.Diccionario;
import util.ConjuntoVisitados;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;


public class Grafo {

    private static final int CAPACIDAD_MAX = 500;

    private Diccionario<String, List<Calle>> vertices;

    public Grafo() {
        this.vertices = new Diccionario<>();
    }

    public void agregarVertice(String nombre) {
        if (!vertices.containsKey(nombre)) {
            vertices.put(nombre, new ArrayList<>());
        }
    }

    /*
     * Agrega una calle (arista dirigida) entre dos intersecciones.
     * Pueden existir varias calles entre el mismo par de intersecciones
     */
    public void agregarCalle(Calle calle) {
        agregarVertice(calle.getOrigen());
        agregarVertice(calle.getDestino());
        vertices.get(calle.getOrigen()).add(calle);
    }

    public boolean existeVertice(String nombre) {
        return vertices.containsKey(nombre);
    }

    /* Devuelve todas las calles que salen de una interseccion */
    public List<Calle> callesDesde(String interseccion) {
        List<Calle> lista = vertices.get(interseccion);
        return (lista != null) ? lista : new ArrayList<>();
    }

    /* Devuelve todas las calles cargadas en la red */
    public List<Calle> todasLasCalles() {
        List<Calle> todas = new ArrayList<>();
        for (List<Calle> lista : vertices.values()) {
            todas.addAll(lista);
        }
        return todas;
    }

    /*
     * Verifica mediante BFS si existe una ruta entre dos puntos,
     * considerando unicamente calles transitables (no cortadas).
     * Usa ConjuntoVisitados (arreglo + busqueda lineal) en lugar de HashSet.
     */
    public boolean existeRuta(String origen, String destino) {
        if (!vertices.containsKey(origen) || !vertices.containsKey(destino)) {
            return false;
        }
        if (origen.equals(destino)) {
            return true;
        }

        ConjuntoVisitados visitados = new ConjuntoVisitados(CAPACIDAD_MAX);
        visitados.agregar(origen);
        Queue<String> cola = new LinkedList<>();
        cola.add(origen);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            for (Calle calle : vertices.get(actual)) {
                if (calle.estaCortada()) {
                    continue;
                }
                String vecino = calle.getDestino();
                if (vecino.equals(destino)) {
                    return true;
                }
                if (!visitados.contiene(vecino)) {
                    visitados.agregar(vecino);
                    cola.add(vecino);
                }
            }
        }
        return false;
    }

    /* Resultado del calculo de ruta: secuencia de calles utilizadas y tiempo total */
    public static class ResultadoRuta {
        public final List<Calle> calles;
        public final double tiempoTotal;

        public ResultadoRuta(List<Calle> calles, double tiempoTotal) {
            this.calles = calles;
            this.tiempoTotal = tiempoTotal;
        }

        public boolean existeCamino() {
            return calles != null;
        }
    }

    /**
     * Encuentra TODAS las rutas simples (sin ciclos) entre dos vertices,
     * usando DFS con backtracking. Ignora calles con corte total.
     * Usa ConjuntoVisitados (arreglo + busqueda lineal) en lugar de HashSet.
     */
    public List<List<Calle>> todasLasRutas(String origen, String destino) {
        List<List<Calle>> resultado = new ArrayList<>();
        if (!vertices.containsKey(origen) || !vertices.containsKey(destino)) {
            return resultado;
        }
        ConjuntoVisitados visitados = new ConjuntoVisitados(CAPACIDAD_MAX);
        visitados.agregar(origen);
        dfsTodasLasRutas(origen, destino, visitados, new ArrayList<>(), resultado);
        return resultado;
    }

    private void dfsTodasLasRutas(String actual, String destino,
                                  ConjuntoVisitados visitados, List<Calle> rutaActual,
                                  List<List<Calle>> resultado) {
        if (actual.equals(destino)) {
            resultado.add(new ArrayList<>(rutaActual));
            return;
        }
        List<Calle> callesActual = vertices.get(actual);
        if (callesActual == null) return;
        for (Calle calle : callesActual) {
            if (calle.estaCortada()) continue;
            String vecino = calle.getDestino();
            if (!visitados.contiene(vecino)) {
                visitados.agregar(vecino);
                rutaActual.add(calle);
                dfsTodasLasRutas(vecino, destino, visitados, rutaActual, resultado);
                rutaActual.remove(rutaActual.size() - 1);
                visitados.eliminar(vecino);   // backtracking
            }
        }
    }

    public ResultadoRuta rutaMasRapida(String origen, String destino) {
        if (!vertices.containsKey(origen) || !vertices.containsKey(destino)) {
            return new ResultadoRuta(null, Double.POSITIVE_INFINITY);
        }

        Diccionario<String, Double> tiempos   = new Diccionario<>();
        Diccionario<String, Calle> calleUsada = new Diccionario<>();
        Diccionario<String, String> previos   = new Diccionario<>();

        for (String v : vertices.keys()) {
            tiempos.put(v, Double.POSITIVE_INFINITY);
        }
        tiempos.put(origen, 0.0);

        PriorityQueue<String> heap = new PriorityQueue<>(
                (a, b) -> Double.compare(tiempos.get(a), tiempos.get(b)));
        heap.add(origen);

        ConjuntoVisitados visitados = new ConjuntoVisitados(CAPACIDAD_MAX);

        while (!heap.isEmpty()) {
            String actual = heap.poll();

            if (visitados.contiene(actual)) {
                continue;
            }
            visitados.agregar(actual);

            if (actual.equals(destino)) {
                break;
            }

            List<Calle> callesActual = vertices.get(actual);
            if (callesActual == null) continue;

            for (Calle calle : callesActual) {
                if (calle.estaCortada()) {
                    continue;
                }

                String vecino = calle.getDestino();
                double tiempoArista = calle.getTiempoEfectivo();
                double nuevoTiempo = tiempos.get(actual) + tiempoArista;

                Double tiempoActual = tiempos.get(vecino);
                if (tiempoActual == null || nuevoTiempo < tiempoActual) {
                    tiempos.put(vecino, nuevoTiempo);
                    previos.put(vecino, actual);
                    calleUsada.put(vecino, calle);
                    heap.add(vecino);
                }
            }
        }

        Double tiempoDestino = tiempos.get(destino);
        if (tiempoDestino == null || tiempoDestino == Double.POSITIVE_INFINITY) {
            return new ResultadoRuta(null, Double.POSITIVE_INFINITY);
        }

        LinkedList<Calle> calles = new LinkedList<>();
        String actual = destino;
        while (previos.containsKey(actual)) {
            calles.addFirst(calleUsada.get(actual));
            actual = previos.get(actual);
        }

        return new ResultadoRuta(new ArrayList<>(calles), tiempoDestino);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Object[] entrada : vertices.entradas()) {
            String v = (String) entrada[0];
            @SuppressWarnings("unchecked")
            List<Calle> calles = (List<Calle>) entrada[1];
            if (calles.isEmpty()) {
                sb.append(v).append(" -> (sin calles salientes)\n");
                continue;
            }
            for (Calle calle : calles) {
                sb.append(v).append(" -> ").append(calle.getDestino())
                        .append(" [").append(calle).append("]\n");
            }
        }
        return sb.toString().stripTrailing();
    }
}

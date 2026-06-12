package redvial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/*
 * Utilizado para representar la red vial de la ciudad. Cada
 * interseccion es un vertice, y cada CALLE (con nombre propio, tiempo
 * base y afectacion actual) es una arista dirigida entre dos intersecciones
 *
 * Puede existir mas de una calle entre el mismo par de intersecciones
 * (calles paralelas / alternativas), cada una con su propio tiempo y afectacion.
 *
 * criterio unico de ruta: la "mejor ruta" siempre es la que minimiza
 * el tiempo total de viaje (suma de los tiempos efectivos de cada
 * calle recorrida), sin importar la cantidad de calles ni la
 * distancia recorrida. Una ruta con mas cuadras pero sin afectaciones
 * puede ser mas rapida que una ruta mas corta pero con una calle
 * congestionada o cortada
 */
public class Grafo {

    private Map<String, List<Calle>> vertices;

    public Grafo() {
        this.vertices = new HashMap<>();
    }

    public void agregarVertice(String nombre) {
        vertices.putIfAbsent(nombre, new ArrayList<>());
    }

    /*
     * Agrega una calle (arista dirigida) entre dos intersecciones.
     * Pueden existir varias calles entre el mismo par de intersecciones (calles paralelas / alternativas)
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
        return vertices.getOrDefault(interseccion, new ArrayList<>());
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
     * considerando unicamente calles transitables (no cortadas)
     */
    public boolean existeRuta(String origen, String destino) {
        if (!vertices.containsKey(origen) || !vertices.containsKey(destino)) {
            return false;
        }
        if (origen.equals(destino)) {
            return true;
        }

        Set<String> visitados = new HashSet<>();
        visitados.add(origen);
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
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.add(vecino);
                }
            }
        }
        return false;
    }

    /* Resultado del calculo de ruta: secuencia de calles utilizadas y tiempo total */
    public static class ResultadoRuta {
        public final List<Calle> calles;     // calles recorridas, en orden
        public final double tiempoTotal;      // suma de tiempos efectivos de cada calle

        public ResultadoRuta(List<Calle> calles, double tiempoTotal) {
            this.calles = calles;
            this.tiempoTotal = tiempoTotal;
        }

        public boolean existeCamino() {
            return calles != null;
        }
    }

    public ResultadoRuta rutaMasRapida(String origen, String destino) {
        if (!vertices.containsKey(origen) || !vertices.containsKey(destino)) {
            return new ResultadoRuta(null, Double.POSITIVE_INFINITY);
        }

        Map<String, Double> tiempos = new HashMap<>();
        Map<String, Calle> calleUsada = new HashMap<>();
        Map<String, String> previos = new HashMap<>();

        for (String v : vertices.keySet()) {
            tiempos.put(v, Double.POSITIVE_INFINITY);
        }
        tiempos.put(origen, 0.0);

        PriorityQueue<String> heap = new PriorityQueue<>(
                (a, b) -> Double.compare(tiempos.get(a), tiempos.get(b)));
        heap.add(origen);
        Set<String> visitados = new HashSet<>();

        while (!heap.isEmpty()) {
            String actual = heap.poll();

            if (visitados.contains(actual)) {
                continue;
            }
            visitados.add(actual);

            if (actual.equals(destino)) {
                break;
            }

            for (Calle calle : vertices.get(actual)) {
                // Las calles con corte total se consideran intransitables.
                if (calle.estaCortada()) {
                    continue;
                }

                String vecino = calle.getDestino();
                double tiempoArista = calle.getTiempoEfectivo();
                double nuevoTiempo = tiempos.get(actual) + tiempoArista;

                if (nuevoTiempo < tiempos.get(vecino)) {
                    tiempos.put(vecino, nuevoTiempo);
                    previos.put(vecino, actual);
                    calleUsada.put(vecino, calle);
                    heap.add(vecino);
                }
            }
        }

        if (tiempos.get(destino) == Double.POSITIVE_INFINITY) {
            return new ResultadoRuta(null, Double.POSITIVE_INFINITY);
        }

        LinkedList<Calle> calles = new LinkedList<>();
        String actual = destino;
        while (previos.containsKey(actual)) {
            calles.addFirst(calleUsada.get(actual));
            actual = previos.get(actual);
        }

        return new ResultadoRuta(new ArrayList<>(calles), tiempos.get(destino));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<Calle>> entry : vertices.entrySet()) {
            String v = entry.getKey();
            List<Calle> calles = entry.getValue();
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

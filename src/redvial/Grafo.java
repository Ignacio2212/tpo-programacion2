package redvial;

import util.Diccionario;
import util.ConjuntoVisitados;
import java.util.ArrayList;
import java.util.List;

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
     * Pueden existir varias calles entre el mismo par de intersecciones
     */
    public void agregarCalle(Calle calle) {
        agregarVertice(calle.getOrigen());
        agregarVertice(calle.getDestino());
        vertices.get(calle.getOrigen()).add(calle);
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
/*UTILIZO DFS*/
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


    public List<Calle> rutaMasRapida(String origen, String destino) {
        if (!vertices.containsKey(origen) || !vertices.containsKey(destino)) {
            return null;
        }

        List<List<Calle>> todasLasRutas = todasLasRutas(origen, destino);

        if (todasLasRutas.isEmpty()) {
            return null;
        }

        List<Calle> mejorRuta = todasLasRutas.get(0);
        double menorTiempo = calcularTiempoRuta(mejorRuta);

        for (List<Calle> ruta : todasLasRutas) {
            double tiempoRuta = calcularTiempoRuta(ruta);
            if (tiempoRuta < menorTiempo) {
                menorTiempo = tiempoRuta;
                mejorRuta = ruta;
            }
        }

        return mejorRuta;
    }

    /**
     * Calcula el tiempo total efectivo de una ruta.
     */
    private double calcularTiempoRuta(List<Calle> ruta) {
        double total = 0;
        for (Calle calle : ruta) {
            total += calle.getTiempoEfectivo();
        }
        return total;
    }

    @Override
    public String toString() {
        String resultado = "";
        for (Object[] entrada : vertices.entradas()) {
            String v = (String) entrada[0];
            @SuppressWarnings("unchecked")
            List<Calle> calles = (List<Calle>) entrada[1];

            if (calles.isEmpty()) {
                resultado += v + " -> (sin calles salientes)\n";
            } else {
                for (Calle calle : calles) {
                    resultado += v + " -> " + calle.getDestino() + " [" + calle + "]\n";
                }
            }
        }
        return resultado;
    }
}
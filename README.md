# Sistema Inteligente de Tráfico y Emergencias

## Integrantes del grupo

* Ignacio Maidana
* Joaquín Roitberg
* Agustín Vega


## Alternativa elegida

Sistema Inteligente de Tráfico y Emergencias: administración de la
infraestructura vial y la respuesta ante incidentes en una ciudad
inteligente.

## Estructuras de datos utilizadas

* Grafo (Red Vial): cada vertice es una interseccion (cruce de dos
calles) y cada CALLE (con nombre propio, sentido, tiempo base de
recorrido y una posible afectacion) es una arista dirigida entre dos
intersecciones. Pueden existir varias calles entre el mismo par de
intersecciones (calles alternativas). Mediante Dijkstra, se calcula
la ruta de menor tiempo total (criterio único: tiempo, no distancia
ni cantidad de cuadras).
* Diccionario de Intersecciones (Red Vial, Tabla Hash - HashMap):
indexa cada interseccion por un ID único interno (ej.
INT-001), generado y administrado automaticamente. El usuario
nunca necesita conocer ni usar este ID: siempre identifica una
interseccion por el nombre de sus dos calles (ej. "Independencia y
Lima").
* Cola de Prioridad (Emergencias, PriorityQueue / Heap): gestiona
las emergencias priorizando su nivel de gravedad por sobre el orden
cronologico de reporte.
Diccionario de Dispositivos (Dispositivos, Tabla Hash -
HashMap): indexa los dispositivos urbanos (semaforos y camaras) por
codigo único, permitiendo búsqueda, consulta y actualización de
estado en tiempo constante O(1).


## Funcionalidades implementadas en esta segunda etapa
1. Modelado de la Ciudad / Red Vial y Rutas:
   - Una interseccion es el cruce de dos calles (ej.
   "Independencia y Lima"). Tiene un ID único interno (ej.
   INT-001) que el sistema usa para administrarla como vertice del
   grafo, pero el usuario solo trabaja con calles: para indicar
   un punto de la ciudad, dice "quiero ir a Independencia y Lima",
   nunca un ID. 
   - Cada calle conecta dos intersecciones en un sentido,
   tiene un tiempo base de recorrido y puede tener una
   afectacion activa (corte total/parcial, accidente, obra,
   semáforo roto, tráfico moderado/pesado, etc.), cada una con su
   propio factor de ponderacion que multiplica el tiempo base:
     | Afectación | Factor |
     |---|---|
     | Sin afectación | x1 |
     | Tráfico moderado | x1.5 |
     | Semáforo fuera de servicio | x1.8 |
     | Obra en la vía | x2 |
     | Tráfico pesado | x2.5 |
     | Accidente de tránsito | x3 |
     | Corte parcial de calle | x4 |
     | Corte total de calle | ∞ (intransitable) | 
   
   - Para ir de una interseccion a otra, el sistema funciona como un
mini "Maps": indica la secuencia de calles a recorrer
(ej. "Tomar Independencia desde 'Independencia y Lima' hasta
'Independencia y Salta', luego tomar Salta hasta 'Belgrano y
Salta'", incluso atravesando intersecciones intermedias no
mencionadas por el usuario). 
   - Criterio unico de calculo: el sistema siempre elige la ruta
con el menor tiempo total (suma de los tiempos efectivos de
cada calle = tiempoBase x factorAfectacion), sin importar la
cantidad de cuadras.
Permite registrar nuevas intersecciones (dando el nombre de sus
dos calles), agregar calles entre intersecciones (en un sentido o
de doble mano), aplicar/quitar afectaciones sobre una calle en
cualquier momento, listar las intersecciones registradas
(mostrando también su ID interno, a modo administrativo) y ver el
estado completo de la red vial.


2. Despacho de Emergencias: registrar emergencias, actualizar su
estado y atender primero la de mayor gravedad, sin importar el
orden de llegada (Cola de Prioridad).

3. Indexación de Dispositivos Urbanos: registrar, buscar por
código y actualizar el estado de semáforos y cámaras en tiempo
constante (Diccionario / Tabla Hash).


Todas las funcionalidades pueden probarse desde Main.java, que
incluye un menú interactivo y carga datos de demostración al iniciar
(con el ejemplo de los dos caminos descrito arriba, entre
"Independencia y Lima" y "Belgrano y Salta").

## Link del repositorio

https://github.com/Ignacio2212/tpo-programacion2/blob/main/README.md

## Actividades realizadas por cada integrante

* Ignacio Maidana: implementación de las entidades Calle,
Interseccion y TipoAfectacion, la estructura Grafo y
DiccionarioIntersecciones, y el módulo GestorRedVial para el
modelado de la ciudad y cálculo de rutas (carpeta parte1_red_vial/).
* Joaquín Roitberg: [COMPLETAR: detallar la actividad realizada,
por ejemplo módulo de Emergencias o Dispositivos].
* Agustín Vega: [COMPLETAR: detallar la actividad realizada,
por ejemplo módulo de Emergencias o Dispositivos].

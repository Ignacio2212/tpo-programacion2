import dispositivos.GestorDispositivos;
import emergencias.GestorEmergencias;
import redvial.GestorRedVial;
import redvial.TipoAfectacion;
import territorial.GestorTerritorial;
import vehicular.GestorVehicular;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        GestorRedVial redVial = new GestorRedVial();
        GestorDispositivos dispositivos = new GestorDispositivos();
        GestorEmergencias emergencias = new GestorEmergencias();
        GestorTerritorial territorial = new GestorTerritorial("Ciudad Inteligente");
        GestorVehicular vehicular = new GestorVehicular();

        cargarDatosDemo(redVial, territorial, vehicular, emergencias, dispositivos);

        boolean salir = false;

        while (!salir) {
            System.out.println("\n========================================");
            System.out.println(" SISTEMA INTELIGENTE DE TRAFICO Y EMERGENCIAS");
            System.out.println("========================================");
            System.out.println("1. Modelado de la Ciudad / Red Vial y Rutas");
            System.out.println("2. Despacho de Emergencias");
            System.out.println("3. Indexacion de Dispositivos Urbanos");
            System.out.println("4. Organizacion Territorial");
            System.out.println("5. Flujo Vehicular");
            System.out.println("0. Salir");

            String opcion = leerLinea("Seleccione una opcion: ");

            switch (opcion) {
                case "1":
                    menuRedVial(redVial);
                    break;
                case "2":
                    menuEmergencias(emergencias);
                    break;
                case "3":
                    menuDispositivos(dispositivos);
                    break;
                case "4":
                    menuTerritorial(territorial);
                    break;
                case "5":
                    menuVehicular(vehicular);
                    break;
                case "0":
                    System.out.println("Saliendo del sistema...");
                    salir = true;
                    break;
                default:
                    System.out.println("-> Opcion invalida.");
            }
        }

        scanner.close();
    }

    // ---------------------------------------------------------------
    // Carga de datos de demostracion
    // ---------------------------------------------------------------

    private static void cargarDatosDemo(GestorRedVial redVial,
                                         GestorTerritorial territorial,
                                         GestorVehicular vehicular,
                                         GestorEmergencias emergencias,
                                         GestorDispositivos dispositivos) {

        // --- Red vial ---
        redVial.registrarInterseccion("Independencia", "Lima");
        redVial.registrarInterseccion("Independencia", "Salta");
        redVial.registrarInterseccion("Belgrano", "Lima");
        redVial.registrarInterseccion("Belgrano", "Salta");
        redVial.registrarInterseccion("San Martin", "Salta");

        redVial.agregarCalle("Lima", "Independencia", "Lima", "Belgrano", "Lima", 1);
        redVial.agregarCalle("Belgrano", "Belgrano", "Lima", "Belgrano", "Salta", 5, TipoAfectacion.CORTE_PARCIAL);
        redVial.agregarCalle("Independencia", "Independencia", "Lima", "Independencia", "Salta", 1);
        redVial.agregarCalle("Salta", "Independencia", "Salta", "Belgrano", "Salta", 2);
        redVial.agregarCalle("Salta", "Belgrano", "Salta", "San Martin", "Salta", 1);

        System.out.println("=== Red vial cargada ===");

        // --- Organizacion territorial ---
        territorial.agregarZona("Zona Norte");
        territorial.agregarZona("Zona Sur");

        territorial.agregarBarrio("Zona Norte", "San Telmo");
        territorial.agregarBarrio("Zona Norte", "Montserrat");
        territorial.agregarBarrio("Zona Sur", "Barracas");
        territorial.agregarBarrio("Zona Sur", "La Boca");

        territorial.agregarManzana("San Telmo", "ST-Manzana 1");
        territorial.agregarManzana("San Telmo", "ST-Manzana 2");
        territorial.agregarManzana("Montserrat", "MON-Manzana 1");
        territorial.agregarManzana("Barracas", "BAR-Manzana 1");
        territorial.agregarManzana("La Boca", "LB-Manzana 1");
        territorial.agregarManzana("La Boca", "LB-Manzana 2");

        territorial.registrarDispositivoEn("San Telmo");
        territorial.registrarDispositivoEn("San Telmo");
        territorial.registrarDispositivoEn("Montserrat");
        territorial.registrarDispositivoEn("Barracas");
        territorial.registrarDispositivoEn("LB-Manzana 1");

        System.out.println("=== Organizacion territorial cargada ===");

        // --- Flujo vehicular ---
        vehicular.registrarArribo("Independencia y Lima", "ABC123", "auto");
        vehicular.registrarArribo("Independencia y Lima", "DEF456", "moto");
        vehicular.registrarArribo("Independencia y Lima", "GHI789", "camion");
        vehicular.registrarArribo("Belgrano y Salta",     "JKL012", "auto");
        vehicular.registrarArribo("Belgrano y Salta",     "MNO345", "auto");

        System.out.println("=== Flujo vehicular cargado ===");

        // --- Emergencias precargadas ---
        emergencias.registrarEmergencia("EMG-001", "Choque multiple en avenida principal", 1, "Independencia y Lima");
        emergencias.registrarEmergencia("EMG-002", "Semaforo fuera de servicio",           3, "Belgrano y Salta");
        emergencias.registrarEmergencia("EMG-003", "Incendio en vehiculo estacionado",     2, "San Martin y Salta");
        emergencias.registrarEmergencia("EMG-004", "Persona herida en via publica",        2, "Independencia y Salta");
        emergencias.registrarEmergencia("EMG-005", "Perdida de combustible en calzada",    3, "Belgrano y Lima");

        System.out.println("=== Emergencias precargadas ===");

        // --- Dispositivos precargados ---
        dispositivos.registrarDispositivo("SEM-001", "semaforo", "Independencia y Lima");
        dispositivos.registrarDispositivo("SEM-002", "semaforo", "Independencia y Salta");
        dispositivos.registrarDispositivo("SEM-003", "semaforo", "Belgrano y Lima");
        dispositivos.registrarDispositivo("SEM-004", "semaforo", "Belgrano y Salta");
        dispositivos.registrarDispositivo("SEM-005", "semaforo", "San Martin y Salta");
        dispositivos.registrarDispositivo("CAM-001", "camara",   "Independencia y Lima");
        dispositivos.registrarDispositivo("CAM-002", "camara",   "Belgrano y Salta");
        dispositivos.registrarDispositivo("CAM-003", "camara",   "San Martin y Salta");

        System.out.println("=== Dispositivos precargados ===\n");
    }

    // ---------------------------------------------------------------
    // Menu: Red Vial y Rutas
    // ---------------------------------------------------------------

    private static void menuRedVial(GestorRedVial gestor) {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- Modelado de la Ciudad / Red Vial y Rutas ---");
            System.out.println("(Una interseccion es el cruce de DOS calles, ej: \"Independencia y Lima\")");
            System.out.println("1. Registrar interseccion");
            System.out.println("2. Agregar calle entre dos intersecciones");
            System.out.println("3. Agregar calle de doble mano");
            System.out.println("4. Verificar si existe ruta entre dos intersecciones");
            System.out.println("5. Calcular ruta mas rapida");
            System.out.println("6. Ver estado de todas las calles");
            System.out.println("7. Aplicar afectacion a una calle");
            System.out.println("8. Quitar afectacion de una calle");
            System.out.println("9. Listar intersecciones registradas");
            System.out.println("10. Mostrar red vial completa");
            System.out.println("0. Volver al menu principal");

            String opcion = leerLinea("Seleccione una opcion: ");

            switch (opcion) {
                case "1": {
                    System.out.println("Indique las dos calles que se cruzan:");
                    String calleUno = leerLinea("Calle 1: ");
                    String calleDos = leerLinea("Calle 2: ");
                    gestor.registrarInterseccion(calleUno, calleDos);
                    break;
                }
                case "2": {
                    String nombreCalle = leerLinea("Nombre de la calle: ");
                    System.out.println("Interseccion de origen:");
                    String origenA = leerLinea("   Calle 1: ");
                    String origenB = leerLinea("   Calle 2: ");
                    System.out.println("Interseccion de destino:");
                    String destinoA = leerLinea("   Calle 1: ");
                    String destinoB = leerLinea("   Calle 2: ");
                    Double tiempo = leerDouble("Tiempo base de recorrido: ");
                    if (tiempo == null) break;
                    gestor.agregarCalle(nombreCalle, origenA, origenB, destinoA, destinoB, tiempo);
                    break;
                }
                case "3": {
                    String nombreCalle = leerLinea("Nombre de la calle: ");
                    System.out.println("Interseccion 1:");
                    String unoA = leerLinea("   Calle 1: ");
                    String unoB = leerLinea("   Calle 2: ");
                    System.out.println("Interseccion 2:");
                    String dosA = leerLinea("   Calle 1: ");
                    String dosB = leerLinea("   Calle 2: ");
                    Double tiempo = leerDouble("Tiempo base de recorrido: ");
                    if (tiempo == null) break;
                    gestor.agregarCalleDobleMano(nombreCalle, unoA, unoB, dosA, dosB, tiempo);
                    break;
                }
                case "4": {
                    System.out.println("Interseccion de origen:");
                    String origenA = leerLinea("   Calle 1: ");
                    String origenB = leerLinea("   Calle 2: ");
                    System.out.println("Interseccion de destino:");
                    String destinoA = leerLinea("   Calle 1: ");
                    String destinoB = leerLinea("   Calle 2: ");
                    gestor.existeRuta(origenA, origenB, destinoA, destinoB);
                    break;
                }
                case "5": {
                    System.out.println("Interseccion de origen:");
                    String origenA = leerLinea("   Calle 1: ");
                    String origenB = leerLinea("   Calle 2: ");
                    System.out.println("Interseccion de destino:");
                    String destinoA = leerLinea("   Calle 1: ");
                    String destinoB = leerLinea("   Calle 2: ");
                    gestor.calcularRuta(origenA, origenB, destinoA, destinoB);
                    break;
                }
                case "6":
                    gestor.mostrarEstadoCalles();
                    break;
                case "7": {
                    String nombreCalle = leerLinea("Nombre de la calle: ");
                    System.out.println("Interseccion de origen:");
                    String origenA = leerLinea("   Calle 1: ");
                    String origenB = leerLinea("   Calle 2: ");
                    System.out.println("Interseccion de destino:");
                    String destinoA = leerLinea("   Calle 1: ");
                    String destinoB = leerLinea("   Calle 2: ");
                    TipoAfectacion afectacion = elegirAfectacion();
                    if (afectacion == null) break;
                    gestor.aplicarAfectacion(nombreCalle, origenA, origenB, destinoA, destinoB, afectacion);
                    break;
                }
                case "8": {
                    String nombreCalle = leerLinea("Nombre de la calle: ");
                    System.out.println("Interseccion de origen:");
                    String origenA = leerLinea("   Calle 1: ");
                    String origenB = leerLinea("   Calle 2: ");
                    System.out.println("Interseccion de destino:");
                    String destinoA = leerLinea("   Calle 1: ");
                    String destinoB = leerLinea("   Calle 2: ");
                    gestor.quitarAfectacion(nombreCalle, origenA, origenB, destinoA, destinoB);
                    break;
                }
                case "9":
                    gestor.mostrarIntersecciones();
                    break;
                case "10":
                    gestor.mostrarRed();
                    break;
                case "0":
                    volver = true;
                    break;
                default:
                    System.out.println("-> Opcion invalida.");
            }
        }
    }

    // ---------------------------------------------------------------
    // Menu: Despacho de Emergencias
    // ---------------------------------------------------------------

    private static void menuEmergencias(GestorEmergencias gestor) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Despacho de Emergencias ---");
            System.out.println("1. Registrar emergencia");
            System.out.println("2. Actualizar estado de una emergencia");
            System.out.println("3. Atender siguiente emergencia (mayor prioridad)");
            System.out.println("4. Listar emergencias pendientes (por prioridad)");
            System.out.println("5. Listar todas las emergencias");
            System.out.println("0. Volver al menu principal");
            String opcion = leerLinea("Seleccione una opcion: ");

            switch (opcion) {
                case "1":
                    String codigo = leerLinea("Codigo de la emergencia: ");
                    String descripcion = leerLinea("Descripcion: ");
                    String gravedadStr = leerLinea("Gravedad (1=critica, 2=alta, 3=media, 4=baja): ");
                    int gravedad;
                    try {
                        gravedad = Integer.parseInt(gravedadStr);
                    } catch (NumberFormatException e) {
                        System.out.println("-> Gravedad invalida, debe ser un numero.");
                        continue;
                    }
                    String ubicacion = leerLinea("Ubicacion: ");
                    gestor.registrarEmergencia(codigo, descripcion, gravedad, ubicacion);
                    break;
                case "2":
                    String codigoAct = leerLinea("Codigo de la emergencia: ");
                    String estado = leerLinea("Nuevo estado (pendiente/en_atencion/resuelta): ");
                    gestor.actualizarEstado(codigoAct, estado);
                    break;
                case "3":
                    gestor.atenderSiguiente();
                    break;
                case "4":
                    gestor.listarPendientes();
                    break;
                case "5":
                    gestor.listarTodas();
                    break;
                case "0":
                    volver = true;
                    break;
                default:
                    System.out.println("-> Opcion invalida.");
            }
        }
    }

    // ---------------------------------------------------------------
    // Menu: Indexacion de Dispositivos Urbanos
    // ---------------------------------------------------------------

    private static void menuDispositivos(GestorDispositivos gestor) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Indexacion de Dispositivos Urbanos ---");
            System.out.println("1. Registrar dispositivo");
            System.out.println("2. Buscar dispositivo por codigo");
            System.out.println("3. Actualizar estado de un dispositivo");
            System.out.println("4. Listar todos los dispositivos");
            System.out.println("0. Volver al menu principal");
            String opcion = leerLinea("Seleccione una opcion: ");

            switch (opcion) {
                case "1":
                    String codigo = leerLinea("Codigo del dispositivo: ");
                    String tipo = leerLinea("Tipo (semaforo/camara): ");
                    String ubicacion = leerLinea("Ubicacion: ");
                    gestor.registrarDispositivo(codigo, tipo, ubicacion);
                    break;
                case "2":
                    String codigoBuscar = leerLinea("Codigo a buscar: ");
                    gestor.buscarDispositivo(codigoBuscar);
                    break;
                case "3":
                    String codigoAct = leerLinea("Codigo del dispositivo: ");
                    String estado = leerLinea("Nuevo estado (activo/inactivo/en_mantenimiento): ");
                    gestor.actualizarEstado(codigoAct, estado);
                    break;
                case "4":
                    gestor.listarDispositivos();
                    break;
                case "0":
                    volver = true;
                    break;
                default:
                    System.out.println("-> Opcion invalida.");
            }
        }
    }

    // ---------------------------------------------------------------
    // Menu: Organizacion Territorial
    // ---------------------------------------------------------------

    private static void menuTerritorial(GestorTerritorial gestor) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Organizacion Territorial ---");
            System.out.println("(Jerarquia: Ciudad > Zona > Barrio > Manzana)");
            System.out.println("1. Agregar zona");
            System.out.println("2. Agregar barrio a una zona");
            System.out.println("3. Agregar manzana a un barrio");
            System.out.println("4. Registrar dispositivo en una unidad territorial");
            System.out.println("5. Reporte estadistico completo");
            System.out.println("6. Reporte estadistico de una zona/barrio/manzana");
            System.out.println("0. Volver al menu principal");
            String opcion = leerLinea("Seleccione una opcion: ");

            switch (opcion) {
                case "1":
                    String zona = leerLinea("Nombre de la zona: ");
                    gestor.agregarZona(zona);
                    break;
                case "2":
                    String zonaPadre = leerLinea("Nombre de la zona: ");
                    String barrio = leerLinea("Nombre del barrio: ");
                    gestor.agregarBarrio(zonaPadre, barrio);
                    break;
                case "3":
                    String barrioPadre = leerLinea("Nombre del barrio: ");
                    String manzana = leerLinea("Nombre/numero de la manzana: ");
                    gestor.agregarManzana(barrioPadre, manzana);
                    break;
                case "4":
                    String unidad = leerLinea("Nombre de la zona/barrio/manzana: ");
                    gestor.registrarDispositivoEn(unidad);
                    break;
                case "5":
                    gestor.reporteCompleto();
                    break;
                case "6":
                    String nombre = leerLinea("Nombre de la zona/barrio/manzana: ");
                    gestor.reporteDe(nombre);
                    break;
                case "0":
                    volver = true;
                    break;
                default:
                    System.out.println("-> Opcion invalida.");
            }
        }
    }

    // ---------------------------------------------------------------
    // Menu: Flujo Vehicular
    // ---------------------------------------------------------------

    private static void menuVehicular(GestorVehicular gestor) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Flujo Vehicular ---");
            System.out.println("(Los vehiculos se liberan en el orden en que llegaron)");
            System.out.println("1. Registrar arribo de un vehiculo a una interseccion");
            System.out.println("2. Liberar el siguiente vehiculo de una interseccion");
            System.out.println("3. Liberar todos los vehiculos de una interseccion");
            System.out.println("4. Ver cola de una interseccion");
            System.out.println("5. Ver colas de todas las intersecciones");
            System.out.println("0. Volver al menu principal");
            String opcion = leerLinea("Seleccione una opcion: ");

            switch (opcion) {
                case "1":
                    String interseccion = leerLinea("Nombre de la interseccion (ej: Independencia y Lima): ");
                    String patente = leerLinea("Patente del vehiculo: ");
                    String tipo = leerLinea("Tipo de vehiculo (auto/moto/camion/bus): ");
                    gestor.registrarArribo(interseccion, patente, tipo);
                    break;
                case "2":
                    String intLiberar = leerLinea("Nombre de la interseccion: ");
                    gestor.liberarSiguiente(intLiberar);
                    break;
                case "3":
                    String intTodos = leerLinea("Nombre de la interseccion: ");
                    gestor.liberarTodos(intTodos);
                    break;
                case "4":
                    String intVer = leerLinea("Nombre de la interseccion: ");
                    gestor.mostrarCola(intVer);
                    break;
                case "5":
                    gestor.mostrarTodasLasColas();
                    break;
                case "0":
                    volver = true;
                    break;
                default:
                    System.out.println("-> Opcion invalida.");
            }
        }
    }

    // ---------------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------------

    private static TipoAfectacion elegirAfectacion() {
        TipoAfectacion[] opciones = TipoAfectacion.values();
        System.out.println("Tipos de afectacion disponibles:");
        for (int i = 0; i < opciones.length; i++) {
            TipoAfectacion af = opciones[i];
            System.out.println((i + 1) + ". " + af.getDescripcion()
                    + " (factor x" + formatearFactor(af.getFactorPonderacion()) + ")");
        }
        Integer indice = leerEntero("Seleccione el tipo de afectacion: ");
        if (indice == null || indice < 1 || indice > opciones.length) {
            System.out.println("-> Opcion invalida.");
            return null;
        }
        return opciones[indice - 1];
    }

    private static String formatearFactor(double valor) {
        if (valor >= 1_000_000) return "INF";
        if (valor == Math.floor(valor)) return String.valueOf((long) valor);
        return String.valueOf(valor);
    }

    private static String leerLinea(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private static Double leerDouble(String mensaje) {
        String texto = leerLinea(mensaje);
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            System.out.println("-> Valor invalido, debe ser un numero.");
            return null;
        }
    }

    private static Integer leerEntero(String mensaje) {
        String texto = leerLinea(mensaje);
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            System.out.println("-> Valor invalido, debe ser un numero entero.");
            return null;
        }
    }
}

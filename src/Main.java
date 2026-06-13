import emergencias.GestorEmergencias;
import redvial.GestorRedVial;
import redvial.TipoAfectacion;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        GestorRedVial redVial = new GestorRedVial();

        cargarDatosDemo(redVial);

        boolean salir = false;

        while (!salir) {
            System.out.println("\nSISTEMA INTELIGENTE DE TRAFICO Y EMERGENCIAS");
            System.out.println("1. Modelado de la Ciudad / Red Vial y Rutas");
            System.out.println("0. Salir");

            String opcion = leerLinea("Seleccione una opcion: ");

            switch (opcion) {
                case "1":
                    menuRedVial(redVial);
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

    private static void cargarDatosDemo(GestorRedVial redVial) {
        redVial.registrarInterseccion("Independencia", "Lima");
        redVial.registrarInterseccion("Independencia", "Salta");
        redVial.registrarInterseccion("Belgrano", "Lima");
        redVial.registrarInterseccion("Belgrano", "Salta");
        redVial.registrarInterseccion("San Martin", "Salta");

        redVial.agregarCalle("Lima", "Independencia", "Lima", "Belgrano", "Lima", 1);

        redVial.agregarCalle(
                "Belgrano",
                "Belgrano",
                "Lima",
                "Belgrano",
                "Salta",
                5,
                TipoAfectacion.CORTE_PARCIAL
        );

        redVial.agregarCalle("Independencia", "Independencia", "Lima", "Independencia", "Salta", 1);
        redVial.agregarCalle("Salta", "Independencia", "Salta", "Belgrano", "Salta", 2);
        redVial.agregarCalle("Salta", "Belgrano", "Salta", "San Martin", "Salta", 1);

        System.out.println("\n=== Datos de demostracion de Red Vial cargados correctamente ===\n");
    }

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
                    System.out.println("Indique las dos calles que se cruzan en esta interseccion:");
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
                    if (tiempo == null) {
                        break;
                    }

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
                    if (tiempo == null) {
                        break;
                    }

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
                    if (afectacion == null) {
                        break;
                    }

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

    private static TipoAfectacion elegirAfectacion() {
        TipoAfectacion[] opciones = TipoAfectacion.values();

        System.out.println("Tipos de afectacion disponibles:");

        for (int i = 0; i < opciones.length; i++) {
            TipoAfectacion afectacion = opciones[i];
            System.out.println(
                    (i + 1) + ". " + afectacion.getDescripcion()
                            + " (factor x" + formatearFactor(afectacion.getFactorPonderacion()) + ")"
            );
        }

        Integer indice = leerEntero("Seleccione el tipo de afectacion: ");

        if (indice == null || indice < 1 || indice > opciones.length) {
            System.out.println("-> Opcion invalida.");
            return null;
        }

        return opciones[indice - 1];
    }

    private static String formatearFactor(double valor) {
        if (valor >= 1_000_000) {
            return "INF";
        }

        if (valor == Math.floor(valor)) {
            return String.valueOf((long) valor);
        }

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
    private static void menuEmergencias(GestorEmergencias gestor) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n Despacho de Emergencias ");
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
                    String codigoActualizar = leerLinea("Codigo de la emergencia: ");
                    String estado = leerLinea("Nuevo estado (pendiente/en_atencion/resuelta): ");
                    gestor.actualizarEstado(codigoActualizar, estado);
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
}
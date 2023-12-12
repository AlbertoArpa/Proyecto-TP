import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Clase principal de Planet Express App, la práctica de Taller de Programación
 *
 * @author Taller de Progamación
 * @version 1.0
 */
public class PlanetExpress {
    private final int maxPortes;
    private final int maxNaves;
    private final int maxClientes;
    private final int maxEnviosPorCliente;
    private ListaPuertosEspaciales listaPuertosEspaciales;
    private final int maxPuertosEspaciales;
    private ListaNaves listaNaves;
    private ListaClientes listaClientes;
    private ListaPortes listaPortes;


    /**
     * TODO: Rellene el constructor de la clase
     *
     * @param maxPuertosEspaciales Máximo número de puertos espaciales que tendrá la lista de puertos espaciales de PlanetExpress App.
     * @param maxNaves             Máximo número de naves que tendrá la lista de naves de PlanetExpress App.
     * @param maxPortes            Máximo número de portes que tendrá la lista de portes de PlanetExpress App.
     * @param maxClientes          Máximo número de clientes que tendrá la lista de clientes de PlanetExpress App.
     * @param maxEnviosPorCliente  Máximo número de envíos por cliente.
     */
    public PlanetExpress(int maxPuertosEspaciales, int maxNaves, int maxPortes, int maxClientes, int maxEnviosPorCliente) {
        this.maxPuertosEspaciales = maxPuertosEspaciales;
        this.maxNaves = maxNaves;
        this.maxPortes = maxPortes;
        this.maxClientes = maxClientes;
        this.maxEnviosPorCliente = maxEnviosPorCliente;
        cargarDatos("puertos.csv", "naves.csv", "portes.csv", "clientes.csv", "envios.csv");
    }

    /**
     * TODO: Metodo para leer los datos de los ficheros específicados en el enunciado y los agrega a
     *  la información de PlanetExpress (listaPuertosEspaciales, listaNaves, listaPortes, listaClientes)
     *
     * @param ficheroPuertos
     * @param ficheroNaves
     * @param ficheroPortes
     * @param ficheroClientes
     * @param ficheroEnvios
     */
    public void cargarDatos(String ficheroPuertos, String ficheroNaves, String ficheroPortes, String ficheroClientes, String ficheroEnvios) {
        this.listaPuertosEspaciales = ListaPuertosEspaciales.leerPuertosEspacialesCsv(ficheroPuertos, maxPuertosEspaciales);
        this.listaNaves = ListaNaves.leerNavesCsv(ficheroNaves, maxNaves);
        this.listaPortes = ListaPortes.leerPortesCsv(ficheroPortes, maxPortes, listaPuertosEspaciales, listaNaves);
        this.listaClientes = ListaClientes.leerClientesCsv(ficheroClientes, maxClientes, maxEnviosPorCliente);
        ListaEnvios.leerEnviosCsv(ficheroEnvios, listaPortes, listaClientes);
    }

    /**
     * TODO: Metodo para almacenar los datos de PlanetExpress en los ficheros .csv especificados
     *  en el enunciado de la práctica
     *
     * @param ficheroPuertos
     * @param ficheroNaves
     * @param ficheroPortes
     * @param ficheroClientes
     * @param ficheroEnvios
     */
    public void guardarDatos(String ficheroPuertos, String ficheroNaves, String ficheroPortes, String ficheroClientes, String ficheroEnvios) {
        listaPuertosEspaciales.escribirPuertosEspacialesCsv(ficheroPuertos);
        listaNaves.escribirNavesCsv(ficheroNaves);
        listaPortes.escribirPortesCsv(ficheroPortes);
        listaClientes.escribirClientesCsv(ficheroClientes);
        PrintWriter out = null;
        try {
            out = new PrintWriter(ficheroEnvios);
            for (int i = 0; i < listaPortes.getOcupacion(); i++) {
                listaPortes.getPorte(i).getListaEnvios().aniadirEnviosCsv(ficheroEnvios);
            }
        } catch (IOException e) {
            System.out.println("Error de escritura en fichero Envios.");
        } finally {
            if (out != null) out.close();
        }
    }

    public boolean maxPortesAlcanzado() {
        return listaPortes.estaLlena();
    }

    public boolean insertarPorte(Porte porte) {
        return listaPortes.insertarPorte(porte);
    }

    public boolean maxClientesAlcanzado() {
        return listaClientes.estaLlena();
    }

    public boolean insertarCliente(Cliente cliente) {
        return listaClientes.insertarCliente(cliente);
    }

    /**
     * TODO: Metodo para buscar los portes especificados tal y como aparece en el enunciado de la práctica,
     *  Devuelve una lista de los portes entre dos puertos espaciales con una fecha de salida solicitados por teclado
     *  al usuario en el orden y con los textos establecidos (tomar como referencia los ejemplos de ejecución en el
     *  enunciado de la prática)
     *
     * @param teclado
     * @return
     */
    public ListaPortes buscarPorte(Scanner teclado) {
        PuertoEspacial puertoEspacialOrigen = listaPuertosEspaciales.seleccionarPuertoEspacial(teclado, "Ingrese código de Puerto Origen:");
        PuertoEspacial puertoEspacialDestino = listaPuertosEspaciales.seleccionarPuertoEspacial(teclado, "Ingrese código de Puerto Destino:");
        Fecha fecha = Utilidades.leerFecha(teclado, "Fecha de Salida:");

        return listaPortes.buscarPortes(puertoEspacialOrigen.getCodigo(), puertoEspacialDestino.getCodigo(), fecha);
    }

    /**
     * TODO: Metodo para contratar un envio tal y como se indica en el enunciado de la práctica. Se contrata un envio para un porte
     *  especificado, pidiendo por teclado los datos necesarios al usuario en el orden y con los textos (tomar como referencia los
     *  ejemplos de ejecución en el enunciado de la prática)
     *
     * @param teclado
     * @param rand
     * @param porte
     */
    public void contratarEnvio(Scanner teclado, Random rand, Porte porte) {
        char letra;
        Cliente cliente = null;
        if (listaClientes.estaLlena()) letra = 'e';
        else if (listaClientes.getOcupacion() == 0) letra = 'n';
        else if (porte.porteLleno()) {
            System.out.println("\tNo quedan huecos en el porte\n");
            letra = 'z';
        } else
            letra = Utilidades.leerLetra(teclado, "¿Comprar billete para un nuevo pasajero (n), o para uno ya existente (e)?", 'a', 'z');
        while (letra != 'n' && letra != 'e' && letra != 'z')
            letra = Utilidades.leerLetra(teclado, "\tEl valor de entrada debe ser 'n' o 'e'\n¿Comprar billete para un nuevo pasajero (n), o para uno ya existente (e)?", 'a', 'z');
        switch (letra) {
            case 'n':
                cliente = Cliente.altaCliente(teclado, listaClientes, maxEnviosPorCliente);
                insertarCliente(cliente);
                Envio.altaEnvio(teclado, rand, porte, cliente);
                break;
            case 'e':
                cliente = listaClientes.seleccionarCliente(teclado, "Email del cliente:");
                if (cliente.maxEnviosAlcanzado())
                    System.out.println("\tSe ha alcanzado el máximo de envíos para el cliente");
                else Envio.altaEnvio(teclado, rand, porte, cliente);
                break;
            case 'z':
                break;
        }
    }

    /**
     * TODO Metodo statico con la interfaz del menú de entrada a la App.
     * Tiene que coincidir con las trazas de ejecución que se muestran en el enunciado
     *
     * @param teclado
     * @return opción seleccionada
     */
    public static int menu(Scanner teclado) {
        System.out.println("1. Alta de Porte");
        System.out.println("2. Alta de Cliente");
        System.out.println("3. Buscar Porte");
        System.out.println("4. Mostrar envíos de un cliente");
        System.out.println("5. Generar lista de envíos");
        System.out.println("0. Salir");
        return Utilidades.leerNumero(teclado, "Seleccione opción:", 0, 5);
    }

    /**
     * TODO: Método Main que carga los datos de los ficheros CSV pasados por argumento (consola) en PlanetExpress,
     *  llama iterativamente al menú y realiza la opción especificada hasta que se indique la opción Salir. Al finalizar
     *  guarda los datos de PlanetExpress en los mismos ficheros CSV.
     *
     * @param args argumentos de la línea de comandos, recibe **10 argumentos** estrictamente en el siguiente orden:
     *             1. Número máximo de puertos espaciales que tendrá la lista de puertos espaciales de PlanetExpress App.
     *             2. Número máximo de naves que tendrá la lista de naves de PlanetExpress App.
     *             3. Número máximo de portes que tendrá la lita de portes de PlanetExpress App.
     *             4. Número máximo de clientes que tendrá la lista de clientes de PlanetExpress App.
     *             5. Número máximo de envíos por pasajero.
     *             6. Nombre del fichero CSV que contiene la lista de puertos espaciales de PlanetExpress App (tanto para lectura como escritura).
     *             7. Nombre del fichero CSV que contiene la lista de naves de PlanetExpress App (tanto para lectura como escritura).
     *             8. Nombre del fichero CSV que contiene la lista de portes de PlanetExpress App (tanto para lectura como escritura).
     *             9. Nombre del fichero CSV que contiene la lista de clientes de PlanetExpress App (tanto para lectura como escritura).
     *             10. Nombre del fichero CSV que contiene los envíos adquiridos en PlanetExpress App (tanto para lectura como escritura).
     *             En el caso de que no se reciban exactamente estos argumentos, el programa mostrará el siguiente mensaje
     *             y concluirá la ejecución del mismo: `Número de argumentos incorrecto`.
     */
    public static void main(String[] args) {
        if (args.length != 10) {
            System.out.println("Número de argumentos incorrecto");
            return;
        }
        Scanner teclado = new Scanner(System.in);
        Random random = new Random();
        PlanetExpress planetExpress = new PlanetExpress(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
        int opcion;
        do {
            opcion = menu(teclado);
            switch (opcion) {
                case 1: // TODO: Alta de Porte
                    if (planetExpress.listaPortes.estaLlena()) {
                        System.out.println("No se pueden dar de alta más portes.");
                    } else {
                        Porte porte = Porte.altaPorte(teclado, random, planetExpress.listaPuertosEspaciales, planetExpress.listaNaves, planetExpress.listaPortes);
                        planetExpress.listaPortes.insertarPorte(porte);
                    }
                    break;
                case 2: // TODO: Alta de Cliente
                    if (planetExpress.listaClientes.estaLlena()) {
                        System.out.println("No se pueden dar de alta más clientes.");
                    } else {
                        Cliente cliente = Cliente.altaCliente(teclado, planetExpress.listaClientes, planetExpress.maxEnviosPorCliente);
                        planetExpress.listaClientes.insertarCliente(cliente);
                    }
                    break;
                case 3: // TODO: Buscar Porte
                    ListaPortes imprimir = planetExpress.buscarPorte(teclado);
                    if (imprimir.getOcupacion() == 0) {
                        System.out.println("Porte no encontrado.");
                    } else {
                        Porte porte = planetExpress.listaPortes.seleccionarPorte(teclado, "Seleccione un porte:", "CANCELAR");
                        planetExpress.contratarEnvio(teclado, random, porte);
                    }
                    break;
                case 4:  // TODO: Listado de envíos de un cliente
                    boolean repetir = true;
                    Cliente cliente = planetExpress.listaClientes.seleccionarCliente(teclado, "Email del cliente: ");
                    if (cliente.getListaEnvios().getOcupacion() == 0) {
                        System.out.println("\tEl cliente seleccionado no ha adquirido ningún envío.");
                    } else {
                        cliente.listarEnvios();
                        Envio envio = cliente.seleccionarEnvio(teclado, "Seleccione un envío: ");
                        do {
                            switch (Utilidades.leerLetra(teclado, "¿Cancelar envío (c), o generar factura (f)?", 'a', 'z')) {
                                case 'c':
                                    String localizador = envio.getLocalizador();
                                    if (envio.cancelar()) System.out.println("\tEnvío " + localizador + " cancelado");
                                    else System.out.println("\tNo se ha podido cancelar el envío " + localizador);
                                    repetir = false;
                                    break;
                                case 'f':
                                    envio.generarFactura(Utilidades.leerCadena(teclado, "Nombre del fichero: "));
                                    repetir = false;
                                    break;
                                default:
                                    System.out.println("\tEl valor de entrada debe ser 'f' o 'c'");
                            }
                        } while (repetir);
                    }
                    break;
                case 5: // TODO: Lista de envíos de un porte
                    Porte porte = planetExpress.listaPortes.seleccionarPorte(teclado, "Seleccione un porte: ", "CANCELAR");
                    String ruta = Utilidades.leerCadena(teclado, "Nombre del fichero: ");
                    if (porte.generarListaEnvios(ruta)) System.out.println("Fichero creado correctamente");
                    break;
                case 0:
                    break;
            }
        } while (opcion != 0);
        planetExpress.guardarDatos(args[5], args[6], args[7], args[8], args[9]); //todo: revisar aquí.
    }
}

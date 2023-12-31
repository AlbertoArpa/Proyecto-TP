import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Vuelo es una clase que encapsula las variables correspondientes para
 * definir un vuel.
 *
 * @author Pedro Fernández-Caballero Zamorano
 * @author Alberto Arpa Hervás
 * @version 1.0
 */
public class Porte {
    private boolean[][] huecos;
    private String id;
    private Nave nave;
    private PuertoEspacial origen;
    private int muelleOrigen;
    private Fecha salida;
    private PuertoEspacial destino;
    private int muelleDestino;
    private Fecha llegada;
    private double precio;
    private ListaEnvios listaEnvios;

    Scanner teclado = new Scanner(System.in);

    /**
     * Constructor de la clase Porte.
     *
     * @param id            Identificador del porte.
     * @param nave          Nave utilizada en el porte.
     * @param origen        Puerto espacial de origen.
     * @param muelleOrigen  Número de muelle en el puerto espacial de origen.
     * @param salida        Fecha y hora de salida del porte.
     * @param destino       Puerto espacial de destino.
     * @param muelleDestino Número de muelle en el puerto espacial de destino.
     * @param llegada       Fecha y hora de llegada del porte.
     * @param precio        Precio del porte.
     */
    public Porte(String id, Nave nave, PuertoEspacial origen, int muelleOrigen, Fecha salida, PuertoEspacial destino, int muelleDestino, Fecha llegada, double precio) {
        this.id = id;
        this.nave = nave;
        this.origen = origen;
        this.muelleOrigen = muelleOrigen;
        this.salida = salida;
        this.destino = destino;
        this.muelleDestino = muelleDestino;
        this.llegada = llegada;
        this.precio = precio;
        huecos = new boolean[10][6];
        listaEnvios = new ListaEnvios(getFilas() * getColumnas());
    }

    /**
     * Obtiene el identificador único del porte.
     *
     * @return Identificador único del porte.
     */
    public String getID() {
        return id;
    }

    /**
     * Obtiene la nave utilizada en el porte.
     *
     * @return Objeto de la clase Nave asociado al porte.
     */
    public Nave getNave() {
        return nave;
    }

    /**
     * Obtiene el puerto espacial de origen asociado al porte.
     *
     * @return Objeto de la clase PuertoEspacial que representa el puerto espacial de origen.
     */
    public PuertoEspacial getOrigen() {
        return origen;
    }

    /**
     * Obtiene el número de muelle de origen asociado al porte.
     *
     * @return Número de muelle de origen.
     */
    public int getMuelleOrigen() {
        return muelleOrigen;
    }

    /**
     * Obtiene la fecha y hora de salida asociada al porte.
     *
     * @return Objeto de la clase Fecha que representa la fecha y hora de salida.
     */
    public Fecha getSalida() {
        return salida;
    }

    /**
     * Obtiene el puerto espacial de destino asociado al porte.
     *
     * @return Objeto de la clase PuertoEspacial que representa el puerto espacial de destino.
     */
    public PuertoEspacial getDestino() {
        return destino;
    }

    /**
     * Obtiene el número de muelle de destino asociado al porte.
     *
     * @return Número de muelle de destino.
     */
    public int getMuelleDestino() {
        return muelleDestino;
    }

    /**
     * Obtiene la fecha y hora de llegada asociada al porte.
     *
     * @return Objeto de la clase Fecha que representa la fecha y hora de llegada.
     */
    public Fecha getLlegada() {
        return llegada;
    }

    /**
     * Obtiene el precio asociado al porte.
     *
     * @return Precio asociado al porte.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Devuelve el número de huecos libres en el porte.
     *
     * @return Número de huecos libres.
     */
    // TODO: Devuelve el número de huecos libres que hay en el porte
    public int numHuecosLibres() {
        int result = 0;
        for (int i = 0; i < huecos.length; i++) {
            for (int j = 0; j < huecos[i].length; j++) {
                if (!huecos[i][j]) result++;
            }
        }
        return result;
    }

    /**
     * Verifica si todos los huecos del porte están ocupados.
     *
     * @return True si todos los huecos están ocupados, false de lo contrario.
     */
    // TODO: ¿Están llenos todos los huecos?
    public boolean porteLleno() {
        return numHuecosLibres() == 0;
    }

    /**
     * Verifica si un hueco en la posición dada está ocupado.
     *
     * @param fila    Fila del hueco.
     * @param columna Columna del hueco.
     * @return True si el hueco está ocupado, false de lo contrario.
     */
    // TODO: ¿Está ocupado el hueco consultado?
    public boolean huecoOcupado(int fila, int columna) {
        return huecos[fila][columna];
    }

    /**
     * Busca un envío por su localizador.
     *
     * @param localizador Localizador del envío.
     * @return Objeto de la clase Envio si encontrado, null de lo contrario.
     */
    public Envio buscarEnvio(String localizador) {
        return listaEnvios.buscarEnvio(localizador);
    }

    /**
     * Devuelve el objeto Envio que corresponde con una fila o columna,
     *
     * @param fila    Fila del envío.
     * @param columna Columna del envío.
     * @return Objeto de la clase Envio si encontrado, null de lo contrario.
     */
    public Envio buscarEnvio(int fila, int columna) {
        Envio result = null;
        int i = 0;
        while (i < listaEnvios.getOcupacion() && listaEnvios.getEnvio(i).getFila() != fila && listaEnvios.getEnvio(i).getColumna() != columna) {
            i++;
        }
        if (listaEnvios.getEnvio(i) != null && listaEnvios.getEnvio(i).getFila() == fila && listaEnvios.getEnvio(i).getColumna() == columna)
            result = listaEnvios.getEnvio(i);
        return result;
    }


    /**
     * Método que Si está desocupado el hueco que indica el envio, lo pone ocupado y devuelve true,
     * si no devuelve false
     *
     * @param envio Objeto de la clase Envio a ocupar.
     * @return True si el hueco se ocupó correctamente, false de lo contrario.
     */
    public boolean ocuparHueco(Envio envio) {
        boolean result = false;
        if (!huecos[envio.getFila() - 1][envio.getColumna() - 1]) {
            huecos[envio.getFila() - 1][envio.getColumna() - 1] = true;
            result = true;
        }
        return result;
    }

    /**
     * A través del localizador del envio, se desocupará el hueco
     *
     * @param localizador Localizador del envío.
     * @return True si el hueco se desocupó correctamente, false de lo contrario.
     */
    public boolean desocuparHueco(String localizador) {
        boolean result = false;
        if (huecos[buscarEnvio(localizador).getFila()][buscarEnvio(localizador).getColumna()]) {
            huecos[buscarEnvio(localizador).getFila()][buscarEnvio(localizador).getColumna()] = false;
            result = true;
        }
        return result;
    }

    /**
     * Devuelve una cadena con información completa del porte
     *
     * @return ejemplo del formato -> "Porte PM0066 de Gaia Galactic Terminal(GGT) M5 (01/01/2023 08:15:00) a
     * Cidonia(CID) M1 (01/01/2024 11:00:05) en Planet Express One(EP-245732X) por 13424,56 SSD, huecos libres: 10"
     */
    public String toString() {
        return "Porte " + id + " de " + origen.toStringSimple() + " M" + muelleOrigen + " (" + salida + ") a " +
                destino.toStringSimple() + " M" + muelleDestino + " (" + llegada + ") en " +
                nave.toStringSimple() + " por " + precio + " SSD, huecos libres: " + numHuecosLibres();
    }


    /**
     * Devuelve una cadena con información abreviada del vuelo
     *
     * @return ejemplo del formato -> "Porte PM0066 de GGT M5 (01/01/2023 08:15:00) a CID M1 (01/01/2024 11:00:05)"
     */
    public String toStringSimple() {
        return "Porte " + id + " de " + origen.getCodigo() + " M" + muelleOrigen + " (" + salida + ") a " +
                destino.getCodigo() + " M" + muelleDestino + " (" + llegada + ")";
    }

    /**
     * Devuelve true si el código origen, destino y fecha son los mismos que el porte
     *
     * @param codigoOrigen  Código del puerto espacial de origen.
     * @param codigoDestino Código del puerto espacial de destino.
     * @param fecha         Fecha a comparar (puede ser la fecha de salida o llegada).
     * @return True si los códigos de origen y destino, así como la fecha, coinciden con el porte; false de lo contrario.
     */
    public boolean coincide(String codigoOrigen, String codigoDestino, Fecha fecha) {

        return codigoOrigen.equals(origen.getCodigo()) && codigoDestino.equals(destino.getCodigo()) && (fecha == salida || fecha == llegada);
    }

    /**
     *  Muestra la matriz de huecos del porte, ejemplo:
     *        A  B  C
     *      1[ ][ ][ ]
     *      2[X][X][X]
     *      3[ ][ ][ ]
     *      4[ ][X][ ]
     *     10[ ][ ][ ]
     */
    public void imprimirMatrizHuecos() {
        System.out.print("\t ");
        for (int i = 0; i < huecos[0].length; i++) {
            System.out.print("  " + (char) ((i) + 'A'));
        }
        System.out.print("\n");
        for (int j = 0; j < huecos.length; j++) {
            System.out.printf("\t%2s", j + 1);
            for (int k = 0; k < huecos[j].length; k++) {
                if (huecos[j][k]) System.out.print("[X]");
                else System.out.print("[ ]");
            }
            System.out.print("\n");
        }
    }

    /**
     * TODO: Devuelve true si ha podido escribir en un fichero la lista de envíos del porte, siguiendo las indicaciones
     *  del enunciado
     *
     * @param fichero Nombre del fichero a generar.
     * @return True si se generó el fichero correctamente, false si hubo un error al generar el fichero.
     */
    public boolean generarListaEnvios(String fichero) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(fichero);
            pw.println("--------------------------------------------------\n-------- Lista de envíos del porte " +
                    id + " --------\n--------------------------------------------------\nHueco\tCliente");
            for (int i = 1; i < huecos.length; i++) {
                for (int j = 0; j < huecos[i].length; j++) {
                    pw.print(i + "" + (char) (j + 'A'));
                    if (buscarEnvio(i, j + 1) != null) pw.print("\t\t" + buscarEnvio(i, j + 1).getCliente().toString());
                    pw.print("\n");
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }


    /**
     * Genera un ID de porte. Este consistirá en una cadena de 6 caracteres, de los cuales los dos primeros
     * serán PM y los 4 siguientes serán números aleatorios.
     * NOTA: Usar el objeto rand pasado como argumento para la parte aleatoria.
     *
     * @param rand
     * @return ejemplo -> "PM0123"
     */
    public static String generarID(Random rand) {
        String result = "PM";
        for (int i = 1; i <= 4; i++) result += (rand.nextInt(10));
        result = result.replace("-","");
        return result.replace("-", "");
    }

    /**
     * Crea y devuelve un objeto Porte de los datos que selecciona el usuario de puertos espaciales
     * y naves y la restricción de que no puede estar repetido el identificador, siguiendo las indicaciones
     * del enunciado
     * La función solicita repetidamente los parametros hasta que sean correctos
     *
     * @param teclado           Objeto de la clase Scanner para la entrada de datos.
     * @param rand              Objeto de la clase Random para generación de ID.
     * @param puertosEspaciales Lista de puertos espaciales disponibles.
     * @param naves             Lista de naves disponibles.
     * @param portes            Lista de portes existentes.
     * @return Objeto de la clase Porte creado.
     */
    public static Porte altaPorte(Scanner teclado, Random rand,
                                  ListaPuertosEspaciales puertosEspaciales,
                                  ListaNaves naves,
                                  ListaPortes portes) {
        PuertoEspacial origen = puertosEspaciales.seleccionarPuertoEspacial(teclado, "Ingrese código de puerto Origen: ");
        int muelleOrigen = Utilidades.leerNumero(teclado, "Ingrese el muelle de Origen (1 - " + origen.getMuelles() + "):", 1, origen.getMuelles());
        PuertoEspacial destino = puertosEspaciales.seleccionarPuertoEspacial(teclado, "Ingrese código de puerto Destino:");
        int muelleDestino = Utilidades.leerNumero(teclado, "Ingrese Terminal Destino (1 - " + destino.getMuelles() + "):", 1, destino.getMuelles());
        naves.mostrarNaves();
        Nave nave = naves.seleccionarNave(teclado, "Ingrese matrícula de la nave: ", origen.distancia(destino));
        Fecha salida = Utilidades.leerFechaHora(teclado, "Introduzca la fecha de salida:");
        Fecha llegada = Utilidades.leerFechaHora(teclado, "Introduzca la fecha de llegada:");
        while (llegada.anterior(salida)) {
            System.out.println("Llegada debe ser posterior a salida.");
            salida = Utilidades.leerFechaHora(teclado, "Introduzca la fecha de salida:");
            llegada = Utilidades.leerFechaHora(teclado, "Introduzca la fecha de llegada:");
        }
        double precio = Utilidades.leerNumero(teclado, "Ingrese precio del pasaje:", 0, 9999.99);
        String id = generarID(rand);
        System.out.println("\tPorte " + id + " creado correctamente\n");
        return new Porte(id, nave, origen, muelleOrigen, salida, destino, muelleDestino, llegada, precio);
    }

    /**
     * Obtiene la lista de envíos asociada al porte.
     *
     * @return Objeto de la clase ListaEnvios que contiene la información de los envíos realizados en el porte.
     */
    public ListaEnvios getListaEnvios() {
        return listaEnvios;
    }

    /**
     * Obtiene el número de filas en la matriz de huecos del porte.
     *
     * @return Número de filas en la matriz de huecos.
     */
    public int getFilas() {
        return huecos.length;
    }

    /**
     * Obtiene el número de columnas en la matriz de huecos del porte.
     *
     * @return Número de columnas en la matriz de huecos.
     */
    public int getColumnas() {
        return huecos[0].length;
    }
}
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author
 * @author
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
     * TODO: Completa el constructo de la clase
     *
     * @param id
     * @param nave
     * @param origen
     * @param muelleOrigen
     * @param salida
     * @param destino
     * @param muelleDestino
     * @param llegada
     * @param precio
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

    public String getID() {
        return id;
    }

    public Nave getNave() {
        return nave;
    }

    public PuertoEspacial getOrigen() {
        return origen;
    }

    public int getMuelleOrigen() {
        return muelleOrigen;
    }

    public Fecha getSalida() {
        return salida;
    }

    public PuertoEspacial getDestino() {
        return destino;
    }

    public int getMuelleDestino() {
        return muelleDestino;
    }

    public Fecha getLlegada() {
        return llegada;
    }

    public double getPrecio() {
        return precio;
    }

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

    // TODO: ¿Están llenos todos los huecos?
    public boolean porteLleno() {
        return numHuecosLibres() == 0;
    }

    // TODO: ¿Está ocupado el hueco consultado?
    public boolean huecoOcupado(int fila, int columna) {
        return huecos[fila][columna];
    }

    public Envio buscarEnvio(String localizador) {
        return listaEnvios.buscarEnvio(localizador);
    }

    /**
     * TODO: Devuelve el objeto Envio que corresponde con una fila o columna,
     *
     * @param fila
     * @param columna
     * @return el objeto Envio que corresponde, o null si está libre o se excede en el límite de fila y columna
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
     * TODO: Método que Si está desocupado el hueco que indica el envio, lo pone ocupado y devuelve true,
     *  si no devuelve false
     *
     * @param envio
     * @return
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
     * TODO: A través del localizador del envio, se desocupará el hueco
     *
     * @param localizador
     * @return
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
     * TODO: Devuelve una cadena con información completa del porte
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
     * TODO: Devuelve una cadena con información abreviada del vuelo
     *
     * @return ejemplo del formato -> "Porte PM0066 de GGT M5 (01/01/2023 08:15:00) a CID M1 (01/01/2024 11:00:05)"
     */
    public String toStringSimple() {
        return "Porte " + id + " de " + origen.getCodigo() + " M" + muelleOrigen + " (" + salida + ") a " +
                destino.getCodigo() + " M" + muelleDestino + " (" + llegada + ")";
    }

    /**
     * TODO: Devuelve true si el código origen, destino y fecha son los mismos que el porte
     *
     * @param codigoOrigen
     * @param codigoDestino
     * @param fecha
     * @return
     */
    public boolean coincide(String codigoOrigen, String codigoDestino, Fecha fecha) {

        return codigoOrigen == origen.getCodigo() && codigoDestino == destino.getCodigo() && (fecha == salida || fecha == llegada);
    }

    /**
     * TODO: Muestra la matriz de huecos del porte, ejemplo:
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
            System.out.print("  " + ((char) (i + 1) + 'A'));
        }
        System.out.print("\n");
        for (int j = 0; j < huecos.length; j++) {
            System.out.printf("%2s", j + 1);
            for (int k = 0; k < huecos[j].length; k++) {
                if (huecos[j][k]) System.out.print("[X]");
                else System.out.print("[ ]");
            }
        }
    }

    /**
     * TODO: Devuelve true si ha podido escribir en un fichero la lista de envíos del porte, siguiendo las indicaciones
     *  del enunciado
     *
     * @param fichero
     * @return
     */
    public boolean generarListaEnvios(String fichero) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(fichero);
            pw.println("--------------------------------------------------\n-------- Lista de envíos del porte " +
                    id + " --------\n--------------------------------------------------\nHueco\tCliente");
            for (int i = 0; i < huecos.length; i++) {
                for (int j = 0; j < huecos[i].length; j++) {
                    pw.print(i + 1 + "" + (char) (j + 'A'));
                    if (buscarEnvio(i, j) != null) pw.print("\t" + buscarEnvio(i, j).getCliente().toString());
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
     * TODO: Genera un ID de porte. Este consistirá en una cadena de 6 caracteres, de los cuales los dos primeros
     *  serán PM y los 4 siguientes serán números aleatorios.
     *  NOTA: Usar el objeto rand pasado como argumento para la parte aleatoria.
     *
     * @param rand
     * @return ejemplo -> "PM0123"
     */
    public static String generarID(Random rand) {
        StringBuilder result = new StringBuilder("PM");
        for (int i = 1; i <= 4; i++) result.append(rand.nextInt(10));
        return result.toString();
    }

    /**
     * TODO: Crea y devuelve un objeto Porte de los datos que selecciona el usuario de puertos espaciales
     *  y naves y la restricción de que no puede estar repetido el identificador, siguiendo las indicaciones
     *  del enunciado
     *  La función solicita repetidamente los parametros hasta que sean correctos
     *
     * @param teclado
     * @param rand
     * @param puertosEspaciales
     * @param naves
     * @param portes
     * @return
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

    public ListaEnvios getListaEnvios() {
        return listaEnvios;
    }

    public int getFilas() {
        return huecos.length;
    }

    public int getColumnas() {
        return huecos[0].length;
    }
}
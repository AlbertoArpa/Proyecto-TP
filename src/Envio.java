import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Envío es una clase que encapsula las variables correspondientes para
 * definir a un envío.
 *
 * @author Pedro Fernández-Caballero Zamorano
 * @author Alberto Arpa Hervas
 * @version 1.0
 */
public class Envio {

    /**
     * Localizador del Billete.
     */
    private String localizador;

    /**
     * Porte.
     */
    private Porte porte;

    /**
     * Cliente al que pertenece el Envío.
     */
    private Cliente cliente;

    /**
     * fila.
     */
    private int fila;

    /**
     * columna.
     */
    private int columna;

    /**
     * Precio del Envío.
     */
    private double precio;

    /**
     * Constructor de la clase Envio.
     *
     * @param localizador El localizador único del envío.
     * @param porte       El porte asociado al envío.
     * @param cliente     El cliente que realiza el envío.
     * @param fila        La fila del hueco en el muelle.
     * @param columna     La columna del hueco en el muelle.
     * @param precio      El precio del envío.
     */
    public Envio(String localizador, Porte porte, Cliente cliente, int fila, int columna, double precio) {
        this.localizador = localizador;
        this.porte = porte;
        this.cliente = cliente;
        this.fila = fila;
        this.columna = columna;
        this.precio = precio;
    }

    /**
     * Obtiene el localizador del envío.
     *
     * @return El localizador del envío.
     */
    public String getLocalizador() {
        return localizador;
    }

    /**
     * Obtiene el porte asociado al envío.
     *
     * @return El porte asociado al envío.
     */
    public Porte getPorte() {
        return porte;
    }

    /**
     * Obtiene el cliente que realiza el envío.
     *
     * @return El cliente que realiza el envío.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Obtiene la fila del hueco en el muelle.
     *
     * @return La fila del hueco en el muelle.
     */
    public int getFila() {
        return fila;
    }

    /**
     * Obtiene la columna del hueco en el muelle.
     *
     * @return La columna del hueco en el muelle.
     */
    public int getColumna() {
        return columna;
    }

    /**
     * Obtiene el hueco en formato de cadena (ejemplos: "1A", "3D").
     *
     * @return El hueco en formato de cadena.
     */
    // TODO: Ejemplos: "1A" para el hueco con fila 1 y columna 1, "3D" para el hueco con fila 3 y columna 4
    public String getHueco() {
        return fila + "" + (char) ((columna) + 'A' - 1);
    }

    /**
     * Obtiene el precio del envío.
     *
     * @return El precio del envío.
     */
    public double getPrecio() {
        return precio;
    }

    //TODO: Texto que debe generar: Envío PM1111AAAABBBBC para Porte PM0066 de GGT M5 (01/01/2023 08:15:00) a CID M1 (01/01/2024 11:00:05) en hueco 6C por 13424,56 SSD
    public String toString() {
        return "Envío " + localizador + " para Porte " + porte.getID() + " de " + porte.getOrigen().toStringSimple() + " M" + porte.getMuelleOrigen() + " (" + porte.getSalida() + ") a " +
                porte.getDestino().toStringSimple() + " M" + porte.getMuelleDestino() + " (" + porte.getDestino() + ") en hueco " + getHueco() + " por " + precio + " SSD";
    }

    /**
     * Cancela este envío, eliminándolo de la lista de envíos del porte y del cliente correspondiente.
     *
     * @return `true` si la cancelación fue exitosa, `false` en caso contrario.
     */
    // TODO: Cancela este envío, eliminándolo de la lista de envíos del porte y del cliente correspondiente
    public boolean cancelar() {
        porte.getListaEnvios().eliminarEnvio(localizador);
        return cliente.cancelarEnvio(localizador);
    }

    /**
     * TODO: Método para imprimir la información de este envío en un fichero que respecta el formato descrito en el
     *  enunciado
     *
     * @param fichero
     * @return Devuelve la información con el siguiente formato como ejemplo ->
     * -----------------------------------------------------
     * --------- Factura del envío PM1111AAAABBBBC ---------
     * -----------------------------------------------------
     * Porte: PM0066
     * Origen: Gaia Galactic Terminal (GGT) M5
     * Destino: Cidonia (CID) M1
     * Salida: 01/01/2023 08:15:00
     * Llegada: 01/01/2024 11:00:05
     * Cliente: Zapp Brannigan, zapp.brannigan@dop.gov
     * Hueco: 6C
     * Precio: 13424,56 SSD
     */
    public boolean generarFactura(String fichero) {
        PrintWriter salida = null;
        try {
            salida = new PrintWriter(fichero);
            salida.println("-----------------------------------------------------\n--------- Factura del envío " +
                    localizador + " ---------\n-----------------------------------------------------\nPorte: " + porte.getID() +
                    "\nOrigen: " + porte.getOrigen().toStringSimple() + " M" + porte.getMuelleOrigen() +
                    "\nDestino: " + porte.getDestino().toStringSimple() + " M" + porte.getMuelleDestino() +
                    "\nSalida: " + porte.getSalida() +
                    "\nLlegada: " + porte.getLlegada() +
                    "\nCliente: " + cliente.toString() +
                    "\nHueco: " + getHueco());
            salida.printf("Precio %.2f SSD", precio);
            System.out.println("\tFactura generada correctamente");
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } finally {
            if (salida != null) salida.close();
        }
    }

    /**
     * Genera un localizador de envío. Este consistirá en una cadena de 15 caracteres, de los cuales los seis
     * primeros será el ID del porte asociado y los 9 siguientes serán letras mayúsculas aleatorias. Ejemplo: PM0123ABCD
     * NOTA: Usar el objeto rand pasado como argumento para la parte aleatoria.
     *
     * @param rand    El objeto Random utilizado para generar letras aleatorias.
     * @param idPorte El ID del porte asociado al envío.
     * @return El localizador generado.
     */
    public static String generarLocalizador(Random rand, String idPorte) {
        StringBuilder localizador = new StringBuilder(idPorte);
        for (int i = 1; i <= 9; i++) {
            localizador.append((char) (rand.nextInt('Z' - 'A' + 1) + 'A'));
        }
        return localizador.toString();
    }


    /**
     * Método para crear un nuevo envío para un porte y cliente específico, pidiendo por teclado los datos
     * necesarios al usuario en el orden y con los textos indicados en los ejemplos de ejecución del enunciado.
     * La función solicita repetidamente los parámetros hasta que sean correctos
     *
     * @param teclado El objeto Scanner utilizado para la entrada de datos.
     * @param rand    El objeto Random utilizado para generar el localizador.
     * @param porte   El porte asociado al envío.
     * @param cliente El cliente que realiza el envío.
     * @return El envío creado.
     */
    public static Envio altaEnvio(Scanner teclado, Random rand, Porte porte, Cliente cliente) {
        String localizador = generarLocalizador(rand, porte.getID());
        int fila = Utilidades.leerNumero(teclado, "Fila del hueco:", 1, porte.getFilas());
        int columna = Utilidades.leerNumero(teclado, "Columna del hueco:", 1, porte.getColumnas());
        double precio = Utilidades.leerNumero(teclado, "Precio del envío:", 0, 9999.99);
        System.out.println("\tEnvío " + localizador + " creado correctamente\n");
        Envio envio = new Envio(localizador, porte, cliente, fila, columna, precio);
        cliente.aniadirEnvio(envio);
        porte.getListaEnvios().insertarEnvio(envio);
        porte.ocuparHueco(envio);
        return envio;
    }
}
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author
 * @author
 * @version 1.0
 */
public class ListaEnvios {
    private Envio[] envios;

    /**
     * TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad
     */
    public ListaEnvios(int capacidad) {
        envios = new Envio[capacidad];
    }

    // TODO: Devuelve el número de envíos que hay en la lista
    public int getOcupacion() {
        int i = 0;
        while (i < envios.length && envios[i] != null) i++;
        return i;
    }

    // TODO: ¿Está llena la lista de envíos?
    public boolean estaLlena() {
        return envios[envios.length - 1] != null;
    }

    //TODO: Devuelve el envio dado un indice
    public Envio getEnvio(int i) {
        return envios[i];
    }

    /**
     * TODO: insertamos un nuevo envío en la lista
     *
     * @param envio
     * @return true en caso de que se añada correctamente, false en caso de lista llena o error
     */
    public boolean insertarEnvio(Envio envio) {
        int i = 0;
        boolean result = false;
        while ((envios[i] != null) && i < envios.length - 1) i++;
        if (envios[i] == null) {
            envios[i] = envio;
            result = true;
        }
        return result;
    }

    /**
     * TODO: Buscamos el envio a partir del localizador pasado como parámetro
     *
     * @param localizador
     * @return el envio que encontramos o null si no existe
     */
    public Envio buscarEnvio(String localizador) {
        int i = 0;
        while (i < envios.length && localizador != envios[i].getLocalizador()) i++;
        Envio result;
        if (envios[i].getLocalizador() == localizador) result = envios[i];
        else result = null;
        return result;
    }

    /**
     * TODO: Buscamos el envio a partir del idPorte, fila y columna pasados como parámetros
     *
     * @param idPorte
     * @param fila
     * @param columna
     * @return el envio que encontramos o null si no existe
     */
    public Envio buscarEnvio(String idPorte, int fila, int columna) {
        Envio result = null;
        int i = 0;
        while (i < envios.length && envios[i].getPorte().getID() != idPorte && envios[i].getFila() != fila && envios[i].getColumna() != columna)
            i++;
        if (envios[i].getPorte().getID() == idPorte && envios[i].getFila() == fila && envios[i].getColumna() == columna)
            result = envios[i];
        return result;
    }

    /**
     * TODO: Eliminamos un envio a través del localizador pasado por parámetro
     *
     * @param localizador
     * @return True si se ha borrado correctamente, false en cualquier otro caso
     */
    public boolean eliminarEnvio(String localizador) {
        int i = 0;
        while ((localizador != envios[i].getLocalizador()) && i < envios.length - 1) i++;
        boolean result = false;
        if (envios[i].getLocalizador() == localizador) {
            envios[i] = null;
            for (int j = i; j < envios.length - 1; j++) envios[j] = envios[j + 1];
            result = true;
        }
        return result;
    }

    /**
     * TODO: Muestra por pantalla los Envios de la lista, con el formato que aparece
     * en el enunciado
     */
    public void listarEnvios() {
        for (int i = 0; i < envios.length; i++) {
            System.out.println("\tPorte " + envios[i].getPorte().getID() + " de " + envios[i].getPorte().getOrigen().getNombre() + "(" + envios[i].getPorte().getOrigen().getCodigo() + ") M" + envios[i].getPorte().getMuelleOrigen() + " (" + envios[i].getPorte().getSalida() +
                    ") a " + envios[i].getPorte().getDestino().getNombre() + "(" + envios[i].getPorte().getDestino().getCodigo() + ") M" + envios[i].getPorte().getMuelleDestino() + " (" + envios[i].getPorte().getLlegada() + ")");
        }
    }

    /**
     * TODO: Permite seleccionar un Envio existente a partir de su localizador, usando el mensaje pasado como argumento
     *  para la solicitud y siguiendo el orden y los textos mostrados en el enunciado
     *  La función solicita repetidamente hasta que se introduzca un localizador correcto
     *
     * @param teclado
     * @param mensaje
     * @return
     */
    public Envio seleccionarEnvio(Scanner teclado, String mensaje) {
        Envio envio = null;
        int i = 0;
        String localizador = Utilidades.leerCadena(teclado, mensaje);
        while (i < envios.length - 1 && envios[i].getLocalizador() != localizador) {
            i++;
        }
        if (envios[i].getLocalizador() == localizador) envio = envios[i];
        return envio;
    }


    /**
     * TODO: Añade los Envios al final de un fichero CSV, SIN SOBREESCRIBIR la información
     *
     * @param fichero
     * @return
     */
    public boolean aniadirEnviosCsv(String fichero) {

        try {
            PrintWriter pw = new PrintWriter(fichero);
            for (int i = 0; i < envios.length; i++) {
                pw.append("\n" + envios[i].getLocalizador() + ";" + envios[i].getPorte().getID() + ";" + envios[i].getCliente().getEmail() + ";" + envios[i].getFila() + ";" + envios[i].getColumna() + ";" + envios[i].getPrecio());
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {

        }
    }

    /**
     * TODO: Lee los Envios del fichero CSV y los añade a las listas de sus respectivos Portes y Clientes
     *
     * @param ficheroEnvios
     * @param portes
     * @param clientes
     */
    public static void leerEnviosCsv(String ficheroEnvios, ListaPortes portes, ListaClientes clientes) {
        Scanner sc = null;
        try {

        } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado el fichero de envíos");
        } finally {

        }
    }
}

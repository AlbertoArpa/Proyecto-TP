import java.io.*;
import java.util.Scanner;

/**
 * ListaEnvíos es una clase que encapsula las variables correspondientes para
 * definir la lista de envío.
 *
 * @author Pedro Fernández-Caballero Zamorano
 * @author Alberto Arpa Hervas
 * @version 1.0
 */
public class ListaEnvios {
    private Envio[] envios;

    /**
     * Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad La capacidad inicial de la lista de envíos.
     */
    public ListaEnvios(int capacidad) {
        envios = new Envio[capacidad];
    }

    /**
     * Obtiene el número de envíos que hay en la lista.
     *
     * @return El número de envíos en la lista.
     */
    // TODO: Devuelve el número de envíos que hay en la lista
    public int getOcupacion() {
        int i = 0;
        while (i < envios.length && envios[i] != null) i++;
        return i;
    }

    /**
     * Verifica si la lista de envíos está llena.
     *
     * @return `true` si la lista está llena, `false` en caso contrario.
     */
    // TODO: ¿Está llena la lista de envíos?
    public boolean estaLlena() {
        return envios[envios.length - 1] != null;
    }

    /**
     * Devuelve el envío en la posición especificada por el índice.
     *
     * @param i El índice del envío en la lista.
     * @return El envío en la posición indicada.
     */
    //TODO: Devuelve el envio dado un indice
    public Envio getEnvio(int i) {
        return envios[i];
    }

    /**
     * Insertamos un nuevo envío en la lista
     *
     * @param envio El envío a insertar.
     * @return `true` si la inserción fue exitosa, `false` en caso contrario.
     */
    public boolean insertarEnvio(Envio envio) {
        boolean result = false;
        if (!estaLlena()) {
            envios[getOcupacion()] = envio;
            result = true;
        }
        return result;
    }

    /**
     * Busca el envio a partir del localizador pasado como parámetro
     *
     * @param localizador El localizador del envío a buscar.
     * @return El envío encontrado, o `null` si no se encontró ningún envío con el localizador especificado.
     */
    public Envio buscarEnvio(String localizador) {
        Envio result = null;
        int i = 0;
        while (i < getOcupacion() - 1 && !localizador.equals(envios[i].getLocalizador())) i++;
        if (envios[i].getLocalizador().equals(localizador)) result = envios[i];
        return result;
    }

    /**
     * Busca el envio a partir del idPorte, fila y columna pasados como parámetros
     *
     * @param idPorte El ID del porte del envío a buscar.
     * @param fila    La fila del hueco del envío.
     * @param columna La columna del hueco del envío.
     * @return El envío encontrado, o `null` si no se encontró ningún envío con los parámetros especificados.
     */
    public Envio buscarEnvio(String idPorte, int fila, int columna) {
        Envio result = null;
        int i = 0;
        while (i < getOcupacion() - 1 && !envios[i].getPorte().getID().equals(idPorte) && envios[i].getFila() != fila && envios[i].getColumna() != columna)
            i++;
        if (envios[i].getPorte().getID().equals(idPorte) && envios[i].getFila() == fila && envios[i].getColumna() == columna)
            result = envios[i];
        return result;
    }

    /**
     * Eliminamos un envio a través del localizador pasado por parámetro
     *
     * @param localizador El localizador del envío a eliminar.
     * @return `true` si el envío se eliminó correctamente, `false` en caso contrario.
     */
    public boolean eliminarEnvio(String localizador) {
        int i = 0;
        while ((!localizador.equals(envios[i].getLocalizador())) && i < envios.length - 1) i++;
        boolean result = false;
        if (envios[i].getLocalizador().equals(localizador)) {
            envios[i] = null;
            for (int j = i; j < envios.length - 1; j++) envios[j] = envios[j + 1];
            result = true;
        }
        return result;
    }

    /**
     * Muestra por pantalla los Envios de la lista, con el formato que aparece
     * en el enunciado
     */
    public void listarEnvios() {
        for (int i = 0; i < getOcupacion(); i++) {
            System.out.println("\tEnvío " + envios[i].getLocalizador() + " para " + envios[i].getPorte().toStringSimple() +
                    " en hueco " + envios[i].getHueco() + " por " + envios[i].getPrecio() + " SSD");
        }
        /*//No podría hacerse así?
        System.out.println(envios[i]);

        no porque el toString() de Envio es diferente a listarEnvios()*/
    }

    /**
     * Permite seleccionar un Envio existente a partir de su localizador, usando el mensaje pasado como argumento
     * para la solicitud y siguiendo el orden y los textos mostrados en el enunciado
     * La función solicita repetidamente hasta que se introduzca un localizador correcto
     *
     * @param teclado El objeto Scanner utilizado para la entrada de datos.
     * @param mensaje El mensaje a mostrar para solicitar el localizador del envío.
     * @return El envío seleccionado.
     */
    public Envio seleccionarEnvio(Scanner teclado, String mensaje) {
        Envio envio = buscarEnvio(Utilidades.leerCadena(teclado, mensaje));
        while (envio == null)
            envio = buscarEnvio(Utilidades.leerCadena(teclado, "\tLocalizador incorrecto\n" + mensaje));
        return envio;
    }


    /**
     * : Añade los Envios al final de un fichero CSV, SIN SOBREESCRIBIR la información
     *
     * @param fichero La ruta del archivo CSV donde se añadirán los envíos.
     * @return `true` si la adición fue exitosa, `false` en caso contrario.
     */
    public boolean aniadirEnviosCsv(String fichero) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(fichero);
            for (int i = 0; i < getOcupacion(); i++) {
                pw.append("\n" + envios[i].getLocalizador() + ";" + envios[i].getPorte().getID() + ";" + envios[i].getCliente().getEmail() + ";" + envios[i].getFila() + ";" + envios[i].getColumna() + ";" + envios[i].getPrecio());
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error de escritura en fichero Envíos.");
            return false;
        } finally {
            if (pw != null) pw.close();
        }
    }

    /**
     * Lee los Envios del fichero CSV y los añade a las listas de sus respectivos Portes y Clientes
     *
     * @param ficheroEnvios La ruta del archivo CSV que contiene la información de los envíos.
     * @param portes        La lista de portes a la que se añadirán los envíos.
     * @param clientes      La lista de clientes a la que se añadirán los envíos.
     */
    public static void leerEnviosCsv(String ficheroEnvios, ListaPortes portes, ListaClientes clientes) {
        BufferedReader out = null;
        try {
            out = new BufferedReader(new FileReader(ficheroEnvios));
            String linea = out.readLine();
            while (linea != null) {
                String[] datos = linea.split(";");
                Porte porte = portes.buscarPorte(datos[1]);
                Envio envio = new Envio(datos[0], portes.buscarPorte(datos[1]), clientes.buscarClienteEmail(datos[2]), Integer.parseInt(datos[3]), Integer.parseInt(datos[4]), Double.parseDouble(datos[5]));
                porte.getListaEnvios().insertarEnvio(envio);
                porte.ocuparHueco(envio);
                clientes.buscarClienteEmail(datos[2]).aniadirEnvio(envio);
                linea = out.readLine();
            }
        } catch (IOException ex) {
            System.out.println("Error de lectura de fichero Envios.");
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                System.out.println("Error de cierre de fichero Envios.");
            }
        }
    }
}

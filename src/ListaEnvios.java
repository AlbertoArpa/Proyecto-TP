import java.io.*;
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
        boolean result = false;
        if (!estaLlena()) {
            envios[getOcupacion()] = envio;
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
        Envio envio = null;
        int i = 0;
        while (i < getOcupacion()) {
            if (envios[i].getLocalizador().equals(localizador)) {
                envio = envios[i];
            }
            i++;
        }
        return envio;
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
        boolean resultado = true;
        Envio envio = null;
        int i = 0;
        while (i < getOcupacion() && resultado) {
            if (envios[i].getPorte().getID().equals(idPorte) && envios[i].getFila() == fila && envios[i].getColumna() == columna) {
                envio = envios[i];
                resultado = false;
            }
        }
        return envio;
    }

    /**
     * TODO: Eliminamos un envio a través del localizador pasado por parámetro
     *
     * @param localizador
     * @return True si se ha borrado correctamente, false en cualquier otro caso
     */
    public boolean eliminarEnvio(String localizador) {
        boolean eliminado = false;
        for (int i = 0; i < getOcupacion(); i++) {
            if (envios[i].getLocalizador().equals(localizador)) {
                for (int j = i + 1; j < getOcupacion(); j++) {
                    envios[j] = envios[i];
                }
                eliminado = true;
            }
        }
        return eliminado;
    }

    /**
     * TODO: Muestra por pantalla los Envios de la lista, con el formato que aparece
     * en el enunciado
     */
    public void listarEnvios() {
        for (int i = 0; i < envios.length; i++) {
            System.out.println("\tEnvío " + envios[i].getLocalizador() + " para " + envios[i].getPorte().toStringSimple() +
                    "en hueco " + envios[i].getHueco() + " por " + envios[i].getPrecio() + " SSD");

            /*//No podría hacerse así?
            System.out.println(envios[i]);*/
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
        Envio envio;
        do {
            System.out.print(mensaje);
            String loc = teclado.nextLine();
            envio = buscarEnvio(loc);
            if (envio == null) {
                System.out.println("Localizador no encontrado.");
            }
        } while (envio == null);
        return envio;
    }

    /**
     * TODO: Añade los Envios al final de un fichero CSV, SIN SOBREESCRIBIR la información
     *
     * @param fichero
     * @return
     */
    public boolean aniadirEnviosCsv(String fichero) {
        boolean escrito = true;
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(fichero, true));
            for (int i = 0; i < getOcupacion(); i++) {
                pw.append("\n" + envios[i].getLocalizador() + ";" + envios[i].getPorte().getID() + ";" + envios[i].getCliente().getEmail() + ";" + envios[i].getFila() + ";" + envios[i].getColumna() + ";" + envios[i].getPrecio());
            }
            escrito = true;
        } catch (Exception e) {
            System.out.println("Error de escritura en fichero Envíos.");
            escrito = false;
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
        return escrito;
    }

    /**
     * TODO: Lee los Envios del fichero CSV y los añade a las listas de sus respectivos Portes y Clientes
     *
     * @param ficheroEnvios
     * @param portes
     * @param clientes
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

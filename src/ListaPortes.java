import java.io.*;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author
 * @author
 * @version 1.0
 */
public class ListaPortes {
    private Porte[] portes;

    /**
     * TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad
     */
    public ListaPortes(int capacidad) {
        portes = new Porte[capacidad];
    }

    // TODO: Devuelve el número de portes que hay en la lista
    public int getOcupacion() {
        int i = 0;
        while (i < portes.length && portes[i] != null) i++;
        return i;
    }

    // TODO: ¿Está llena la lista?
    public boolean estaLlena() {
        return portes[portes.length - 1] != null;
    }

    //TODO: devuelve un porte dado un indice
    public Porte getPorte(int i) {
        return portes[i];
    }


    /**
     * TODO: Devuelve true si puede insertar el porte
     *
     * @param porte
     * @return false en caso de estar llena la lista o de error
     */
    public boolean insertarPorte(Porte porte) {
        boolean result = false;
        if (!estaLlena()) {
            portes[getOcupacion()] = porte;
            result = true;
        }
        return result;
    }

    /**
     * TODO: Devuelve el objeto Porte que tenga el identificador igual al parámetro id
     *
     * @param id
     * @return el objeto Porte que encontramos o null si no existe
     */
    public Porte buscarPorte(String id) {
        boolean resultado = true;
        Porte porte = null;
        int i = 0;
        while (i < getOcupacion() && resultado){
            if (portes[i].getID().equals(id)) {
                porte = portes[i];
                resultado = false;
            }
            i++;
        }
        return porte;
    }

    /**
     * TODO: Devuelve un nuevo objeto ListaPortes conteniendo los Portes que vayan de un puerto espacial a otro
     *  en una determinada fecha
     *
     * @param codigoOrigen
     * @param codigoDestino
     * @param fecha
     * @return
     */
    public ListaPortes buscarPortes(String codigoOrigen, String codigoDestino, Fecha fecha) {
        ListaPortes listaPortes = new ListaPortes(portes.length);
        for (int i = 0; i < getOcupacion(); i++) {
            if (portes[i].coincide(codigoOrigen, codigoDestino, fecha)) {
                listaPortes.insertarPorte(portes[i]);
            }
        }
        return listaPortes;
    }

    /**
     * TODO: Muestra por pantalla los Portes siguiendo el formato de los ejemplos del enunciado
     */
    public void listarPortes() {
        for (int i = 0; i < portes.length; i++)
            System.out.println("Porte " + portes[i].getID() + " de " + portes[i].getOrigen().getNombre() + "(" + portes[i].getOrigen().getCodigo() + ") M" + " (" + portes[i].getSalida() +
                    ") a " + portes[i].getDestino().getNombre() + "(" + portes[i].getDestino().getCodigo() + ") M" + portes[i].getLlegada() + ")");
        /*//Tendría que hacerse así?
        for (int i = 0; i < getOcupacion(); i++) {
            System.out.println(portes[i].toString());
        }*/
    }

    /**
     * TODO: Permite seleccionar un Porte existente a partir de su ID, usando el mensaje pasado como argumento para
     *  la solicitud y siguiendo el orden y los textos mostrados en el enunciado, y usando la cadena cancelar para
     *  salir devolviendo null.
     *  La función solicita repetidamente hasta que se introduzca un ID correcto
     *
     * @param teclado
     * @param mensaje
     * @param cancelar
     * @return
     */
    public Porte seleccionarPorte(Scanner teclado, String mensaje, String cancelar) {
        Porte porte = null;
        int i = 0;
        boolean sigue = true;
        boolean sigueteclado = true;
        System.out.print(mensaje);
        String linea = teclado.nextLine();
        do {
            if (!linea.toUpperCase().equals(cancelar.toUpperCase())) {
                while (i < getOcupacion() && sigue) {
                    if (portes[i].getID().equals(linea)) {
                        porte = portes[i];
                        sigue = false;
                        sigueteclado = false;
                    }
                    i++;
                }
                if (porte == null) {
                    System.out.println("ID de porte no encontrado.");
                    System.out.print(mensaje);
                    linea = teclado.nextLine();
                }
            } else {
                sigueteclado = false;
            }
        } while (sigueteclado);
        return porte;
    }

    /**
     * TODO: Ha de escribir la lista de Portes en la ruta y nombre del fichero pasado como parámetro.
     *  Si existe el fichero, SE SOBREESCRIBE, si no existe se crea.
     *
     * @param fichero
     * @return
     */
    public boolean escribirPortesCsv(String fichero) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(fichero);
            for (int i = 0; i < getOcupacion(); i++) {
                System.out.println(portes[i].getID() + ";" + portes[i].getNave().getMatricula() + ";" + portes[i].getOrigen().getCodigo() + ";" + portes[i].getMuelleOrigen() + ";" + portes[i].getSalida() +
                        ";" + portes[i].getDestino().getCodigo() + ";" + portes[i].getMuelleDestino() + ";" + portes[i].getLlegada() + ";" + portes[i].getPrecio());
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } finally {
            if (pw != null) pw.close();
        }
    }

    /**
     * TODO: Genera una lista de Portes a partir del fichero CSV, usando los límites especificados como argumentos para
     *  la capacidad de la lista
     *
     * @param fichero
     * @param capacidad
     * @param puertosEspaciales
     * @param naves
     * @return
     */
    public static ListaPortes leerPortesCsv(String fichero, int capacidad, ListaPuertosEspaciales puertosEspaciales, ListaNaves naves) {
        BufferedReader in = null;
        ListaPortes listaPortes = new ListaPortes(capacidad);
        try {
            in = new BufferedReader(new FileReader(fichero));
            String linea = in.readLine();
            while (linea != null) {
                String[] datos = linea.split(";");
                Porte porte = new Porte(datos[0], naves.buscarNave(datos[1]), puertosEspaciales.buscarPuertoEspacial(datos[2]), Integer.parseInt(datos[3]), Fecha.fromString(datos[4]), puertosEspaciales.buscarPuertoEspacial(datos[5]), Integer.parseInt(datos[6]), Fecha.fromString(datos[7]), Double.parseDouble(datos[8]));
                listaPortes.insertarPorte(porte);
                linea = in.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichero Portes no encontrado.");
        } catch (IOException ex) {
            System.out.println("Error de lectura de fichero Portes.");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                System.out.println("Error de cierre de fichero Portes.");
            }
        }
        return listaPortes;
    }
}

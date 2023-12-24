import java.io.*;
import java.util.Scanner;

import static java.lang.Double.valueOf;

/**
 * ListaPuertosEspaciales es una clase que encapsula las variables correspondientes para
 * definir la lista de puerto espaciale.
 *
 * @author Pedro Fernández-Caballero Zamorano
 * @author Alberto Arpa Hervas
 * @version 1.0
 */
public class ListaPuertosEspaciales {
    private PuertoEspacial[] lista;

    /**
     * Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad La capacidad inicial de la lista de puertos espaciales.
     */
    public ListaPuertosEspaciales(int capacidad) {
        lista = new PuertoEspacial[capacidad];
    }

    /**
     * Devuelve el número de puertos espaciales que hay en la lista.
     *
     * @return El número de puertos espaciales en la lista.
     */
    // TODO: Devuelve el número de puertos espaciales que hay en la lista
    public int getOcupacion() {
        int i = 0;
        while (i < lista.length && lista[i] != null) i++;
        return i;
    }

    /**
     * Verifica si la lista de puertos espaciales está llena.
     *
     * @return `true` si la lista está llena, `false` en caso contrario.
     */
    // TODO: ¿Está llena la lista?
    public boolean estaLlena() {
        return lista[lista.length - 1] != null;
    }

    /**
     * Devuelve un puerto espacial en la posición especificada por el índice.
     *
     * @param i El índice de la lista de puertos espaciales.
     * @return El objeto PuertoEspacial en la posición indicada.
     */
    // TODO: Devuelve un puerto espacial dado un indice
    public PuertoEspacial getPuertoEspacial(int i) {
        return lista[i];
    }

    /**
     * Insertamos un Puerto espacial nuevo en la lista
     *
     * @param puertoEspacial El puerto espacial a insertar.
     * @return `true` en caso de que se añada correctamente, `false` en caso de lista llena o error.
     */
    public boolean insertarPuertoEspacial(PuertoEspacial puertoEspacial) {
        boolean result = false;
        if (!estaLlena()) {
            lista[getOcupacion()] = puertoEspacial;
            result = true;
        }
        return result;
    }

    /**
     * Buscamos un Puerto espacial a partir del codigo pasado como parámetro
     *
     * @param codigo El código del puerto espacial a buscar.
     * @return El objeto PuertoEspacial que se encontró, o `null` si no existe.
     */
    public PuertoEspacial buscarPuertoEspacial(String codigo) {
        PuertoEspacial result = null;
        int i = 0;
        while (i < getOcupacion() - 1 && !lista[i].getCodigo().equals(codigo)) i++;
        if (lista[i].getCodigo().equals(codigo)) result = lista[i];
        return result;
    }

    /**
     * Permite seleccionar un puerto espacial existente a partir de su código, usando el mensaje pasado como
     * argumento para la solicitud y siguiendo el orden y los textos mostrados en el enunciado.
     * La función solicita repetidamente el código hasta que se introduzca uno correcto
     *
     * @param teclado El objeto Scanner utilizado para la entrada de datos.
     * @param mensaje El mensaje a mostrar para solicitar el código del puerto espacial.
     * @return El objeto PuertoEspacial seleccionado.
     */
    public PuertoEspacial seleccionarPuertoEspacial(Scanner teclado, String mensaje) {
        PuertoEspacial puertoEspacial = null;
        String codigo = Utilidades.leerCadena(teclado, mensaje);
        while (buscarPuertoEspacial(codigo) == null || !buscarPuertoEspacial(codigo).getCodigo().equals(codigo)) {
            System.out.println("\tCódigo de puerto no encontrado.");
            codigo = Utilidades.leerCadena(teclado, mensaje);
        }
        puertoEspacial = buscarPuertoEspacial(codigo);
        return puertoEspacial;
    }

    /**
     * Genera un fichero CSV con la lista de puertos espaciales, SOBREESCRIBIENDOLO
     *
     * @param nombre La ruta y nombre del fichero CSV.
     * @return `true` si la escritura fue exitosa, `false` en caso contrario.
     */
    public boolean escribirPuertosEspacialesCsv(String nombre) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(nombre);
            for (int i = 0; i < getOcupacion(); i++) {
                pw.println(lista[i].getNombre() + ";" + lista[i].getCodigo() + ";" + lista[i].getRadio() + ";" + lista[i].getAzimut() + ";" + lista[i].getPolar() + ";" + lista[i].getMuelles() + ";");
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (pw != null) pw.close();
        }
    }


    /**
     * Genera una lista de PuertosEspaciales a partir del fichero CSV, usando el argumento como capacidad máxima
     * de la lista
     *
     * @param fichero   La ruta del archivo CSV que contiene la información de los puertos espaciales.
     * @param capacidad La capacidad máxima de la lista de puertos espaciales.
     * @return La lista de puertos espaciales generada a partir del archivo CSV.
     */
    public static ListaPuertosEspaciales leerPuertosEspacialesCsv(String fichero, int capacidad) {
        BufferedReader in = null;
        ListaPuertosEspaciales listaPuertosEspaciales = new ListaPuertosEspaciales(capacidad);
        try {
            in = new BufferedReader(new FileReader(fichero));
            String linea = in.readLine();
            while (linea != null) {
                String[] datos = linea.split(";");
                String nombre = datos[0];
                String codigo = datos[1];
                double radio = Double.parseDouble(datos[2]);
                double azimut = Double.parseDouble(datos[3]);
                double polar = Double.parseDouble(datos[4]);
                int numMuelles = Integer.parseInt(datos[5]);

                PuertoEspacial puertoEspacial = new PuertoEspacial(nombre, codigo, radio, azimut, polar, numMuelles);
                listaPuertosEspaciales.insertarPuertoEspacial(puertoEspacial);
                linea = in.readLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Fichero Puertos Espaciales no encontrado.");
        } catch (IOException ex) {
            System.out.println("Error de lectura de fichero Puertos Espaciales.");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                System.out.println("Error de cierre de fichero Puertos Espaciales.");
            }
        }
        return listaPuertosEspaciales;
    }
}

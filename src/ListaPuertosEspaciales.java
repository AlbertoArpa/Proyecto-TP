import java.io.*;
import java.util.Scanner;

import static java.lang.Double.valueOf;

/**
 * Description of the class,
 *
 * @author
 * @author
 * @version 1.0
 */
public class ListaPuertosEspaciales {
    private PuertoEspacial[] lista;

    /**
     * TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad
     */
    public ListaPuertosEspaciales(int capacidad) {
        lista = new PuertoEspacial[capacidad];
    }

    // TODO: Devuelve el número de puertos espaciales que hay en la lista
    public int getOcupacion() {
        int i = 0;
        while (i < lista.length && lista[i] != null) i++;
        return i;
    }

    // TODO: ¿Está llena la lista?
    public boolean estaLlena() {
        return lista[lista.length - 1] != null;
    }

    // TODO: Devuelve un puerto espacial dado un indice
    public PuertoEspacial getPuertoEspacial(int i) {
        return lista[i];
    }

    /**
     * TODO: insertamos un Puerto espacial nuevo en la lista
     *
     * @param puertoEspacial
     * @return true en caso de que se añada correctamente, false en caso de lista llena o error
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
     * TODO: Buscamos un Puerto espacial a partir del codigo pasado como parámetro
     *
     * @param codigo
     * @return Puerto espacial que encontramos o null si no existe
     */
    public PuertoEspacial buscarPuertoEspacial(String codigo) {
        PuertoEspacial result = null;
        int i = 0;
        while (i < getOcupacion() - 1 && lista[i].getCodigo() != codigo) i++;
        if (lista[i].getCodigo() == codigo) result = lista[i];
        return result;
    }

    /**
     * TODO: Permite seleccionar un puerto espacial existente a partir de su código, usando el mensaje pasado como
     *  argumento para la solicitud y siguiendo el orden y los textos mostrados en el enunciado.
     *  La función solicita repetidamente el código hasta que se introduzca uno correcto
     *
     * @param teclado
     * @param mensaje
     * @return
     */
    public PuertoEspacial seleccionarPuertoEspacial(Scanner teclado, String mensaje) {
        PuertoEspacial puertoEspacial = null;
        String codigo = Utilidades.leerCadena(teclado, mensaje);
        while (buscarPuertoEspacial(codigo).getCodigo() != codigo) {
            System.out.println("\tCódigo de puerto no encontrado.");
            codigo = Utilidades.leerCadena(teclado, mensaje);
        }
        puertoEspacial = buscarPuertoEspacial(codigo);
        return puertoEspacial;
    }

    /**
     * TODO: Genera un fichero CSV con la lista de puertos espaciales, SOBREESCRIBIENDOLO
     *
     * @param nombre
     * @return
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
     * TODO: Genera una lista de PuertosEspaciales a partir del fichero CSV, usando el argumento como capacidad máxima
     *  de la lista
     *
     * @param fichero
     * @param capacidad
     * @return
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

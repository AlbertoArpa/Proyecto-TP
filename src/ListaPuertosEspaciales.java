import java.io.FileReader;
import java.io.PrintWriter;
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
        while (lista[i] != null && i < lista.length - 1) i++;
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
        int i = 0;
        while (lista[i] != null) i++;
        if (lista[i] != null) {
            lista[i] = puertoEspacial;
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
        while (lista[i].getCodigo() != codigo) i++;
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
        try {
            PrintWriter pw = new PrintWriter(nombre);
            for (int i = 0; i < lista.length; i++) {
                pw.println(lista[i].getNombre() + ";" + lista[i].getCodigo() + ";" + lista[i].getRadio() + ";" + lista[i].getAzimut() + ";" + lista[i].getPolar() + ";" + lista[i].getMuelles() + ";");
                pw.close();
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {

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
        ListaPuertosEspaciales listaPuertosEspaciales = new ListaPuertosEspaciales(capacidad);
        try {
            Scanner sc = new Scanner(new FileReader(fichero));
            for (int i = 0; i < capacidad - 1; i++) {
                String linea, nombre = "", codigo = "";
                double radio, azimut, polar;
                int numMuelles;
                linea = sc.nextLine();
                int j = 0;
                while (linea.charAt(j) != ';') {
                    nombre += linea.charAt(j);
                    j++;
                }
                j++;
                while (linea.charAt(j) != ';') {
                    codigo += linea.charAt(j);
                    j++;
                }
                radio =
                listaPuertosEspaciales.insertarPuertoEspacial(new PuertoEspacial(nombre, codigo, radio, azimut, polar, numMuelles));
            }
        } catch (Exception e) {
            return null;
        } finally {

        }
        return listaPuertosEspaciales;
    }
}

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author
 * @author
 * @version 1.0
 */
public class ListaClientes {
    private Cliente[] clientes;

    /**
     * TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad
     */
    public ListaClientes(int capacidad) {
        clientes = new Cliente[capacidad];
    }

    // TODO: Devuelve el número de clientes que hay en la lista de clientes
    public int getOcupacion() {
        int i = 0;
        while (i < clientes.length && clientes[i] != null) i++;
        return i;
    }

    // TODO: ¿Está llena la lista de clientes?
    public boolean estaLlena() {
        return clientes[clientes.length - 1] != null;
    }

    // TODO: Devuelve el cliente dada el indice
    public Cliente getCliente(int i) {
        return clientes[i];
    }

    // TODO: Inserta el cliente en la lista de clientes
    public boolean insertarCliente(Cliente cliente) {
        boolean result = false;
        if (!estaLlena()) {
            clientes[getOcupacion()] = cliente;
            result = true;
        }
        return result;
    }

    // TODO: Devuelve el cliente que coincida con el email, o null en caso de no encontrarlo
    public Cliente buscarClienteEmail(String email) {
        Cliente cliente = null;
        int i = 0;
        while (i < getOcupacion() - 1 && clientes[i].getEmail() != email) {
            i++;
        }
        if (clientes[i].getEmail() == email) cliente = clientes[i];
        return cliente;
    }

    /**
     * TODO: Método para seleccionar un Cliente existente a partir de su email, usando el mensaje pasado como argumento
     *  para la solicitud y, siguiendo el orden y los textos mostrados en el enunciado.
     *  La función debe solicitar repetidamente hasta que se introduzca un email correcto
     *
     * @param teclado
     * @param mensaje
     * @return
     */
    public Cliente seleccionarCliente(Scanner teclado, String mensaje) {
        Cliente cliente = buscarClienteEmail(Utilidades.leerCadena(teclado, mensaje));
        while (cliente == null) cliente = buscarClienteEmail(Utilidades.leerCadena(teclado, "Email no encontrado.\n"));
        return cliente;
    }

    /**
     * TODO: Método para guardar la lista de clientes en un fichero .csv, sobreescribiendo la información del mismo
     *  fichero
     *
     * @param fichero
     * @return
     */
    public boolean escribirClientesCsv(String fichero) {
        boolean result = false;
        try {
        PrintWriter pw = new PrintWriter(fichero);
        for (int i = 0; i < getOcupacion(); i++) {
            pw.println(clientes[i].getNombre() + ";" + clientes[i].getApellidos() + ";" + clientes[i].getEmail());
        }
        pw.close();
        } catch (FileNotFoundException e) {
            return false;
        } finally {

        }
        return true;
    }

    /**
     * TODO: Genera una lista de Clientes a partir del fichero CSV, usando los límites especificados como argumentos
     *  para la capacidad de la lista y el número de billetes máximo por pasajero
     *
     * @param fichero
     * @param capacidad
     * @param maxEnviosPorCliente
     * @return lista de clientes
     */
    public static ListaClientes leerClientesCsv(String fichero, int capacidad, int maxEnviosPorCliente) {

        try {

        } catch (FileNotFoundException e) {
            return null;
        } finally {

        }
        return listaClientes;
    }
}
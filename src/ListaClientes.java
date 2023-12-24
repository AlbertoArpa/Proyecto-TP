import java.io.*;
import java.util.Scanner;

/**
 * ListaClientes es una clase que encapsula las variables correspondientes para
 * definir la lista de cliente.
 *
 * @author Pedro Fernández-Caballero Zamorano
 * @author Alberto Arpa Hervás
 * @version 1.0
 */
public class ListaClientes {
    private Cliente[] clientes;

    /**
     * Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad La capacidad inicial de la lista de clientes.
     */
    public ListaClientes(int capacidad) {
        clientes = new Cliente[capacidad];
    }

    /**
     * Obtiene el número de clientes que hay en la lista de clientes.
     *
     * @return El número de clientes en la lista.
     */
    // TODO: Devuelve el número de clientes que hay en la lista de clientes
    public int getOcupacion() {
        int i = 0;
        while (i < clientes.length && clientes[i] != null) i++;
        return i;
    }

    /**
     * Verifica si la lista de clientes está llena.
     *
     * @return `true` si la lista está llena, `false` en caso contrario.
     */
    // TODO: ¿Está llena la lista de clientes?
    public boolean estaLlena() {
        return clientes[clientes.length - 1] != null;
    }

    /**
     * Devuelve el cliente en la posición especificada por el índice.
     *
     * @param i El índice del cliente en la lista.
     * @return El cliente en la posición indicada.
     */
    // TODO: Devuelve el cliente dada el indice
    public Cliente getCliente(int i) {
        return clientes[i];
    }

    /**
     * Inserta un cliente en la lista de clientes.
     *
     * @param cliente El cliente a insertar.
     * @return `true` si la inserción fue exitosa, `false` en caso contrario.
     */
    // TODO: Inserta el cliente en la lista de clientes
    public boolean insertarCliente(Cliente cliente) {
        boolean result = false;
        if (!estaLlena()) {
            clientes[getOcupacion()] = cliente;
            result = true;
        }
        return result;
    }

    /**
     * Busca un cliente en la lista por su email.
     *
     * @param email El email del cliente a buscar.
     * @return El cliente encontrado, o `null` si no se encontró ningún cliente con el email especificado.
     */
    // TODO: Devuelve el cliente que coincida con el email, o null en caso de no encontrarlo
    public Cliente buscarClienteEmail(String email) {
        Cliente cliente = null;
        int i = 0;
        while (i < getOcupacion() - 1 && !clientes[i].getEmail().equals(email)) {
            i++;
        }
        if (clientes[i] != null && clientes[i].getEmail().equals(email)) cliente = clientes[i];
        return cliente;
    }

    /**
     * Método para seleccionar un Cliente existente a partir de su email, usando el mensaje pasado como argumento
     * para la solicitud y, siguiendo el orden y los textos mostrados en el enunciado.
     * La función debe solicitar repetidamente hasta que se introduzca un email correcto
     *
     * @param teclado El objeto Scanner utilizado para la entrada de datos.
     * @param mensaje El mensaje a mostrar para solicitar el email del cliente.
     * @return El cliente seleccionado.
     */
    public Cliente seleccionarCliente(Scanner teclado, String mensaje) {
        Cliente cliente = buscarClienteEmail(Utilidades.leerCadena(teclado, mensaje));
        while (cliente == null)
            cliente = buscarClienteEmail(Utilidades.leerCadena(teclado, "\tEmail no encontrado.\n" + mensaje));
        return cliente;
    }

    /**
     * Método para guardar la lista de clientes en un fichero .csv, sobreescribiendo la información del mismo
     * fichero
     *
     * @param fichero La ruta del archivo donde se guardará la lista de clientes en formato CSV.
     * @return `true` si la escritura fue exitosa, `false` en caso contrario.
     */
    public boolean escribirClientesCsv(String fichero) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(fichero);
            for (int i = 0; i < getOcupacion(); i++) {
                pw.println(clientes[i].getNombre() + ";" + clientes[i].getApellidos() + ";" + clientes[i].getEmail());
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } finally {
            if (pw != null) pw.close();
        }
    }

    /**
     * Genera una lista de Clientes a partir del fichero CSV, usando los límites especificados como argumentos
     * para la capacidad de la lista y el número de billetes máximo por pasajero
     *
     * @param fichero             La ruta del archivo CSV que contiene la información de los clientes.
     * @param capacidad           La capacidad inicial de la lista de clientes.
     * @param maxEnviosPorCliente El número máximo de envíos permitidos por cliente.
     * @return La lista de clientes generada a partir del fichero CSV.
     */
    public static ListaClientes leerClientesCsv(String fichero, int capacidad, int maxEnviosPorCliente) {
        BufferedReader in = null;
        ListaClientes listaClientes = new ListaClientes(capacidad);
        try {
            in = new BufferedReader(new FileReader(fichero));
            String linea = in.readLine();
            while (linea != null) {
                String[] datos = linea.split(";");
                Cliente cliente = new Cliente(datos[0], datos[1], datos[2], Integer.parseInt(datos[3])); //todo: pendiente
                listaClientes.insertarCliente(cliente);
                linea = in.readLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Fichero Clientes no encontrado.");
        } catch (IOException ex) {
            System.out.println("Error de lectura de fichero Clientes.");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                System.out.println("Error de cierre de fichero Clientes.");
            }
        }
        return listaClientes;
    }
}
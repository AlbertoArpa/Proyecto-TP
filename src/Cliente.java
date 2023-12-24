import java.util.Random;
import java.util.Scanner;

import static jdk.nashorn.internal.objects.NativeString.toLowerCase;

/**
 * Cliente es una clase que encapsula las variables correspondientes para
 * definir a un cliente.
 *
 * @author Pedro Fernández-Caballero Zamorano
 * @author Alberto Arpa Hervas
 * @version 1.0
 */
public class Cliente {
    private final ListaEnvios listaEnvios;
    /**
     * Nombre del Cliente.
     */
    private final String nombre;
    /**
     * Apellidos del Cliente.
     */
    private final String apellidos;
    /**
     * Email del Cliente.
     */
    private final String email;

    /**
     * Constructor de la clase Cliente utilizado para
     * definir los atributos de un cliente.
     *
     * @param nombre    Nombre del cliente
     * @param apellidos Apellidos del cliente
     * @param email     Email del cliente
     * @param maxEnvios Número máximo de envíos que puede tener el cliente
     */
    public Cliente(String nombre, String apellidos, String email, int maxEnvios) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.listaEnvios = new ListaEnvios(maxEnvios);
    }

    /**
     * Getter del atributo nombre.
     *
     * @return Nombre del Cliente
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Getter del atributo apellidos.
     *
     * @return Apellidos del Cliente
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Getter del atributo email.
     *
     * @return Email del Cliente
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retorna un String con los datos de un Cliente con el siguiente formato:
     * nombre+" "+apellidos+", "+email
     * <p>
     * Texto que debe generar: Zapp Brannigan, zapp.brannigan@dop.gov
     *
     * @return String con los datos de un Cliente
     */

    // TODO: Texto que debe generar: Zapp Brannigan, zapp.brannigan@dop.gov
    public String toString() {
        return nombre + " " + apellidos + ", " + email;
    }

    /**
     * Método que comprueba si se ha alcanzado el número máximo de envíos vendidos de la lista.
     *
     * @return true si se ha alcanzado el número máximo de envíos vendidos de la lista, false en caso contrario
     */
    // TODO: Devuelve un booleano que indica si se ha alcanzado el número máximo de envíos
    public boolean maxEnviosAlcanzado() {
        return listaEnvios.estaLlena();
    }

    /**
     * Método que devuelve el envío en función de su posición.
     *
     * @param i índice
     * @return el envío en la posición que ocupa en la lista
     */
    // TODO: Devuelve un envío en función de su posición
    public Envio getEnvio(int i) {
        return listaEnvios.getEnvio(i - 1);
    }

    /**
     * Obtiene la lista de envíos del cliente.
     *
     * @return La lista de envíos del cliente.
     */
    public ListaEnvios getListaEnvios() {
        return listaEnvios;
    }

    /**
     * Añade un nuevo envío a la lista de envíos del cliente.
     *
     * @param envio El envío a añadir.
     * @return `true` si el envío se añadió correctamente, `false` en caso contrario.
     */
    // TODO: Añade un envío al cliente.
    public boolean aniadirEnvio(Envio envio) {
        return listaEnvios.insertarEnvio(envio);
    }

    /**
     * Busca un envío en la lista de envíos del cliente por su localizador.
     *
     * @param localizador El localizador del envío a buscar.
     * @return El envío encontrado, o `null` si no se encontró ningún envío con el localizador especificado.
     */
    public Envio buscarEnvio(String localizador) {
        return listaEnvios.buscarEnvio(localizador);
    }

    /**
     * Elimina un envío de la lista de envíos del cliente por su localizador.
     *
     * @param localizador El localizador del envío a eliminar.
     * @return `true` si el envío se eliminó correctamente, `false` en caso contrario.
     */
    // TODO: Elimina el envío de la lista de envíos del pasajero
    public boolean cancelarEnvio(String localizador) {
        return listaEnvios.eliminarEnvio(localizador);
    }

    /**
     * Muestra en consola la lista de envíos del cliente.
     */
    public void listarEnvios() {
        listaEnvios.listarEnvios();
    }

    /**
     * Encapsula la funcionalidad de seleccionar un envío utilizando un objeto Scanner y un mensaje específico.
     *
     * @param teclado El objeto Scanner utilizado para la entrada de datos.
     * @param mensaje El mensaje a mostrar para solicitar la selección del envío.
     * @return El envío seleccionado.
     */
    // Encapsula la funcionalidad seleccionarEnvio de ListaEnvios
    public Envio seleccionarEnvio(Scanner teclado, String mensaje) {
        return listaEnvios.seleccionarEnvio(teclado, mensaje);
    }

    /**
     * Método estático para crear un nuevo cliente "no repetido", se pide por teclado los datos necesarios
     * al usuario en el orden y con los textos indicados en los ejemplos de ejecución del enunciado
     * La función tiene que solicitar repetidamente los parámetros hasta que sean correctos
     *
     * @param teclado
     * @param clientes
     * @param maxEnvios
     * @return Cliente
     */
    public static Cliente altaCliente(Scanner teclado, ListaClientes clientes, int maxEnvios) {
        String nombre = Utilidades.leerCadena(teclado, "Nombre:");
        String apellidos = Utilidades.leerCadena(teclado, "Apellidos:");
        String email = toLowerCase(Utilidades.leerCadena(teclado, "Email: "));
        while (!correctoEmail(email))
            email = toLowerCase(Utilidades.leerCadena(teclado, "\tEmail incorrecto\nEmail: "));
        while (clientes.buscarClienteEmail(email) != null)
            email = toLowerCase(Utilidades.leerCadena(teclado, "\tEl email ya está registrado\nEmail: "));
        System.out.println("\tCliente con email " + email + " creado correctamente\n");
        return new Cliente(nombre, apellidos, email, maxEnvios);
    }


    /**
     * Función que comprueba si un email introducido es correcto. El email se considera correcto si contiene al final de este: @
     * Además ha de estar compuesto únicamente por caracteres alfabéticos y el carácter punto ‘.’ entre estos (no
     * pudiendo por tanto estar al principio del correo ni justo delante de la @).
     * <p>
     *
     * @param email Email del cliente a comprobar
     * @return true si el email es correcto, false en caso contrario
     */
    public static boolean correctoEmail(String email) {
        boolean correcto = true;
        if (!email.contains("@")) {
            correcto = false;
        } else {
            String[] datos = email.split("@");
            if (datos[0].startsWith(".") || datos[0].endsWith(".")) {
                correcto = false;
            }
            for (int i = 0; i < datos[0].length(); i++) {
                char caracter = datos[0].charAt(i);
                if (((caracter < 'a' || caracter > 'z') && (caracter < 'A' || caracter > 'Z')) && caracter != '.') {
                    correcto = false;
                }
                if ((datos[0].charAt(i) == '.') && (datos[0].charAt(i + 1) == '.')) {
                    correcto = false;
                }
            }
        }
        return correcto;
    }
}

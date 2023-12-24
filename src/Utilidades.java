import java.util.Scanner;

/**
 * Utilidades es una clase que encapsula
 * metodos que se repiten a lo largo del programa
 * y que tienen que ver con pedir datos al usuario por pantalla.
 *
 * @author Pedro Fernández-Caballero Zamorano
 * @author Alberto Arpa Hervás
 * @version 1.0
 */
public class Utilidades {

    /**
     * Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     *
     * @param teclado Scanner utilizado para la entrada de datos.
     * @param mensaje Mensaje que se muestra solicitando la entrada.
     * @param minimo  Valor mínimo permitido.
     * @param maximo  Valor máximo permitido.
     * @return Número entero dentro de los límites especificados.
     */
    public static int leerNumero(Scanner teclado, String mensaje, int minimo, int maximo) {
        System.out.print(mensaje);
        int resultado = teclado.nextInt();
        while (resultado < minimo || resultado > maximo) {
            System.out.print(mensaje);
            resultado = teclado.nextInt();
        }

        teclado.nextLine();
        return resultado;
    }

    /**
     * Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     *
     * @param teclado Scanner utilizado para la entrada de datos.
     * @param mensaje Mensaje que se muestra solicitando la entrada.
     * @param minimo  Valor mínimo permitido.
     * @param maximo  Valor máximo permitido.
     * @return Número largo dentro de los límites especificados.
     */
    public static long leerNumero(Scanner teclado, String mensaje, long minimo, long maximo) {
        System.out.print(mensaje);
        long resultado = teclado.nextLong();

        while (resultado < minimo || resultado > maximo) {
            System.out.print(mensaje);
            resultado = teclado.nextLong();
        }

        teclado.nextLine();
        return resultado;
    }

    /**
     * Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     *
     * @param teclado Scanner utilizado para la entrada de datos.
     * @param mensaje Mensaje que se muestra solicitando la entrada.
     * @param minimo  Valor mínimo permitido.
     * @param maximo  Valor máximo permitido.
     * @return Número decimal dentro de los límites especificados.
     */
    public static double leerNumero(Scanner teclado, String mensaje, double minimo, double maximo) {
        System.out.print(mensaje);
        double resultado = teclado.nextDouble();

        while (resultado < minimo || resultado > maximo) {
            System.out.print(mensaje);
            resultado = teclado.nextDouble();
        }

        teclado.nextLine();
        return resultado;
    }

    /**
     * Solicita una letra repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     *
     * @param teclado Scanner utilizado para la entrada de datos.
     * @param mensaje Mensaje que se muestra solicitando la entrada.
     * @param minimo  Valor mínimo permitido.
     * @param maximo  Valor máximo permitido.
     * @return Carácter correspondiente a la letra introducida dentro de los límites especificados.
     */
    public static char leerLetra(Scanner teclado, String mensaje, char minimo, char maximo) {
        System.out.print(mensaje);
        char resultado = teclado.next().charAt(0);
        while (resultado < minimo || resultado > maximo) {
            System.out.print(mensaje);
            resultado = teclado.next().charAt(0);
        }
        return resultado;
    }

    /**
     * Solicita una fecha repetidamente hasta que se introduzca una correcta
     *
     * @param teclado Scanner utilizado para la entrada de datos.
     * @param mensaje Mensaje que se muestra solicitando la entrada.
     * @return Objeto `Fecha` representando la fecha introducida.
     */
    public static Fecha leerFecha(Scanner teclado, String mensaje) {
        Fecha fecha = null;
        boolean correcto = true;
        while (correcto) {
            System.out.println(mensaje);
            int dia = leerNumero(teclado, "Ingrese día:", 1, 31);
            int mes = leerNumero(teclado, "Ingrese mes:", 1, 12);
            int anio = leerNumero(teclado, "Ingrese año:", Fecha.PRIMER_ANIO, Fecha.ULTIMO_ANIO);
            if (Fecha.comprobarFecha(dia, mes, anio)) {
                correcto = false;
                fecha = new Fecha(dia, mes, anio);
            } else {
                System.out.println("Fecha introducida incorrecta.");
            }
        }
        return fecha;
    }

    /**
     * Solicita una fecha y hora repetidamente hasta que se introduzcan unas correctas
     *
     * @param teclado Scanner utilizado para la entrada de datos.
     * @param mensaje Mensaje que se muestra solicitando la entrada.
     * @return Objeto `Fecha` representando la fecha y hora introducidas.
     */
    public static Fecha leerFechaHora(Scanner teclado, String mensaje) {
        Fecha fecha = null;
        boolean correcto = true;

        while (correcto) {
            System.out.println(mensaje);
            int dia = leerNumero(teclado, "Ingrese día:", 1, 31);
            int mes = leerNumero(teclado, "Ingrese mes:", 1, 12);
            int anio = leerNumero(teclado, "Ingrese año:", Fecha.PRIMER_ANIO, Fecha.ULTIMO_ANIO);
            int hora = leerNumero(teclado, "Ingrese hora:", 00, 23);
            int minuto = leerNumero(teclado, "Ingrese minuto:", 00, 59);
            int segundo = leerNumero(teclado, "Ingrese segundo:", 00, 59);

            if (Fecha.comprobarFecha(dia, mes, anio) && Fecha.comprobarHora(hora, minuto, segundo)) {
                correcto = false;
            } else {
                System.out.println("Fecha u hora introducida incorrecta.");
            }
            fecha = new Fecha(dia, mes, anio, hora, minuto, segundo);
        }

        return fecha;
    }

    /**
     * Imprime por pantalla el String pasado por parámetro
     *
     * @param teclado Scanner utilizado para la entrada de datos.
     * @param s       Mensaje que se muestra solicitando la entrada.
     * @return Cadena de texto introducida.
     */
    public static String leerCadena(Scanner teclado, String s) {
        System.out.print(s);
        return teclado.next();
    }
}
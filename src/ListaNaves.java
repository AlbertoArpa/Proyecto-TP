import java.io.*;
import java.util.Scanner;

/**
 * ListaNaves es una clase que encapsula las variables correspondientes para
 * definir la lista de nave.
 *
 * @author Pedro Fernández-Caballero Zamorano
 * @author Alberto Arpa Hervas
 * @version 1.0
 */
public class ListaNaves {
    private Nave[] naves;

    /**
     * Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad La capacidad inicial de la lista de naves.
     */
    public ListaNaves(int capacidad) {
        naves = new Nave[capacidad];
    }

    /**
     * Devuelve el número de naves que hay en la lista.
     *
     * @return El número de naves en la lista.
     */
    // TODO: Devuelve el número de naves que hay en la lista
    public int getOcupacion() {
        int i = 0;
        while (i < naves.length && naves[i] != null) {
            i++;
        }
        return i;
    }

    /**
     * Verifica si la lista de naves está llena.
     *
     * @return `true` si la lista está llena, `false` en caso contrario.
     */
    // TODO: ¿Está llena la lista de naves?
    public boolean estaLlena() {
        return naves[naves.length - 1] != null;
    }

    /**
     * Devuelve la nave en la posición especificada por el índice.
     *
     * @param posicion El índice de la nave en la lista.
     * @return La nave en la posición indicada.
     */
    // TODO: Devuelve nave dado un indice
    public Nave getNave(int posicion) {
        return naves[posicion];
    }

    /**
     * Insertamos una nueva nave en la lista
     *
     * @param nave La nave a insertar.
     * @return `true` si la inserción fue exitosa, `false` en caso de lista llena o error.
     */
    public boolean insertarNave(Nave nave) {
        boolean result = false;
        if (!estaLlena()) {
            naves[getOcupacion()] = nave;
            result = true;
        }
        return result;
    }

    /**
     * Buscamos la nave a partir de la matricula pasada como parámetro
     *
     * @param matricula La matrícula de la nave a buscar.
     * @return La nave encontrada, o `null` si no se encontró ninguna nave con la matrícula especificada.
     */
    public Nave buscarNave(String matricula) {
        Nave result = null;
        int i = 0;
        while (i < getOcupacion() - 1 && !naves[i].getMatricula().equals(matricula)) {
            i++;
        }
        if (naves[i].getMatricula().equals(matricula)) result = naves[i];
        return result;
    }

    /**
     * Muestra por pantalla la información de las naves de la lista con el formato indicado en el enunciado.
     */
    // TODO: Muestra por pantalla las naves de la lista con el formato indicado en el enunciado
    public void mostrarNaves() {
        for (int i = 0; i < getOcupacion(); i++) {
            System.out.println("\t" + naves[i].getMarca() + " " + naves[i].getModelo() + " (" + naves[i].getMatricula() + "): " + naves[i].getFilas() * naves[i].getColumnas() + " contenedores, hasta " + naves[i].getAlcance() + " UA");
        }
    }

    /**
     * Permite seleccionar una nave existente a partir de su matrícula, y comprueba si dispone de un alcance
     * mayor o igual que el pasado como argumento, usando el mensaje pasado como argumento para la solicitud y
     * siguiendo el orden y los textos mostrados en el enunciado.
     * La función solicita repetidamente la matrícula de la nave hasta que se introduzca una con alcance suficiente
     *
     * @param teclado El objeto Scanner utilizado para la entrada de datos.
     * @param mensaje El mensaje a mostrar para solicitar la matrícula de la nave.
     * @param alcance El alcance mínimo requerido.
     * @return La nave seleccionada.
     */
    public Nave seleccionarNave(Scanner teclado, String mensaje, double alcance) {
        Nave nave = buscarNave(Utilidades.leerCadena(teclado, mensaje));
        while (nave == null)
            nave = buscarNave(Utilidades.leerCadena(teclado, "\tMatrícula de avión no encontrada.\n" + mensaje));
        while (nave.getAlcance() < alcance)
            nave = buscarNave(Utilidades.leerCadena(teclado, "\tAvión seleccionado con alcance insuficiente.\n" + mensaje));
        return nave;
    }


    /**
     * Genera un fichero CSV con la lista de Naves, SOBREESCRIBIÉNDOLO
     *
     * @param nombre La ruta del archivo CSV donde se escribirán las naves.
     * @return `true` si la escritura fue exitosa, `false` en caso contrario.
     */
    public boolean escribirNavesCsv(String nombre) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(nombre);
            for (int i = 0; i < getOcupacion(); i++) {
                pw.println(naves[i].getMarca() + ";" + naves[i].getModelo() + ";" + naves[i].getMatricula() + ";" + naves[i].getFilas() + ";" + naves[i].getColumnas() + ";" + naves[i].getAlcance() + "E-5");
            }
            pw.close();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (pw != null) pw.close();
        }
    }


    /**
     * Genera una lista de naves a partir del fichero CSV, usando el argumento como capacidad máxima de la lista
     *
     * @param fichero   La ruta del archivo CSV que contiene la información de las naves.
     * @param capacidad La capacidad máxima de la lista de naves.
     * @return La lista de naves generada a partir del archivo CSV.
     */
    public static ListaNaves leerNavesCsv(String fichero, int capacidad) {
        BufferedReader in = null;
        ListaNaves listaNaves = new ListaNaves(capacidad);
        try {
            in = new BufferedReader(new FileReader(fichero));
            String linea = in.readLine();
            while (linea != null) {
                String[] datos = linea.split(";");
                String marca = datos[0];
                String modelo = datos[1];
                String matricula = datos[2];
                int filas = Integer.parseInt(datos[3]);
                int columnas = Integer.parseInt(datos[4]);
                double alcance = Double.parseDouble(datos[5]);
                Nave nave = new Nave(marca, modelo, matricula, columnas, filas, alcance);
                listaNaves.insertarNave(nave);
                linea = in.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichero Naves no encontrado.");
        } catch (IOException ex) {
            System.out.println("Error de lectura de fichero Naves.");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                System.out.println("Error de cierre de fichero Naves.");
            }
        }
        return listaNaves;
    }
}

import java.io.*;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author
 * @author
 * @version 1.0
 */
public class ListaNaves {
    private Nave[] naves;

    /**
     * TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad
     */
    public ListaNaves(int capacidad) {
        naves = new Nave[capacidad];
    }

    // TODO: Devuelve el número de naves que hay en la lista
    public int getOcupacion() {
        int i = 0;
        while (i < naves.length && naves[i] != null) {
            i++;
        }
        return i;
    }

    // TODO: ¿Está llena la lista de naves?
    public boolean estaLlena() {
        return naves[naves.length - 1] != null;
    }

    // TODO: Devuelve nave dado un indice
    public Nave getNave(int posicion) {
        return naves[posicion];
    }

    /**
     * TODO: insertamos una nueva nave en la lista
     *
     * @param nave
     * @return true en caso de que se añada correctamente, false en caso de lista llena o error
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
     * TODO: Buscamos la nave a partir de la matricula pasada como parámetro
     *
     * @param matricula
     * @return la nave que encontramos o null si no existe
     */
    public Nave buscarNave(String matricula) {
        Nave result = null;
        int i = 0;
        while (i < getOcupacion() - 1 && naves[i].getMatricula() != matricula) {
            i++;
        }
        if (naves[i].getMatricula() == matricula) result = naves[i];
        return result;
    }

    // TODO: Muestra por pantalla las naves de la lista con el formato indicado en el enunciado
    public void mostrarNaves() {
        for (int i = 0; i < naves.length; i++) {
            System.out.println("\t" + naves[i].getMarca() + " " + naves[i].getModelo() + " (" + naves[i].getMatricula() + "): " + naves[i].getFilas() * naves[i].getColumnas() + " contenedores, hasta " + naves[i].getAlcance() + " UA");
        }
    }

    /**
     * TODO: Permite seleccionar una nave existente a partir de su matrícula, y comprueba si dispone de un alcance
     *  mayor o igual que el pasado como argumento, usando el mensaje pasado como argumento para la solicitud y
     *  siguiendo el orden y los textos mostrados en el enunciado.
     *  La función solicita repetidamente la matrícula de la nave hasta que se introduzca una con alcance suficiente
     *
     * @param teclado
     * @param mensaje
     * @param alcance
     * @return
     */
    public Nave seleccionarNave(Scanner teclado, String mensaje, double alcance) {
        Nave nave = null;
        do {
            System.out.print(mensaje);
            String matricula = teclado.nextLine();
            nave = buscarNave(matricula);
            if (nave == null) {
                System.out.println("Matrícula de nave no encontrada.");
            } else if (nave.getAlcance() < alcance) {
                System.out.printf("Nave seleccionada con alcance insuficiente (menor que %.3f km).\n", alcance);
            }
        } while (nave == null || alcance > nave.getAlcance());
        return nave;
    }

    /**
     * TODO: Genera un fichero CSV con la lista de Naves, SOBREESCRIBIÉNDOLO
     *
     * @param nombre
     * @return
     */
    public boolean escribirNavesCsv(String nombre) {
        boolean escrito = true;
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(nombre);
            for (int i = 0; i < getOcupacion(); i++) {
                pw.println(naves[i].getMarca() + ";" + naves[i].getModelo() + ";" + naves[i].getMatricula() + ";" + naves[i].getFilas() + ";" + naves[i].getColumnas() + ";" + naves[i].getAlcance() + "E-5");
            }
            escrito = true;
        } catch (Exception e) {
            System.out.println("Error de escritura en fichero Naves.");
            escrito = false;
        } finally {
            if (pw != null)
                pw.close();
        }
        return escrito;
    }

    /**
     * TODO: Genera una lista de naves a partir del fichero CSV, usando el argumento como capacidad máxima de la lista
     *
     * @param fichero
     * @param capacidad
     * @return
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

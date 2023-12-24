/**
 * Nave es una clase que encapsula las variables correspondientes para
 * definir una nave.
 *
 * @author Pedro Fernández-Caballero Zamorano
 * @author Alberto Arpa Hervas
 * @version 1.0
 */
public class Nave {
    /**
     * Marca de la nave.
     */
    private String marca;
    /**
     * Modelo de la nave.
     */
    private String modelo;

    /**
     * Matrícula de la nave.
     */
    private String matricula;
    /**
     * Número de columnas de la nave.
     */
    private int columnas;
    /**
     * Número de filas de la nave.
     */
    private int filas;
    /**
     * Alcance máximo de la nave (en km).
     */
    private double alcance;


    /**
     * Constructor de la clase.
     *
     * @param marca     La marca de la nave.
     * @param modelo    El modelo de la nave.
     * @param matricula La matrícula de la nave.
     * @param columnas  El número de columnas de contenedores de la nave.
     * @param filas     El número de filas de contenedores de la nave.
     * @param alcance   El alcance de la nave.
     */
    public Nave(String marca, String modelo, String matricula, int columnas, int filas, double alcance) {
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.columnas = columnas;
        this.filas = filas;
        this.alcance = alcance;
    }

    /**
     * Obtiene la marca de la nave.
     *
     * @return La marca de la nave.
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Obtiene el modelo de la nave.
     *
     * @return El modelo de la nave.
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Obtiene la matrícula de la nave.
     *
     * @return La matrícula de la nave.
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Obtiene el número de columnas de contenedores de la nave.
     *
     * @return El número de columnas de contenedores.
     */
    public int getColumnas() {
        return columnas;
    }

    /**
     * Obtiene el número de filas de contenedores de la nave.
     *
     * @return El número de filas de contenedores.
     */
    public int getFilas() {
        return filas;
    }

    /**
     * Obtiene el alcance de la nave.
     *
     * @return El alcance de la nave.
     */
    public double getAlcance() {
        return alcance;
    }


    /**
     * Crea un String con los datos de una nave con el siguiente formato:
     *
     * @return ejemplo del formato -> "Planet Express One (EP-245732X): 40 contenedores, hasta 1.57 UA"
     */
    public String toString() {
        return marca + " " + modelo + "(" + matricula + "): " + (filas * columnas) + " contenedores, hasta " + alcance + "UA";
    }


    /**
     * Crea un String con los datos de una nave con el siguiente formato:
     *
     * @return ejemplo del formato -> "Planet Express One (EP-245732X)"
     */
    public String toStringSimple() {
        return marca + " " + modelo + "(" + matricula + ")";
    }
}
//COMPLETA
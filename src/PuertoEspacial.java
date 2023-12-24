/**
 * PuertoEspacial es una clase que encapsula las variables correspondientes para
 * definir un puerto espacial.
 *
 * @author Pedro Fernández-Caballero Zamorano
 * @author Alberto Arpa Hervas
 * @version 1.0
 */
public class PuertoEspacial {

    private String nombre;
    private String codigo;
    private double radio;
    private double azimut;
    private double polar;
    private int numMuelles;

    /**
     * Constructor de la clase PuertoEspacial.
     *
     * @param nombre     Nombre del puerto espacial.
     * @param codigo     Código identificador del puerto espacial.
     * @param radio      Radio del puerto espacial.
     * @param azimut     Ángulo azimutal del puerto espacial.
     * @param polar      Ángulo polar del puerto espacial.
     * @param numMuelles Número de muelles en el puerto espacial.
     */
    public PuertoEspacial(String nombre, String codigo, double radio, double azimut, double polar, int numMuelles) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.radio = radio;
        this.azimut = azimut;
        this.polar = polar;
        this.numMuelles = numMuelles;
    }

    /**
     * Obtiene el nombre del puerto espacial.
     *
     * @return Nombre del puerto espacial.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el código identificador del puerto espacial.
     *
     * @return Código identificador del puerto espacial.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Obtiene el radio del puerto espacial.
     *
     * @return Radio del puerto espacial.
     */
    public double getRadio() {
        return radio;
    }

    /**
     * Obtiene el ángulo azimutal del puerto espacial.
     *
     * @return Ángulo azimutal del puerto espacial.
     */
    public double getAzimut() {
        return azimut;
    }

    /**
     * Obtiene el ángulo polar del puerto espacial.
     *
     * @return Ángulo polar del puerto espacial.
     */
    public double getPolar() {
        return polar;
    }

    /**
     * Obtiene el número de muelles en el puerto espacial.
     *
     * @return Número de muelles en el puerto espacial.
     */
    public int getMuelles() {
        return numMuelles;
    }

    /**
     * Método para calcular la distancia entre el puerto espacial que recibe el mensaje y el puerto
     * espacial "destino" siguiendo las ecuaciones del enunciado (Las formulas se encuentran en el enunciado)
     *
     * @param destino PuertoEspacial destino al cual se calcula la distancia.
     * @return Distancia entre los puertos espaciales.
     */
    public double distancia(PuertoEspacial destino) {
        // TODO: Para calcular la distancia entre dos Puertos Espaciales, se transforman sus coordenadas esféricas a cartesianas
        // TODO: Una vez se tienen las coordenadas cartesianas, basta con calcular la distancia euclídea entre ellas:

        double x1 = radio * Math.sin(azimut) * Math.cos(polar);
        double y1 = radio * Math.sin(azimut) * Math.sin(polar);
        double z1 = radio * Math.cos(azimut);
        double x2 = destino.getRadio() * Math.sin(destino.getAzimut()) * Math.cos(destino.getPolar());
        double y2 = destino.getRadio() * Math.sin(destino.getAzimut()) * Math.sin(destino.getPolar());
        double z2 = destino.getRadio() * Math.cos(destino.getAzimut());
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
    }

    /**
     * Método que crea un String con los datos de un puerto espacial con el siguiente formato:
     *
     * @return ejemplo -> "Gaia Galactic Terminal(GGT), en (1.0 90.0 0.0), con 8 muelles" (Radio, Azimut, Polar)
     */
    public String toString() {
        return "" + nombre + " (" + codigo + "), en (" + radio + " " + azimut + " " + polar + "), con " + numMuelles + " muelles";
    }

    /**
     * Método que crea un String con los datos de un aeropuerto con el siguiente formato:
     *
     * @return ejemplo -> "Gaia Galactic Terminal (GGT)"
     */
    public String toStringSimple() {
        return nombre + " (" + codigo + ")";
    }
}

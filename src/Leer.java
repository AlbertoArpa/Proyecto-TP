import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Leer {
    public static void leerEnviosCsv(String ficheroEnvios, ListaPortes portes, ListaClientes clientes) {
        BufferedReader out = null;
        try {
            out = new BufferedReader(new FileReader(ficheroEnvios));
            String linea = out.readLine();
            while (linea != null) {
                String[] datos = linea.split(";");
                Porte porte = portes.buscarPorte(datos[1]);
                Cliente cliente = new Cliente(datos[0], porte, clientes, Envio.TIPO.valueOf(datos[3]), Integer.parseInt(datos[4]);
                porte.ocuparHueco(envio);
                cliente.aniadirEnvio(envio);
                linea = out.readLine();
            }
        } catch (IOException ex) {
            System.out.println("Error de lectura de fichero Envios.");
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                System.out.println("Error de cierre de fichero Envios.");
            }
        }
    }

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
        } catch (Exception e) {
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

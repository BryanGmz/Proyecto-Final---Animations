/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanimaciones.backed.manejadores;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import proyectoanimaciones.backed.objetos.*;

/**
 *
 * @author bryan
 */
public class ManejadorEscritura {
    public static final String LIENZOS = "LIENZOS";
    public static final String NOMBRE = "nombre";
    public static final String TIPO = "tipo";
    public static final String FONDO = "Fondo";
    public static final String RED = "Red";
    public static final String BLUE = "Blue";
    public static final String GREEN = "Green";
    public static final String TAMANIO = "tamaño";
    public static final String DIMENSION_X = "dimension_x";
    public static final String DIMENSION_Y = "dimension_y";
    public static final String CUADRO = "cuadro";
    public static final String HEX = "HEX";
    public static final String COLORES = "COLORES";
    public static final String TIEMPOS = "TIEMPOS";
    public static final String INICIO = "inicio";
    public static final String FIN = "fin";
    public static final String IMAGENES = "imagenes";
    public static final String ID = "id";
    public static final String DURACION = "duracion";
    public static final String VARS = "VARS";
    public static final String COMENTARIO = "Sección de Variables";
    public static final String INSTRUCCIONES = "INSTRUCCIONES";
    public static final String PINTAR = "PINTAR";
    private static ManejadorEscritura manejadorEscritura;

    private ManejadorEscritura() {}
    
    public static ManejadorEscritura getInstancia() {
        if (manejadorEscritura == null) {
            manejadorEscritura = new ManejadorEscritura();
        } return manejadorEscritura;
    }
    
    public void escribirArchivoSalida(String path, String textoSalida) throws IOException {
        File chooser = new File(path);
        try (FileOutputStream salida = new FileOutputStream(chooser)) {
            byte[] byteTxt = textoSalida.getBytes();
            salida.write(byteTxt);
        }
    }
    
    public String archivoLienzo(List<Lienzo> lienzo){
        String salida = "{";
        salida += "\n\t" + LIENZOS + ": {";
        for (int i = 0; i < lienzo.size(); i++) {
            salida += "\n\t\t" + lienzo.get(i).getId() + ": {";
            salida += "\n\t\t\t" + NOMBRE + ": \"" + lienzo.get(i).getNombre() + "\",";
            salida += "\n\t\t\t" + TIPO + ": \"" + lienzo.get(i).getTipo() + "\",";
            salida += "\n\t\t\t" + FONDO + ": {";
            if (lienzo.get(i).getFondo().isHexadecimal()) {
                salida += "\n\t\t\t\t" + HEX + ": #" + lienzo.get(i).getFondo().getHex();
            } else {
                salida += "\n\t\t\t\t" + RED + ": " + lienzo.get(i).getFondo().getRed().getCantidadRed() + ",";
                salida += "\n\t\t\t\t" + BLUE + ": " + lienzo.get(i).getFondo().getBlue().getCantidadBlue() + ",";
                salida += "\n\t\t\t\t" + GREEN + ": " + lienzo.get(i).getFondo().getGreen().getCantidadGreen();
            }
            salida += "\n\t\t\t},";
            salida += "\n\t\t\t" + TAMANIO + ": {";
            salida += "\n\t\t\t\t" + CUADRO + ": " + lienzo.get(i).getTamaño().getCuadrado() + ",";
            salida += "\n\t\t\t\t" + DIMENSION_X + ": " + lienzo.get(i).getTamaño().getDimensionX() + ",";
            salida += "\n\t\t\t\t" + DIMENSION_Y + ": " + lienzo.get(i).getTamaño().getDimensionY();
            salida += "\n\t\t\t}";
            if (i == lienzo.size() - 1) {
                salida += "\n\t\t}";
            } else {
                salida += "\n\t\t},";
            }
        }
        salida += "\n\t}";
        salida += "\n}";
        return salida;
    }
    
    public String archivoColores(List<Lienzo> lienzo){
        String salida = "{";
        salida += "\n\t" + COLORES + ": {";
        for (int i = 0; i < lienzo.size(); i++) {
            salida += "\n\t\t" + lienzo.get(i).getId() + ": {";
            for (int j = 0; j < lienzo.get(i).getColores().size(); j++) {
                salida += "\n\t\t\t" + lienzo.get(i).getColores().get(j).getIdColor() + " :{";
                if (lienzo.get(i).getColores().get(j).getFondo().isHexadecimal()) {
                    salida += "\n\t\t\t\t" + HEX + ": #" + lienzo.get(i).getColores().get(j).getFondo().getHex();
                } else {
                    salida += "\n\t\t\t\t" + RED + ": " + lienzo.get(i).getColores().get(j).getFondo().getRed().getCantidadRed() + ",";
                    salida += "\n\t\t\t\t" + BLUE + ": " + lienzo.get(i).getColores().get(j).getFondo().getBlue().getCantidadBlue() + ",";
                    salida += "\n\t\t\t\t" + GREEN + ": " + lienzo.get(i).getColores().get(j).getFondo().getGreen().getCantidadGreen();
                }
                if (j == lienzo.get(i).getColores().size() - 1) {
                    salida += "\n\t\t\t}";                
                } else {
                    salida += "\n\t\t\t},";                
                }
            }
            if (i == lienzo.size() - 1) {
                salida += "\n\t\t}";
            } else {
                salida += "\n\t\t},";
            }
        }
        salida += "\n\t}";
        salida += "\n}";
        return salida;
    }
    
    public String archivoTiempo(List<Lienzo> lienzo){
        String salida = "{";
        salida += "\n\t" + TIEMPOS + ": {";
        for (int i = 0; i < lienzo.size(); i++) {
            salida += "\n\t\t" + lienzo.get(i).getId() + ": {";
            salida += "\n\t\t\t" + INICIO + ": \"" + lienzo.get(i).getTiempo().getInicio() + "\",";
            salida += "\n\t\t\t" + FIN + ": \"" + lienzo.get(i).getTiempo().getFin()+ "\",";
            salida += "\n\t\t\t" + IMAGENES + ": [";
            for (int j = 0; j < lienzo.get(i).getTiempo().getImagenes().size(); j++) {
                salida += "\n\t\t\t\t{";
                salida += "\n\t\t\t\t\t" + ID + ": \"" + lienzo.get(i).getTiempo().getImagenes().get(j).getId() + "\",";
                salida += "\n\t\t\t\t\t" + DURACION + ": " + lienzo.get(i).getTiempo().getImagenes().get(j).getDuracion();
                if (j == lienzo.get(i).getTiempo().getImagenes().size() - 1) {
                    salida += "\n\t\t\t\t}";
                } else {
                    salida += "\n\t\t\t\t},";
                }
            }
            salida += "\n\t\t\t]";
            if (i == lienzo.size() - 1) {
                salida += "\n\t\t}";
            } else {
                salida += "\n\t\t},";
            }
        }
        salida += "\n\t}";
        salida += "\n}";
        return salida;
    }
    
    public String archivoPintar(List<Lienzo> lienzo) {
        String salida = "";
        salida += VARS + " [";
        salida += "\n\t/*";
        salida += "\n\t" + COMENTARIO;
        salida += "\n\t*/";
        salida += "\n]";
        for (int i = 0; i < lienzo.size(); i++) {
            salida += "\n" + INSTRUCCIONES + "(" +  lienzo.get(i).getId() + ") [";
            for (Pintar pintar : lienzo.get(i).getPintar()) {
                if (pintar.getDimesionX().isRango()) {
                    salida += "\n\t" + PINTAR + "(" + pintar.getIdColor().getIdColor() + ", " +  pintar.getIdImg().getId() + ", " + 
                            pintar.getDimesionX().getRangoInicial() + ".." + pintar.getDimesionX().getRangoFinal() + ", " + pintar.getDimesionY().getDimension() +");";
                } else if (pintar.getDimesionY().isRango()) {
                    salida += "\n\t" + PINTAR + "(" + pintar.getIdColor().getIdColor() + ", " +  pintar.getIdImg().getId() + ", " + 
                            pintar.getDimesionX().getDimension() + ", " + pintar.getDimesionY().getRangoInicial() + ".." + pintar.getDimesionY().getRangoFinal() + ");";
                } else if (pintar.getDimesionY().isRango() && pintar.getDimesionX().isRango()) {
                    salida += "\n\t" + PINTAR + "(" + pintar.getIdColor().getIdColor() + ", " +  pintar.getIdImg().getId() + ", " + 
                            pintar.getDimesionX().getRangoInicial() + ".." + pintar.getDimesionX().getRangoFinal() + ", " + pintar.getDimesionY().getRangoInicial() + ".." + pintar.getDimesionY().getRangoFinal() +");";
                } else {
                    salida += "\n\t" + PINTAR + "(" + pintar.getIdColor().getIdColor() + ", " +  pintar.getIdImg().getId() + ", " + pintar.getDimesionX().getDimension() + ", " + pintar.getDimesionY().getDimension() +");";
                }
            }
            salida += "\n]";
        }
        return salida;
    }
}



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanimaciones.backed.objetos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bryan
 */
public class Lienzo {
    private String id;
    private String nombre;
    private String tipo;
    private Fondo fondo;
    private Tiempo tiempo;
    private Tamaño tamaño;
    private List<Color> colores;
    private List<Pintar> pintar;
    
    public Lienzo(String nombre, String tipo, Fondo fondo, Tamaño tamaño, String id) {
        this.nombre = nombre.replaceAll("\"", "");
        this.tipo = tipo.replaceAll("\"", "");
        this.fondo = fondo;
        this.tamaño = tamaño;
        this.id = id;
        this.pintar = new ArrayList<>();
    }

    public List<Pintar> getPintar() {
        return pintar;
    }

    public void setPintar(List<Pintar> pintar) {
        this.pintar = pintar;
    }
    
    public List<Color> getColores() {
        return colores;
    }

    public void setColores(List<Color> colores) {
        this.colores = colores;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Fondo getFondo() {
        return fondo;
    }

    public void setFondo(Fondo fondo) {
        this.fondo = fondo;
    }

    public Tamaño getTamaño() {
        return tamaño;
    }

    public void setTamaño(Tamaño tamaño) {
        this.tamaño = tamaño;
    }

    public Tiempo getTiempo() {
        return tiempo;
    }

    public void setTiempo(Tiempo tiempo) {
        this.tiempo = tiempo;
    }
 
    public List<Pintar> listaPintarID(String id) {
        List<Pintar> listaRetornar = new ArrayList<>();
        pintar.stream().filter((pint) -> (pint.getIdImg().getId().equals(id))).forEachOrdered((pint) -> {
            listaRetornar.add(pint);
        });
        return listaRetornar;
    }
    
    public Duracion retornaImg(String id){
        for (Duracion img : tiempo.getImagenes()) {
            if (img.getId().equals(id)) {
                return img;
            }
        } return null;
    }
    
    public void cleanPint(String id){
        List<Pintar> lista = new ArrayList<>();
        pintar.stream().filter((pint) -> (!pint.getIdImg().getId().equals(id))).forEachOrdered((pint) -> {
            lista.add(pint);
        });
        pintar = lista;
    }
    
    public void imprimir(){
        System.out.println("\n\nLienzo: " + this.id);
        System.out.println("\nNombre: " + this.nombre);
        System.out.println("Tipo: " + this.tipo);
        if (this.fondo != null) {
            System.out.println("\n  Fondo: ");
            if (this.fondo.isHexadecimal()) {
                System.out.println("        HEX: " + this.fondo.getHex());
            } else {
                System.out.println("        R: " + this.fondo.getRed().getCantidadRed() + "  B: " + this.fondo.getBlue().getCantidadBlue() + "  G: " + this.fondo.getGreen().getCantidadGreen()) ;
            }
        }
        if (this.tiempo != null) {
            System.out.println("\n  Tiempo:");
            System.out.println("\t\tInicio: " + this.tiempo.getInicio());
            System.out.println("\t\tFin: " + this.tiempo.getFin());
            System.out.println("\t\tImagenes: \n");
            this.tiempo.getImagenes().stream().map((img) -> {
                System.out.println("\n\t\t\tID: " + img.getId());
                return img;
            }).forEachOrdered((img) -> {
                System.out.println("\t\t\tDuracion: " + img.getDuracion());
            });
        }
        if (this.tamaño != null) {
            System.out.println("\n  Tamaño:");
            System.out.println("\n\t\tCuadrado: " + this.tamaño.getCuadrado());
            System.out.println("\t\tX " + this.tamaño.getDimensionX());
            System.out.println("\t\tY " + this.tamaño.getDimensionY());
        }
        if (colores != null) {
            System.out.println("\nColores:");
            colores.stream().map((color) -> {
                System.out.println("\n\tID: " + color.getIdColor());
                return color;
            }).forEachOrdered((color) -> {
                color.getFondo().imp();
            });
        }
        System.out.println("\n\tPintar:");
        pintar.stream().map((paint) -> {
            System.out.println("\n\t\tID Color " + paint.getIdColor().getIdColor());
            return paint;
        }).map((paint) -> {
            System.out.println("\t\tID Img " + paint.getIdImg().getId());
            return paint;
        }).map((paint) -> {
            System.out.println("\t\tX " + paint.getDimesionX().toString());
            return paint;
        }).forEachOrdered((paint) -> {
            System.out.println("\t\tY " + paint.getDimesionY().toString());
        });
    }
    
    public void modificarDuracion(int duracion, String id){
        tiempo.getImagenes().stream().filter((img) -> (img.getId().equals(id))).forEachOrdered((img) -> {
            img.setDuracion(duracion);
        });
    }
    
}

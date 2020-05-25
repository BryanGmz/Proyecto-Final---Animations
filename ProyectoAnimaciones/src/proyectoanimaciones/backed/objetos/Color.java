/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanimaciones.backed.objetos;

/**
 *
 * @author bryan
 */
public class Color {
   private Fondo fondo;
   private String idColor;

    public Color() {}

    public Color(Fondo fondo, String idColor) {
        this.fondo = fondo;
        this.idColor = idColor;
    }

    @Override
    public  Color clone() throws CloneNotSupportedException {
        return new Color(fondo, idColor); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Fondo getFondo() {
        return fondo;
    }

    public void setFondo(Fondo fondo) {
        this.fondo = fondo;
    }

    public String getIdColor() {
        return idColor;
    }

    public void setIdColor(String idColor) {
        this.idColor = idColor;
    }

    public java.awt.Color getColor() {
        if (fondo.isHexadecimal()) {
            return java.awt.Color.decode("#" + fondo.getHex());
        } else {
            return new java.awt.Color(fondo.getRed().getCantidadRed(), fondo.getGreen().getCantidadGreen(), fondo.getBlue().getCantidadBlue());
        }
    }
    
   @Override
    public String toString(){
        if (idColor != null) {
            return this.idColor;
        } return "";
    }
}

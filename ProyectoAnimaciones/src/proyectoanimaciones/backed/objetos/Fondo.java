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
public class Fondo {
    private Red red;
    private Blue blue;
    private Green green;
    private String hex;
    private boolean hexadecimal;

    public Fondo() {}

    public Fondo(Red red, Blue blue, Green green) {
        this.red = red;
        this.blue = blue;
        this.green = green;
    }

    
    public Fondo(String hex) {
        this.hex = hex;
        this.hexadecimal = true;
    }

    public Red getRed() {
        return red;
    }

    public void setRed(Red red) {
        this.red = red;
    }

    public Blue getBlue() {
        return blue;
    }

    public void setBlue(Blue blue) {
        this.blue = blue;
    }

    public Green getGreen() {
        return green;
    }

    public void setGreen(Green green) {
        this.green = green;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public boolean isHexadecimal() {
        return hexadecimal;
    }

    public void setHexadecimal(boolean hexadecimal) {
        this.hexadecimal = hexadecimal;
    }
    
    public void imp(){
        System.out.println("\n  \tFondo: ");
        if (isHexadecimal()) {
            System.out.println("        \tHEX: " + this.getHex());
        } else {
            System.out.println("        \tR: " + this.getRed().getCantidadRed() + "  B: " + this.getBlue().getCantidadBlue() + "  G: " + this.getGreen().getCantidadGreen()) ;
        }
    }
    
    public java.awt.Color getColor() {
        if (isHexadecimal()) {
            return java.awt.Color.decode("#" + getHex());
        } else {
            return new java.awt.Color(getRed().getCantidadRed(), getGreen().getCantidadGreen(), getBlue().getCantidadBlue());
        }
    }
}

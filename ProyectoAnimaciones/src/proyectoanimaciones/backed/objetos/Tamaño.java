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
public class Tamaño {
    private int cuadrado;
    private int dimensionX;
    private int dimensionY;

    public Tamaño(int cuadrado, int dimensionX, int dimensionY) {
        this.cuadrado = cuadrado;
        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
    }

    public Tamaño() {}

    public int getCuadrado() {
        return cuadrado;
    }

    public void setCuadrado(int cuadrado) {
        this.cuadrado = cuadrado;
    }

    public int getDimensionX() {
        return dimensionX;
    }

    public void setDimensionX(int dimensionX) {
        this.dimensionX = dimensionX;
    }

    public int getDimensionY() {
        return dimensionY;
    }

    public void setDimensionY(int dimensionY) {
        this.dimensionY = dimensionY;
    }
    
}

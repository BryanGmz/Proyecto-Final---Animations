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
public class Dimension {
    private int dimension;
    private int rangoInicial;
    private int rangoFinal;
    private boolean rango;
    private boolean scadena;
    private String dString;
    
    public Dimension(int dimension) {
        this.dimension = dimension;
    }

    public Dimension(int rangoInicial, int rangoFinal) {
        this.rangoInicial = rangoInicial;
        this.rangoFinal = rangoFinal;
        this.rango = true;
    }

    public Dimension(String dString) {
        this.dString = dString;
        this.scadena = true;
    }
    
    public String getdString() {
        return dString;
    }

    public boolean isScadena() {
        return scadena;
    }
    
    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int getRangoInicial() {
        return rangoInicial;
    }

    public void setRangoInicial(int rangoInicial) {
        this.rangoInicial = rangoInicial;
    }

    public int getRangoFinal() {
        return rangoFinal;
    }

    public void setRangoFinal(int rangoFinal) {
        this.rangoFinal = rangoFinal;
    }

    public boolean isRango() {
        return rango;
    }
    
    @Override
    public Dimension clone() throws CloneNotSupportedException{
        if (rango) {
            return new Dimension(this.rangoInicial, this.rangoFinal);
        } else {
            return new Dimension(this.dimension);
        }
    }
    
     @Override
    public String toString() {
        if (rango) {
            return "Incial -> " + rangoInicial + "\tFinal -> " + rangoFinal;
        } else {
            return "Dimension: " + dimension;
        }
    }
}

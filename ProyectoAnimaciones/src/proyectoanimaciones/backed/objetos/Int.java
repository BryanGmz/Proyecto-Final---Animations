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
public class Int {
    private int contenido;
    private Dimension dimension;
    
    public Int(int contenido) {
        this.contenido = contenido;
    }

    public int getContenido() {
        return contenido;
    }

    public void setContenido(int contenido) {
        this.contenido = contenido;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }
    
    @Override
    public Int clone() throws CloneNotSupportedException {
        return new Int(this.contenido);
    }
    
    
}

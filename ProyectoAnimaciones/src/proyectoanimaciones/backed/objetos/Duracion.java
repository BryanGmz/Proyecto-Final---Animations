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
public class Duracion {
    private String id;
    private int duracion;

    public Duracion(String id, int duracion) {
        this.id = id;
        this.duracion = duracion;
    }

    @Override
    public Duracion clone() throws CloneNotSupportedException {
        return new Duracion(id, duracion);//To change body of generated methods, choose Tools | Templates.
    }
    
    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

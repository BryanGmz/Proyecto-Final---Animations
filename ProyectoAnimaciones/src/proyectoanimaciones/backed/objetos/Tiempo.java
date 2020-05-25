/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanimaciones.backed.objetos;

import java.util.List;

/**
 *
 * @author bryan
 */
public class Tiempo {
    private String inicio;
    private String fin;
    private List<Duracion> imagenes;

    public Tiempo(String inicio, String fin) {
        this.inicio = inicio;
        this.fin = fin;
    }

    public Tiempo() {}

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public List<Duracion> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Duracion> imagenes) {
        this.imagenes = imagenes;
    }
}

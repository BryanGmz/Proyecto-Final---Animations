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
public class Pintar {
    private Color idColor;
    private Duracion idImg;
    private Dimension dimesionX;
    private Dimension dimesionY;
    
    public Pintar(Color idColor, Duracion idImg, Dimension dimesionX, Dimension dimesionY) {
        this.idColor = idColor;
        this.idImg = idImg;
        this.dimesionX = dimesionX;
        this.dimesionY = dimesionY;
    }
    
    public Color getIdColor() {
        return idColor;
    }

    public void setIdColor(Color idColor) {
        this.idColor = idColor;
    }

    public Duracion getIdImg() {
        return idImg;
    }

    public void setIdImg(Duracion idImg) {
        this.idImg = idImg;
    }

    public Dimension getDimesionX() {
        return dimesionX;
    }

    public void setDimesionX(Dimension dimesionX) {
        this.dimesionX = dimesionX;
    }

    public Dimension getDimesionY() {
        return dimesionY;
    }

    public void setDimesionY(Dimension dimesionY) {
        this.dimesionY = dimesionY;
    }

   
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanimaciones.backed.manejadores;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import proyectoanimaciones.backed.manejadores.gif_png.AnimatedGifEncoder;
import proyectoanimaciones.backed.objetos.Duracion;
import proyectoanimaciones.backed.objetos.Lienzo;
import proyectoanimaciones.backed.objetos.Pintar;

/**
 *
 * @author bryan
 */
public class ManejadorExportar {
    
    private static ManejadorExportar manejadorExportar;

    private ManejadorExportar() {}
    
    public static ManejadorExportar getInstancia(){
        if (manejadorExportar == null) {
            manejadorExportar = new ManejadorExportar();
        } return manejadorExportar;
    }
    
    public void pintar(Graphics g, java.util.List<Pintar> pintar, int pixels){
        pintar.forEach((pint) -> {
            if (pint.getDimesionX().isRango() && pint.getDimesionY().isRango()) {
                for (int i = pint.getDimesionX().getRangoInicial(); i < pint.getDimesionX().getRangoFinal(); i++) {
                    for (int j = pint.getDimesionY().getRangoInicial(); j < pint.getDimesionY().getRangoFinal(); j++) {
                        g.setColor(pint.getIdColor().getColor());
                        g.fillRect(i*pixels, j*pixels, pixels, pixels);
                    }
                }
            } else if (pint.getDimesionX().isRango()){
                for (int i = pint.getDimesionX().getRangoInicial(); i < pint.getDimesionX().getRangoFinal(); i++) {
                    g.setColor(pint.getIdColor().getColor());
                    g.fillRect(i*pixels, pint.getDimesionY().getDimension()*pixels, pixels, pixels);
                }
            } else if (pint.getDimesionY().isRango()) {
                for (int i = pint.getDimesionY().getRangoInicial(); i < pint.getDimesionY().getRangoFinal(); i++) {
                    g.setColor(pint.getIdColor().getColor());
                    g.fillRect(pint.getDimesionX().getDimension()*pixels, i*pixels, pixels, pixels);
                }
            } else {
                g.setColor(pint.getIdColor().getColor());
                g.fillRect(pint.getDimesionX().getDimension()*pixels, pint.getDimesionY().getDimension()*pixels, pixels, pixels);
            }
        });
    }
    
    public Duracion searchDuracion(Lienzo lienzo, String id){
        for (Duracion img : lienzo.getTiempo().getImagenes()) {
            if (img.getId().equals(id)) {
                return img;
            }
        } return null;
    }
    
    private void sourcePint(int dimensionX, int dimensionY, Lienzo lienzo, AnimatedGifEncoder gifEncoder, String id){
        BufferedImage img = new BufferedImage(dimensionX, dimensionY, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        g.setColor(lienzo.getFondo().getColor());
        g.fillRect(0, 0, dimensionX, dimensionY);
        pintar(g, lienzo.listaPintarID(id), lienzo.getTamaño().getCuadrado());
        gifEncoder.addFrame(img);
    }
    
    public boolean exportarGif(Lienzo lienzo, String directorio){
        int dimensionX = lienzo.getTamaño().getCuadrado() * lienzo.getTamaño().getDimensionX();
        int dimensionY = lienzo.getTamaño().getCuadrado() * lienzo.getTamaño().getDimensionY();
        AnimatedGifEncoder gifEncoder = new AnimatedGifEncoder();
        gifEncoder.start(directorio + "/" + lienzo.getNombre() + "." + lienzo.getTipo());
        gifEncoder.setDelay(searchDuracion(lienzo, lienzo.getTiempo().getInicio()).getDuracion());
        sourcePint(dimensionX, dimensionY, lienzo, gifEncoder, lienzo.getTiempo().getInicio());
        lienzo.getTiempo().getImagenes().stream().filter((duraciones) -> (!duraciones.getId().equals(lienzo.getTiempo().getInicio()) && !duraciones.getId().equals(lienzo.getTiempo().getFin()))).map((duraciones) -> {
            gifEncoder.setDelay(searchDuracion(lienzo, duraciones.getId()).getDuracion());
            return duraciones;
        }).forEachOrdered((duraciones) -> {
            sourcePint(dimensionX, dimensionY, lienzo, gifEncoder, duraciones.getId());
        });
        gifEncoder.setDelay(searchDuracion(lienzo, lienzo.getTiempo().getFin()).getDuracion());
        sourcePint(dimensionX, dimensionY, lienzo, gifEncoder, lienzo.getTiempo().getFin());
        return gifEncoder.finish();
    }
 
    public void expotarPNG(Lienzo lienzo, String directorio) throws IOException{
        int dimensionX = lienzo.getTamaño().getCuadrado() * lienzo.getTamaño().getDimensionX();
        int dimensionY = lienzo.getTamaño().getCuadrado() * lienzo.getTamaño().getDimensionY();
        for (Duracion img : lienzo.getTiempo().getImagenes()) {
            String path = directorio + "/" + lienzo.getNombre() + "-" + img.getId() + "." + lienzo.getTipo();
            BufferedImage imagen = new BufferedImage(dimensionX, dimensionY, BufferedImage.TYPE_INT_ARGB);
            Graphics g = imagen.getGraphics();
            g.setColor(lienzo.getFondo().getColor());
            g.fillRect(0, 0, dimensionX, dimensionY);
            pintar(g, lienzo.listaPintarID(img.getId()), lienzo.getTamaño().getCuadrado());
            ImageIO.write(imagen, "png", new File(path));
        }
    }
    
}

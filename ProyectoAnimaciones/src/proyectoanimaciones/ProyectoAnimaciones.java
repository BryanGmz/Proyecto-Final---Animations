/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanimaciones;

import java.io.StringReader;
import javax.swing.UIManager;
import proyectoanimaciones.backed.analizador.Lexico;
import proyectoanimaciones.backed.analizador.Sintax;
import proyectoanimaciones.backed.analizador.pintar.LexicoPintar;
import proyectoanimaciones.backed.analizador.pintar.SintaxPintar;
import proyectoanimaciones.gui.Principal;

/**
 *
 * @author bryan
 */
public class ProyectoAnimaciones {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception Excepcion de Principal
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        GeneradorArchivos generadorArchivos = GeneradorArchivos.getGeneradorArchivos();
//        generadorArchivos.genearar(GeneradorArchivos.PATH_FLEX_PINTAR, GeneradorArchivos.PATH_CUP_PINTAR, GeneradorArchivos.PATH_SYM_PINTAR, 
//                GeneradorArchivos.PATH_SINTAX_PINTAR, GeneradorArchivos.NOMBRE_SINTAX_PINTAR, GeneradorArchivos.NOMBRE_CLASE_SINTAX_PINTAR);
////        UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
//        generadorArchivos.genearar(GeneradorArchivos.PATH_FLEX, GeneradorArchivos.PATH_CUP, GeneradorArchivos.PATH_SYM, 
//                GeneradorArchivos.PATH_SINTAX, GeneradorArchivos.NOMBRE_SINTAX, GeneradorArchivos.NOMBRE_CLASE_SINTAX);

        Principal principal = new Principal();
        principal.setVisible(true);
       
    }
    
}

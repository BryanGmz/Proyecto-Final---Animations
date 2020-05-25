/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanimaciones.backed.manejadores;

import java.util.ArrayList;
import java.util.List;
import proyectoanimaciones.backed.objetos.*;

/**
 *
 * @author bryan
 */
public class ManejadorPintar {
    private TablaSimbolos actual;
    private Lienzo lienzo;
    private static ManejadorPintar manejadorPintar;
    private ManejadorSintactico ms;
    
    private ManejadorPintar() {}

    public static ManejadorPintar getManejadorPintar() {
        if (manejadorPintar == null) {
            manejadorPintar = new ManejadorPintar();
        }
        return manejadorPintar;
    }

    public void setManejador(ManejadorSintactico ms) {
        this.ms = ms;
    }
    
    public TablaSimbolos getActual() {
        return actual;
    }

    public void setActual(TablaSimbolos actual) {
        this.actual = actual;
    }
    
    public Lienzo buscarLienzo(String id, List<Lienzo> liezo){
        for (Lienzo comparar : liezo) {
            if (comparar.getId().equals(id)) {
                lienzo = comparar;
                return lienzo;
            }
        } return null;
    }
    
    public Color buscaColores(Object id, int left, int right){
        if (actual != null && id != null) {
            Object colores = actual.buscarPorTipo("List<Color>");
            if (colores != null) {
                for (Color color : ((List<Color>) colores)) {
                    if (color.getIdColor().equals(id.toString())) {
                        return color;
                    }
                }
                ms.errorSemantico(left, right, "Color - ID: " + id, "El color con el ID: " + id + ", no se encuentra definido en el lienzo.");
                return null;
            } else {
                ms.errorSemantico(left, right, "Color- ID: " + id, "Lo siento el lienzo no tiene definido colores.");
                return null;
            }
        } else {
            ms.errorSemantico(left, right, "Color - ID: " + id, "El color con el ID: " + id + ", no se encuentra definido en el lienzo.");
            return null;
        }
    }
    
    public Duracion buscaImg(Object id, int left, int right){
        if (actual != null && id != null) {
            Object tiempos = actual.buscarPorTipo("Tiempo");
            if (tiempos != null) {
                for (Duracion imagene : ((Tiempo) tiempos).getImagenes()) {
                    if (imagene.getId().equals(id.toString())) {
                        return imagene;
                    }
                }
                ms.errorSemantico(left, right, "Imagen - ID: " + id, "La imagen con el ID: " + id + ", no se encuentra definido en el lienzo.");
                return null;
            } else {
                ms.errorSemantico(left, right, "Imagen- ID: " + id, "Lo siento el lienzo no tiene definido imagenes.");
                return null;
            }
        } else {
            ms.errorSemantico(left, right, "Imagen - ID: " + id, "La imagen con el ID: " + id + ", no se encuentra definido en el lienzo.");
            return null;
        }
    }
    
    public String buscaImgId(Object id, Object cadena, int left, int right){
        if (actual != null ) {
            if (id != null) {
                return sDI(id.toString(), left, right);
            } else {
                if (cadena != null) {
                    return sDI(cadena.toString(), left, right);
                } else {
                    ms.errorSemantico(left, right, "Imagen - ID: " + id, "La imagen con el ID: " + id + ", no se encuentra definido en el lienzo.");
                    return null;
                }
            }
        } else {
            ms.errorSemantico(left, right, "Lienzo", "No se puede realizar busquedas en el lienzo por que no se encuentra definido.");
            return null;
        }
    }
    
    private String sDI(String id, int left, int right){
        Object tiempos = actual.buscarPorTipo("Tiempo");
        if (tiempos != null) {
            for (Duracion imagene : ((Tiempo) tiempos).getImagenes()) {
                if (imagene.getId().equals(id)) {
                    return imagene.getId();
                }
            }
            ms.errorSemantico(left, right, "Imagen - ID: " + id, "La imagen con el ID: " + id + ", no se encuentra definido en el lienzo.");
            return null;
        } else {
            ms.errorSemantico(left, right, "Imagen- ID: " + id, "Lo siento el lienzo no tiene definido imagenes.");
            return null;
        }
    }
    
    private String sCI(String id, int left, int right){
        Object colores = actual.buscarPorTipo("List<Color>");
        if (colores != null) {
            for (Color color : ((List<Color>) colores)) {
                if (color.getIdColor().equals(id)) {
                    return color.getIdColor();
                }
            }
            ms.errorSemantico(left, right, "Color - ID: " + id, "El color con ID: " + id + ", no se encuentra definido en el lienzo.");
            return null;
        } else {
            ms.errorSemantico(left, right, "Color - ID: " + id, "Lo siento el lienzo no tiene definido colores.");
            return null;
        }
    }
    
    public String buscaColoresId(Object id, Object cadena, int left, int right){
        if (actual != null) {
            if (id != null) {
                return sCI(id.toString(), left, right);
            } else {
                if (cadena != null) {
                    return sCI(cadena.toString(), left, right);
                } else {
                    ms.errorSemantico(left, right, "Color - ID: " + id, "El color con ID: " + id + ", no se encuentra definido en el lienzo.");
                    return null;
                }
            }
        } else {
            ms.errorSemantico(left, right, "Lienzo", "No se puede realizar busquedas en el lienzo por que no se encuentra definido.");
            return null;
        }
    }
    
    public boolean comprobarSiSonMismoTipo(Object a, Object b, int aleft, int aright, int bleft, int bright, int caso){
        if (a != null) {
            if (b != null) {
                switch (caso) {
                    case 1:
                        if (a instanceof String && b instanceof String) {
                            return true;
                        }
                        break;
                    case 2:
                        try {
                            int auxA = (int) a;
                            auxA = (int) b;
                            return true;
                        } catch (Exception e) {
                            break;
                        } 
                    case 3:
                        try {
                            boolean auxA  = (boolean) a;
                            auxA  = (boolean) b;
                            return true;
                        } catch (Exception e) {
                            break;
                        }
                }
            } else {
                ms.errorSemantico(bleft, bright, "Variable", "El tipo de dato no posee ningun valor.");
                return false;
            }
        } else {
            ms.errorSemantico(aleft, aright, "Variable", "El tipo de dato no posee ningun valor.");
            return false;
        } 
        ms.errorSemantico(aleft, aright, "Variable", "El dato: " + a + ", es diferente al tipo de dato: " + b);
        return false;
    }
    
    public Pintar comprobarPintar(Object a, Object b, Object c, Object d, int aleft, int aright, int bleft, int bright, 
            int cleft, int cright, int dleft, int dright, boolean soloRetornar) {
        Dimension x;
        Dimension y;
        if (a != null) {
            if (b != null) {
                if (c != null) {
                    if (c instanceof String) {
                        x = new Dimension((String) c);
                    } else {
                        if (!(c instanceof Dimension)) {
                            x = new Dimension((int) c);
                        } else {
                            x = ((Dimension) c);
                        }
                    }
                    if (d != null) {
                        if (d instanceof String) {
                            y = new Dimension((String) d);
                        } else {
                            if (!(d instanceof Dimension)) {
                                y = new Dimension((int) d);
                            } else {
                                y = ((Dimension) d);
                            }
                        }
                        Pintar pint = new Pintar((Color) a, (Duracion) b, x, y);
                        if (!soloRetornar) {
                            asignarPintar(pint);
                        }
                        return pint;
                    } else {
                        ms.errorSintax(dleft, dright, "Dimension Y", "La dimension_y no se encuentra definida");
                    }
                } else {
                    ms.errorSintax(cleft, cright, "Dimension X", "La dimension_x no se encuentra definida");
                }
            } else {
                ms.errorSintax(bleft, bright, "Imagen", "No se encuentra definido el << ID >> de la imagen");
            }
        } else {
            ms.errorSintax(bleft, bright, "Imagen", "No se encuentra definido el << ID >> del lienzo");
        }
        return null;
    }

    public void asignarPintar(Pintar pint){
        if (actual != null) {
            Object listPintar = actual.buscarPorTipo("List<Pintar>");
            if (listPintar != null) {
                ((List<Pintar>)listPintar).add(pint);
                lienzo.getPintar().add(pint);
            } else {
                List<Pintar> lista = new ArrayList<>();
                lista.add(pint);
                actual.agregarTablaTemp(lista, "List<Pintar>");
                lienzo.getPintar().add(pint);
            }
        } else {
            ms.errorSemantico(0, 0, "Lienzo", "No se puede realizar busquedas en el lienzo por que no se encuentra definido.");
        }
    }
    
    public String comprobarTipoObjeto(Object object) {
        if (object instanceof Pintar) {
            return "Pintar";
        } else if (object instanceof String) {
            return "String";
        } else if (object instanceof TablaSimbolos) {
            return ((TablaSimbolos) object).getId();
        } else {
            try {
                int a = (int) object;
                return "Int";
            } catch (Exception e) {
                return "Boolean";
            }
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanimaciones.backed.manejadores;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import proyectoanimaciones.backed.objetos.*;
import proyectoanimaciones.gui.Principal;


/**
 *
 * @author bryan
 */
public class ManejadorSintactico {
    private static ManejadorSintactico manejadorSintactico;
    private static ManejadorPintar mp = ManejadorPintar.getManejadorPintar();
    private static TablaSimbolos simbolosLnz;
    private static TablaSimbolos simbolosPintarEnTurno;
    private static List<TablaSimbolos> listaSimbolos;
    private static List<Lienzo> lienzos;
    private static Stack<TablaSimbolos> whilesEIfs;
    private Principal principal;
    private static final String M_EDE = " Ya se encuentra definido en la estructura.";//Constante para indicar que el token ya se definio en la estructura
    private static final String E_N_E_C = " La estructura no esta completa.";//Constante para indicar que no se encuentra completa.
    
    
    private ManejadorSintactico() {}
    
    public static ManejadorSintactico getInstancia() {
        if (manejadorSintactico == null) {
            manejadorSintactico = new ManejadorSintactico();
            simbolosLnz = new TablaSimbolos(new ArrayList<>(), "");
            listaSimbolos = new ArrayList<>();
            lienzos = new ArrayList<>();
            whilesEIfs = new Stack<>();
            mp.setManejador(manejadorSintactico);
        }
        return manejadorSintactico;
    }

    public void clear(){
        if (simbolosLnz != null) {
            if (simbolosLnz.getSimbolos() != null) {
                simbolosLnz.getSimbolos().clear();
            }
            if (simbolosLnz.getSimbolosElse()!= null) {
                simbolosLnz.getSimbolosElse().clear();
            }
        }
        if (simbolosPintarEnTurno != null) {
            if (simbolosPintarEnTurno.getSimbolos() != null) {
                simbolosPintarEnTurno.getSimbolos().clear();
            }
            if (simbolosPintarEnTurno.getSimbolosElse()!= null) {
                simbolosPintarEnTurno.getSimbolosElse().clear();
            }
        }
        if (listaSimbolos != null) {
            listaSimbolos.clear();
        }
        if (whilesEIfs != null) {
            whilesEIfs.clear();
        }
        if (lienzos != null) {
            lienzos.clear();
        }
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
    
    public static List<Lienzo> getLienzos() {
        return lienzos;
    }

    public static void setLienzos(List<Lienzo> lienzos) {
        ManejadorSintactico.lienzos = lienzos;
    }
    
    public static TablaSimbolos getSimbolosLnz() {
        return simbolosLnz;
    }
    
    public void errorSintax(int left, int right, String value, String mensaje){
        if (mensaje.isEmpty()) {
            principal.addErrores(
            "\nError de Sintaxis: " 
            + "\n\tLinea #:                 << " + (right + 1) + " >>"
            + "\n\tColumna #                << " + (((left)/(right)) + 1) + " >>"
            + "\n\tToken NO Reconocido:     << " + (value) + " >>" );
        } else {
            principal.addErrores(
            "\nError de Sintaxis: " 
            + "\n\tLinea #:                 << " + (right + 1) + " >>"
            + "\n\tColumna #                << " + (((left)/(right)) + 1) + " >>"
            + "\n\tToken NO Reconocido:     << " + (value) + " >>" 
            + "\n\tMensaje (Informacion): "
            + "\n\t\t-> " + mensaje);
        }
    }
    
    public void errorSemantico(int left, int right, String valor, String mensaje){
        principal.addErrores(
            "\nError Semantico: " 
            + "\n\tLinea #:                 << " + (right + 1) + " >>"
            + "\n\tColumna #                << " + (((left)/(right)) + 1) + " >>"
            + "\n\tValor:                   << " + valor + " >>"
            + "\n\tMensaje (Informacion): "
            + "\n\t\t-> " + mensaje);
    }
    
    public Object addRGB(Object objeto, int caso,  int left, int right){
        Object aux;
        switch (caso) {
            case 1://Red
                aux = simbolosLnz.buscarPorTipo("Red");
                if (aux == null) {
                    simbolosLnz.agregarTablaTemp((Red) objeto, "Red");
                    return (Red) objeto;
                } else {
                    errorSintax(left, right,  "Red", "<< Red >> -> Valor: " + ((Red) objeto).getCantidadRed() + M_EDE);
                    return null;
                }
            case 2://Blue
                aux = simbolosLnz.buscarPorTipo("Blue");
                if (aux == null) {
                    simbolosLnz.agregarTablaTemp((Blue) objeto, "Blue");
                    return (Blue) objeto;
                } else {
                    errorSintax(left, right,  "Blue", "<< Blue >> -> Valor: " + ((Blue) objeto).getCantidadBlue() + M_EDE);
                    return null;
                }
            case 3://Green
                aux = simbolosLnz.buscarPorTipo("Green");
                if (aux == null) {
                    simbolosLnz.agregarTablaTemp((Green) objeto, "Green");
                    return (Green) objeto;
                } else {
                    errorSintax(left, right,  "Green", "<< Green >> -> Valor: " + ((Green) objeto).getCantidadGreen() + M_EDE);
                    return null;
                }
            default:
                return null;
        }
    }
    
    public Integer addCDXY(Object objeto, int caso,  int left, int right){
        Object aux;
        switch (caso) {
            case 1://Color
                aux = simbolosLnz.buscarPorTipo("Color");
                if (aux == null) {
                    simbolosLnz.agregarTablaTemp(Integer.parseInt(objeto.toString()), "Color");
                    return Integer.parseInt(objeto.toString());
                } else {
                    errorSintax(left, right,  "Color", "<< Color >> -> Valor: " + Integer.parseInt(objeto.toString()) + M_EDE);
                    return null;
                }
            case 2://Dimension X
                aux = simbolosLnz.buscarPorTipo("DimensionX");
                if (aux == null) {
                    simbolosLnz.agregarTablaTemp(Integer.parseInt(objeto.toString()), "DimensionX");
                    return Integer.parseInt(objeto.toString());
                } else {
                    errorSintax(left, right,  "Dimension_X", "<< Dimension_X >> -> Valor: " + Integer.parseInt(objeto.toString()) + M_EDE);
                    return null;
                }
            case 3://Green
                aux = simbolosLnz.buscarPorTipo("DimensionY");
                if (aux == null) {
                    simbolosLnz.agregarTablaTemp(Integer.parseInt(objeto.toString()), "DimensionY");
                    return Integer.parseInt(objeto.toString());
                } else {
                    errorSintax(left, right,  "Dimension_Y", "<< Dimension_Y >> -> Valor: " + Integer.parseInt(objeto.toString()) + M_EDE);
                    return null;
                }
            default:
                return null;
        }
    }
    
    public Fondo addFondo(Object objeto, int left, int right) {
        Object aux = simbolosLnz.buscarPorTipo("Fondo");
        if (aux == null && objeto != null) {
            simbolosLnz.agregarTablaTemp((Fondo) objeto, "Fondo");
            return (Fondo) objeto;
        } else {
            errorSintax(left, right,  "Fondo", "<< Fondo >> " + M_EDE);
            return null;
        }
    }
    
    public Tamaño addTamaño(Object objeto, int left, int right){
        if (simbolosLnz.buscarPorTipo("Tamaño") == null && objeto != null) {
            simbolosLnz.agregarTablaTemp((Tamaño) objeto, "Tamaño");
            return (Tamaño) objeto;
        } else {
            errorSintax(left, right,  "Tamaño", "<< Tamaño >> " + M_EDE);
            return null;
        }
    }
    
    //Agregar Objetos String
    public Object add(Object object, int left, int right, String tipo){
        System.out.println("O: " + object + " T: " + tipo);
        if (simbolosLnz.buscarPorTipo(tipo) == null && object != null) {
            simbolosLnz.agregarTablaTemp(object, tipo);
            return object;
        } else {
            errorSintax(left, right,  tipo, "<< " + tipo + ">> -> Valor: " + object + M_EDE);
            return null;
        }
    }
    
    public Fondo comprobarRGB(Object a, Object b, Object c, int al, int ar, int bl, int br, int cl, int cr){
        Fondo fondo = new Fondo();
        if (a != null && b != null && c != null) {
            fondo.setRed((Red) simbolosLnz.buscarPorTipo("Red"));
            simbolosLnz.removerPorTipo("Red");
            fondo.setBlue((Blue) simbolosLnz.buscarPorTipo("Blue"));
            simbolosLnz.removerPorTipo("Blue");
            fondo.setGreen((Green) simbolosLnz.buscarPorTipo("Green"));
            simbolosLnz.removerPorTipo("Green");
            return fondo;
        } else {
            if (a == null) {
                errorSintax(al, ar,  "Fondo", "<< " + "Estructura RGB" + ">> " + E_N_E_C);
            }
            if (b == null) {
                errorSintax(bl, br,  "Fondo", "<< " + "Estructura RGB" + ">> " + E_N_E_C);
            }
            if (a == null) {
                errorSintax(cl, cr,  "Fondo", "<< " + "Estructura RGB" + ">> " + E_N_E_C);
            }
            simbolosLnz.removerPorTipo("Red");
            simbolosLnz.removerPorTipo("Blue");
            simbolosLnz.removerPorTipo("Green");
            return null;
        }
    }
    
    public Tamaño comprobarTamaño(Object a, Object b, Object c, int al, int ar, int bl, int br, int cl, int cr){
        Tamaño tamaño = new Tamaño();
        if (a != null && b != null && c != null) {
            tamaño.setCuadrado((Integer) simbolosLnz.buscarPorTipo("Color"));
            simbolosLnz.removerPorTipo("Color");
            tamaño.setDimensionX((Integer) simbolosLnz.buscarPorTipo("DimensionX"));
            simbolosLnz.removerPorTipo("DimensionX");
            tamaño.setDimensionY((Integer) simbolosLnz.buscarPorTipo("DimensionY"));
            simbolosLnz.removerPorTipo("DimensionY");
            return tamaño;
        } else {
            if (a == null) {
                errorSintax(al, ar,  "Tamaño", "<< " + "Estructura Tamaño" + ">> " + E_N_E_C);
            }
            if (b == null) {
                errorSintax(bl, br,  "Tamaño", "<< " + "Estructura Tamaño" + ">> " + E_N_E_C);
            }
            if (a == null) {
                errorSintax(cl, cr,  "Tamaño", "<< " + "Estructura Tamaño" + ">> " + E_N_E_C);
            }
            return null;
        }
    }
    
    public TablaSimbolos buscarIdLista(String idLsita){
        for (TablaSimbolos t : listaSimbolos) {
            if (t.getId().equals(idLsita)) {
                return t;
            } 
        } return null;
    }
    
    public boolean copiarLista(String idLista, int left, int right) {
        if (buscarIdLista(idLista) == null) {
            List<Simbolo> lista = new ArrayList<>();
            simbolosLnz.getSimbolos().forEach((simbolo) -> {
                lista.add(simbolo);
            });
            listaSimbolos.add(new TablaSimbolos(lista, idLista));
            return true;
        } else {
            errorSemantico(left, right,  idLista, "No se a registrado el lienzo debido a que, el ID: << " + idLista + " >>, ya se encuentra definido en el archivo lienzo.");
            return false;
        }
    }
    
    public void imprimir(){
        listaSimbolos.stream().map((listaSimbolo) -> {
            System.out.println("Tabla Simbolos: \n\n" + listaSimbolo.getId() + "\n\nSimbolos\n\n");
            return listaSimbolo;
        }).map((listaSimbolo) -> {
            listaSimbolo.getSimbolos().forEach((simbolo) -> {
                System.out.println("Tipo: " + simbolo.getTipo() + "\t\t" + "ID: " + simbolo.getId() + "\t\tValor: " + simbolo.getValor().toString() );
            });
            return listaSimbolo;
        }).forEachOrdered((_item) -> {
            System.out.println("\n\n");
        });
        lienzos.forEach((lienzo) -> {
            lienzo.imprimir();
        });
    }
    
    public void imp(){
        simbolosLnz.getSimbolos().forEach((simbolo) -> {
            System.out.println("Tipo: " + simbolo.getTipo() + "\t\t" + "ID: "+ simbolo.getId() + "\t\tValor: " + simbolo.getValor());
        });
    }
    
    public Lienzo addLienzo(Object a, Object b, Object c, Object d, int left, int right, String idLista,
            int al, int ar, int bl, int br, int cl, int cr, int dl, int dr){
        if (a != null && b != null && c != null && d != null) {
            if (copiarLista(idLista, left, right)) {
                Lienzo lienzo = new Lienzo(simbolosLnz.buscarPorTipo("Nombre").toString(), simbolosLnz.buscarPorTipo("Tipo").toString(), 
                    (Fondo) simbolosLnz.buscarPorTipo("Fondo"), (Tamaño) simbolosLnz.buscarPorTipo("Tamaño"), idLista);
                simbolosLnz.getSimbolos().clear();
                return lienzo;
            } else {
                simbolosLnz.getSimbolos().clear();
                return null;
            }
        } else {
            if (a == null) {
                errorSintax(al, ar,  "Lienzo", "<< " + "Estructura Lienzo" + ">> " + E_N_E_C);
            }
            if (b == null) {
                errorSintax(bl, br,  "Lienzo", "<< " + "Estructura Lienzo" + ">> " + E_N_E_C);
            }
            if (c == null) {
                errorSintax(cl, cr,  "Lienzo", "<< " + "Estructura Lienzo" + ">> " + E_N_E_C);
            }
            if (d == null) {
                errorSintax(dl, dr,  "Lienzo", "<< " + "Estructura Lienzo" + ">> " + E_N_E_C);
            }
            return null;
        }
    }
 
    @SuppressWarnings("empty-statement")
    public List<Lienzo> addListLienzo(Object a, Object b, int aleft, int aright, int bleft, int bright){
        List<Lienzo> listaLienzos = new ArrayList<>();
        if (a != null) {
            if (a instanceof Lienzo) {
                if (b != null) {
                    listaLienzos.add((Lienzo) a);
                    listaLienzos.add((Lienzo) b);
                    return listaLienzos;
                } else {
                    System.out.println("Error List Lienzo: " + bleft + " " + bright);
                    listaLienzos.add((Lienzo) a);
                    return listaLienzos;
                }
            } else {
                listaLienzos = (List<Lienzo>) a;
                if (b != null) {
                    listaLienzos.add((Lienzo) b);
                } else {
                    System.out.println("Error List Lienzo: " + bleft + " " + bright);
                }
                return listaLienzos;
            }
        } else {
            System.out.println("Error List Lienzo: " + aleft + " " + aright);
            if (b != null) {
                listaLienzos.add((Lienzo) b);
                return listaLienzos;
            } else {
                System.out.println("Error List Lienzo: " + bleft + " " + bright);
                return listaLienzos;
            }
        }
    }
    
    /* ESTRUCTURA COLORES */
    
    public Color comprobarColor(Object id, Fondo color, int cleft, int cright, int aleft, int aright){
        simbolosLnz.removerPorTipo("Red");
        simbolosLnz.removerPorTipo("Blue");
        simbolosLnz.removerPorTipo("Green");
        if (simbolosLnz.buscarPorId(id.toString()) == null) {
            if (color != null) {
                Color colorAdd  = new Color(color, id.toString());
                simbolosLnz.agregarTabla(colorAdd, "Color", id.toString());
                return colorAdd;
            } else {
                errorSintax(cleft, cright,  "Color", "<< " + "Estructura Color RGB" + ">> " + E_N_E_C);
                return null;
            }
        } else {
            errorSemantico(aleft, aright, id.toString(), "Ya existe un color con el identificador: " + id.toString() + ", en la estructura.");
            return null;
        }
        
    }
    
    public List<Color> listaColores(Object a, Color b, int aleft, int aright, int bleft, int bright){
        List<Color> listaColor = new ArrayList<>();
        if (a != null) {
            if (a instanceof Color) {
                if (b != null) {
                    listaColor.add((Color) a);
                    listaColor.add((Color) b);
                    return listaColor;
                } else {
                    System.out.println("Error List Colores: " + bleft + " " + bright);
                    listaColor.add((Color) a);
                    return listaColor;
                }
            } else {
                listaColor = (List<Color>) a;
                if (b != null) {
                    listaColor.add((Color) b);
                } else {
                    System.out.println("Error List Colores: " + bleft + " " + bright);
                }
                return listaColor;
            }
        } else {
            System.out.println("Error List Colores: " + aleft + " " + aright);
            if (b != null) {
                listaColor.add((Color) b);
                return listaColor;
            } else {
                System.out.println("Error List Colores: " + bleft + " " + bright);
                return listaColor;
            }
        }
    }
    
    public Lienzo buscarObjetoLienzo(String id) {
        for (Lienzo lienzo : lienzos) {
            if (lienzo.getId().equals(id)) {
                return lienzo;
            }
        } return null;
    }
    
    public void comprobarAddColores(Object id, Object colores, int ileft, int iright){
        Lienzo lienzo = buscarObjetoLienzo(id.toString());
        TablaSimbolos tablaLienzo = buscarIdLista(id.toString());
        if (colores != null) {
            if (tablaLienzo != null) {
                List<Color> listColores = (List<Color>) tablaLienzo.buscarPorTipo("List<Color>");
                if (listColores == null) {
                    if (colores instanceof Color) {
                        listColores = new ArrayList<>();
                        listColores.add((Color) colores);
                        tablaLienzo.agregarTablaTemp((List<Color>) listColores, "List<Color>");
                        lienzo.setColores(listColores);
                    } else {
                        tablaLienzo.agregarTablaTemp((List<Color>) colores, "List<Color>");
                        lienzo.setColores((List<Color>) colores);
                    }
                } else {
                    if (colores instanceof Color) {
                        listColores.add((Color) colores);
                        lienzo.getColores().add((Color) colores);
                    } else {
                        for (Color listColore : (List<Color>) colores) {
                            listColores.add(listColore);
                            lienzo.getColores().add(listColore);
                        }
                    }
                }
            } else {
                errorSemantico(ileft, iright, id.toString(), "No se encuentra definido un lienzo con el id: " + id.toString() + ", por lo tanto no se puede ralizar la asignación del color");
            }
        } else {
            errorSintax(ileft, iright, "Colores", "<< Estructura Colores >> " + E_N_E_C);
        }
        simbolosLnz.getSimbolos().clear();
    }
    
    /* ESTRUCTURA TIEMPO */
    
    public List<Duracion> comprobarTimeImg(Object e, int left, int right){
        if (simbolosLnz.buscarPorTipo("Imagenes") == null) {
            if (e instanceof Duracion) {
                List<Duracion> lista = new ArrayList<>();
                lista.add((Duracion) e);
                simbolosLnz.removerPorIdTipo("Duracion", ((Duracion) e).getId());
                simbolosLnz.agregarTablaTemp(lista, "Imagenes");
                return lista;
            } else {
                ((List<Duracion>) e).forEach((duracion) -> {
                    simbolosLnz.removerPorIdTipo("Duracion", duracion.getId());
                });
                simbolosLnz.agregarTablaTemp((List<Duracion>) e, "Imagenes");
                return  ((List<Duracion>) e);
            }
        } else {
            errorSintax(left, right, "Imagenes", "<< Estructura Imagenes >> " + M_EDE);
            return null;
        }
    }
    
    public Tiempo comprobarTiempo(Object a, Object b, Object c, int al, int ar, int bl, int br, int cl, int cr){
        Tiempo tiempo = new Tiempo();
        if (a != null && b != null && c != null) {
            tiempo.setInicio(simbolosLnz.buscarPorTipo("Inicio").toString());
            simbolosLnz.removerPorTipo("Inicio");
            tiempo.setFin(simbolosLnz.buscarPorTipo("Fin").toString());
            simbolosLnz.removerPorTipo("Fin");
            tiempo.setImagenes((List<Duracion>) simbolosLnz.buscarPorTipo("Imagenes"));
            simbolosLnz.removerPorTipo("Imagenes");
            return tiempo;
        } else {
            if (a == null) {
                 errorSintax(al, ar, "Tiempo", "<< Estructura Tiempo >> " + E_N_E_C);
            }
            if (b == null) {
                 errorSintax(bl, br, "Tiempo", "<< Estructura Tiempo >> " + E_N_E_C);
            }
            if (c == null) {
                 errorSintax(cl, cr, "Tiempo", "<< Estructura Tiempo >> " + E_N_E_C);
            }
            return null;
        }
    }
    
    public void comprobarDuracion(Object id, Tiempo tiempo, int left, int right){
        TablaSimbolos tablaDuracion = buscarIdLista(id.toString());
        Lienzo lienzo  = buscarObjetoLienzo(id.toString());
        if (tiempo != null) {
            if (tablaDuracion != null) {
                Object time = tablaDuracion.buscarPorTipo("Tiempo");
                if (time == null) {
                    tablaDuracion.agregarTablaTemp(tiempo, "Tiempo");
                    lienzo.setTiempo(tiempo);
                } else {
                    errorSintax(left, right, "Tiempo", "<< Estructura Tiempo >> No se puede asignar. " + E_N_E_C);
                }
            } else {
                errorSemantico(left, right, id.toString(), "No existe un Lienzo con el ID: " + id.toString() + ", por lo tanto no se puede realizar la asignación. ");
            }
        } else {
            errorSintax(left, right, "Tiempo", "<< Estructura Tiempo >> No se puede asignar. " + E_N_E_C);
        }
    }
    
    public Object comprobarExistenciaDuracionImg(Duracion add, int left, int right){
        for (Simbolo simbolo : simbolosLnz.getSimbolos()) {
            if (simbolo.getId() != null && simbolo.getId().equals(add.getId()) && simbolo.getTipo().equals("Duracion")) {
                errorSemantico(left, right, add.getId(), "Lo siento el id : " + add.getId() + ", ya se encuentra definido en la estructura.");
                return null;
            }
        } 
        simbolosLnz.agregarTabla(add, "Duracion", add.getId());
        return add;
    }
    
    public List<Duracion> listaDuraciones(Object a, Object b){
        List<Duracion> temp;
        if (a != null) {
            if (b != null) {
                if (a instanceof Duracion) {
                    temp = new ArrayList<>();
                    temp.add((Duracion) a);
                    temp.add((Duracion) b);
                    return temp;
                } else {
                    ((List<Duracion>) a).add((Duracion) b);
                    return ((List<Duracion>) a); 
                }
            } else {
                if (a instanceof Duracion) {
                    temp = new ArrayList<>();
                    temp.add((Duracion) a);
                    return temp;
                } else {
                    return ((List<Duracion>) a);
                }
            }
        } else {
            if (b != null) {
                temp = new ArrayList<>();
                temp.add((Duracion) b);
                return temp;
            } else {
                return new ArrayList<>();
            }
        }
    }
    
    public List<Duracion> addVacioTimeImg(){
        simbolosLnz.agregarTablaTemp(new ArrayList<>(), "Imagenes");
        return new ArrayList<>();
    }
    
    /* ESTRUCTURA PINTAR */
    /* BLOQUE VARS*/
    
    public Object realizarOperaciones(Object a, Object b, int aleft, int aright, int bleft, int bright, int caso){
        if (a != null) {
            if (b != null) {
                int valorA = (int) a;
                int valorB = (int) b;
                switch (caso) {
                    case 1://Suma
                        return (valorA + valorB);
                    case 2://Resta
                        return (valorA - valorB);
                    case 3://Multiplicacion
                        return (valorA * valorB);
                    case 4://Division
                        return (valorA / valorB);
                }
            } else {
                errorSemantico(bleft, bright, "Variable", "Lo siento no se puede realizar la operación debido a que la variable no posee ningun dato.");
            }
        } else {
            errorSemantico(aleft, aright, "Variable", "Lo siento no se puede realizar la operación debido a que la variable no posee ningun dato.");
        } return null;
    }
    
    private void asignarInt(String id, Object valor, int left, int right){
        Object aux = simbolosLnz.buscarPorId(id);
        if (aux == null) {
            simbolosLnz.agregarTabla(valor, "Int", id);
        } else {
            errorSemantico(left, right, id, "Lo siento no se puede realizar la asignación, debido a que ya existe una variable definida con el mismo id.");
        }
    }
    
    public void asignarValorIdInt(String id, Object valor, int left, int right, boolean sinValor){
        if (valor != null) {
            asignarInt(id, (int) valor, left, right);
        } else {
            if (sinValor) {
                asignarInt(id, null, left, right);
            } else {
                errorSemantico(left, right, "Variable -> ID: " + id, "No se realizo la asignación debido a que el valor que se desea asignar no corresponde al tipo de variable << Int >>");
            }
        }
    }
    
    private void asignarString(String id, Object valor, int left, int right){
        Object aux = simbolosLnz.buscarPorId(id);
        if (aux == null) {
            simbolosLnz.agregarTabla(valor, "String", id);
        } else {
            errorSemantico(left, right, id, "Lo siento no se puede realizar la asignacion debido a que la variable ya se encuentra definida.");
        }
    }
    
    public void asignarValorIdString(String id, Object valor, int left, int right, boolean sinValor){
        if (valor != null) {
            asignarString(id, valor.toString().replaceAll("\"", ""), left, right);
        } else {
            if (sinValor) {
                asignarString(id, null, left, right);
            } else {
                errorSemantico(left, right, "Variable -> ID: " + id, "No se realizo la asignación debido a que el valor que se desea asignar no corresponde al tipo de variable << String >>");
            }
        }
    }
    
    public Object buscarIdEnTabla(String id, int left, int right){
        for (Simbolo simbolo : simbolosLnz.getSimbolos()) {
            if (simbolo.getId() != null && simbolo.getId().equals(id)) {
                return simbolo.getValor();
            }
        } 
        errorSemantico(left, right, "Variable -> ID: " + id, "La variable no se encuentra definida.");
        return null;
    }
    
    public Object comrpobarInt(Object e, int left, int right){
        try {
            if (e != null) {
                return (int) e;
            }
        } catch (Exception ex) {
        }
        errorSemantico(left, right, "Variable: " + e, "El dato no es de tipo << Int >>.");
        return null;
    }
    
    public Object comprobarString(Object e, int left, int right){
        if (e != null && e instanceof String) {
            return e.toString();
        } 
        errorSemantico(left, right, "Variable: " + e, "El dato no es de tipo << String >>.");
        return null;
    }
    
    public Object concatenarCadena(Object a, Object b, int aleft, int aright, int bleft, int bright){
        if (a != null) {
            if (b != null) {
                return (a.toString() + b.toString());
            } else {
                errorSemantico(bleft, bright, "Variable", "Lo siento no se puede realizar la operación debido a que la variable no posee ningun dato.");
            }
        } else {
             errorSemantico(aleft, aright, "Variable", "Lo siento no se puede realizar la operación debido a que la variable no posee ningun dato.");
        } return null;
    }
    
    public Object realizarOperacionesLogicas(Object a, Object b, int aleft, int aright, int bleft, int bright, int caso) {
        try {
            if (a == null) {
                errorSemantico(aleft, aright, "Variable", "Lo siento no se puede realizar la operación debido a que la variable no posee ningun dato.");
                return null;
            }
            if (b == null) {
                errorSemantico(bleft, bright, "Variable", "Lo siento no se puede realizar la operación debido a que la variable no posee ningun dato.");
                return null;
            }
            boolean aux = (boolean) a;
            if (aux || !aux) {
                errorSemantico(aleft, aright, Boolean.toString(aux), "Lo siento no se puede realizar la operación una variable de tipo << Booolean >>.");
                return null;
            }
            aux = (boolean) b;
            errorSemantico(bleft, bright, Boolean.toString(aux), "Lo siento no se puede realizar la operación una variable de tipo << Booolean >>.");
            return null;
        } catch (Exception e) {
            Object valorA = comrpobarInt(a, aleft, aright);
            Object valorB = comrpobarInt(b, bleft, bright);
            if (valorA != null && valorB != null) {
                switch (caso) {
                    case 1:
                        return (((int) a) < ((int) b));
                    case 2:
                        return (((int) a) > ((int) b));
                    case 3:
                        return (((int) a) == ((int) b));
                    case 4:
                        return (((int) a) <= ((int) b));
                    case 5:
                        return (((int) a) >= ((int) b));
                    case 6:
                        return (((int) a) != ((int) b));
                }
            } else {
                if (valorA == null) {
                    errorSemantico(aleft, aright, "Variable: " + a, "Lo siento no se puede realizar la operacion logica debido a que la variable no es un << Int >>");
                } else {
                    errorSemantico(bleft, bright, "Variable: " + b, "Lo siento no se puede realizar la operacion logica debido a que la variable no es un << Int >>");
                }
            }
        } return null;
    }
    
    public Object realizarOperacionesAndOr(Object a, Object b, int aleft, int aright, int bleft, int bright, int caso) {
        try {
            if (a == null) {
                errorSemantico(aleft, aright, "Variable", "Lo siento no se puede realizar la operación debido a que la variable no posee ningun dato.");
                return null;
            }
            if (b == null) {
                errorSemantico(bleft, bright, "Variable", "Lo siento no se puede realizar la operación debido a que la variable no posee ningun dato.");
                return null;
            }
            boolean valorA = (boolean) a;
            try {
                boolean valorB = (boolean) b;
                switch (caso) {
                    case 1: //AND
                        return valorA && valorB;
                    case 2: //OR
                        return valorA || valorB;
                }
            } catch (Exception e) {
                errorSemantico(bleft, bright, "Variable: " + b, "Lo siento no se puede realizar la operacion logica debido a que la variable no es un << Boolean >>");
            }
            return null;
        } catch (Exception e) {
            errorSemantico(aleft, aright, "Variable: " + a, "Lo siento no se puede realizar la operacion logica debido a que la variable no es un << Boolean >>");
        } return null;
    }
    
    private void asignarBoolean(String id, Object valor, int left, int right){
        Object aux = simbolosLnz.buscarPorId(id);
        if (aux == null) {
            simbolosLnz.agregarTabla(valor, "Boolean", id);
        } else {
            errorSemantico(left, right, "Variable: " + valor, "Lo siento no se puede realizar la asignación debido a que ya se encuentra definida una variable con el ID: " + id);
        }
    }
    
    public void asignarValorIdBoolean(String id, Object valor, int left, int right, boolean sinValor){
        if (valor != null) {
            try {
                asignarBoolean(id, (boolean) valor, left, right);
            } catch (Exception e) {
                errorSemantico(left, right, "Variable: " + valor, "El dato no es de tipo << Boolean >>");
            }
            
        } else {
            if (sinValor) {
                asignarBoolean(id, null, left, right);
            } else {
                errorSemantico(left, right, "Variable - ID: " + id, "El dato que se desea asignar es null.");
            }
        }
    }
    
    public Object asignarVal(String id, Object valor, int left, int right, boolean soloRetornar){
        Object aux = simbolosLnz.buscarPorId(id);
        if (aux != null && !soloRetornar) {
            switch (comprobarTipoVar(valor)) {
                case 1:   
                    if (((Simbolo) aux).getTipo().equalsIgnoreCase("String")) {
                        ((Simbolo) aux).setValor(valor);
                    } else {
                        errorSemantico(left, right, "Variable: " + valor, "El dato no es de tipo << String >>");
                    }
                    break;
                case 2:   
                    if (((Simbolo) aux).getTipo().equalsIgnoreCase("Int")) {
                        ((Simbolo) aux).setValor(valor);
                    } else {
                        errorSemantico(left, right, "Variable: " + valor, "El dato no es de tipo << Int >>");
                    }
                    break;
                case 3:   
                    if (((Simbolo) aux).getTipo().equalsIgnoreCase("Boolean")) {
                        ((Simbolo) aux).setValor(valor);
                    } else {
                        errorSemantico(left, right, "Variable: " + valor, "El dato no es de tipo << Boolean >>");
                    }
                    break;
                default:
                    errorSemantico(left, right, "Variable - ID: " + id, "No se a realizado la asignación debido a que el dato que se desea asignar es null");
            }
        } else if(aux == null){
            errorSemantico(left, right, "Variable - ID: " + id, "No se puede realizar la asignación debido a que no se a definido una varible con el ID: " + id);
        }
        return valor;
    }
    
    private int comprobarTipoVar(Object valor){
        if (valor instanceof String) {
            return 1;
        } else {
            try {
                int a = (int) valor;
                return 2;
            } catch (Exception e) {
                try {
                    boolean a = (boolean) valor;
                    return 3;
                } catch (Exception ex) {
                    return -1;
                }
            }
        }
    }
    
    /*BLOQUE INSTRUCCIONES */
    
    public void cargarTablaCorrespondiente(String id, int left, int right) {
        simbolosPintarEnTurno = null;
        mp.buscarLienzo("", lienzos);
        mp.setActual(null);
        listaSimbolos.stream().filter((tabla) -> (tabla.getId().equals(id))).forEachOrdered((tabla) -> {
            simbolosPintarEnTurno = tabla;
            System.out.println("Tabla cargada");
            mp.buscarLienzo(id, lienzos);
            mp.setActual(tabla);
        });
        if (simbolosPintarEnTurno == null) {
            errorSemantico(left, right, "Lienzo - ID: " + id, "No se encuentra definido un lienzo con el ID: " + id);
        }
    }
    
    public Object buscarSimbolo(String id, String tipo, int left, int right, boolean soloTabla, boolean idSearch, boolean mostrarError){
        if (soloTabla) {
            return search(simbolosLnz, id, tipo, left, right, soloTabla, idSearch, mostrarError);
        } else {
            Object aux = search(simbolosLnz, id, tipo, left, right, soloTabla, idSearch, mostrarError);
            if (aux != null) {
                return aux;
            } else {
                return search(simbolosPintarEnTurno, id, tipo, left, right, soloTabla, idSearch, mostrarError);
            }
        }
    }
    
    public Object search(TablaSimbolos simbolos, String id, String tipo, int left, int right, boolean soloTabla, boolean idSearch, boolean mostrarError) {
        if (simbolos != null) {
            for (Simbolo simbolo : simbolos.getSimbolos()) {
                if (idSearch) {
                    if (simbolo.getId() != null && simbolo.getId().equals(id)) {
                        System.out.println("Valor: " + simbolo.getValor());
                        return simbolo.getValor();
                    }
                } else {
                    if (simbolo.getTipo()!= null && simbolo.getTipo().equals(tipo)) {
                        return simbolo;
                    }
                }
            }
            if (mostrarError) {
                errorSemantico(left, right, "Variable - ID: " + id, "El ID: " + id + ", no se encuentra definido");
            }
            return null;
        } else {
            errorSemantico(left, right, "Lienzo - ID: " + id, "No se encuentra definido un lienzo con el ID: " + id);
            return null;
        }
    }
    
    public Object realizarOperacionesInstrIP(Object a, Object b, int aleft, int aright, int bleft, int bright, int caso, boolean uniIdPint){
        switch (caso) {
            case 1://Suma
                if (uniIdPint) {
                    if (mp.comprobarSiSonMismoTipo(a, b, aleft, aright, bleft, bright, 1)) {
                        return (a.toString().replaceAll("\"", "") + b.toString().replaceAll("\"", ""));
                    }
                } else {
                    if (mp.comprobarSiSonMismoTipo(a, b, aleft, aright, bleft, bright, 2)) {
                        return (((int) a) + ((int) b));
                    }
                    if (mp.comprobarSiSonMismoTipo(a, b, aleft, aright, bleft, bright, 1)) {
                        return (a.toString().replaceAll("\"", "") + b.toString().replaceAll("\"", ""));
                    }
                }
                break;
            case 2://Resta
                if (mp.comprobarSiSonMismoTipo(a, b, aleft, aright, bleft, bright, 2)) {
                    return (((int) a) - ((int) b));
                }
                break;
            case 3://Multiplicacion
                if (mp.comprobarSiSonMismoTipo(a, b, aleft, aright, bleft, bright, 2)) {
                    return (((int) a) * ((int) b));
                }
                break;
            case 4://Division
                if (mp.comprobarSiSonMismoTipo(a, b, aleft, aright, bleft, bright, 2)) {
                    return (((int) a) / ((int) b));
                }
                break;
            case 5://Rango 
                if (mp.comprobarSiSonMismoTipo(a, b, aleft, aright, bleft, bright, 2)) {
                    if (((int) a) < ((int) b)) {
                        return new Dimension((int) a, (int) b);
                    } else {
                        return new Dimension((int) b, (int) a);
                    }
                }
                break;
        } 
        errorSemantico(aleft, aright, "Operaciones", "No se a realizado la operacion debido a que existen errores semanticos.");
        return null;
    }
    
    public Object realizarOperacionesBoolean(Object a, Object b, int aleft, int aright, int bleft, int bright, int caso){
        switch (caso) {
            case 1:
                if (mp.comprobarSiSonMismoTipo(a, b, aleft, aright, bleft, bright, 2)) {
                    return (((int) a) < ((int) b));
                }
                break;
            case 2:
                if (mp.comprobarSiSonMismoTipo(a, b, aleft, aright, bleft, bright, 2)) {
                    return (((int) a) > ((int) b));
                }
                break;
            case 3:
                if (mp.comprobarSiSonMismoTipo(a, b, aleft, aright, bleft, bright, 2)) {
                    return (((int) a) == ((int) b));
                }
                break;
            case 4:
                if (mp.comprobarSiSonMismoTipo(a, b, aleft, aright, bleft, bright, 2)) {
                    return (((int) a) <= ((int) b));
                }
                break;
            case 5: 
                if (mp.comprobarSiSonMismoTipo(a, b, aleft, aright, bleft, bright, 2)) {
                    return (((int) a) >= ((int) b));
                }
                break;
            case 6: 
                if (mp.comprobarSiSonMismoTipo(a, b, aleft, aright, bleft, bright, 2)) {
                    return (((int) a) != ((int) b));
                }
                break;
        } 
        errorSemantico(aleft, aright, "Operaciones", "No se a realizado la operación lógica debido a que existen errores semanticos.");
        return null;
    }
    
    public Object realizarOperacionesAndOrInst(Object a, Object b, int aleft, int aright, int bleft, int bright, int caso){
        switch (caso) {
            case 1:
                if (mp.comprobarSiSonMismoTipo(a, b, aleft, aright, bleft, bright, 3)) {
                    return (((boolean) a) && ((boolean) b));
                }
                break;
            case 2:
                if (mp.comprobarSiSonMismoTipo(a, b, aleft, aright, bleft, bright, 3)) {
                    return (((boolean) a) || ((boolean) b));
                }
                break;
        } 
        errorSemantico(aleft, aright, "Operaciones", "No se a realizado la operación lógica debido a que existen errores semanticos.");
        return null;
    }
    
    public Object addValorIfWhile(Object add, int left, int right){
        if (add != null) {
            if (add instanceof Simbolo) {
                whilesEIfs.peek().getSimbolos().add((Simbolo) add);
            } else {
                whilesEIfs.peek().agregarTablaTemp(add, mp.comprobarTipoObjeto(add));
            }
        } else {
            errorSemantico(left, right, "Asignaciones", "No se realizo la instrucción debido a que el dato no posee valor.");
        } return add;
    }
    
    public Object addValorElse(Object add, int left, int right){
        if (add != null) {
            whilesEIfs.peek().getSimbolosElse().add(new Simbolo(mp.comprobarTipoObjeto(add), add));
        } else {
            errorSemantico(left, right, "Asignaciones", "No se realizo la instrucción debido a que el dato no posee valor.");
        } return add;
    }
    
    public void agregarNuevaEstructura(boolean ifs, Object booleanoCodicional){
        TablaSimbolos ts;
        if (ifs) {
            ts = new TablaSimbolos(new ArrayList<>(), "if", (boolean) booleanoCodicional);
            ts.setSimbolosElse(new ArrayList<>());
        } else {
            ts = new TablaSimbolos(new ArrayList<>(), "while", (String)  booleanoCodicional);
        }
        whilesEIfs.push(ts);
    }
    
    public Object removerEstructura(){
        if (!whilesEIfs.empty()) {
            return whilesEIfs.pop();
        } return null;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanimaciones.backed.manejadores;

import java.io.StringReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import proyectoanimaciones.backed.analizador.pintar.*;
import proyectoanimaciones.backed.objetos.Dimension;
import proyectoanimaciones.backed.objetos.Pintar;
import proyectoanimaciones.backed.objetos.Simbolo;
import proyectoanimaciones.backed.objetos.TablaSimbolos;
import proyectoanimaciones.gui.Principal;

/**
 *
 * @author bryan
 */
public class ManejadorIfWhile {
    private static ManejadorIfWhile ifWhile;
    private Principal principal;
    private final ManejadorPintar mp = ManejadorPintar.getManejadorPintar();
    private boolean condicional;
    private Object valor;
    
    private ManejadorIfWhile() {}

    public static ManejadorIfWhile getInstancia() {
        if (ifWhile == null) {
            ifWhile = new ManejadorIfWhile();
        }
        return ifWhile;
    }

    public void setPrincipal (Principal principal){
        this.principal = principal;
    }
    
    public void setValor(Object valor) {
        this.valor = valor;
    }
    
    public void setCondicional(boolean condicional) {
        this.condicional = condicional;
    }
    
    public void realizarIfWhile(TablaSimbolos simbolos){
        if (simbolos.getBoleanoIf() != null || simbolos.getCondicionalWhile() != null) {
            if (simbolos.getId().equalsIgnoreCase("if")) {
                if (((boolean) simbolos.getBoleanoIf())) {
                    realizarIfWhile(simbolos.getSimbolos());
                } else {
                    realizarIfWhile(simbolos.getSimbolosElse());
                }
            } else {
                LexicoPintar lp = new LexicoPintar(new StringReader(simbolos.getCondicionalWhile().toString()));
                lp.setPrincipal(principal);
                SintaxPintar sp = new SintaxPintar(lp);
                sp.setPrincipal(principal);
                try {
                    sp.parse();
                    while (condicional) {
                        realizarIfWhile(simbolos.getSimbolos());
                        lp = null;
                        sp = null;
                        lp = new LexicoPintar(new StringReader(simbolos.getCondicionalWhile().toString()));
                        lp.setPrincipal(principal);
                        sp = new SintaxPintar(lp);
                        sp.setPrincipal(principal);
                        sp.parse();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            System.out.println("Nada");
        }
    }
    
    private void realizarIfWhile(List<Simbolo> simbolos) {
        simbolos.forEach((simbolo) -> {
            LexicoPintar lp;
            SintaxPintar sp;
            switch (comprobarTipo(simbolo)) {
                case 1: case 2: case 3:
                    lp = new LexicoPintar(new StringReader(simbolo.getValor().toString()));
                    lp.setPrincipal(principal);
                    sp = new SintaxPintar(lp);
                    sp.setPrincipal(principal);
                    try {
                        sp.parse();
                        lp = null;
                        sp = null;
                    } catch (Exception ex) {
                        Logger.getLogger(ManejadorIfWhile.class.getName()).log(Level.SEVERE, null, ex);
                    }   break;
                case 6:
                    try {
                        boolean add = true;
                        Pintar aux = (Pintar) simbolo.getValor();
                        if (aux.getDimesionX().isScadena()) {
                            lp = new LexicoPintar(new StringReader("PINTAR " + aux.getDimesionX().getdString()));
                            lp.setPrincipal(principal);
                            sp = new SintaxPintar(lp);
                            sp.setPrincipal(principal);
                            sp.parse();
                            lp = null;
                            sp = null;
                            if (valor != null) {
                                if (!(valor instanceof Dimension)) {
                                    ((Pintar) simbolo.getValor()).getDimesionX().setDimension((int) valor);
                                } else {
                                    ((Pintar) simbolo.getValor()).setDimesionX(((Dimension) valor).clone());
                                }
                            } else {
                                add = false;
                            }
                        }
                        if (aux.getDimesionY().isScadena()) {
                            lp = new LexicoPintar(new StringReader("PINTAR " + aux.getDimesionY().getdString()));
                            lp.setPrincipal(principal);
                            sp = new SintaxPintar(lp);
                            sp.setPrincipal(principal);
                            sp.parse();
                            lp = null;
                            sp = null;
                            if (valor != null) {
                                if (!(valor instanceof Dimension)) {
                                    ((Pintar) simbolo.getValor()).getDimesionY().setDimension((int) valor);
                                } else {
                                    ((Pintar) simbolo.getValor()).setDimesionY(((Dimension) valor).clone());
                                }
                            } else {
                                add = false;
                            }
                        }
                        if(add){
                            mp.asignarPintar(new Pintar(aux.getIdColor().clone(), aux.getIdImg().clone(), aux.getDimesionX().clone(), aux.getDimesionY().clone()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 4: case 5:
                    realizarIfWhile((TablaSimbolos) simbolo.getValor());
                    break;
                default:
                    System.out.println("Valio: " + simbolo.getTipo() + " valor: " + simbolo.getValor());
                    break;
            }
        });
    }
    
    private int comprobarTipo(Simbolo simbolo){
        if (simbolo != null) {
            switch (simbolo.getTipo()) {
                case "Int":         return 1;
                case "String":      return 2;
                case "Boolean":     return 3;
                case "if":          return 4;
                case "while":       return 5;
                case "Pintar":      return 6;
            }
        } return -1;
    }
}

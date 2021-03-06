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
public class TablaSimbolos {
    private List<Simbolo> simbolos;
    private List<Simbolo> simbolosElse;
    private String id;
    private Object boleanoIf;
    private Object condicionalWhile;
    
    public TablaSimbolos() {}

    public TablaSimbolos(List<Simbolo> simbolos, String id) {
        this.simbolos = simbolos;
        this.id = id;
    }

    /* Unicamente para la condicional if */
    public TablaSimbolos(List<Simbolo> simbolos, String id, Object boleanoIf) {
        this.simbolos = simbolos;
        this.id = id;
        this.boleanoIf = boleanoIf;
    }

    public TablaSimbolos(List<Simbolo> simbolos, String id, String condicionalWhile) {
        this.simbolos = simbolos;
        this.id = id;
        this.condicionalWhile = condicionalWhile;
    }

    public Object getBoleanoIf() {
        return boleanoIf;
    }

    public Object getCondicionalWhile() {
        return condicionalWhile;
    }
    
    public List<Simbolo> getSimbolos() {
        return simbolos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSimbolos(List<Simbolo> simbolos) {
        this.simbolos = simbolos;
    }
    
    public void removerPorTipo(String removerTipo) {
        for (Simbolo simbolo : simbolos) {
            if (simbolo.getTipo().equalsIgnoreCase(removerTipo)) {
                simbolos.remove(simbolo);
                break;
            }
        }
    }
    
    public void removerPorIdTipo(String tipo, String id){
        for (Simbolo simbolo : simbolos) {
            if (simbolo.getId() != null && simbolo.getTipo().equals(tipo) && simbolo.getId().equals(id)) {
                simbolos.remove(simbolo);
                break;
            }
        }
    }
    
    public void removerPorId(String removerId) {
        simbolos.stream().filter((simbolo) -> (simbolo.getTipo().equalsIgnoreCase(removerId))).forEachOrdered((simbolo) -> {
            simbolos.remove(simbolo);
        });
    }
    
    public Object buscarPorTipo(String tipo) {
        for (Simbolo simbolo : simbolos) {
            if (simbolo.getTipo().equalsIgnoreCase(tipo)) {
                return simbolo.getValor();
            }
        } return null;
    }
    
    public Object buscarPorId(String id) { 
        for (Simbolo simbolo : simbolos) {
            if (simbolo.getId() != null && simbolo.getId().equalsIgnoreCase(id)) {
                return simbolo;
            }
        } return null;
    }
    
    public void agregarTablaTemp(Object object, String tipo){
        simbolos.add(new Simbolo(tipo, object));
    }
    
    public void agregarTabla(Object object, String tipo, String id){
        simbolos.add(new Simbolo(tipo, object, id));
    }

    public List<Simbolo> getSimbolosElse() {
        return simbolosElse;
    }

    public void setSimbolosElse(List<Simbolo> simbolosElse) {
        this.simbolosElse = simbolosElse;
    }
    
    @Override
    public String toString(){
        String salida = "\n\nID: " + this.id;
        salida += "\n\nEstructura: \n";
        salida = simbolos.stream().map((simbolo) -> "\nID: " + simbolo.getId() + " \tTipo: " + simbolo.getTipo() + " \tValor: " + simbolo.getValor()).reduce(salida, String::concat);        
        if (simbolosElse != null) {
            salida += "\nElse\n";
            salida += simbolosElse.stream().map((simbolo) -> "\nID: " + simbolo.getId() + " \tTipo: " + simbolo.getTipo() + " \tValor: " + simbolo.getValor()).reduce(salida, String::concat);
        }
        return salida;
    }
}

package proyectoanimaciones.backed.analizador;

import java_cup.runtime.Symbol;
import proyectoanimaciones.gui.*;

%%
%class Lexic
%type java_cup.runtime.Symbol
%cup
%public
%cupdebug
%full
%line
%column
%char

L=[a-zA-Z]
H=[a-fA-F0-9]
D=[0-9]
RGB=[0-5]
S=[|°@ł€¶ŧ←↓→øþ·ĸŋđðßæ«»¢“”½¬!$%&/()=?¡+*~_{}'¨-]
espacio=[\t\r]
salto=[\n]

%{
    private Principal principal;

    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline + 1, yycolumn + 1, value);
    }

    private Symbol symbol(int type){
        return new Symbol(type, yyline + 1, yycolumn + 1);
    }

    public void setPrincipal(Principal principal){
        this.principal = principal;
    }

%}
%%

<YYINITIAL> {

    /* Espacios*/
    
    {salto}+        {/*Ignore*/}
    {espacio}+      {/*Ignore*/}
    ("â")*        {/*Ignore*/}
    (" ")*          {/*Ignore*/}

    /* Tokens */
    
    /* Estructuras */
    
    ( LIENZOS ) {return new Symbol(sym.Lienzos, yychar, yyline, yytext());}
    ( COLORES ) {return new Symbol(sym.Colores, yychar, yyline, yytext());}
    ( TIEMPOS ) {return new Symbol(sym.Tiempos, yychar, yyline, yytext());}

    /* Lienzos - Colores */
    
    ( nombre )      {return new Symbol(sym.Nombre, yychar, yyline, yytext());}
    ( tipo )        {return new Symbol(sym.Tipo, yychar, yyline, yytext());}
    ( Fondo )       {return new Symbol(sym.Fondo, yychar, yyline, yytext());}
    ( Red )         {return new Symbol(sym.Red, yychar, yyline, yytext());}
    ( Blue )        {return new Symbol(sym.Blue, yychar, yyline, yytext());}
    ( Green )       {return new Symbol(sym.Green, yychar, yyline, yytext());}
    ( tamaÃ±o )     {return new Symbol(sym.TamañoR, yychar, yyline, yytext());}
    ( tamaño )      {return new Symbol(sym.Tamaño, yychar, yyline, yytext());}
    ( cuadro )      {return new Symbol(sym.Cuadro, yychar, yyline, yytext());}
    ( dimension_x ) {return new Symbol(sym.DimensionX, yychar, yyline, yytext());}
    ( dimension_y ) {return new Symbol(sym.DimensionY, yychar, yyline, yytext());}
    ( HEX )         {return new Symbol(sym.Hex, yychar, yyline, yytext());}

    /* Tiempos */
    
    ( inicio )      {return new Symbol(sym.Inicio, yychar, yyline, yytext());}
    ( fin )         {return new Symbol(sym.Fin, yychar, yyline, yytext());}
    ( imagenes )    {return new Symbol(sym.Imagenes, yychar, yyline, yytext());}
    ( id )          {return new Symbol(sym.Id, yychar, yyline, yytext());}
    ( duracion )    {return new Symbol(sym.Duracion, yychar, yyline, yytext());}

    /* Otros */

    ( "\"" )    {System.out.println("Comillas: " + yytext() + " L " + yyline +1 + " C " + yycolumn +1 );return new Symbol(sym.Comillas, yychar, yyline, yytext());}
    ( ":" )     {return new Symbol(sym.DosPuntos, yychar, yyline, yytext());}
    ( "," )     {return new Symbol(sym.Coma, yychar, yyline, yytext());}
    ( "{" )     {return new Symbol(sym.CorcheteA, yychar, yyline, yytext());}
    ( "}" )     {return new Symbol(sym.CorcheteC, yychar, yyline, yytext());}
    ( "[" )     {return new Symbol(sym.LlaveA, yychar, yyline, yytext());}
    ( "]" )     {return new Symbol(sym.LlaveC, yychar, yyline, yytext());}

    /* Expreciones */
    
    /* Identificador Lienzo - Color*/
        
    ({L}|"_")({L}|"_"|{D})*                                 {System.out.println("ID: " + yytext() + " L " + yyline +1 + " C " + yycolumn +1 );return new Symbol(sym.Identificador, yychar, yyline, new String(yytext()));}

    /* Primer Parte RGB (Números de 0 a 199)*/

    ("1")?(({D}){1, 2})                                     {return new Symbol(sym.NumeroRGB_1, yychar, yyline, new String(yytext()));} 
    
    /* Segunda Parte RGB (Números de 200 a 255)*/

    ("2")(({RGB}){2})                                       {return new Symbol(sym.NumeroRGB_2, yychar, yyline, new String(yytext()));} 

    /* Numero */

    {D}+                                                    {return new Symbol(sym.Numero, yychar, yyline, new String(yytext()));}
 
    /* Extensión */

    ("\"")("png"|"gif")("\"")                               {return new Symbol(sym.Extension, yychar, yyline, new String(yytext()));}

    /* Nombre del Archivo */

    ("\"")({L}|{S}|{D})({L}|{S}|{D}|{espacio}|" ")+("\"")   {System.out.println("NA: " + yytext() + " L " + yyline +1 + " C " + yycolumn +1 );return new Symbol(sym.NombreLienzo, yychar, yyline, new String(yytext()));}
    
    /* Configuracion Hexadecimal */

    ("#")({H}){6}                                           {return new Symbol(sym.Hexadecimal, yychar, yyline, new String(yytext()));}

    /* Error */

    . {principal.addErrores(
                      "\nError Lexico: "
              + "\n\tLinea #:                     << " + (yyline + 1) + " >> "
              + "\n\tColumna #:                   << " + (yycolumn + 1) + " >>"
              + "\n\tToken NO Reconocido:         << " + yytext() + " >> ");
    } 
}


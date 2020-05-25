package proyectoanimaciones.backed.analizador.pintar;

import java_cup.runtime.Symbol;
import proyectoanimaciones.gui.*;

%%
%class LexicoPintar
%type java_cup.runtime.Symbol
%cup
%public
%cupdebug
%line
%column
%char

LineTerminator = \r|\n|\r\n
InputCaracter = [^\r\n]
WhiteSpace = {LineTerminator} | [ \t\f]

/* Comentarios */
Comment = {ComentarioTradicional} | {FinLineaComment} | {DocumentoComentado}
ComentarioTradicional = "/*" [^*] ~"*/" | "/*" "*"+ "/"
FinLineaComment = "//" {InputCaracter}* {LineTerminator}?
DocumentoComentado = "/**" {CommentContenido} "*"+ "/"
CommentContenido = ( [^*] | \*+ [^/*] )*

L = [a-zA-Z]
D = [0-9]
S = [|°@ł€¶ŧ←↓→øþ·ĸŋđðßæ«»¢“”½¬!$%&/()=?¡+*~_{}'¨-]

%{
    
    private Principal principal;

    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline + 1, yycolumn + 1, value);
    }

    private Symbol symbol(int type){
        return new Symbol(type, yyline + 1, yycolumn + 1);
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

%}
%%

<YYINITIAL> {

    /* Espacios*/
    
    {WhiteSpace}      {/*Ignore*/}
    ("â")*          {/*Ignore*/}
    (" ")*            {/*Ignore*/}
    {Comment}         {/*Ignore*/}

    /* Tokens */
    
    /* Estructuras */
    
    ( VARS )            {return new Symbol(sym.Vars, yychar, yyline, yytext());}
    ( INSTRUCCIONES )   {return new Symbol(sym.Instrucciones, yychar, yyline, yytext());}
    ( PINTAR )          {return new Symbol(sym.Pintar, yychar, yyline, yytext());}
    
    /* Variables */
    
    ( int )         {return new Symbol(sym.Int, yychar, yyline, yytext());}
    ( String )      {return new Symbol(sym.String, yychar, yyline, yytext());}
    ( boolean )     {return new Symbol(sym.Boolean, yychar, yyline, yytext());}
    
    /* Otros */

    ( if )      {return new Symbol(sym.If, yychar, yyline, yytext());}
    ( else )    {return new Symbol(sym.Else, yychar, yyline, yytext());}
    ( while )   {return new Symbol(sym.While, yychar, yyline, yytext());}
    ( true )    {return new Symbol(sym.True, yychar, yyline, yytext());}
    ( false )   {return new Symbol(sym.False, yychar, yyline, yytext());}
    ( AND )     {return new Symbol(sym.And, yychar, yyline, yytext());}
    ( OR )      {return new Symbol(sym.Or, yychar, yyline, yytext());}
    ( "<" )     {return new Symbol(sym.Menor, yychar, yyline, yytext());}
    ( ">" )     {return new Symbol(sym.Mayor, yychar, yyline, yytext());}
    ( "=" )     {return new Symbol(sym.Igual, yychar, yyline, yytext());}
    ( "==" )    {return new Symbol(sym.IgualIgual, yychar, yyline, yytext());}
    ( "<=" )    {return new Symbol(sym.MenorIgual, yychar, yyline, yytext());}
    ( ">=" )    {return new Symbol(sym.MayorIgual, yychar, yyline, yytext());}
    ( "<>" )    {return new Symbol(sym.MenorMayor, yychar, yyline, yytext());}
    ( "+" )     {return new Symbol(sym.Mas, yychar, yyline, yytext());}
    ( "-" )     {return new Symbol(sym.Menos, yychar, yyline, yytext());}
    ( "*" )     {return new Symbol(sym.Por, yychar, yyline, yytext());}
    ( "/" )     {return new Symbol(sym.Div, yychar, yyline, yytext());}
    ( ".." )    {return new Symbol(sym.Punto, yychar, yyline, yytext());}

    ( "," )     {return new Symbol(sym.Coma, yychar, yyline, yytext());}
    ( ";" )     {return new Symbol(sym.PuntoComa, yychar, yyline, yytext());}
    ( "{" )     {return new Symbol(sym.CorcheteA, yychar, yyline, yytext());}
    ( "}" )     {return new Symbol(sym.CorcheteC, yychar, yyline, yytext());}
    ( "[" )     {return new Symbol(sym.LlaveA, yychar, yyline, yytext());}
    ( "]" )     {return new Symbol(sym.LlaveC, yychar, yyline, yytext());}
    ( "(" )     {return new Symbol(sym.ParentesisA, yychar, yyline, yytext());}
    ( ")" )     {return new Symbol(sym.ParentesisC, yychar, yyline, yytext());}

    /* Expreciones */
    
    /* Identificador */
        
    ({L}|"_")({L}|"_"|{D})*                     {return new Symbol(sym.Identificador, yychar, yyline, new String(yytext()));}

    /* Numero */

    {D}+                                        {return new Symbol(sym.Numero, yychar, yyline, new String(yytext()));}
    
    /* Cadena */

    ("\"")({L}|{S}|{D}|{WhiteSpace})*("\"")     {return new Symbol(sym.Cadena, yychar, yyline, new String(yytext()));}
    

    /* Error */

    . {principal.addErrores(
                      "\nError Lexico: "
              + "\n\tLinea #:                     << " + (yyline + 1) + " >> "
              + "\n\tColumna #:                   << " + (yycolumn + 1) + " >> "
              + "\n\tToken NO Reconocido:         << " + yytext() + " >> ");
    } 
}

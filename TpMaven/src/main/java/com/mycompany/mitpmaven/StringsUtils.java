/*
*/
package com.mycompany.mitpmaven;

/**
* @author GRUPO 7
*/

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringsUtils {
    // esto se hace para evitar la instanciación de la clase
    private StringsUtils() {}

    // Metodo para eliminar las comillas dobles de un string
    public static String eliminarComillasDobles(String str) {
        // Expresión regular para la comilla doble
        Pattern pattern = Pattern.compile("\"");
        
        Matcher matcher = pattern.matcher(str);
        
        // Reemplaza todas las coincidencias que encuentra con una cadena vacía
        return matcher.replaceAll("");
    }

    // Metodo para eliminar las comillas simples de un string
    public static String eliminarComillasSimples(String str) {
        // Expresión regular para la comilla simple
        Pattern pattern = Pattern.compile("\'");
        
        Matcher matcher = pattern.matcher(str);
        
       // Reemplaza todas las coincidencias que encuentra con una cadena vacía
        return matcher.replaceAll("");
    }
    
    // Metodo para ajustar a un ancho fijo
    private static String ajustarAncho(String str, String pattern, int longitud) {
        // Expresión regular para 
//        Pattern pattern = Pattern.compile("\'");
        
//        Matcher matcher = pattern.matcher(str);
        
       // Reemplaza todas las coincidencias que encuentra con una cadena vacía
        return str.replaceAll(pattern, String.format("%-" + longitud + "s", "$1"));
    }
}

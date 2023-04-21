/*
*/
package com.mycompany.mitpmaven;

/**
* @author GRUPO 7
*/

import org.beryx.textio.TextIO;
import org.beryx.textio.system.SystemTextTerminal;

public class Menu {
    public int runMenu() {
        SystemTextTerminal systerm = new SystemTextTerminal();
        TextIO textIO = new TextIO (systerm);
        Integer opcionSeleccionada = 0;

        System.out.println();
        System.out.println("      Seleccione una opción     ");
        System.out.println("--------------------------------");
        System.out.println(" 1 - Cargar desde archivos      ");
        System.out.println(" 2 - Cargar desde base de datos ");
        System.out.println("--------------------------------");
        System.out.println(" 0 - Salir                      ");
        System.out.println("--------------------------------");
        System.out.println("--------------------------------");

        opcionSeleccionada = textIO.newIntInputReader()
                            .withMinVal(0)
                            .withMaxVal(2)
                            .read("Opcion : ");


        switch (opcionSeleccionada) {
            case 0 :
                System.out.println();
                System.out.println("Saliendo del programa...");
                System.out.println();
                break;
            case 1 :
                System.out.println();
                System.out.println("Procesando datos desde archivos...");
                System.out.println();
                break;
            case 2 :
                System.out.println();
                System.out.println("Procesando datos desde base de datos...");
                System.out.println();
                break;
            default :
                System.out.println();
                System.out.println("Opción inválida. Intente de nuevo.");
                System.out.println();
            }
        
        return opcionSeleccionada;
    }
}

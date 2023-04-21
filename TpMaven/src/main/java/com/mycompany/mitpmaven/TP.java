/*
 Para entrega 2
 */
package com.mycompany.mitpmaven;

//import tp.PronosticoDeportivo;

public class TP {

    public static PronosticoDeportivo PRODE;
    
   
    public static void main(String[] args) {
        System.out.println ("Sistema de simulación de pronósticos deportivos.");
        System.out.println ("Ejecutándose desde:" + System.getProperty("user.dir"));
        System.out.println ();
        
        PRODE = new PronosticoDeportivo();

        //PRODE.play();
    }
}

/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
*/

package com.mycompany.tp.maven;

/**
* @author GRUPO 7
*/
public class TpMaven {

    public static PronosticoDeportivo PRODE;

    public static void main(String[] args) {
        System.out.println ();
        System.out.println ("Sistema de simulación de pronósticos deportivos.");
        System.out.println ("Ejecutándose desde:" + System.getProperty("user.dir"));
        
        PRODE = new PronosticoDeportivo();

        PRODE.play();
    }
}

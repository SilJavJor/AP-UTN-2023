/*
*/
package com.mycompany.tp.maven;

/**
 * @author GRUPO 7
 */

public class Equipo {
    // Atributos
    private Integer idEquipo;
    private String nombre;
    private String descripcion;
    
    // Metodos
    public Equipo(Integer idEquipo, String nombre, String descripcion) {
        this.idEquipo = idEquipo;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    public Equipo() {
        this.idEquipo = null;
        this.nombre = null;
        this.descripcion = null;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescriopcion() {
        return descripcion;
    }

    public void setDescriopcion(String descriopcion) {
        this.descripcion = descriopcion;
    }

    @Override
    public String toString() {
        return this.getIdEquipo() + "\t" + this.getNombre() + "\t" + "\t" + 
               this.getDescriopcion();
    }
    
    // Metodos Especificos
    public String dibujar(int caracteresMaximos) {
        String id = padRight(String.valueOf(this.idEquipo), 5); 
        String nombre = padRight(this.nombre, caracteresMaximos); 
        String descripcion = padRight(this.descripcion, 10); 
        
        return id + nombre + descripcion;
    }
    
    //
    public static String padRight(String s, int n) {
    
        return String.format("%-" + n + "s", s);  
    }
}

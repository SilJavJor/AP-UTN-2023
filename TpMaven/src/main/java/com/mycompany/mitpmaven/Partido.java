/*
*/
package com.mycompany.mitpmaven;

/**
 * @author GRUPO 7
 */

public class Partido {
    // Atributos
    private Integer idPartido;
    private Equipo equipo1;
    private Equipo equipo2;
    private Integer golesEquipo1;
    private Integer golesEquipo2;

    // Metodos
    public Partido(Integer idPartido, Equipo equipo1, Equipo equipo2, Integer golesEquipo1, Integer golesEquipo2) {
        this.idPartido = idPartido;
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.golesEquipo1 = golesEquipo1;
        this.golesEquipo2 = golesEquipo2;
    }

    public Partido() {
        this.idPartido = null;
        this.equipo1 = null;
        this.equipo2 = null;
        this.golesEquipo1 = null;
        this.golesEquipo2 = null;
    }

    public int getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(Integer idPartido) {
        this.idPartido = idPartido;
    }

    public Equipo getEquipo1() {
        return equipo1;
    }

    public void setEquipo1(Equipo equipoLocal) {
        this.equipo1 = equipoLocal;
    }

    public Equipo getEquipo2() {
        return equipo2;
    }

    public void setEquipo2(Equipo equipoVisitante) {
        this.equipo2 = equipoVisitante;
    }

    public Integer getGolesEquipo1() {
        return golesEquipo1;
    }

    public void setGolesEquipo1(Integer golesEquipo1) {
        this.golesEquipo1 = golesEquipo1;
    }

    public Integer getGolesEquipo2() {
        return golesEquipo2;
    }

    public void setGolesEquipo2(Integer golesEquipo2) {
        this.golesEquipo2 = golesEquipo2;
    }

    @Override
    public String toString() {
        return this.getIdPartido() + "\t" + this.getEquipo1().getNombre() + "\t" + 
               this.getGolesEquipo1() +  "\t" + "\t vs \t" + this.getEquipo2().getNombre() + "\t" + 
               this.getGolesEquipo2() +  "\t" + this.getResultado(equipo1) + "\t";
    }
    
    // Metodos Especificos
    public char getResultado (Equipo equipo) {
        char resultado = 'X'; // POR DEFECTO NO SE SABE QUIEN GANO
        
        if (equipo.getNombre().equals(equipo1.getNombre())) {
            if (this.golesEquipo1 > this.golesEquipo2) {
                resultado = 'G';
            } else if (this.golesEquipo1 < this.golesEquipo2) {
                resultado = 'P';
            } else {
                resultado = 'E';
            }
        } else if (equipo.getNombre().equals(equipo2.getNombre())) {
            if (this.golesEquipo2 > this.golesEquipo1) {
                resultado = 'G';
            } else if (this.golesEquipo2 < this.golesEquipo1) {
                resultado = 'P';
            } else {
                resultado = 'E';
            }
        }
        
        return resultado;
    }
}

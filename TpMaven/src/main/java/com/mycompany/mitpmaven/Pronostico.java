/*
*/
package com.mycompany.mitpmaven;

/**
 *
 * @author GRUPO 7
 */
public class Pronostico {
    // Atributos
    private Integer idPronostico;
    private Participante participante;
    private Equipo equipo;
    private Partido partido;
    private char resultado;
    
    
    // Metodos
    public Pronostico(int idPronostico, Equipo equipo, Partido partido, char resultado) {
        this.idPronostico = idPronostico; 
        this.participante = null; 
        this.equipo = equipo;
        this.partido = partido;
        this.resultado = resultado;
    }

    public Pronostico(Integer idPronostico, Equipo equipo, Partido partido, char resultado,  Participante participante) {
        this.idPronostico = idPronostico; 
        this.equipo = equipo;
        this.partido = partido;
        this.resultado = resultado;
        this.participante = participante; 
    }
    
    public Pronostico() {
        this.idPronostico = null; 
        this.participante = null;
        this.equipo = null;
        this.partido = null;
        this.resultado = ' ';
    }

    public int getIdPronostico() {
        return idPronostico;
    }

    public void setIdPronostico(int idPronostico) {
        this.idPronostico = idPronostico;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public char getResultado() {
        return resultado;
    }

    public void setResultado(char resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        // return "Pronostico : " + this.getPartido();
        //return "Pronostico : " + this.getPartido().toString();
        return "Pronostico : " + this.getPartido().toString() + System.lineSeparator();
    }
    
    // Metodos Especificos
    public int getPuntaje(){
        char resultadoPronostico = this.getResultado();
        // Consulta el resultado del partido para el equipo
        char resultadoPartido = this.partido.getResultado(this.equipo);
        
        // Ahora vemos si el resultado coincide con el pronostico
//        if (resultado == this.getResultado()){
//        if (resultado == otroResultado){
        if (resultadoPronostico == resultadoPartido){
            // Si coincide acerte al pronostico se retorna 1
            return 1;
        } else {
            // Si no coincide erre al pronostico se retorna 0s
            return 0;
        }
    }
}

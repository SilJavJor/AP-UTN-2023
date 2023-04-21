/*
*/
package com.mycompany.mitpmaven;

import java.util.List;
/**
 * @author GRUPO 7
 */

public class Participante implements Comparable<Participante> {
    // Atributos
    private Integer idParticipante;
    private String nombre;
    private ListaPronosticos pronosticos;

    // Metodos
    public Participante(Integer idParticipante, String nombre, ListaPronosticos pronosticos) {
        this.idParticipante = idParticipante;
        this.nombre = nombre;
        this.pronosticos = pronosticos;
    }
    
    public Participante(Integer idParticipante, String nombre) {
        this.idParticipante = idParticipante;
        this.nombre = nombre;
        this.pronosticos = new ListaPronosticos();
    }
    
    public Participante() {
        this.idParticipante = null;
        this.nombre = null;
        this.pronosticos = new ListaPronosticos();
    }

    public int getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(Integer idParticipante) {
        this.idParticipante = idParticipante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ListaPronosticos getPronosticos() {
        return pronosticos;
    }

    public void setPronosticos(ListaPronosticos pronosticos) {
        this.pronosticos = pronosticos;
    }

    @Override
    public String toString() {
        return this.getIdParticipante() + "\t" + this.getNombre() + "\t";
    }
    
    @Override
    public int compareTo(Participante participante){
        // Devuelve 0 si es igual, 1 si es mayor y -1 si es menor
        //Integer miPuntaje = this.getPuntaje();
        Integer puntajeActual = this.getPuntaje();
        //Integer otroPuntaje = participante.getPuntaje();
        
        // if (miPuntaje == otroPuntaje) {
        if (puntajeActual == participante.getPuntaje()) {
            return 0;
        //} else if (miPuantaje > otroPuntaje) {
        } else if (puntajeActual > participante.getPuntaje()) {
            return 1;
        } else {
            return -1;
        }
    }

    // Metodos Especificos
    // Carga los pronosticos del participante actual
    void cargarPronosticos (int opcion, ListaEquipos listaEquipos, ListaPartidos listaPartidos) {
        switch (opcion) {
            case 1 :
                // Carga desde el archivo
                this.pronosticos.cargarDeArchivo(this.getIdParticipante(), listaEquipos, listaPartidos);
                break;
            case 2 :
                // Carga desde la base de datos
                this.pronosticos.cargarDeDb(this.getIdParticipante(), listaEquipos, listaPartidos);
                break;
            default :
                System.out.println();
                System.out.println("Opción no implementada.");
                System.out.println();
                break;
        }
    }
    
    // retorna el puntaje del participando calculando los valores de los pronosticos
    public int getPuntaje() {
        // Para ver el puntaje debo recorrer los pronosticos y ver el puntaje
        // de cada uno y acumularlo. Debo devolver el total.
        int puntaje = 0;
        // el primer mensaje corresponde al atributo pronosticos de parrticipante
        // el segundo mensaje corresponde a la lista que tiene el atributo pronosticos
        // de esa lista se obtiene cada pronostico y se saca el puntaje acumulandolo en 
        // la variable puntaje
        for (Pronostico pronostico : this.getPronosticos().getPronosticos()) {
            puntaje += pronostico.getPuntaje();
        }

        return puntaje;
    }

    //
    public String listaPronosticos() {
        String listado = "";

        //for (Pronostico pronostico : this.getPronosticos()) {
        //for (Pronostico pronostico : pronosticos.getPronosticos()) {
        //    listado += this.pronosticos.getPronosticos().toString() + System.lineSeparator();
            listado += this.pronosticos.getPronosticos().toString() + System.lineSeparator();
        //}
        
        //listado += "- - - - - - - - - - - - - - - - - -"+ System.lineSeparator();

        return listado;
    }
}

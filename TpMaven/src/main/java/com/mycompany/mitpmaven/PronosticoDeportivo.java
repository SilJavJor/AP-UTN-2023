/*
Para entrega 2
 */
package com.mycompany.mitpmaven;

/**
 *
 * @author aguzman
 */

public class PronosticoDeportivo {
    private ListaEquipos equipos;
    private ListaPartidos partidos;
    private ListaParticipantes participantes;
    private ListaPronosticos pronosticos;
    // private int opcion;

    public PronosticoDeportivo() {
        equipos = new ListaEquipos();
        partidos = new ListaPartidos();
        participantes = new ListaParticipantes();
        pronosticos = new ListaPronosticos();
        //opcion = 0;
    }

    public void play(){
        // Opciones de menu
        Menu menu = new Menu();
        //this.opcion = menu.runMenu();
        int opcion = menu.runMenu();
        
        if ((opcion == 1) || (opcion == 2)){
            // cargar y listar los equipos
            //equipos.cargarDeArchivo();
            equipos.cargaDeDatos(opcion);
            //System.out.println(equipos.listar());
            equipos.listar(opcion);
        
            // cargar y listar los participantes
            //participantes.cargarDeArchivo();
            participantes.cargaDeDatos(opcion);
            //System.out.println(participantes.listar());
            participantes.listar(opcion);

            // cargar y listar los participantes
            //partidos.cargarDeArchivo(equipos);
            partidos.cargaDeDatos(opcion, equipos);
            //System.out.println(partidos.listar());
            partidos.listar(opcion, equipos);

            // Una vez cargados los participantes, para cada uno de ellos
            // cargar sus pronósticos
            //for (Participante participante : participantes.getParticipantes()) {
            //    participante.cargarPronosticos(opcion, equipos, partidos);
            //}
            //participantes.listarPronosticosPorParticipantes(opcion);
            
            // System.out.println(participantes.listar());
        
            // agregar y/o modificar el codigo que quieran
        }
    } 
}
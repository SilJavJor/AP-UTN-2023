/*
Clase ListaParticipantes para la entrega 2
 */
package com.mycompany.tp.maven;

import static com.mycompany.tp.maven.BaseDeDatos.abrirConexion;
import static com.mycompany.tp.maven.BaseDeDatos.cerrarConexion;
import static com.mycompany.tp.maven.BaseDeDatos.cerrarSentencia;
import static com.mycompany.tp.maven.BaseDeDatos.crearSentencia;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.a7.A7_Grids;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ListaParticipantes {
    // atributo
    private List<Participante> participantes;
    private String participantesCSV;

    public ListaParticipantes(List<Participante> participantes, String participantesCSV) {
        this.participantes = participantes;
        this.participantesCSV = participantesCSV;
    }
    
    public ListaParticipantes() {
        this.participantes = new ArrayList<Participante>();
        this.participantesCSV = "./Archivos/participantes.csv";
    }

    public List<Participante> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Participante> participantes) {
        this.participantes = participantes;
    }

    public String getParticipantesCSV() {
        return participantesCSV;
    }

    public void setParticipantesCSV(String participantesCSV) {
        this.participantesCSV = participantesCSV;
    }
    
    // add y remove elementos
    public void addParticipante(Participante p) {
        this.participantes.add(p);
    }
    public void removeParticipante(Participante p) {
        this.participantes.remove(p);
    }
    
    @Override
    public String toString() {
        return "ListaParticipantes{" + "participantes=" + participantes + '}';
    }

    /***
     * Este método devuelve un Participante (o null) buscandolo por idParticipante
     * @param idParticipante Identificador del equipo deseado
     * @return Objeto Participante (o null si no se encuentra)
     */
    public Participante getParticipante (int idParticipante) {
        // Defini un objeto de tipo Participante en dónde va a ir mi resultado
        // Inicialmente es null, ya que no he encontrado el equipo que 
        // buscaba todavía.
        Participante encontrado = null;
        // Recorro la lista de participantes que está cargada
        for (Participante eq : this.getParticipantes()) {
            // Para cada equipo obtengo el valor del ID y lo comparo con el que
            // estoy buscando
            if (eq.getIdParticipante() == idParticipante) {
                // Si lo encuentro (son iguales) lo asigno como valor de encontrado
                encontrado = eq;
                // Y hago un break para salir del ciclo ya que no hace falta seguir buscando
                break;
            }
        }
        // Una vez fuera del ciclo retorno el Participante, pueden pasar dos cosas:
        // 1- Lo encontré en el ciclo, entonces encontrado tiene el objeto encontrado
        // 2- No lo encontré en el ciclo, entonces conserva el valor null del principio
        return encontrado;
    }

    // Metodos Especificos    
    public void listar(int opcion) {
        switch (opcion) {
            case 1 :
                listadoParticipantesEstandar();  // Listado para carga de archivos
                break;
            case 2 :
                listadoParticipantesTabla();   // Listado para carga de DB
                break;
            default :
                System.out.println();
                System.out.println("Opción no implementada.");
                System.out.println();
                break;
        }
    }
    
    // Realiza una impresion eestandard por pantalla
    public void listadoParticipantesEstandar() {
        String lista = "";

        lista += System.lineSeparator();
        lista += "Los participantes cargados son : " + System.lineSeparator();
        lista += "--------------------------------" + System.lineSeparator();
        
        for (Participante participante: this.getParticipantes()) {
            lista += participante + System.lineSeparator();
        }           
 
        System.out.println(lista);
    }
    
    
    // 
    public void listadoOrdenadoPorPuntajeEstandar() {
        List<Participante> listaOrdenadaPorPuntaje = this.getOrdenadosPorPuntaje();
        String listado = "";

        listado += System.lineSeparator();
        listado += "Participantes ordenados por ppuntaje : " + System.lineSeparator();
        listado += "--------------------------------------" + System.lineSeparator();
        
        for (Participante participante: listaOrdenadaPorPuntaje) {
            listado += participante + System.lineSeparator();
        }           
 
        System.out.println(listado);
    }
    
    // 
    public void listadoParticipantesTabla() {
        //SystemTextTerminal systerm = new SystemTextTerminal();
        //TextIO textIO = new TextIO (systerm);
        //TextTerminal terminal = textIO.getTextTerminal();
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("LISTADO DE PARTICIPANTES","");
        at.addRule();
        at.addRow("Número","Nombre");
        at.addRule();

        for (Participante participante : participantes) {
            at.addRow(participante.getIdParticipante(), 
                    participante.getNombre());
        }           

        at.addRule();
        
        //String rend = at.render();
        //terminal.print (rend);

        at.getContext().setGrid(A7_Grids.minusBarPlus());
        //at.getContext().setGrid(A7_Grids.minusBarPlusEquals());

        at.setPaddingLeftRight(1);
        
        System.out.println(at.render(90));

        System.out.println();
        System.out.println();
    }
    
    public void listarPronosticosPorParticipante(int opcion) {
        switch (opcion) {
            case 1 :
                listadoPronosticosPorParticipanteEstandar();  // Listado para carga de archivos
                break;
            case 2 :
                listadoPronosticosPorParticipanteTabla();   // Listado para carga de DB
                break;
            default :
                System.out.println();
                System.out.println("Opción no implementada.");
                System.out.println();
        }
    }

    //
    private void listadoPronosticosPorParticipanteEstandar() {
        String listado = "";

        listado += System.lineSeparator();
        listado += "Los pronosticos cargados son : \t" + System.lineSeparator(); 

        for (Participante participante : participantes) {
            listado += "Para el participante : \t" + 
                    this.getParticipante(participante.getIdParticipante()).getNombre() + 
                    System.lineSeparator();
            listado += "----------------------" + System.lineSeparator();
        
            //listado += this.getParticipante(participante.getIdParticipante()).listaPronosticos() + 
            //         System.lineSeparator();
            listado += participante.listaPronosticos() + System.lineSeparator();
            listado += System.lineSeparator();
        }           
 
        System.out.println(listado);
    }

    //
    public void listadoPronosticosPorParticipanteTabla() {

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("LISTADO DE PRONOSTICOS","","","","");
        at.addRule();
        at.addRow("Participante","Partido","Equipo","Pronostico","Puntaje");
        at.addRule();
        
        for (Participante participante : this.getParticipantes()) {
            at.addRow(participante.getNombre(),"","","","");
        }
        
        at.addRule();
        at.getContext().setGrid(A7_Grids.minusBarPlus());
        at.setPaddingLeftRight(1);
        
        System.out.println(at.render(90));
        System.out.println();
        System.out.println();
    }

    //
    public void listarParticipantesPorPuntaje(int opcion) {
        switch (opcion) {
            case 1 :
                listadoParticipantesPorPuntajeEstandar();  // Listado para carga de archivos
                break;
            case 2 :
                listadoParticipantesPorPuntajeTabla();   // Listado para carga de DB
                break;
            default :
                System.out.println();
                System.out.println("Opción no implementada.");
                System.out.println();
        }
    }

    //
    public void listadoParticipantesPorPuntajeEstandar() {
        int puntaje = 0;
        String listado = "";

        listado += System.lineSeparator();
        listado += "Los puntajes por participante son : " + System.lineSeparator();
        listado += "-----------------------------------" + System.lineSeparator();
        
        for (Participante participante : this.getParticipantes()) {
            puntaje += participante.getPuntaje();
            listado += participante.getNombre() + "\t\t" + puntaje + System.lineSeparator();
        }
        
        System.out.println(listado);
        System.out.println();
        System.out.println();
    }
    
    //
    public void listadoParticipantesPorPuntajeTabla() {
        //SystemTextTerminal systerm = new SystemTextTerminal();
        //TextIO textIO = new TextIO (systerm);
        //TextTerminal terminal = textIO.getTextTerminal();
        int puntaje = 0;

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("LISTADO DE PUNTAJES","");
        at.addRule();
        at.addRow("Participante","Puntaje");
        at.addRule();
        
        for (Participante participante : this.getParticipantes()) {
            //at.addRow(participante.getNombre(),participante.getPuntaje());
            puntaje += participante.getPuntaje();
            at.addRow(participante.getNombre(),puntaje);
            //participante.cargarPronosticos(opcion, equipos, partidos);
        }
        
        at.addRule();
        at.getContext().setGrid(A7_Grids.minusBarPlus());
        at.setPaddingLeftRight(1);
        
        System.out.println(at.render(90));
        System.out.println();
        System.out.println();
    }
    
    //
    public void listarParticipantesOrdenadosPorPuntajes(int opcion) {
        switch (opcion) {
            case 1 :
                //listadoParticipantesPorPuntajesOrdenadosEstandar();  // Listado para carga de archivos
                break;
            case 2 :
                listadoParticipantesOrdenadosPorPuntajesTabla();   // Listado para carga de DB
                break;
            default :
                System.out.println();
                System.out.println("Opción no implementada.");
                System.out.println();
        }
    }

    //
    public void listadoParticipantesOrdenadosPorPuntajesTabla() {
        int puntaje = 0;

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("LISTADO DE PUNTAJES","(ORDENADO POR PUNTAJE)");
        at.addRule();
        at.addRow("Participante","Puntaje");
        at.addRule();
        

        for (Participante participante : this.getOrdenadosPorPuntaje()) {
            at.addRow(participante.getNombre(),participante.getPuntaje());
        }
        
        at.addRule();
        at.getContext().setGrid(A7_Grids.minusBarPlus());
        at.setPaddingLeftRight(1);
        
        System.out.println(at.render(90));
        System.out.println();
        System.out.println();
    }
    
    // Seleccion de la carga de datos
    public void cargaDeDatos(int opcion) {
        switch (opcion) {
            case 1 :
                cargarDeArchivo();  // Carga desde la base de datos0
                break;
            case 2 :
                cargarDeDb();   // Carga desde la base de datos
                break;
            default :
                System.out.println();
                System.out.println("Opción no implementada.");
                System.out.println();
        }
    }

    // cargar desde el archivo
    private void cargarDeArchivo() {
        // para las lineas del archivo csv
        String datosParticipante;
        // para los datos individuales de cada linea
        String vectorParticipante[];
        // para el objeto en memoria
        Participante participante;
        int fila = 0;
       
        try { 
            Scanner sc;
            sc = new Scanner(new File(this.getParticipantesCSV()));
            sc.useDelimiter(System.lineSeparator() );   //setea el separador de los datos
                
            while (sc.hasNext()) {
                // levanta los datos de cada linea
                datosParticipante = sc.next();
                //System.out.println(datosParticipante);  //muestra los datos levantados 
                // Descomentar si se quiere mostrar cada línea leída desde el archivo
                // System.out.println(datosParticipante);  //muestra los datos levantados 
                fila ++;
                // si es la cabecera la descarto y no se considera para armar el listado
                if (fila == 1)
                    continue;              
                 
                //Proceso auxiliar para convertir los string en vector
                // guarda en un vector los elementos individuales
                vectorParticipante = datosParticipante.split(",");   
                
                // graba el equipo en memoria
                // convertir un string a un entero;
                int readIdParticipante = Integer.parseInt(vectorParticipante[0]);
                String readNombre = vectorParticipante[1];
                // crea el objeto en memoria
                participante = new Participante(readIdParticipante, 
                        StringsUtils.eliminarComillasDobles(readNombre));
                
                // llama al metodo add para grabar el equipo en la lista en memoria
                this.addParticipante(participante);
            }

            //closes the scanner
            sc.close();
        } catch (IOException ex) {
                System.out.println("Mensaje: " + ex.getMessage());
        }       
    }

    // cargar desde la base de datos
    private void cargarDeDb() {
        // Todas las lineas comentadas son del codigo original 
        //Connection conn = null;
        
        try {
            // Establecer una conexión
            //conn = DriverManager.getConnection("jdbc:sqlite:./Archivos/pronosticos.db");
            abrirConexion();
            
            // Crear la base de datos
            //Statement stmt = conn.createStatement();
            Statement stmt = crearSentencia();

            //System.out.println("Consultando datos...");
            String sq = "SELECT idParticipante, Nombre FROM participantes";
            ResultSet rs = stmt.executeQuery(sq); // loop through the result set

            //ListaEquipos lista = new ListaEquipos();
            while (rs.next()) {
                Participante participante = new Participante(
                        rs.getInt("idParticipante"),
                        StringsUtils.eliminarComillasDobles(rs.getString("Nombre"))
                );

                // llama al metodo add para grabar el equipo en la lista en memoria
                this.addParticipante(participante);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            cerrarSentencia();
            cerrarConexion();
        }
    }
    
    // Ordena la lista
    public List<Participante> getOrdenadosPorPuntaje() {
        // Genera una copia de la lista de participantes
        List<Participante> listaOrdenada = new ArrayList<Participante>();
        listaOrdenada.addAll(this.getParticipantes());
        
        // Obtiene la lista ordenada de mayor a menor
        Collections.sort(listaOrdenada, Collections.reverseOrder());
        //Collections.sort(listaOrdenada);
        
        return listaOrdenada;
    }
}
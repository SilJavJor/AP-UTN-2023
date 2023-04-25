/*
*/
package com.mycompany.tp.maven;

/**
* @author GRUPO 7
*/

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
import java.util.List;
import java.util.Scanner;

class ListaPartidos {
    // Atributos
    private List<Partido> partidos;
    private String partidosCSV;
    
    // Metodos
    public ListaPartidos(List<Partido> partidos, String partidosCSV) {
        this.partidos = partidos;
        this.partidosCSV = partidosCSV;
    }
    
    public ListaPartidos() {
        this.partidos = new ArrayList<Partido>();
        this.partidosCSV = "./Archivos/partidos.csv";
    }
    
    public List<Partido> getPartidos() {
        return partidos;
    }
    
    public void setPartidos(List<Partido> partidos) {
        this.partidos = partidos;
    }

    public String getPartidosCSV() {
        return partidosCSV;
    }

    public void setPartidosCSV(String partidosCSV) {
        this.partidosCSV = partidosCSV;
    }
    
    // Agregar elemento a la lista
    public void addPartido(Partido partido) {
        this.partidos.add(partido);
    }

    // Eliminar elemento de la lista
    public void removePartido(Partido partido) {
        this.partidos.remove(partido);
    }

    /***
     * Este método devuelve un Equipo (o null) buscandolo por idEquipo
     * @param idPartido Identificador del equipo deseado
     * @return Objeto Equipo (o null si no se encuentra)
     */
    public Partido getPartido (Integer idPartido) {
        // Defini un objeto de tipo Equipo en dónde va a ir mi resultado
        // Inicialmente es null, ya que no he encontrado el equipo que 
        // buscaba todavía.
        Partido encontrado = null;
        // Recorro la lista de equipos que está cargada
        for (Partido partido : this.getPartidos()) {
            // Para cada equipo obtengo el valor del ID y lo comparo con el que
            // estoy buscando
            if (partido.getIdPartido() == idPartido) {
                // Si lo encuentro (son iguales) lo asigno como valor de encontrado
                encontrado = partido;
                // Y hago un break para salir del ciclo ya que no hace falta seguir buscando
                break;
            }
        }
        // Una vez fuera del ciclo retorno el equipo, pueden pasar dos cosas:
        // 1- Lo encontré en el ciclo, entonces encontrado tiene el objeto encontrado
        // 2- No lo encontré en el ciclo, entonces conserva el valor null del principio
        return encontrado;
    }

    @Override
    public String toString() {
        return "ListaEquipos{" + "equipos=" + partidos + '}';
    }
    
    // Metodos Especificos    
    public void listar(Integer opcion, ListaEquipos listaequipos) {
        switch (opcion) {
            case 1 :
                listadoEstandar();  // Listado para carga de archivos
                break;
            case 2 :
                listadoTabla();   // Listado para carga de DB
                break;
            default :
                System.out.println();
                System.out.println("Opción no implementada.");
                System.out.println();
        }
    }
    
    private void listadoEstandar() {
        String listado = "";
        
        listado += System.lineSeparator();
        listado += "Los partidos cargados son : " + System.lineSeparator();
        listado += "---------------------------" + System.lineSeparator();
        
        for (Partido partido: partidos) {
            listado += partido + System.lineSeparator();
        } 
        
        System.out.println(listado);
        System.out.println();
        System.out.println();
    }
    
    private void listadoTabla() {
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("LISTADO DE PARTIDOS","", "","", "", "");
        at.addRule();
        at.addRow("Partido","Equipo 1", "Goles 1","Equipo 2", "Goles 2", "Resultado");
        at.addRule();

        for (Partido partido : partidos) {
            at.addRow(partido.getIdPartido(), partido.getEquipo1(), 
                    partido.getGolesEquipo1(), partido.getEquipo2(), 
                    partido.getGolesEquipo2(), partido.getResultado(partido.getEquipo1()));
        }           

        at.addRule();
        at.getContext().setGrid(A7_Grids.minusBarPlus());
        System.out.println(at.render(195));

        System.out.println();
        System.out.println();
    }
    
    // Seleccion de la carga de datos
    public void cargaDeDatos(Integer opcion, ListaEquipos listaequipos) {
        switch (opcion) {
            case 1 : 
                cargarDeArchivo(listaequipos);  // Carga desde la base de datos0
                break;
            case 2 :
                cargarDeDb(listaequipos);   // Carga desde la base de datos
                break;
            default :
                System.out.println();
                System.out.println("Opción no implementada.");
                System.out.println();
        }
    }

    // cargar desde el archivo
    private void cargarDeArchivo(ListaEquipos listaEquipos) {
        // para las lineas del archivo csv
        String datosPartido;
        // para los datos individuales de cada linea
        String vectorPartido[];
        // para el objeto en memoria
        Partido partido;
        
        int fila = 0;
       
        try { 
            Scanner sc;
            //sc = new Scanner(new File("./Archivos/partidos.csv"));
            sc = new Scanner(new File(this.getPartidosCSV()));
            sc.useDelimiter(System.lineSeparator());   //setea el separador de los datos
                
            while (sc.hasNext()) {
                // levanta los datos de cada linea
                datosPartido = sc.next();
                //System.out.println(datosPartido);  //muestra los datos levantados 
                fila ++;
                // si es la cabecera la descarto y no se considera para armar el listado
                if (fila == 1)
                    continue;              
                 
                // Proceso auxiliar para convertir los string en vector
                // guarda en un vector los elementos individuales
                vectorPartido = datosPartido.split(",");   
                
                // graba el equipo en memoria
                // convertir un string a un entero;
                int readIdPartido = Integer.parseInt(vectorPartido[0]);
                int readIdEquipo1 = Integer.parseInt(vectorPartido[1]);
                int readIdEquipo2 = Integer.parseInt(vectorPartido[2]);
                int readGolesEquipo1 = Integer.parseInt(vectorPartido[3]);
                int readGolesEquipo2 = Integer.parseInt(vectorPartido[4]);

                Equipo equipo1 = listaEquipos.getEquipo(readIdEquipo1);
                Equipo equipo2 = listaEquipos.getEquipo(readIdEquipo2);
                
                // crea el objeto en memoria
                partido = new Partido(readIdPartido, equipo1, equipo2, 
                        readGolesEquipo1, readGolesEquipo2);
                // crea el objeto en memoria
                
                // llama al metodo add para grabar el equipo en la lista en memoria
                this.addPartido(partido);
            }

            // closes the scanner
            sc.close();
        } catch (IOException ex) {
                System.out.println("Mensaje: " + ex.getMessage());
        }           
    }

    // cargar desde la base de datos
    private void cargarDeDb(ListaEquipos listaEquipos) {
        try { 
            // Establecer una conexión
            //conn = DriverManager.getConnection("jdbc:sqlite:./Archivos/pronosticos.db");
            abrirConexion();
            
            // Crear la base de datos
            //Statement stmt = conn.createStatement();
            Statement stmt = crearSentencia();

            //System.out.println("Consultando datos...");
            String sq = "SELECT idPartido, idEquipo1, idEquipo2, golesEquipo1,"
                    + "golesEquipo2  FROM partidos";
            ResultSet rs = stmt.executeQuery(sq); // loop through the result set

            //ListaEquipos lista = new ListaEquipos();
            while (rs.next()) {
                Equipo equipo1 = listaEquipos.getEquipo(rs.getInt("idEquipo1"));
                Equipo equipo2 = listaEquipos.getEquipo(rs.getInt("idEquipo2"));
                
                // crea el objeto en memoria
                Partido partido = new Partido(rs.getInt("idPartido"), equipo1, equipo2, 
                        rs.getInt("golesEquipo1"), rs.getInt("golesEquipo2"));
                // crea el objeto en memoria

                // llama al metodo add para grabar el equipo en la lista en memoria
                this.addPartido(partido);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            cerrarSentencia();
            cerrarConexion();
        }   
    }
}
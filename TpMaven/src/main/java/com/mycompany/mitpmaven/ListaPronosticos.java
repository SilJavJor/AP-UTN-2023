/*
Clase ListaPronosticos para la entrega 2
 */
package com.mycompany.mitpmaven;

import static com.mycompany.mitpmaven.BaseDeDatos.abrirConexion;
import static com.mycompany.mitpmaven.BaseDeDatos.cerrarConexion;
import static com.mycompany.mitpmaven.BaseDeDatos.cerrarSentencia;
import static com.mycompany.mitpmaven.BaseDeDatos.crearSentencia;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListaPronosticos {
    // atributo
    private List<Pronostico> pronosticos;
    private String pronosticosCSV;

    public ListaPronosticos(List<Pronostico> pronosticos, String pronosticosCSV) {
        this.pronosticos = pronosticos;
        this.pronosticosCSV = pronosticosCSV;
    }

    public ListaPronosticos() {
        this.pronosticos = new ArrayList<Pronostico>();
        this.pronosticosCSV = "./Archivos/pronosticos.csv";
    }

    public List<Pronostico> getPronosticos() {
        return pronosticos;
    }

    public void setPronosticos(List<Pronostico> pronosticos) {
        this.pronosticos = pronosticos;
    }

    public String getPronosticosCSV() {
        return pronosticosCSV;
    }

    public void setPronosticosCSV(String pronosticosCSV) {
        this.pronosticosCSV = pronosticosCSV;
    }

    // add y remove elementos
    public void addPronostico(Pronostico p) {
        this.pronosticos.add(p);
    }

    public void removePronostico(Pronostico p) {
        this.pronosticos.remove(p);
    }

    @Override
    public String toString() {
        return "ListaPronosticos = " + pronosticos + '}';
    }

    // 
    public void listar(int opcion) {
        switch (opcion) {
            case 1 :
                listadoEstandar();  // Listado para carga de archivos
                break;
            case 2 :
                //listadoTabla();   // Listado para carga de DB
                break;
            default :
                System.out.println();
                System.out.println("Opción no implementada.");
                System.out.println();
        }
    }
    
    // Realiza una impresion eestandard por pantalla
    public void listadoEstandar() {
        String listado = "";

        listado += System.lineSeparator();
        listado += "Los pronosticos cargados son : " + System.lineSeparator();
        listado += "------------------------------" + System.lineSeparator();
        
        for (Pronostico pronostico : pronosticos) {
            listado += pronostico + System.lineSeparator();
        }           
 
        System.out.println(listado);
        System.out.println();
        System.out.println();
    }
    
    // Cargar desde el archivo, filtrando solamente aquellos pronósticos
    // cuyo idParticipante coincide con el indicado
    // De esa forma todos los pronósticos de la lista pertenecen al mismo participante.
    public void cargarDeArchivo(
            int idParticipante, // id del participante que realizó el pronóstico
            ListaEquipos listaEquipos, // lista de equipos
            ListaPartidos listaPartidos // lista de partidos
    ) {
        // para las lineas del archivo csv
        String datosPronostico;
        // para los datos individuales de cada linea
        String vectorPronostico[];

        int fila = 0;

        try {
            Scanner sc;
            sc = new Scanner(new File(this.getPronosticosCSV()));
            sc.useDelimiter(System.lineSeparator());

            while (sc.hasNext()) {
                // levanta los datos de cada linea
                datosPronostico = sc.next();
                // Descomentar si se quiere mostrar cada línea leída desde el archivo
                // System.out.println(datosPronostico);  //muestra los datos levantados 
                fila++;
                // si es la cabecera la descarto y no se considera para armar el listado
                if (fila == 1) {
                    continue;
                }

                //Proceso auxiliar para convertir los string en vector
                // guarda en un vector los elementos individuales
                vectorPronostico = datosPronostico.split(",");

                // graba el equipo en memoria
                //convertir un string a un entero;
                int readIdPronostico = Integer.parseInt(vectorPronostico[0]);
                int readIdParticipante = Integer.parseInt(vectorPronostico[1]);
                int readIdPartido = Integer.parseInt(vectorPronostico[2]);
                int readIdEquipo = Integer.parseInt(vectorPronostico[3]);
                char readResultado = vectorPronostico[4].charAt(1);     // El primer caracter es una comilla delimitadora de campo
                // Si coincide el idParticipante con el que estoy queriendo cargar,
                // sigo, si no, salteo el registro y voy al siguiente
                if (readIdParticipante == idParticipante) {
                    // Obtener los objetos que necesito para el constructor
                    Partido partido = listaPartidos.getPartido(readIdPartido);
                    Equipo equipo = listaEquipos.getEquipo(readIdEquipo);
                    // crea el objeto en memoria
                    Pronostico pronostico = new Pronostico(
                            readIdPronostico, // El id leido del archivo
                            equipo, // El Equipo que obtuvimos de la lista
                            partido, // El Partido que obtuvimos de la lista
                            readResultado // El resultado que leimos del archivo
                    );

                    // llama al metodo add para grabar el equipo en la lista en memoria
                    this.addPronostico(pronostico);
                }
            }
            
            //closes the scanner
            sc.close();
        } catch (IOException ex) {
            System.out.println("Mensaje: " + ex.getMessage());
        }
    }
    
    // cargar desde la base de datos
    public void cargarDeDb(
            int idParticipante, // id del participante que realizó el pronóstico
            ListaEquipos listaEquipos, // lista de equipos
            ListaPartidos listaPartidos // lista de partidos
    ) {
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
            String sq = "SELECT idPronostico, idParticipante, idPartido, idEquipo, resultado "
                        + "FROM pronosticos WHERE idParticipante = " + idParticipante;
            ResultSet rs = stmt.executeQuery(sq); // loop through the result set

            //ListaEquipos lista = new ListaEquipos();
            while (rs.next()) {
                // ObtIene el partido
                Partido partido = listaPartidos.getPartido(rs.getInt("idPartido"));
                // Obtien el partido
                Equipo equipo = listaEquipos.getEquipo(rs.getInt("idEquipo"));
                
                // Crea el objeto en memoria
                Pronostico pronostico = new Pronostico(rs.getInt("idPronostico"),
                                  equipo, partido, rs.getString("resultado").charAt(0)
                );

                // llama al metodo add para grabar el equipo en la lista en memoria
                this.addPronostico(pronostico);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            cerrarSentencia();
            cerrarConexion();
        }
    }
    
    //   
    public void cargarDeArchivoTodos(
            ListaEquipos listaEquipos, // lista de equipos
            ListaPartidos listaPartidos // lista de partidos
    ) {
        // para las lineas del archivo csv
        String datosPronostico;
        // para los datos individuales de cada linea
        String vectorPronostico[];

        int fila = 0;

        try {
            Scanner sc = new Scanner(new File(this.getPronosticosCSV()));
            sc.useDelimiter("\n");   //setea el separador de los datos

            while (sc.hasNext()) {
                // levanta los datos de cada linea
                datosPronostico = sc.next();
                // Descomentar si se quiere mostrar cada línea leída desde el archivo
                // System.out.println(datosPronostico);  //muestra los datos levantados 
                fila++;
                // si es la cabecera la descarto y no se considera para armar el listado
                if (fila == 1) {
                    continue;
                }

                //Proceso auxiliar para convertir los string en vector
                // guarda en un vector los elementos individuales
                vectorPronostico = datosPronostico.split(",");

                // graba el equipo en memoria
                //convertir un string a un entero;
                int readIdPronostico = Integer.parseInt(vectorPronostico[0]);
                int readIdParticipante = Integer.parseInt(vectorPronostico[1]);
                int readIdPartido = Integer.parseInt(vectorPronostico[2]);
                int readIdEquipo = Integer.parseInt(vectorPronostico[3]);
                char readResultado = vectorPronostico[4].charAt(1);     // El primer caracter es una comilla delimitadora de campo
                
                // Obtener los objetos que necesito para el constructor
                Partido partido = listaPartidos.getPartido(readIdPartido);
                Equipo equipo = listaEquipos.getEquipo(readIdEquipo);
                // crea el objeto en memoria
                //Pronostico pronostico = new Pronostico(
                //        readIdPronostico, // El id leido del archivo
                //        equipo, // El Equipo que obtuvimos de la lista
                //        partido, // El Partido que obtuvimos de la lista
                //        readResultado, // El resultado que leimos del archivo,
                //        readIdParticipante
                //);

                // llama al metodo add para grabar el equipo en la lista en memoria
                //this.addPronostico(pronostico);
            }
            
        // closes the scanner
        } catch (IOException ex) {
            System.out.println("Mensaje: " + ex.getMessage());
        }
    }
}
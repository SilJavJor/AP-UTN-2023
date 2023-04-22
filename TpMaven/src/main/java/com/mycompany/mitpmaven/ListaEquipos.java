/*
*/
package com.mycompany.mitpmaven;

/**
* @author GRUPO 7
*/

import static com.mycompany.mitpmaven.BaseDeDatos.abrirConexion;
import static com.mycompany.mitpmaven.BaseDeDatos.cerrarConexion;
import static com.mycompany.mitpmaven.BaseDeDatos.cerrarSentencia;
import static com.mycompany.mitpmaven.BaseDeDatos.crearSentencia;
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

public class ListaEquipos {
    // Atributos
    private List<Equipo> equipos;
    private String equiposCSV;
    
    // Metodos
    public ListaEquipos(List<Equipo> equipos, String equiposCSV) {
        this.equipos = equipos;
        this.equiposCSV = equiposCSV;
    }
    
    public ListaEquipos() {
        //this.equipos = new ArrayList<Equipo>();
        this.equipos = new ArrayList<>();
        this.equiposCSV = "./Archivos/equipos.csv";
    }
    
    public List<Equipo> getEquipos() {
        return equipos;
    }
    
    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    public String getEquiposCSV() {
        return equiposCSV;
    }

    public void setEquiposCSV(String equiposCSV) {
        this.equiposCSV = equiposCSV;
    }
    
    // Agregar elemento a la lista
    public void addEquipo(Equipo equipo) {
        this.equipos.add(equipo);
    }

    // Eliminar elemento de la lista
    public void removeEquipo(Equipo equipo) {
        this.equipos.remove(equipo);
    }

    /***
     * Este método devuelve un Equipo (o null) buscandolo por idEquipo
     * @param idEquipo Identificador del equipo deseado
     * @return Objeto Equipo (o null si no se encuentra)
     */
    public Equipo getEquipo (Integer idEquipo) {
        // Defini un objeto de tipo Equipo en dónde va a ir mi resultado
        // Inicialmente es null, ya que no he encontrado el equipo que 
        // buscaba todavía.
        Equipo encontrado = null;
        // Recorro la lista de equipos que está cargada
        for (Equipo eq : this.getEquipos()) {
            // Para cada equipo obtengo el valor del ID y lo comparo con el que
            // estoy buscando
            if (eq.getIdEquipo() == idEquipo) {
                // Si lo encuentro (son iguales) lo asigno como valor de encontrado
                encontrado = eq;
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
        return "ListaEquipos{" + "equipos=" + equipos + '}';
    }

    // Metodos Especificos    
    public void listar(Integer opcion) {
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
        listado += "Los equipos cargados son : " + System.lineSeparator();
        listado += "--------------------------" + System.lineSeparator();
        
        for (Equipo equipo: equipos) {
            listado += equipo + System.lineSeparator();
        }           
        
        System.out.println(listado);
    }
    
    private void listadoTabla() {
        //SystemTextTerminal systerm = new SystemTextTerminal();
        //TextIO textIO = new TextIO (systerm);
        //TextTerminal terminal = textIO.getTextTerminal();
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("LISTADO DE EQUIPOS", "", "");
        at.addRule();
        at.addRow("Número","Nombre","Descripción");
        at.addRule();

        for (Equipo equipo : equipos) {
            at.addRow(equipo.getIdEquipo(), equipo.getNombre(), 
                    equipo.getDescriopcion());
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
    
    // Seleccion de la carga de datos
    public void cargaDeDatos(Integer opcion) {
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
        String datosEquipo;
        // para los datos individuales de cada linea
        String vectorEquipo[];
        // para el objeto en memoria
        Equipo equipo;
        int fila = 0;
       
        try { 
            //Scanner sc = new Scanner(new File("./Archivos/equipos.csv"));
            Scanner sc;
            sc = new Scanner(new File(this.getEquiposCSV()));
            sc.useDelimiter(System.lineSeparator() );   //setea el separador de los datos

            while (sc.hasNext()) {
                // levanta los datos de cada linea
                datosEquipo = sc.next();
                //System.out.println(datosEquipo);  //muestra los datos levantados 
                fila ++;
                // si es la cabecera la descarto y no se considera para armar el listado
                if (fila == 1)
                    continue;              
                 
                //Proceso auxiliar para convertir los string en vector
                // guarda en un vector los elementos individuales
                vectorEquipo = datosEquipo.split(",");   
                
                // graba el equipo en memoria
                // convertir un string a un entero;
                int readIdEquipo = Integer.parseInt(vectorEquipo[0]);
                //String readNombre = vectorEquipo[1];
                String readNombre = StringsUtils.eliminarComillasDobles(vectorEquipo[1]);
                String readDescripcion = StringsUtils.eliminarComillasDobles(vectorEquipo[2]);
                // crea el objeto en memoria
                equipo = new Equipo(readIdEquipo, readNombre, readDescripcion);
                
                // llama al metodo add para grabar el equipo en la lista en memoria
                this.addEquipo(equipo);
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
            //conn = abrirConexion();
            abrirConexion();
            
            // Crear la base de datos
            // Statement stmt = conn.createStatement();
            Statement stmt = crearSentencia();

            String sq = "SELECT idEquipo, Nombre, Descripcion FROM equipos";
            ResultSet rs = stmt.executeQuery(sq); // loop through the result set

            while (rs.next()) {
                Equipo equipo = new Equipo(
                        rs.getInt("idEquipo"),
                        StringsUtils.eliminarComillasDobles(rs.getString("Nombre")),
                        StringsUtils.eliminarComillasDobles(rs.getString("Descripcion"))
                );
                
                this.addEquipo(equipo);
            }
       } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            cerrarSentencia();
            cerrarConexion();
        }
    }
}
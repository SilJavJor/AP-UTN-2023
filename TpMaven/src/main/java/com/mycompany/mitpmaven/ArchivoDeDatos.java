/*
*/
package com.mycompany.mitpmaven;

/**
* @author  GRUPO 7
*/

import java.io.File;
import java.io.IOException;
//import java.io.FileNotFoundException;
import java.util.Scanner;

public class ArchivoDeDatos {
    //
/*
    private Scanner scanner;

    // Función para abrir el archivo
    public void openFile(String fileName) {
        try {
            scanner = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("Error: el archivo no pudo ser encontrado.");
        }
    }

    // Función para cerrar el archivo
    public void closeFile() {
        scanner.close();
    }
}
*/
    
    //
    private File file;
    private Scanner scanner;

    public ArchivoDeDatos(String nombreArchivo) throws IOException {
        this.file = new File(nombreArchivo);
        
        this.scanner = new Scanner(file);
    }

    public Scanner abrirArchivo(String nombreArchivo) throws IOException {
        this.file = new File(nombreArchivo);
        
        this.scanner = new Scanner(file);
        return null;
    }

    // Cierra el archivo abierto
    public void cerrarArchivo() throws IOException {
        this.scanner.close();
    }
    
    // 
    public boolean hayMasLineas() {
        return this.scanner.hasNextLine();
    }

    // Devuelve la proxima linea leida
    public String leerLinea() {
        return this.scanner.nextLine();
    }
}

/*
    private String nombreArchivo;   // el nombre deberia ser obligatorio
    private String extensionArchivo;    // la extension deberia ser abligatoria
    private String directotioArchivos;  // el directorio de los archivos es el que se indique y se pas deberia ser el directorio don se est ejecutando + Archivos ej (./Archivos/)
    private String archivoDatos;

    public ArchivoDeDatos(String directotioArchivos, String nombreArchivo, String extensionArchivo, String archivoDatos) {
        this.directotioArchivos = directotioArchivos;
        this.nombreArchivo = nombreArchivo;
        this.extensionArchivo = extensionArchivo;
        this.archivoDatos = archivoDatos;
    }

    public ArchivoDeDatos() {
        this.directotioArchivos = "./Archivos/";
        this.nombreArchivo = null;
        this.extensionArchivo = null;
        this.archivoDatos = null;
    }

    public String getDirectotioArchivos() {
        return directotioArchivos;
        // este deberia ser el valor por defecto System.getProperty("user.dir")
    }

    public void setDirectotioArchivos(String directotioArchivos) {
        this.directotioArchivos = directotioArchivos;
        // el directorio de los archivos deberia ser ./Archivos por defecto
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getExtensionArchivo() {
        return extensionArchivo;
    }

    public void setExtensionArchivo(String extensionArchivo) {
        this.extensionArchivo = extensionArchivo;
    }

    public String getArchivoDatos() {
        return archivoDatos;
    }

    public void setArchivoDatos(String archivoDatos) {
        this.archivoDatos = archivoDatos;
    }  
}
*/
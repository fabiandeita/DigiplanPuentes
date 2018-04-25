package dao;

import java.util.HashSet;
import java.util.Set;


/**
 * Origen entity. @author MyEclipse Persistence Tools
 */

public class Origen  implements java.io.Serializable {


    // Fields    

     private Integer idOrigen = 0;
     private String nombre;
     private Set documentos = new HashSet(0);


    // Constructors

    /** default constructor */
    public Origen() {
    }

    
    /** full constructor */
    public Origen(String nombre, Set documentos) {
        this.nombre = nombre;
        this.documentos = documentos;
    }

   
    // Property accessors

    public Integer getIdOrigen() {
        return this.idOrigen;
    }
    
    public void setIdOrigen(Integer idOrigen) {
        this.idOrigen = idOrigen;
    }

    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set getDocumentos() {
        return this.documentos;
    }
    
    public void setDocumentos(Set documentos) {
        this.documentos = documentos;
    }
   








}
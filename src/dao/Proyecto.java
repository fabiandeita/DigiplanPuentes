package dao;

import java.util.HashSet;
import java.util.Set;

/**
 * Proyecto entity. @author MyEclipse Persistence Tools
 */

public class Proyecto implements java.io.Serializable {

	// Fields

	private Integer idProyecto;
	private String nombre;
	private String numProy;
	private String tramos;
	public String getNumProy() {
		return numProy;
	}

	

	// Constructors

	/** default constructor */
	public Proyecto() {
	}

	/** minimal constructor */
	public Proyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}

	/** full constructor */
	public Proyecto(Integer idProyecto, String nombre, String numProy, Set documentos) {
		this.idProyecto = idProyecto;
		this.nombre = nombre;
		this.numProy = numProy;
		this.documentos = documentos;
	}

	// Property accessors

	public Integer getIdProyecto() {
		return this.idProyecto;
	}

	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setNumProy(String numProy) {
		this.numProy = numProy;
	}

	private Set documentos = new HashSet(0);

	public Set getDocumentos() {
		return this.documentos;
	}

	public void setDocumentos(Set documentos) {
		this.documentos = documentos;
	}

	public String getTramos() {
		return tramos;
	}

	public void setTramos(String tramos) {
		this.tramos = tramos;
	}

}
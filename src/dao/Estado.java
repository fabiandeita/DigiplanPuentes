package dao;

import java.util.HashSet;
import java.util.Set;

/**
 * Estado entity. @author MyEclipse Persistence Tools
 */

public class Estado implements java.io.Serializable {

	// Fields

	private Integer idEstado = 0;
	private String nombre;
	private Set documentos = new HashSet(0);
	private Set tramos = new HashSet(0);

	// Constructors

	/** default constructor */
	public Estado() {
	}

	/** full constructor */
	public Estado(String nombre, Set documentos, Set tramos) {
		this.nombre = nombre;
		this.documentos = documentos;
		this.tramos = tramos;
	}

	// Property accessors

	public Integer getIdEstado() {
		return this.idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
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

	public Set getTramos() {
		return this.tramos;
	}

	public void setTramos(Set tramos) {
		this.tramos = tramos;
	}

}
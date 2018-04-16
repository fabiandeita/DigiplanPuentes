package dao;

import java.util.HashSet;
import java.util.Set;

/**
 * Tipodocumento entity. @author MyEclipse Persistence Tools
 */

public class Tipodocumento implements java.io.Serializable {

	// Fields

	private Integer idTipoDocumento = 0;
	private String nombre;
	private Set documentos = new HashSet(0);

	// Constructors

	/** default constructor */
	public Tipodocumento() {
	}

	/** full constructor */
	public Tipodocumento(String nombre, Set documentos) {
		this.nombre = nombre;
		this.documentos = documentos;
	}

	// Property accessors

	public Integer getIdTipoDocumento() {
		return this.idTipoDocumento;
	}

	public void setIdTipoDocumento(Integer idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
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
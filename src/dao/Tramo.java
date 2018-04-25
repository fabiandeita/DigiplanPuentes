package dao;

import java.util.HashSet;
import java.util.Set;

/**
 * Tramo entity. @author MyEclipse Persistence Tools
 */

public class Tramo implements java.io.Serializable {

	// Fields

	private Integer idTramo = 0;
	private Estado estado = new Estado();
	private Carretera carretera = new Carretera();
	private String nombre;
	private Set documentosForIdTramo = new HashSet(0);
	private Set documentosForIdTramoOrigen = new HashSet(0);

	// Constructors

	/** default constructor */
	public Tramo() {
	}

	/** full constructor */
	public Tramo(Estado estado,  String nombre,
			Set documentosForIdTramo, Set documentosForIdTramoOrigen,Carretera carretera) {
		this.estado = estado;
		this.carretera = carretera;
		this.nombre = nombre;
		this.documentosForIdTramo = documentosForIdTramo;
		this.documentosForIdTramoOrigen = documentosForIdTramoOrigen;
	}

	// Property accessors

	public Integer getIdTramo() {
		return this.idTramo;
	}

	public void setIdTramo(Integer idTramo) {
		this.idTramo = idTramo;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
/*
	public Carretera getCarretera() {
		return this.carretera;
	}

	public void setCarretera(Carretera carretera) {
		this.carretera = carretera;
	}
*/
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set getDocumentosForIdTramo() {
		return this.documentosForIdTramo;
	}

	public void setDocumentosForIdTramo(Set documentosForIdTramo) {
		this.documentosForIdTramo = documentosForIdTramo;
	}

	public Set getDocumentosForIdTramoOrigen() {
		return this.documentosForIdTramoOrigen;
	}

	public void setDocumentosForIdTramoOrigen(Set documentosForIdTramoOrigen) {
		this.documentosForIdTramoOrigen = documentosForIdTramoOrigen;
	}

	public Carretera getCarretera() {
		return carretera;
	}

	public void setCarretera(Carretera carretera) {
		this.carretera = carretera;
	}

}
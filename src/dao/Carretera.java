package dao;

/**
 * Carretera entity. @author MyEclipse Persistence Tools
 */

public class Carretera implements java.io.Serializable {

	// Fields

	private Integer idCarretera;
	private String nombre;

	// Constructors

	/** default constructor */
	public Carretera() {
	}

	/** full constructor */
	public Carretera(String nombre) {
		this.nombre = nombre;
	}

	// Property accessors

	public Integer getIdCarretera() {
		return this.idCarretera;
	}

	public void setIdCarretera(Integer idCarretera) {
		this.idCarretera = idCarretera;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
package dao;

/**
 * Tipoestructura entity. @author MyEclipse Persistence Tools
 */

public class Tipoestructura implements java.io.Serializable {

	// Fields

	private Integer idTipoEstructura = 0;
	private String nombre;

	// Constructors

	/** default constructor */
	public Tipoestructura() {
	}

	/** full constructor */
	public Tipoestructura(String nombre) {
		this.nombre = nombre;
	}

	// Property accessors

	public Integer getIdTipoEstructura() {
		return this.idTipoEstructura;
	}

	public void setIdTipoEstructura(Integer idTipoEstructura) {
		this.idTipoEstructura = idTipoEstructura;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
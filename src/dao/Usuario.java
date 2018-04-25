package dao;

/**
 * Usuario entity. @author MyEclipse Persistence Tools
 */

public class Usuario implements java.io.Serializable {

	// Fields

	private Integer idUsuario;
	private String usuario;
	private String contrasena;
	private String nombre;
	private Integer permisos;

	// Constructors

	/** default constructor */
	public Usuario() {
	}

	/** full constructor */
	public Usuario(String usuario, String contrasena, String nombre,
			Integer permisos) {
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.nombre = nombre;
		this.permisos = permisos;
	}

	// Property accessors

	public Integer getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return this.contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getPermisos() {
		return this.permisos;
	}

	public void setPermisos(Integer permisos) {
		this.permisos = permisos;
	}

}
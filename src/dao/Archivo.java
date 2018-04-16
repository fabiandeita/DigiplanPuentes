package dao;

/**
 * Carretera entity. @author MyEclipse Persistence Tools
 */

public class Archivo implements java.io.Serializable {

	// Fields

	private Integer idArchivo;
	private String archivo;
	private Documento documento;
	
	public Archivo() {
	}
	
	public Archivo(Integer idArchivo, String archivo, Documento documento) {
		super();
		this.idArchivo = idArchivo;
		this.archivo = archivo;
		this.documento = documento;
	}
	public Integer getIdArchivo() {
		return idArchivo;
	}
	public void setIdArchivo(Integer idArchivo) {
		this.idArchivo = idArchivo;
	}
	public String getArchivo() {
		return archivo;
	}
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
	public Documento getDocumento() {
		return documento;
	}
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	

}
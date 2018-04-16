package dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Documento entity. @author MyEclipse Persistence Tools
 */

public class Documento implements java.io.Serializable {

	// Fields

	private Integer idDocumento = 0;
	private Estado estado = new Estado();
	private Origen origen = new Origen();
	private Tipodocumento tipodocumento = new Tipodocumento();
	private Tramo tramo = new Tramo();
	private Proyecto proyecto = new Proyecto();
	private String kmInicial;
	private String kmFinal;
	private Date fechaCreacion;
	private String archivo = "";
	private String observaciones;
	private Short activo = 1;
	private Tipoestructura tipoEstructura = new Tipoestructura();
	private Set archivos = new HashSet(0);
	
	private boolean exportar = false;

	// Constructors

	/** default constructor */
	public Documento() {
	}

	/** full constructor */
	public Documento(Estado estado, Origen origen, Tipodocumento tipodocumento,
			Tramo tramo, Proyecto proyecto, String kmInicial, String kmFinal,
			Date fechaCreacion, String archivo, String observaciones,
			Short activo,Tipoestructura tipoEstructura, Set archivos) {
		this.estado = estado;
		this.origen = origen;
		this.tipodocumento = tipodocumento;
		this.tramo = tramo;
		this.proyecto = proyecto;
		this.kmInicial = kmInicial;
		this.kmFinal = kmFinal;
		this.fechaCreacion = fechaCreacion;
		this.archivo = archivo;
		this.observaciones = observaciones;
		this.activo = activo;
		this.tipoEstructura = tipoEstructura;
		this.archivos = archivos;
	}

	// Property accessors

	public Integer getIdDocumento() {
		return this.idDocumento;
	}

	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Origen getOrigen() {
		return this.origen;
	}

	public void setOrigen(Origen origen) {
		this.origen = origen;
	}

	public Tipodocumento getTipodocumento() {
		return this.tipodocumento;
	}

	public void setTipodocumento(Tipodocumento tipodocumento) {
		this.tipodocumento = tipodocumento;
	}

	public Tramo getTramo() {
		return this.tramo;
	}

	public void setTramo(Tramo tramo) {
		this.tramo = tramo;
	}

	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public String getKmInicial() {
		return this.kmInicial;
	}

	public void setKmInicial(String kmInicial) {
		this.kmInicial = kmInicial;
	}

	public String getKmFinal() {
		return this.kmFinal;
	}

	public void setKmFinal(String kmFinal) {
		this.kmFinal = kmFinal;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getArchivo() {
		return this.archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Short getActivo() {
		return this.activo;
	}

	public void setActivo(Short activo) {
		this.activo = activo;
	}

	public boolean isExportar() {
		return exportar;
	}

	public void setExportar(boolean exportar) {
		this.exportar = exportar;
	}

	public Tipoestructura getTipoEstructura() {
		return tipoEstructura;
	}

	public void setTipoEstructura(Tipoestructura tipoEstructura) {
		this.tipoEstructura = tipoEstructura;
	}

	public Set getArchivos() {
		return archivos;
	}

	public void setArchivos(Set archivos) {
		this.archivos = archivos;
	}

}
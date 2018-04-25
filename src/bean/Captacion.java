/**
 * 
 */
package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.icesoft.faces.component.ext.RowSelectorEvent;
import com.icesoft.faces.component.selectinputtext.SelectInputText;
import com.icesoft.faces.component.selectinputtext.TextChangeEvent;

import archivos.InputFileData;
import archivos.Uploader;
import dao.Archivo;
import dao.ArchivoDAO;
import dao.Documento;
import dao.DocumentoDAO;
import dao.Estado;
import dao.EstadoDAO;
import dao.Origen;
import dao.Tramo;
import dao.TramoDAO;

/**
 * @author Fabian De Ita
 *
 */
public class Captacion {
	private Documento doc;
	private DocumentoDAO docDAO = new DocumentoDAO();
	private Documento docAux;		
	
	private EstadoDAO estadoDAO = new EstadoDAO();
	
	private TramoDAO tramoDAO = new TramoDAO();
	private Catalogo catalogo = new Catalogo();
	private Uploader carp;
	private Validador validador = new Validador();
	
	
	public Uploader getCarp() {
		return carp;
	}


	public void setCarp(Uploader carp) {
		this.carp = carp;
	}


	private boolean popup = false;
	private boolean panelCesion = false;
	private boolean edited = false;
	
	
	private String msgEstado = "";
	private String msgNoProyecto = "";
	private String msgCarretera = "";
	private String msgTramo = "";
	private String msgProyecto = "";
	private String msgKmInicial = "";
	private String msgKmFinal = "";
	private String msgOrigen = "";
	private String msgFechaCreacion = "";
	private String msgTipoDocumento = "";
	private String msgTipoEstructura = "";
	private String msgArchivo = "";
	private String msgObservaciones = "";
	private String msgPopup = "";
	private String selectedTramo = "";
	private String selectedOrigen = "";
	private String msgTipoProyecto = "";
	private String to = "";
	public Captacion() {
		doc = new Documento();
		catalogo = new Catalogo();				
		carp = new Uploader();		
	}
	
	 public void selectInputValueChanged(TextChangeEvent  event) {
		 System.out.println(event.getComponent().getId());
		 if (event.getComponent().getId().equals("AutoCmpTxtTramo")){	        	        
			 String newWord = event.getNewValue().toString();
			 System.out.println(newWord);
			 catalogo.renderTramo(newWord);	        
		 }else if (event.getComponent().getId().equals("AutoCmpTxtOrigen")){
			 String newWord = event.getNewValue().toString();
			 System.out.println(newWord);
			 catalogo.renderOrigen(newWord);	   
		 }
	 }
	
	 
	public void rowSelectionListener(RowSelectorEvent event) {
		int i = event.getRow();
		
	}
	
	public void actionPerformed(ActionEvent evt) {
		//System.out.println("Action Listener");
		
	}
	public void init(){				
		//catalogo.fillCarretera(0);
		catalogo.fillTramo(0);
		selectedOrigen = "";
		selectedTramo = "";
		this.carp.removeFile();	
		//getDocumentoDAO().getSession().close();
		getDocumentoDAO().getSession().reconnect();
		getDocumentoDAO().getSession().flush();
	}
	public String ok(){
		popup = false;
		msgPopup = "";
		return to;
	}
	public String Test(){
		return "test";
	}
	
	public Tramo newTramo(String nombre){
		Tramo nuevo = new Tramo();
		nuevo.setNombre(nombre);	
		nuevo.setEstado(doc.getEstado());		
		catalogo.getTramoDAO().getSession().beginTransaction();
		catalogo.getTramoDAO().save(nuevo);
		catalogo.getTramoDAO().getSession().getTransaction().commit();
		catalogo.makeLista();
		return nuevo;
	}
		
	public void save(){
		try{
			to = "";
			if(validacion()){
				
				if(doc.getIdDocumento() == 0 ){
					guardar(doc, 0);
					doc = new Documento();				
					msgPopup = "El expediente ha sido guardado!";			
					popup = true;
					init();
				}					
				else{					
					
					guardar(doc, 1);
					msgPopup = "El expediente ha sido actualizado!";	
					popup = true;	
					to = "toBusqueda";
					FacesContext context = FacesContext.getCurrentInstance();
					Busqueda b = (Busqueda)context.getELContext().getELResolver().getValue(context.getELContext(), null, "busqueda");
					b.toBusqueda();													
				}												
			}
		}catch(Exception e){
			e.printStackTrace();			
		}
			
	}
	
	public void valueChangeListener(ValueChangeEvent event) throws Exception {
		int i = Integer.parseInt(event.getNewValue().toString());
		if (event.getComponent().getId().equals("tipoDocumento")){
			
			doc.getTipodocumento().setIdTipoDocumento(i);
		}
	}
	public void guardar(Documento documento, int update){
		if(doc.getIdDocumento() != 0 )
			documento = docDAO.findById(doc.getIdDocumento());
		//Asigna Estado
		documento.setEstado(catalogo.getEstadoDAO().findById(doc.getEstado().getIdEstado()));
		//Se asigna el Tramo
		documento.setTramo(catalogo.getTramoDAO().findById(doc.getTramo().getIdTramo()));
		//Se asigna el Origen				
		documento.setOrigen(catalogo.getOrigenDAO().findById(doc.getOrigen().getIdOrigen()));					
		
		documento.setTipodocumento(catalogo.getTdDAO().findById(doc.getTipodocumento().getIdTipoDocumento()));
		documento.setTipoEstructura(catalogo.getTipoEstructuraDAO().findById(doc.getTipoEstructura().getIdTipoEstructura()));
		
		documento.setFechaCreacion(doc.getFechaCreacion());
		documento.setKmFinal(doc.getKmFinal().replace(".", "+"));
		documento.setKmInicial(doc.getKmInicial().replace(".", "+"));
		documento.setProyecto(catalogo.getProyectoDAO().findById(doc.getProyecto().getIdProyecto()));
		documento.setObservaciones(doc.getObservaciones());
		
		/*//Configura el Uploader
		//carp.setPath(doc.getEstado().getNombre().toString() + "/" + doc.getCarretera().getNombre() + "/" + doc.getTramo().getNombre() + "/");				
		if(!doc.getArchivo().isEmpty() && carp.getFileList().isEmpty())
			documento.setArchivo(doc.getArchivo());
		else{
			carp.saveFile();
			documento.setArchivo(carp.getRuta() + carp.getArchivo());
		}
		*/
		//Salva el archivo de la Sesión							
		//System.out.println("Id: " + doc.getIdDocumento());
		ArchivoDAO archivoDAO = new ArchivoDAO();
		archivoDAO.getSession().beginTransaction();
		Iterator it = doc.getArchivos().iterator();
		while(it.hasNext()){
			Archivo a  = (Archivo)it.next();
			archivoDAO.delete(a);
		}
		archivoDAO.getSession().getTransaction().commit();
		archivoDAO.getSession().clear();
		archivoDAO.getSession().flush();
		
		docDAO.getSession().getTransaction().begin();
		if(update == 1)
			docDAO.getSession().update(documento);
		else
			docDAO.getSession().save(documento);
		
		docDAO.getSession().getTransaction().commit();
		
		for(InputFileData up : (List<InputFileData>)carp.getFileList()){
			archivoDAO.getSession().beginTransaction();
			Archivo archivo = new Archivo();
			archivo.setArchivo(up.getFile().getName());
			archivo.setDocumento(documento);
			archivoDAO.save(archivo);
			archivoDAO.getSession().getTransaction().commit();
		}
		
		
		docDAO.getSession().clear();
		docDAO.getSession().flush();
		//docDAO.getSession().close();
		docDAO = new DocumentoDAO();
		documento = null;
		documento = new Documento();
		doc = null;
		doc = new Documento();
		carp = new Uploader();
	}
	public Origen getOrigen(String nombreOrigen){
		List<Origen> posibleOrigen = catalogo.getOrigenDAO().findByNombre(nombreOrigen);
		if(!posibleOrigen.isEmpty()){
				return posibleOrigen.get(0);
		}else{
			Origen o = new Origen();
			o.setNombre(nombreOrigen);			
			catalogo.getOrigenDAO().getSession().getTransaction().begin();
			catalogo.getOrigenDAO().save(o);
			catalogo.getOrigenDAO().getSession().getTransaction().commit();
			return o;
		}
		
			
	}
	
	public void saveEdit(){
		try{
		
			//System.out.println("Actualizado: " + validacion());
			if(validacion()){
				
				
				//Salva el archivo de la MIA
				carp.setPath(doc.getEstado().getNombre().toString() + "/" );
				
				
				if(doc.getArchivo().isEmpty()){
					carp.saveFile();
					doc.setArchivo(carp.getRuta() + carp.getArchivo());
				}
				
							
				
				setPopup(true);
				msgPopup = "El expediente ha sido actualizado!";
				
			}
		
	}catch(Exception e){
		
	}
	}

	public void quitarArchivoResolutivo(){
		doc.setArchivo("");
	}
	
	public boolean validacion(){
		
		this.cleanMsg();
		/*if(this.doc.getProyecto().getNumProy().isEmpty())
			msgNoProyecto = "Especifique"; */
		if(this.doc.getEstado().getIdEstado() < 1)
			msgEstado = "Seleccione";	
		if(doc.getTramo().getCarretera().getIdCarretera() < 1)
			msgCarretera = "Seleccione";
		if(doc.getTramo().getIdTramo() < 1)
			msgTramo = "Seleccione";
		if(doc.getTipoEstructura().getIdTipoEstructura() < 1)
			msgTipoEstructura = "Seleccione";
		if(doc.getOrigen().getIdOrigen() < 1)
			msgOrigen = "Seleccione";
		if(doc.getKmInicial().isEmpty())
			msgKmInicial = "Especifique";
		//else
		//	if(!validador.validarCadenamiento(doc.getKmInicial()))
			//	msgKmInicial = "Utilize formato ###.##";
		
		if(doc.getKmFinal().isEmpty())
			msgKmFinal = "Especifique";
		//else
		//	if(!validador.validarCadenamiento(doc.getKmFinal()))
			//	msgKmFinal = "Utilize formato ###.##";
		
		if(doc.getProyecto().getIdProyecto() == 0)
			msgProyecto = "Especifique";
		if(doc.getTipodocumento().getIdTipoDocumento() < 1)
			msgTipoDocumento = "Seleccione";		
		if(doc.getFechaCreacion() == null && !isDate(doc.getFechaCreacion()))
			msgFechaCreacion = "Especifique";
		if(getCarp().getFileList().isEmpty())	
			if(carp.getFileList().isEmpty())
				msgArchivo = "Cargue el documento digital.";
		
		if(doc.getObservaciones().isEmpty())
			msgObservaciones = "Especifique";
		//System.out.println("No Expediente: " + carpeta.getNoExpediente());
		if(msgTipoProyecto.isEmpty() && msgEstado.isEmpty() &&  msgTramo.isEmpty() && msgOrigen.isEmpty() && msgKmInicial.isEmpty() && msgKmFinal.isEmpty()
				&& msgTipoDocumento.isEmpty() && msgArchivo.isEmpty() && msgObservaciones.isEmpty() && msgCarretera.isEmpty() && msgTipoEstructura.isEmpty())
			return true;
		else
			return false;
	}
	
	public boolean isDate(Date date){
		try{
			System.out.println(date.toString());
			date.getYear();
			date.getMonth();
			date.getDate();
			return true;
		}catch(Exception e){
			System.out.println("Error en casteo " + e.toString());
			return false;
		}
	}
	public void cleanMsg(){
		msgEstado = "";		
		msgCarretera = "";
		msgTramo = "";
		msgOrigen = "";
		msgKmInicial = "";
		msgKmFinal = "";
		msgProyecto = "";
		msgTipoDocumento = "";
		msgFechaCreacion = "";
		msgArchivo = "";
		msgObservaciones = "";
		msgTipoProyecto = "";
		msgTipoDocumento = "";
		msgTipoProyecto = "";
		msgCarretera ="";
		msgTipoEstructura ="";
	}

	
	




	public Catalogo getCatalogo() {
		return catalogo;
	}

	public DocumentoDAO getDocumentoDAO(){
		return docDAO;
	}

	public void setCatalogo(Catalogo catalogo) {
		this.catalogo = catalogo;
	}


	public String getMsgEstado() {
		return msgEstado;
	}


	public void setMsgEstado(String msgEstado) {
		this.msgEstado = msgEstado;
	}


	public String getMsgCarretera() {
		return msgCarretera;
	}


	public void setMsgCarretera(String msgCarretera) {
		this.msgCarretera = msgCarretera;
	}


	public String getMsgTramo() {
		return msgTramo;
	}


	public void setMsgTramo(String msgTramo) {
		this.msgTramo = msgTramo;
	}


	public String getMsgProyecto() {
		return msgProyecto;
	}


	public void setMsgProyecto(String msgProyecto) {
		this.msgProyecto = msgProyecto;
	}


	public String getMsgKmInicial() {
		return msgKmInicial;
	}


	public void setMsgKmInicial(String msgKmInicial) {
		this.msgKmInicial = msgKmInicial;
	}


	public String getMsgKmFinal() {
		return msgKmFinal;
	}


	public void setMsgKmFinal(String msgKmFinal) {
		this.msgKmFinal = msgKmFinal;
	}


	public String getMsgOrigen() {
		return msgOrigen;
	}


	public void setMsgOrigen(String msgOrigen) {
		this.msgOrigen = msgOrigen;
	}


	public String getMsgFechaCreacion() {
		return msgFechaCreacion;
	}


	public void setMsgFechaCreacion(String msgFechaCreacion) {
		this.msgFechaCreacion = msgFechaCreacion;
	}


	
	public String getMsgArchivo() {
		return msgArchivo;
	}


	public void setMsgArchivo(String msgArchivo) {
		this.msgArchivo = msgArchivo;
	}


	public String getMsgObservaciones() {
		return msgObservaciones;
	}


	public void setMsgObservaciones(String msgObservaciones) {
		this.msgObservaciones = msgObservaciones;
	}


	public String getMsgPopup() {
		return msgPopup;
	}


	public void setMsgPopup(String msgPopup) {
		this.msgPopup = msgPopup;
	}


	public Documento getDoc() {
		return doc;
	}


	public void setDoc(Documento doc) {
		this.doc = doc;
	}


	public String getMsgTipoDocumento() {
		return msgTipoDocumento;
	}


	public void setMsgTipoDocumento(String msgTipoDocumento) {
		this.msgTipoDocumento = msgTipoDocumento;
	}


	public String getSelectedTramo() {
		return selectedTramo;
	}


	public void setSelectedTramo(String selectedTramo) {
		this.selectedTramo = selectedTramo;
	}


	public String getSelectedOrigen() {
		return selectedOrigen;
	}


	public void setSelectedOrigen(String selectedOrigen) {
		this.selectedOrigen = selectedOrigen;
	}


	public boolean isPopup() {
		return popup;
	}


	public void setPopup(boolean popup) {
		this.popup = popup;
	}


	public String getMsgTipoProyecto() {
		return msgTipoProyecto;
	}


	public void setMsgTipoProyecto(String msgTipoProyecto) {
		this.msgTipoProyecto = msgTipoProyecto;
	}


	public String getMsgTipoEstructura() {
		return msgTipoEstructura;
	}


	public void setMsgTipoEstructura(String msgTipoEstructura) {
		this.msgTipoEstructura = msgTipoEstructura;
	}


	public String getMsgNoProyecto() {
		return msgNoProyecto;
	}


	public void setMsgNoProyecto(String msgNoProyecto) {
		this.msgNoProyecto = msgNoProyecto;
	}



	
	
}

/**
 * 
 */
package bean;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.print.Doc;
import javax.swing.JFileChooser;

import com.icesoft.faces.component.inputfile.FileInfo;
import com.icesoft.faces.component.selectinputtext.SelectInputText;
import com.icesoft.faces.webapp.xmlhttp.PersistentFacesState;

import archivos.InputFileData;
import dao.Archivo;
import dao.ArchivoDAO;
import dao.Documento;
import dao.Estado;
import dao.Origen;
import dao.Proyecto;
import dao.Tipodocumento;
import dao.Tipoestructura;
import dao.Tramo;


/**
 * @author Fabian De Ita
 *
 */
public class Busqueda {
	
	private List<Documento> lista = new ArrayList<Documento>();
	private Documento detalle = new Documento();
	private Catalogo catalogo = new Catalogo();
	private String msgEstado = "";
	private String msgCarretera = "";
	private String msgTramo = "";
	private String msgNombre = "";
	private Documento edit = new Documento();
	
	private int tramoSelected = 0;
	private String query = "";
	private String queryAux = "";
	private Captacion captacion = new Captacion();
	private boolean panelCollapsible = false;
	private boolean panelCollapsible2 = true;
	private boolean panelCollapsible3 = false;
	
	private List<SelectItem> proyectoListMatch = new ArrayList();
	private List<SelectItem> noProyectoListMatch = new ArrayList();
	private List<SelectItem>  tramoListMatch = new ArrayList();
	private List<SelectItem>  carreteraListMatch = new ArrayList();
	private List<SelectItem>  tipoEstructuraListMatch = new ArrayList();
	private List<SelectItem>  origenListMatch = new ArrayList();
	private List<SelectItem>  TDListMatch = new ArrayList();
	
	private String selectedProyectoValue = "";
	private String selectedNoProyectoValue = "";
	private String selectedTramoValue = "";
	private String selectedCarreteraValue = "";
	private String selectedOrigenValue = "";
	private String selectedTDValue = "";
	private String selectedTEValue = "";
	
	private String newNoProyecto= "";
	private String newProyecto= "";
	private String newCarretera = "";
	private String newTramo = "";
	private String newTD = "";
	private String newTE = "";
	private String newOrigen = "";
	
	private Exportar exportar = new Exportar();
	private String rutaExport = "";
	private boolean mostrarPopup = false;
	private boolean select = false;
	private String busquedaConicidencia = "";
	
	public Busqueda() {				
	}
	
	public void changePanelCollapsible(ActionEvent event) throws Exception {
		panelCollapsible2 = false;
		panelCollapsible = true;
		panelCollapsible3 = false;
		limpiarBusqueda();
		clearAll();
	}
	public void changePanelCollapsible2(ActionEvent event) throws Exception {
		panelCollapsible2 = true;
		panelCollapsible = false;
		panelCollapsible3 = false;
		limpiarBusqueda();
		clearAll();
	}
	
	public void changePanelCollapsible3(ActionEvent event) throws Exception {
		
		panelCollapsible2 = false;
		panelCollapsible = false;
		panelCollapsible3 = true;
		limpiarBusqueda();
		clearAll();
	}
	
	public void selectAll(){
		for(Documento doc : (List<Documento>)lista){
			doc.setExportar(true);
		}
		select = true;
	}
	
	
	public void deselectAll(){
		for(Documento doc : (List<Documento>)lista){
			doc.setExportar(false);
		}
		select = false;
	}
	
	
	public void exportar(){
		boolean mostrar = true;
		for(Documento doc : (List<Documento>)lista){
			if(doc.isExportar()){
				mostrar = false;
			}
			
		}
		
		if(!mostrar){
			String rutaAbsoluta = getDate();
			
			File carpetaDestino = new File("c:\\Busqueda\\" + rutaAbsoluta + "\\");
			if(!carpetaDestino.exists())
				carpetaDestino.mkdirs();
			carpetaDestino = null;
			
			Excel excel = new Excel();
			excel.setRutaAbsoluta(rutaAbsoluta);
			excel.setLista(lista);
			excel.setInputFile("c:\\Busqueda\\" + rutaAbsoluta + "\\Reporte.xls");
			excel.writeExcelJurisdiccion();
			
			//Archivos
			exportar.setPathDestino("c:\\Busqueda\\" + rutaAbsoluta + "\\");
			
			exportar.setPathOrigen("c:\\Servidor\\webapps\\DIGIPLANPUENTES\\resources\\");
			exportar.setLista(lista);
			exportar.exportar();
		
		}else{
			setMostrarPopup(mostrar);
		}
		
		
	}
	public void cerrarPopup(){
		setMostrarPopup(false);
	}
	public String getDate(){
		Calendar currDtCal = Calendar.getInstance();
		Date actual = currDtCal.getTime();
        //Mostramos por pantalla la hora actual y la introducida manualmente
        //System.out.println("La hora actual es: "+actual);
       return Integer.toString(actual.getHours()) + "-" + Integer.toString(actual.getMinutes()) + "-" + Integer.toString(actual.getSeconds());
	}
	
	@SuppressWarnings("unchecked")
	public void buscarCoincidencia(){
		select = false;
		deselectAll();
		String query = "where model.proyecto.numProy  like '%"+busquedaConicidencia+"%' or model.estado.nombre like '%"+busquedaConicidencia+"%' or model.tramo.carretera.nombre   like '%"+busquedaConicidencia+"%' " +
				"or model.tramo.nombre like '%"+busquedaConicidencia+"%' or model.tipoEstructura.nombre  like '%"+busquedaConicidencia+"%' " +
				"or model.origen.nombre like '%"+busquedaConicidencia+"%' or model.proyecto.nombre like '%"+busquedaConicidencia+"%' " +
				"or model.tipodocumento.nombre like '%"+busquedaConicidencia+"%' or model.observaciones like '%"+busquedaConicidencia+"%'" +
				"and model.activo =  1  order by  model.proyecto.nombre,  model.tramo, model.kmInicial ";
		System.out.println(query);
		if(!busquedaConicidencia.isEmpty())
			setLista(captacion.getDocumentoDAO().findByQuery(query));
		else
			setLista(captacion.getDocumentoDAO().findAll());
		
		/*try {
			Integer
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		
	}
	
	@SuppressWarnings("unchecked")
	public void buscar(){
		lista.clear();
		edit = null;
		edit = new Documento();
		captacion.getDocumentoDAO().getSession().clear();
		captacion.getDocumentoDAO().getSession().flush();
		select = false;
		deselectAll();
		query = "";
		if(detalle.getEstado().getIdEstado() > 0)
			query += "where model.estado.idEstado = " + detalle.getEstado().getIdEstado();
		
		if(detalle.getTramo().getCarretera().getIdCarretera() > 0)		
			query += " and model.tramo.carretera.idCarretera = " + detalle.getTramo().getCarretera().getIdCarretera();
		
		if(detalle.getTramo().getIdTramo() > 0)							
				query += " and model.tramo.idTramo = " + detalle.getTramo().getIdTramo();
		
		if(detalle.getOrigen().getIdOrigen() > 0)	
			if(!query.isEmpty())
				query += " and model.origen.idOrigen =  " + detalle.getOrigen().getIdOrigen();
			else
				query += " where  model.origen.idOrigen =  " + detalle.getOrigen().getIdOrigen();
		
		if(detalle.getProyecto().getIdProyecto() > 0)
			if(!query.isEmpty())
				query += " and model.proyecto.idProyecto =" + detalle.getProyecto().getIdProyecto();
			else
				query += " where model.proyecto.idProyecto = " + detalle.getProyecto().getIdProyecto();
		if(detalle.getTipoEstructura().getIdTipoEstructura() > 0)		
			query += " and model.tipoEstructura.idTipoEstructura = " + detalle.getTipoEstructura().getIdTipoEstructura();
		if(detalle.getTipodocumento().getIdTipoDocumento() > 0)
			if(!query.isEmpty())
				query += " and model.tipodocumento.idTipoDocumento =  " + detalle.getTipodocumento().getIdTipoDocumento();
			else
				query += " where  model.tipodocumento.idTipoDocumento =  " + detalle.getTipodocumento().getIdTipoDocumento() ;
		
		if(!query.isEmpty())
			query += " and model.activo =  1 order by  model.proyecto.nombre,  model.tramo, model.kmInicial";
		else
			query += " where  model.activo =  1  order by  model.proyecto.nombre,  model.tramo, model.kmInicial";
		
		
		
		queryAux = query;
		/*System.out.println("Estado: " + ubi.getEstado().getIdEstado());
		System.out.println("Carretera: " + ubi.getCarretera().getIdCarretera());
		System.out.println("Tramo: " + ubi.getTramo().getIdTramo());*/
		System.out.println("Query: " + query);
		if(!query.isEmpty())
			setLista(captacion.getDocumentoDAO().findByQuery(query));
		else
			setLista(captacion.getDocumentoDAO().findAll());
	}

	public void clearAll(){
		lista.clear();
		selectedProyectoValue = "";
		selectedTramoValue = "";
		selectedOrigenValue = "";
		selectedTDValue = "";
		selectedCarreteraValue = "";
		selectedTEValue ="";
		busquedaConicidencia = "";
		newProyecto = "";
		newTramo = "";
		newOrigen = "";
		newTD = "";
		newTE = "";
		newCarretera ="";
		

	}
	public String queryBuilder(){
		String query = "" ;
		if(newProyecto.length() > 2)
			query = " and documento.proyecto.nombre like '%" + newProyecto + "%' ";
		if(newCarretera.length() > 2)
			query += " and documento.tramo.carretera.nombre like '%" + newCarretera + "%' ";
		if(newTramo.length() > 2)
			query += " and documento.tramo.nombre like '%" + newTramo + "%' ";
		if(newTD.length() > 2)
			query += " and documento.tipodocumento.nombre like '%" + newTD + "%' ";
		if(newOrigen.length() > 2)
			query += " and documento.origen.nombre like '%" + newOrigen + "%' ";
		if(newTE.length() > 2)
			query += " and documento.tipoEstructura.nombre like '%" + newTE + "%' ";
		if(newNoProyecto.length() >= 1)
			query += " and documento.proyecto.numProy like '%" + newNoProyecto + "%' ";
		
		return query;
	}
	
	
	
	public void selectInputValueChanged(ValueChangeEvent event) {
		select = false;
		deselectAll();
		
		if (event.getComponent().getId().equals("AutoCmpNoProyecto")){
	        if (event.getComponent() instanceof SelectInputText) {
	             SelectInputText autoComplete = (SelectInputText) event.getComponent();
	             newNoProyecto = (String) event.getNewValue();
	             
	             if(newNoProyecto.length() > 2){
	            	 noProyectoListMatch.clear();
	            	 //List<Documento> lisdoc = catalogo.getDocumentoDAO().findBynoProyecto(queryBuilder());
	            	 int i = 0;
	            	 for(String d : (List<String>)catalogo.getProyectoDAO().findByCoincidencia(queryBuilder()))
	            		 noProyectoListMatch.add(new SelectItem(i, d));	   
	            	 i++;
	            }             
	            
	                 lista = captacion.getDocumentoDAO().findByQuerySmart(queryBuilder() +  " order by  documento.proyecto.nombre,  documento.tramo, documento.kmInicial");
	             	           
	        }
		}
		if (event.getComponent().getId().equals("AutoCmpProyecto")){
	        if (event.getComponent() instanceof SelectInputText) {
	             SelectInputText autoComplete = (SelectInputText) event.getComponent();
	             newProyecto = (String) event.getNewValue();
	             
	             if(newProyecto.length() > 2){
	            	 proyectoListMatch.clear();
	            	 for(Proyecto d : (List<Proyecto>)catalogo.getProyectoDAO().findByCoincidenceQuery(queryBuilder()))
	            		 proyectoListMatch.add(new SelectItem(d, d.getNombre()));	            	 
	            }             
	            
	                 lista = captacion.getDocumentoDAO().findByQuerySmart(queryBuilder() +  " order by  documento.proyecto.nombre,  documento.tramo, documento.kmInicial");
	             	           
	        }
		}else if (event.getComponent().getId().equals("AutoCmpCarretera")){
			SelectInputText autoComplete = (SelectInputText) event.getComponent();
			
			selectedProyectoValue = "";
			selectedTDValue = "";
			selectedOrigenValue  = "";
			
			if (event.getComponent() instanceof SelectInputText) {				 	          
				newCarretera = (String) event.getNewValue();	             
	             if(newCarretera.length() > 2){
	            	 carreteraListMatch.clear();
	            	 for(Tramo d : (List<Tramo>)catalogo.getTramoDAO().findByCoincidenceQuery(queryBuilder())){
	            		 carreteraListMatch.add(new SelectItem(d.getCarretera().getIdCarretera(), d.getCarretera().getNombre()));
	            	 }
	             }
	            
	                
	                 lista = captacion.getDocumentoDAO().findByQuerySmart(queryBuilder() +  " order by  documento.proyecto.nombre,  documento.tramo, documento.kmInicial");
	                
			}			
		}else if (event.getComponent().getId().equals("AutoCmpTramo")){
			SelectInputText autoComplete = (SelectInputText) event.getComponent();
			
			selectedProyectoValue = "";
			selectedTDValue = "";
			selectedOrigenValue  = "";
			
			if (event.getComponent() instanceof SelectInputText) {				 	          
				newTramo = (String) event.getNewValue();	             
	             if(newTramo.length() >= 1){
	            	 tramoListMatch.clear();
	            	 for(Tramo d : (List<Tramo>)catalogo.getTramoDAO().findByCoincidenceQuery(queryBuilder())){
	            		 tramoListMatch.add(new SelectItem(d, d.getNombre()));
	            	 }
	             }
	            
	                
	                 lista = captacion.getDocumentoDAO().findByQuerySmart(queryBuilder() +  " order by  documento.proyecto.nombre,  documento.tramo, documento.kmInicial");
	                
			}			
		}else if (event.getComponent().getId().equals("AutoCmpTD")){
			SelectInputText autoComplete = (SelectInputText) event.getComponent();
			selectedTramoValue= "";
			selectedOrigenValue = "";
			selectedProyectoValue  = "";
			if (event.getComponent() instanceof SelectInputText) {				 	          
				newTD = (String) event.getNewValue();	             
	             if(newTD.length() > 2){
	            	 TDListMatch.clear();
	            	 for(Tipodocumento d : (List<Tipodocumento>)catalogo.getTdDAO().findByCoincidenceQuery(queryBuilder())){
	            		 TDListMatch.add(new SelectItem(d, d.getNombre()));
	            	 }
	             }
			}
			
               
                lista = captacion.getDocumentoDAO().findByQuerySmart(queryBuilder() +  " order by  documento.proyecto.nombre,  documento.tramo, documento.kmInicial");              
           	   
           
		}else if (event.getComponent().getId().equals("AutoCmpTE")){
			SelectInputText autoComplete = (SelectInputText) event.getComponent();
			selectedTramoValue= "";
			selectedOrigenValue = "";
			selectedProyectoValue  = "";
			selectedCarreteraValue = "";
			if (event.getComponent() instanceof SelectInputText) {				 	          
				newTE = (String) event.getNewValue();	             
	             if(newTE.length() > 2){
	            	 tipoEstructuraListMatch.clear();
	            	 for(Tipoestructura d : (List<Tipoestructura>)catalogo.getTipoEstructuraDAO().findByCoincidenceQuery(queryBuilder())){
	            		 tipoEstructuraListMatch.add(new SelectItem(d, d.getNombre()));
	            	 }
	             }
			}
			
               
                lista = captacion.getDocumentoDAO().findByQuerySmart(queryBuilder() +  " order by  documento.proyecto.nombre,  documento.tramo, documento.kmInicial");              
           	   
           
		}else if (event.getComponent().getId().equals("AutoCmpOrigen")){
			selectedTramoValue= "";
			selectedTDValue = "";
			selectedProyectoValue  = "";
			SelectInputText autoComplete = (SelectInputText) event.getComponent();
			if (event.getComponent() instanceof SelectInputText) {				 	          
				newOrigen = (String) event.getNewValue();
	             if(newOrigen.length() > 2){
	            	 origenListMatch.clear();
	            	 for(Origen d : (List<Origen>)catalogo.getOrigenDAO().findByCoincidenceQuery(queryBuilder())){
	            		 origenListMatch.add(new SelectItem(d, d.getNombre()));
	            	 }
	             }
	             
	                 lista = captacion.getDocumentoDAO().findByQuerySmart(queryBuilder() +  " order by  documento.proyecto.nombre,  documento.tramo, documento.kmInicial");	                
	            	
			}
		}
     }
	
	public String limpiarBusqueda(){
		catalogo.fillCarreteraByProyecto(0, 0);
		catalogo.fillTipoEstructura(0,0);
		catalogo.loadProyectos(0);
		catalogo.fillTramoByProyecto(0,0,0);
		catalogo.fillOrigenByTramo(0);
		catalogo.fillTD("", null);
		detalle.setEstado(new Estado());
		
		lista.clear();
		return "";
	}
	
	public String limpiarBusquedaListado(){
		catalogo.loadProyectos(0);
		catalogo.fillCarreteraByProyecto(0, 0);
		catalogo.fillTipoEstructura(0,0);
		catalogo.fillTramoByProyecto(0,0,0);
		catalogo.fillOrigenByTramo(0);
		catalogo.fillTD("", null);
		detalle.setEstado(new Estado());
		lista.clear();
		getProyectoListMatch().clear();
		getTramoListMatch().clear();
		getOrigenListMatch().clear();
		getTDListMatch().clear();
		return "";
	}
	public String toDetalle(){	
		
		
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		String idDocumento = (String) map.get("idDocumento");
		
		
		lista.clear();
		captacion.getDocumentoDAO().getSession().clear();
		captacion.getDocumentoDAO().getSession().flush();
		//captacion.getDocumentoDAO().getSession().close();
		
		//edit.setIdDocumento(Integer.parseInt(idDocumento));
		//lista.clear();
		//Se envia el Documento actual a captacion para ser editado 
		captacion.setDoc(captacion.getDocumentoDAO().findById(Integer.parseInt(idDocumento)));
		
		ArchivoDAO archivoDAO = new ArchivoDAO();
		List lista = archivoDAO.findByIdDocumento(captacion.getDoc().getIdDocumento());
		for(Archivo archivo : (List<Archivo>)lista){
			FileInfo fileInfo = new FileInfo();
			fileInfo.setFile(new File(archivo.getArchivo()));
			fileInfo.setFileName(archivo.getArchivo());
			InputFileData currentFile = new InputFileData(fileInfo);  
			captacion.getCarp().getFileList().add(currentFile);
		}
		//Se asigna el Proyecto
		catalogo.loadProyectos(captacion.getDoc().getEstado().getIdEstado());		
		
		//Se obtienen las carreteras
		catalogo.fillCarreteraByProyecto(captacion.getDoc().getEstado().getIdEstado(), captacion.getDoc().getProyecto().getIdProyecto());
		
		//Se obtienen los tramos
		catalogo.fillTramoByProyecto(captacion.getDoc().getEstado().getIdEstado(), captacion.getDoc().getProyecto().getIdProyecto(),captacion.getDoc().getTramo().getCarretera().getIdCarretera());
		
		
		//Tipo Estructura
		catalogo.fillTipoEstructura(captacion.getDoc().getTramo().getIdTramo(), captacion.getDoc().getProyecto().getIdProyecto());
		
		//Se obtienen los Origenes
		catalogo.fillOrigenByTramo(captacion.getDoc().getTramo().getIdTramo());
		captacion.getCarp().setArchivo(captacion.getDoc().getArchivo());
		
		//Se obtienen los Tipos de Documento
		catalogo.fillTD("origen", captacion.getDoc().getOrigen());
		captacion.getCarp().setArchivo(captacion.getDoc().getArchivo());
		
		edit = null;
		edit = new Documento();
		return "toDetalle";
	}
	public String toBusqueda(){
		//catalogo.fillTDByTramo(captacion.getDoc().getProyecto().getIdProyecto(), captacion.getDoc().getTramo().getIdTramo());
		edit = null;
		edit = new Documento();
		captacion.setDoc(null);
		captacion.setDoc(new Documento());
		this.clearAll();
		//captacion.getDocumentoDAO().getSession().close();
		//captacion.getDocumentoDAO().getSession().reconnect();
		captacion.getDocumentoDAO().getSession().flush();
		
		
		return "toBusqueda";
	}
	

	public Catalogo getCatalogo() {
		return catalogo;
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

	public String getMsgNombre() {
		return msgNombre;
	}

	public void setMsgNombre(String msgNombre) {
		this.msgNombre = msgNombre;
	}	

	

	public int getTramoSelected() {
		return tramoSelected;
	}

	public void setTramoSelected(int tramoSelected) {
		this.tramoSelected = tramoSelected;
	}

	public Captacion getCaptacion() {
		return captacion;
	}

	public void setCaptacion(Captacion captacion) {
		this.captacion = captacion;
	}

	public Documento getDetalle() {
		return detalle;
	}

	public void setDetalle(Documento detalle) {
		this.detalle = detalle;
	}

	public List<Documento> getLista() {
		return lista;
	}

	public void setLista(List<Documento> lista) {
		this.lista = lista;
	}

	public boolean isPanelCollapsible() {
		return panelCollapsible;
	}

	public void setPanelCollapsible(boolean panelCollapsible) {
		this.panelCollapsible = panelCollapsible;
	}
	
	public List<SelectItem> getTramoListMatch() {
		return tramoListMatch;
	}

	public void setTramoListMatch(List<SelectItem> tramoListMatch) {
		this.tramoListMatch = tramoListMatch;
	}

	public List<SelectItem> getOrigenListMatch() {
		return origenListMatch;
	}

	public void setOrigenListMatch(List<SelectItem> origenListMatch) {
		this.origenListMatch = origenListMatch;
	}

	public List<SelectItem> getTDListMatch() {
		return TDListMatch;
	}

	public void setTDListMatch(List<SelectItem> tDListMatch) {
		TDListMatch = tDListMatch;
	}
	
	public List<SelectItem> getProyectoListMatch() {
		return proyectoListMatch;
	}

	public void setProyectoListMatch(List<SelectItem> proyectoListMatch) {
		this.proyectoListMatch = proyectoListMatch;
	}

	public String getSelectedProyectoValue() {
		return selectedProyectoValue;
	}

	public void setSelectedProyectoValue(String selectedProyectoValue) {
		this.selectedProyectoValue = selectedProyectoValue;
	}

	public String getSelectedTramoValue() {
		return selectedTramoValue;
	}

	public void setSelectedTramoValue(String selectedTramoValue) {
		this.selectedTramoValue = selectedTramoValue;
	}

	public String getSelectedOrigenValue() {
		return selectedOrigenValue;
	}

	public void setSelectedOrigenValue(String selectedOrigenValue) {
		this.selectedOrigenValue = selectedOrigenValue;
	}

	public String getSelectedTDValue() {
		return selectedTDValue;
	}

	public void setSelectedTDValue(String selectedTDValue) {
		this.selectedTDValue = selectedTDValue;
	}

	public boolean isPanelCollapsible2() {
		return panelCollapsible2;
	}

	public void setPanelCollapsible2(boolean panelCollapsible2) {
		this.panelCollapsible2 = panelCollapsible2;
	}

	public Exportar getExportar() {
		return exportar;
	}

	public void setExportar(Exportar exportar) {
		this.exportar = exportar;
	}

	public String getRutaExport() {
		return rutaExport;
	}

	public void setRutaExport(String rutaExport) {
		this.rutaExport = rutaExport;
	}

	public boolean isMostrarPopup() {
		return mostrarPopup;
	}

	public void setMostrarPopup(boolean mostrarPopup) {
		this.mostrarPopup = mostrarPopup;
	}

	public List<SelectItem> getCarreteraListMatch() {
		return carreteraListMatch;
	}

	public void setCarreteraListMatch(List<SelectItem> carreteraListMatch) {
		this.carreteraListMatch = carreteraListMatch;
	}

	public List<SelectItem> getTipoEstructuraListMatch() {
		return tipoEstructuraListMatch;
	}

	public void setTipoEstructuraListMatch(List<SelectItem> tipoEstructuraListMatch) {
		this.tipoEstructuraListMatch = tipoEstructuraListMatch;
	}

	public String getSelectedCarreteraValue() {
		return selectedCarreteraValue;
	}

	public void setSelectedCarreteraValue(String selectedCarreteraValue) {
		this.selectedCarreteraValue = selectedCarreteraValue;
	}

	public String getSelectedTEValue() {
		return selectedTEValue;
	}

	public void setSelectedTEValue(String selectedTEValue) {
		this.selectedTEValue = selectedTEValue;
	}

	public String getNewTE() {
		return newTE;
	}

	public void setNewTE(String newTE) {
		this.newTE = newTE;
	}

	public String getNewCarretera() {
		return newCarretera;
	}

	public void setNewCarretera(String newCarretera) {
		this.newCarretera = newCarretera;
	}

	public boolean getSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public boolean isPanelCollapsible3() {
		return panelCollapsible3;
	}

	public void setPanelCollapsible3(boolean panelCollapsible3) {
		this.panelCollapsible3 = panelCollapsible3;
	}

	public String getBusquedaConicidencia() {
		return busquedaConicidencia;
	}

	public void setBusquedaConicidencia(String busquedaConicidencia) {
		this.busquedaConicidencia = busquedaConicidencia;
	}

	public String getSelectedNoProyectoValue() {
		return selectedNoProyectoValue;
	}

	public void setSelectedNoProyectoValue(String selectedNoProyectoValue) {
		this.selectedNoProyectoValue = selectedNoProyectoValue;
	}

	public List<SelectItem> getNoProyectoListMatch() {
		return noProyectoListMatch;
	}

	public void setNoProyectoListMatch(List<SelectItem> noProyectoListMatch) {
		this.noProyectoListMatch = noProyectoListMatch;
	}

	public String getNewNoProyecto() {
		return newNoProyecto;
	}

	public void setNewNoProyecto(String newNoProyecto) {
		this.newNoProyecto = newNoProyecto;
	}

}

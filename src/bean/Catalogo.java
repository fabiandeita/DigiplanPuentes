package bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import com.icesoft.faces.component.ext.RowSelectorEvent;
import com.icesoft.faces.component.selectinputtext.SelectInputText;


import dao.CarreteraDAO;
import dao.Documento;
import dao.DocumentoDAO;
import dao.Estado;
import dao.EstadoDAO;
import dao.Origen;
import dao.OrigenDAO;
import dao.Proyecto;
import dao.ProyectoDAO;
import dao.Tipodocumento;
import dao.TipodocumentoDAO;
import dao.Tipoestructura;
import dao.TipoestructuraDAO;

import dao.Tramo;
import dao.TramoDAO;


public class Catalogo{
	private EstadoDAO estadoDAO = new EstadoDAO();
	private TramoDAO tramoDAO = new TramoDAO();
	private CarreteraDAO carreteraDAO = new CarreteraDAO();
	private Documento documento = new Documento();
	private DocumentoDAO documentoDAO = new DocumentoDAO();
	private OrigenDAO origenDAO = new OrigenDAO();
	private TipoestructuraDAO tipoEstructuraDAO = new TipoestructuraDAO();
	private ProyectoDAO proyectoDAO = new ProyectoDAO();
	private TipodocumentoDAO tdDAO = new TipodocumentoDAO();	
	private List<SelectItem> estadosList = new ArrayList<SelectItem>();
	private List<SelectItem> carreteraList = new ArrayList<SelectItem>();
	private List<SelectItem> carreteraListB = new ArrayList<SelectItem>();
	private List<SelectItem> tramosList = new ArrayList<SelectItem>();
	private List<SelectItem> carreterasList = new ArrayList<SelectItem>();
	private List<SelectItem> tramosListC = new ArrayList<SelectItem>();
	private List<SelectItem> tramosListB = new ArrayList<SelectItem>();
	private List<SelectItem> tramosFija = new ArrayList<SelectItem>();
	
	private List<SelectItem> tramosNames = new ArrayList<SelectItem>();
	private List<SelectItem> tramosNamesAux = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaOrigen = new ArrayList<SelectItem>();
	private List<SelectItem> origenList = new ArrayList<SelectItem>();
	private List<SelectItem> tipoEstructuraList = new ArrayList<SelectItem>();
	private List<SelectItem> listaTP = new ArrayList<SelectItem>();
	private List<SelectItem> listaTD = new ArrayList<SelectItem>();
	private List<SelectItem> tipoDocumentoList = new ArrayList<SelectItem>();
	private List<SelectItem> origenesNames = new ArrayList<SelectItem>();
	private List<SelectItem> origenesNamesAux = new ArrayList<SelectItem>();
	private List<SelectItem> listaProyecto = new ArrayList<SelectItem>();
	private List<SelectItem> proyectoList = new ArrayList<SelectItem>();
	private List<Proyecto> proyectoListC = new ArrayList<Proyecto>();
	
	private List<SelectItem> tipoList = new ArrayList<SelectItem>();
	private Tramo tramo = new Tramo();
	private Estado estado = new Estado();
	
	private Tramo currentTramo = new Tramo();
	private Tipoestructura currentTipoEstructura = new Tipoestructura();
	private Validador validador = new Validador();
	private boolean modalRendered = false;
	private String errores = "";
	
	private Origen currentOrigen = new Origen();
	private Proyecto currentProyecto = new Proyecto();
	private Tipodocumento currentTipoDocumento = new Tipodocumento();
	public String sustituirO = "";
	public String sustitutoO = "";
	public String msgSustituirO = "";
	public String msgSustitutoO = "";
	
	public String sustituirP = "";
	public String sustitutoP = "";
	public String msgSustituirP = "";
	public String msgSustitutoP = "";
	
	public String sustituirT = "";
	

	public String sustitutoT = "";
	public String msgSustituirT = "";
	public String msgSustitutoT = "";
	
	private int idProyecto = 0;
	private int idTramo = 0;
	private int idCarretera = 0;
	private int idTipoDoc = 0;
	private int idTipoEst = 0;
	
	
	
	public Catalogo(){	
		init();
	}

	public void init(){
		
		//this.carpeta = carpeta;
		loadEstados();
		estado.setIdEstado(0);
		//fillCarretera(estado.getIdEstado());
		//fillCarreteraB();
		fillTD("", null);
		//fillTramo(0);
		tramosList.add(new SelectItem(0, "Seleccione"));	
		carreterasList.add(new SelectItem(0, "Selecciona"));	
		tipoEstructuraList.add(new SelectItem(0,"Selecciona"));
		//makeLista();
		loadProyectos(0);
		fillOrigenByTramo(0);
	
		
	}
	
	public void limpiar(){
		//this.carpeta = carpeta;
		//loadEstados();
		//estado.setIdEstado(0);
		//fillCarretera(estado.getIdEstado());
		tramosList.clear();
		carreteraList.clear();
		
		
	}
	
	public void replaceOrigen(){
		origenList.clear();
		if(sustituirO.isEmpty())
			msgSustituirO = "Especifique";
		else
			msgSustituirO = "";
		
	
		
		if(msgSustituirO.isEmpty()){
			origenDAO.getSession().getTransaction().begin();
			for(Origen o : (List<Origen>)origenDAO.findAll()){
				o.setNombre(o.getNombre().replace(sustituirO, sustitutoO));
				origenDAO.getSession().update(o);
			}
			origenDAO.getSession().getTransaction().commit();								
		}
		msgSustitutoO = "";
		makeLista();
	}
	
	public void replaceProyecto(){
		proyectoList.clear();
		if(sustituirP.isEmpty())
			msgSustituirP = "Especifique";
		else
			msgSustituirP = "";
				
		
		if(msgSustituirP.isEmpty()){
			proyectoDAO.getSession().getTransaction().begin();
			for(Proyecto p : (List<Proyecto>)proyectoDAO.findAll()){
				p.setNombre(p.getNombre().replace(sustituirP, sustitutoP));
				proyectoDAO.getSession().update(p);
			}
			proyectoDAO.getSession().getTransaction().commit();								
		}
		loadProyectos(0);
	}
	
	public void replaceTramo(){
		tramosList.clear();
		if(sustituirT.isEmpty())
			msgSustituirT = "Especifique";
		else
			msgSustituirT = "";

		if(msgSustituirT.isEmpty()){
			tramoDAO.getSession().getTransaction().begin();
			for(Tramo t : (List<Tramo>)tramoDAO.findByIdEstado(currentTramo.getEstado().getIdEstado())){
				t.setNombre(t.getNombre().replace(sustituirT, sustitutoT));
				tramoDAO.getSession().update(t);
			}
			tramoDAO.getSession().getTransaction().commit();								
		}		
		fillTramo(currentTramo.getEstado().getIdEstado());
	}
	
	
	
	public void aceptar(){
		setErrores("");
		modalRendered = false;
	}
	
	public void newTramo(){
		currentTramo = new Tramo(); 
	}
	
	public void newOrigen(){
		currentOrigen = new Origen(); 
	}
	
	public void newProyecto(){
		currentProyecto = new Proyecto(); 
	}
	
	public void newTipoDocumento(){
		currentTipoDocumento = new Tipodocumento(); 
	}
	
	public void agregarTramo(){
		int idEstado = currentTramo.getEstado().getIdEstado();
		if(validador.validarNombre(currentTramo.getNombre())){
			if(currentTramo.getIdTramo() == 0){//Es nuevo
				if(!exisTramo(currentTramo)){
					tramoDAO.getSession().beginTransaction();
					currentTramo.setEstado(estadoDAO.findById(idEstado));
					tramoDAO.getSession().save(currentTramo);
					tramoDAO.getSession().getTransaction().commit();
					setErrores("Se agreg� el Tramo con �xito.");
				}else{
					setErrores("El Tramo que se intenta agregar ya existe.");
				}
			}else{//No es nuevo
				if(exisTramo(currentTramo)){
					setErrores("El Tramo que se intenta agregar ya existe.");
				}else{
					tramoDAO.getSession().beginTransaction();
					String nombre = currentTramo.getNombre();
					currentTramo = tramoDAO.findById(currentTramo.getIdTramo());
					currentTramo.setEstado(estadoDAO.findById(idEstado));
					currentTramo.setNombre(nombre);
					tramoDAO.getSession().update(currentTramo);
					tramoDAO.getSession().getTransaction().commit();
					setErrores("Se actualiz� el Tramo con �xito.");
				}
			}
		}else{
			setErrores("Especifique el Tramo que se intenta agregar.");
		}
		currentTramo = new Tramo();
		currentTramo.getEstado().setIdEstado(idEstado);
		fillTramoCatalogo(idEstado);
		this.fillTramo(idEstado);
		modalRendered = true;
	}
	
	
	public boolean exisTramo(Tramo currentTramo){
		List<Tramo> listaAux = tramoDAO.find(currentTramo.getEstado().getIdEstado(), currentTramo.getNombre());
		for(Tramo t : listaAux){
			if(t.getIdTramo() == currentTramo.getIdTramo())
				return false;
			else
				return true;
		}
		return false;
	}
	public void agregarProyecto(){		
		if(validador.validarNombre(currentProyecto.getNombre())){
			if(currentProyecto.getIdProyecto() == 0){//Es nuevo
				if(!existProyecto(currentProyecto)){
					proyectoDAO.getSession().beginTransaction();					
					proyectoDAO.getSession().save(currentProyecto);
					proyectoDAO.getSession().getTransaction().commit();
					setErrores("Se agregó el Proyecto con éxito.");
				}else{
					setErrores("El Proyecto que se intenta agregar ya existe.");
				}
			}else{//No es nuevo
				if(existProyecto(currentProyecto)){
					setErrores("El Proyecto que se intenta agregar ya existe.");
				}else{
					proyectoDAO.getSession().beginTransaction();
					String nombre = currentProyecto.getNombre();
					currentProyecto = proyectoDAO.findById(currentProyecto.getIdProyecto());					
					currentProyecto.setNombre(nombre);
					proyectoDAO.getSession().update(currentProyecto);
					proyectoDAO.getSession().getTransaction().commit();
					setErrores("Se actualizó el Proyecto con éxito.");
				}
			}
		}else{
			setErrores("Especifique el nombre del Proyecto que se intenta dar de alta.");
		}
		newProyecto();
		loadProyectos(0);
		modalRendered = true;
	}
	
	
	public boolean existProyecto(Proyecto currentProyecto){
		List<Proyecto> listaAux = proyectoDAO.findByNombre(currentProyecto.getNombre());
		for(Proyecto p : listaAux){
			if(p.getIdProyecto() == currentProyecto.getIdProyecto())
				return false;
			else
				return true;
		}
		return false;
	}
	
	public void agregarTipodocumento(){		
		if(!currentTipoDocumento.getNombre().isEmpty()){
			if(currentTipoDocumento.getIdTipoDocumento() == 0){//Es nuevo
				if(!existTipoDocumento(currentTipoDocumento)){
					tdDAO.getSession().beginTransaction();					
					tdDAO.getSession().save(currentTipoDocumento);
					tdDAO.getSession().getTransaction().commit();
					setErrores("Se agreg� el tipo de documento con �xito.");
				}else{
					setErrores("El tipo de documento que se intenta agregar ya existe.");
				}
			}else{//No es nuevo
				if(existTipoDocumento(currentTipoDocumento)){
					setErrores("El tipo de documento que se intenta agregar ya existe.");
				}else{
					tdDAO.getSession().beginTransaction();
					String nombre = currentTipoDocumento.getNombre();
					currentTipoDocumento = tdDAO.findById(currentTipoDocumento.getIdTipoDocumento());					
					currentTipoDocumento.setNombre(nombre);
					tdDAO.getSession().update(currentTipoDocumento);
					tdDAO.getSession().getTransaction().commit();
					setErrores("Se actualiz� el Proyecto con �xito.");
				}
			}
		}else{
			setErrores("Especifique el tipo de documento a dar de alta.");
		}
		newTipoDocumento();
		loadTipoDocumento();
		modalRendered = true;
	}
	
	
	public boolean existTipoDocumento(Tipodocumento currentTipoDocumento){
		List<Tipodocumento> listaAux = tdDAO.findByNombre(currentTipoDocumento.getNombre());
		for(Tipodocumento td : listaAux){
			if(td.getIdTipoDocumento() == currentTipoDocumento.getIdTipoDocumento())
				return false;
			else
				return true;
		}
		return false;
	}
	
	public void agregarOrigen(){		
		if(validador.validarNombre(currentOrigen.getNombre())){
			if(currentOrigen.getIdOrigen() == 0){//Es nuevo
				if(!exisOrigen(currentOrigen)){
					origenDAO.getSession().beginTransaction();					
					origenDAO.getSession().save(currentOrigen);
					origenDAO.getSession().getTransaction().commit();
					setErrores("Se agreg� el Origen con �xito.");
				}else{
					setErrores("El Origen que se intenta agregar ya existe.");
				}
			}else{//No es nuevo
				if(exisOrigen(currentOrigen)){
					setErrores("El Origen que se intenta agregar ya existe.");
				}else{
					origenDAO.getSession().beginTransaction();
					String nombre = currentOrigen.getNombre();
					currentOrigen = origenDAO.findById(currentOrigen.getIdOrigen());		
					currentOrigen.setNombre(nombre);
					origenDAO.getSession().update(currentOrigen);
					origenDAO.getSession().getTransaction().commit();
					setErrores("Se actualiz� el Origen con �xito.");
				}
			}
		}else{
			setErrores("Especifique el Origen que se intenta agregar.");
		}
		currentOrigen = new Origen();		
		makeLista();
		modalRendered = true;
	}
	
	
	public boolean exisOrigen(Origen currentOrigen){
		List<Origen> listaAux = origenDAO.findByNombre(currentOrigen.getNombre());
		for(Origen o : listaAux){
			if(o.getIdOrigen() == currentOrigen.getIdOrigen())
				return false;
			else
				return true;
		}
		return false;
	}
	
	public void selectionListenerTramo(RowSelectorEvent event){
		currentTramo = tramoDAO.findById(Integer.parseInt(tramosList.get(event.getRow()).getValue().toString()));		
		tramosListC.add(new SelectItem(currentTramo, tramoDAO.findPlanos(currentTramo).toString()));
		sustituirT = currentTramo.getNombre();
	}
	
	public void selectionListenerTramoC(RowSelectorEvent event){		
		tramosListC.remove(event.getRow());
	}
	
	public void combinar(){
		Tramo asig = new Tramo();
		boolean u = true;
		for(SelectItem si : tramosListC){
			if(u){
				u = false;
				asig = tramoDAO.findById(((Tramo)si.getValue()).getIdTramo());
			}
			else{
				documentoDAO.getSession().beginTransaction();
				Tramo t = tramoDAO.findById(((Tramo)si.getValue()).getIdTramo());
				List <Documento> tdList = documentoDAO.findByProperty("tramo", t);	
				int dm = tdList.size();
				System.out.println("Documentos modificados en tramo : " + t.getNombre() + ": " + dm);
				for(Documento doc : tdList){
					doc.setTramo(asig);
					documentoDAO.getSession().update(doc);
				}				
				documentoDAO.getSession().getTransaction().commit();
				tramoDAO.getSession().beginTransaction();
				tramoDAO.delete(t);
				tramoDAO.getSession().getTransaction().commit();
			}
		}
		tramosListC.clear();
		setErrores("Se combinaron los tramos seleccionados.");
		fillTramo(currentTramo.getEstado().getIdEstado());
		int idEstado = currentTramo.getEstado().getIdEstado();
		currentTramo = new Tramo();		
		currentTramo.getEstado().setIdEstado(idEstado);
	}
	public void combinarTramo(){
		Tramo asig = new Tramo();
		boolean u = true;
		int c = 0;
		for(SelectItem si : tramosListC){
			if(u){
				u = false;
				asig = tramoDAO.findById(((Tramo)si.getValue()).getIdTramo());
			}
			else{
				documentoDAO.getSession().beginTransaction();
				Tramo t = tramoDAO.findById(((Tramo)si.getValue()).getIdTramo());
				List <Documento> tdList = documentoDAO.findByProperty("tramo", t);	
				c += tdList.size();				
				for(Documento doc : tdList){
					doc.setTramo(asig);
					documentoDAO.getSession().update(doc);
				}				
				documentoDAO.getSession().getTransaction().commit();
				tramoDAO.getSession().beginTransaction();
				tramoDAO.delete(t);
				tramoDAO.getSession().getTransaction().commit();
			}
		}
		
		tramosListC.clear();
		setErrores("Se agregaron " + c + " planos al tramo " + asig.getNombre());
		modalRendered = true;
		fillTramo(currentTramo.getEstado().getIdEstado());
		int idEstado = currentTramo.getEstado().getIdEstado();
		currentTramo = new Tramo();		
		currentTramo.getEstado().setIdEstado(idEstado);
		c = 0;
	}
	public void selectionListenerOrigen(RowSelectorEvent event){
		setCurrentOrigen(origenDAO.findById(Integer.parseInt(origenList.get(event.getRow()).getValue().toString())));
		sustituirO = getCurrentOrigen().getNombre();
	}
	
	public void selectionListenerProyecto(RowSelectorEvent event){
		setCurrentProyecto(proyectoDAO.findById(Integer.parseInt(proyectoList.get(event.getRow()).getValue().toString())));
		getCurrentProyecto().setTramos(getTramosProyecto(getCurrentProyecto()));
		proyectoListC.add(getCurrentProyecto());
		sustituirP = getCurrentProyecto().getNombre();
	}
	
	public String getTramosProyecto(Proyecto p){
		String tramos = "";
		Map map = new HashMap();    // hash table 
        map = new TreeMap();  
        
		for(Documento d : (List<Documento>)documentoDAO.findByProperty("proyecto", p)){
			map.put(d.getTramo().getNombre(), d.getTramo().getNombre());			
		}
		Iterator it = map.keySet().iterator(); 
        while (it.hasNext()) { 
            // Get Clave 
            tramos += (String) it.next() + "  /  ";             
         }      
		return tramos;
	}
	
	public void selectionListenerProyecto2(RowSelectorEvent event){
		proyectoListC.remove(event.getRow());		
	}
	public void combinarProyectos(){
		if(proyectoListC.size() > 1){
			boolean pasa = true;
			Proyecto ganador = new Proyecto();
			int c = 0;
			for(Proyecto p :proyectoListC){
				if(pasa){
					ganador = proyectoDAO.findById(p.getIdProyecto());
					pasa = false;
				}else{
					Proyecto proyDel = proyectoDAO.findById(p.getIdProyecto());
					
					for(Documento d : (List<Documento>)documentoDAO.findByProperty("proyecto", proyDel)){
						documentoDAO.getSession().beginTransaction();
						d.setProyecto(ganador);												
						documentoDAO.getSession().update(d);
						documentoDAO.getSession().getTransaction().commit();
						c++;
					}
					
					proyectoDAO.getSession().beginTransaction();
					proyectoDAO.delete(proyDel);
					proyectoDAO.getSession().getTransaction().commit();
					setErrores("Se agregaron " + c + " documentos al proyecto.");
					
				}
			}
			c = 0;
			loadProyectos(0);
			proyectoListC.clear();
			modalRendered = true;
		}
	}
	
	public void selectionListenerTipoDocumento(RowSelectorEvent event){
		setCurrentTipoDocumento(tdDAO.findById(Integer.parseInt(tipoDocumentoList.get(event.getRow()).getValue().toString())));
	}
	
	
	
	public void loadTipoDocumento(){
		listaTD.clear();
		tipoDocumentoList.clear();
		listaTD.add(new SelectItem(0, "Seleccione"));	
		for(Tipodocumento td : (List<Tipodocumento>)tdDAO.findAll()){
			listaTD.add(new SelectItem(td.getIdTipoDocumento(), td.getNombre()));
			tipoDocumentoList.add(new SelectItem(td.getIdTipoDocumento(), td.getNombre()));
		}
	}
	
	public void loadProyectos(int idEntidad){
		listaProyecto.clear();
		//proyectoList.clear();
		listaProyecto.add(new SelectItem(0, "Seleccione"));	
		if(idEntidad != 0){
			
			for(Proyecto p : (List<Proyecto>)proyectoDAO.findByEntidad(estadoDAO.findById(idEntidad))){
				listaProyecto.add(new SelectItem(p.getIdProyecto(), p.getNumProy() + " - " + p.getNombre()));
				//proyectoList.add(new SelectItem(p.getIdProyecto(), p.getNombre()));
			}
			System.out.println(listaProyecto.size());
		}
	}
	
	public void fillTipoDocumento(){
		tipoList.clear();
		tipoList.add(new SelectItem(0,"Seleccione"));
		tipoList.add(new SelectItem(1,"Campo"));
		tipoList.add(new SelectItem(2,"Terracerias"));
	}
	
	
 
	public void generateProyectoMatch(){
		
	}
	public void valueChangeListener(ValueChangeEvent event) throws Exception {
		
		if (event.getComponent().getId().equals("selectEdo")){
			//cambioT();
			fillTramoByProyecto(0,0,0);			
			fillTD("", null);
			fillTipoEstructura(0,0);
			fillCarreteraByProyecto(0,0);
			fillOrigenByTramo(0);
			estado.setIdEstado(Integer.parseInt(event.getNewValue().toString()));
			loadProyectos(estado.getIdEstado());
			
			
		}else if (event.getComponent().getId().equals("selectProyecto")){
			fillTramoByProyecto(0,0,0);
			fillOrigenByTramo(0);
			fillTipoEstructura(0,0);
			fillCarreteraByProyecto(0,0);
			fillTD("", null);
			idProyecto = Integer.parseInt(event.getNewValue().toString());
			if(idProyecto != 0){
				fillCarreteraByProyecto(estado.getIdEstado(), idProyecto);
			}
		}else if (event.getComponent().getId().equals("selectCarreteraS")){			
			fillTD("", null);
			fillTipoEstructura(0,0);
			fillOrigenByTramo(0);
			fillTramoByProyecto(0, 0, 0);
			idCarretera = Integer.parseInt(event.getNewValue().toString());
			if(idCarretera > 0){
				fillTramoByProyecto(estado.getIdEstado(),idProyecto,idCarretera);
			}
		}
		else if (event.getComponent().getId().equals("selectTramo") && event.getNewValue() != null){			
			fillTD("", null);
			fillOrigenByTramo(0);
			fillTipoEstructura(0,0);
			idTramo = Integer.parseInt(event.getNewValue().toString());
			if(idTramo > 0){
			fillTDByTramo(idProyecto, idTramo);
			}
		}
		else if(event.getComponent().getId().equals("selectTipoPlano")){
			tipoEstructuraList.add(new SelectItem(0,"Selecciona"));
			idTipoDoc = Integer.parseInt(event.getNewValue().toString());
			fillTipoEstructura(0,0);
			fillOrigenByTramo(0);
			for(Tipoestructura tE : (List<Tipoestructura>)tipoEstructuraDAO.findByTramoProyectofindByEdoProTraTipEst(estado.getIdEstado(), idProyecto, idTramo, idTipoDoc)){
				tipoEstructuraList.add(new SelectItem(tE.getIdTipoEstructura(),tE.getNombre()));
			}
		}
		else if(event.getComponent().getId().equals("selectTipoEstructura")){
			origenList.add(new SelectItem(0,"Selecciona"));
			idTipoEst = Integer.parseInt(event.getNewValue().toString());
			fillOrigenByTramo(0);
			for(Origen o : (List<Origen>)origenDAO.findByTramoProyectofindByEdoProTraTipDoc(estado.getIdEstado(), idProyecto, idTramo, idTipoDoc,idTipoEst)){
				origenList.add(new SelectItem(o.getIdOrigen(), o.getNombre()));
			}
		}	
		else if (event.getComponent().getId().equals("selectEdoCatalogo")){
			cambioT();			
			int idEstado = Integer.parseInt(event.getNewValue().toString());
			fillTramoCatalogo(idEstado);
			currentTramo = new Tramo();
			currentTramo.getEstado().setIdEstado(idEstado);
			
		}else if (event.getComponent().getId().equals("selectOrigen")){
			int idOrigen = Integer.parseInt(event.getNewValue().toString());
			currentOrigen = origenDAO.findById(idOrigen);
			//fillTD("origen", currentOrigen);
		}else if (event.getComponent().getId().equals("selectEdoB")){
			cambioT();			
			estado.setIdEstado(Integer.parseInt(event.getNewValue().toString()));
			fillTramo(estado.getIdEstado());
			//fillCarretera(estado.getIdEstado());	
			if(!carreteraList.isEmpty())
				fillTramoB(Integer.parseInt(event.getNewValue().toString()));
		}else if(event.getComponent().getId().equals("selectCarreter")){
			cambioT();
			//carretera.setIdCarretera(Integer.parseInt(event.getNewValue().toString()));
			//fillTramo(carretera.getIdCarretera());
			
			System.out.println("Ok");
		}else if(event.getComponent().getId().equals("selectCarreteraB")){
			cambioT();
			fillTramoB(Integer.parseInt(event.getNewValue().toString()));
		}
		
	}
	
	public void cambioC(){
		carreteraList.clear();	
		//carretera.setIdCarretera(0);
		cambioT();
	}
	
	public void fillTD(String fiel, Object value){				
		listaTD.clear();
		listaTD.add(new SelectItem(0,"Selecciona"));
		if(!fiel.isEmpty())
			for(Tipodocumento td : (List<Tipodocumento>)tdDAO.findTDByTramo(fiel, value)){
				listaTD.add(new SelectItem(td.getIdTipoDocumento(), td.getNombre()));
			}
			
	}
	
	public void fillTramo(int idEstado){				
		tramosList.clear();
		if(idEstado != 0){
			for(Tramo t : (List<Tramo>)tramoDAO.orderABC(idEstado)){
				if(tramosList.isEmpty())
					tramosList.add(new SelectItem(0, "Seleccione un tramo"));		
				tramosList.add(new SelectItem(t.getIdTramo(), t.getNombre()));
			}
			if(tramosList.isEmpty())
				tramosList.add(new SelectItem(0, "SIN TRAMOS"));
		}
			
	}
	
	public void fillCarreteraByProyecto(int idEstado, int idProyecto){				
		carreterasList.clear();
		carreterasList.add(new SelectItem(0,"Selecciona"));
		currentProyecto = proyectoDAO.findById(idProyecto);
		for(Tramo t : (List<Tramo>)tramoDAO.findByCarretera(idEstado, currentProyecto)){
			carreterasList.add(new SelectItem(t.getCarretera().getIdCarretera(), t.getCarretera().getNombre()));
		}
			
	}
	
	public void fillTramoByProyecto(int idEstado, int idProyecto, int idCarretera){				
		tramosList.clear();
		tramosList.add(new SelectItem(0,"Selecciona"));
		currentProyecto = proyectoDAO.findById(idProyecto);
		for(Tramo t : (List<Tramo>)tramoDAO.findByProyecto(idEstado, currentProyecto,idCarretera)){
			tramosList.add(new SelectItem(t.getIdTramo(), t.getNombre()));
		}
			
	}
	
	public void fillOrigenByTramo(int idTramo){				
		origenList.clear();
		origenList.add(new SelectItem(0,"Selecciona"));
		currentTramo = tramoDAO.findById(idTramo);
		if(currentTramo != null)
			for(Origen o : (List<Origen>)origenDAO.findByTramoProyecto(currentTramo, currentProyecto)){
				origenList.add(new SelectItem(o.getIdOrigen(), o.getNombre()));
			}
			
	}
	
	public void fillTipoEstructura(int idTramo , int idProyecto){
		tipoEstructuraList.clear();
		tipoEstructuraList.add(new SelectItem(0,"Selecciona"));
		currentProyecto = proyectoDAO.findById(idProyecto);
		currentTramo = tramoDAO.findById(idTramo);
		if(currentTipoEstructura != null)
			for(Tipoestructura tipEs : (List<Tipoestructura>)tipoEstructuraDAO.findByTramoProyecto(currentTramo, currentProyecto)){
				tipoEstructuraList.add(new SelectItem(tipEs.getIdTipoEstructura(), tipEs.getNombre()));
			}
	}
	
	public void fillTDByTramo(int idProyecto,int idTramo){	
		listaTD.clear();
		listaTD.add(new SelectItem(0,"Selecciona"));
		for(Tipodocumento td : (List<Tipodocumento>)tdDAO.findTDByTramo(idProyecto, idTramo)){
			listaTD.add(new SelectItem(td.getIdTipoDocumento(), td.getNombre()));
		}

	
	}
	public void fillTramoCatalogo(int idEstado){				
		tramosList.clear();
		if(idEstado != 0){
			for(Tramo t : (List<Tramo>)tramoDAO.orderABC(idEstado)){					
				tramosList.add(new SelectItem(t.getIdTramo(), t.getNombre()));
			}
			if(tramosList.isEmpty())
				tramosList.add(new SelectItem(0, "SIN TRAMOS"));
		}
			
	}
	public void fillTramoB(int idCarretera){
		tramosListB.clear();
		for(Tramo t : (List<Tramo>)tramoDAO.orderABC(idCarretera)){
			if(tramosListB.isEmpty())
				tramosListB.add(new SelectItem(0, "Seleccione un tramo"));			
			tramosListB.add(new SelectItem(t.getIdTramo(), t.getNombre()));
		}
		if(tramosListB.isEmpty())
			tramosListB.add(new SelectItem(0, "Carretera sin tramos"));
		
	}
	public void renderTramo(String match){
		//tramosNamesAux = tramosNames;
		match = match.toUpperCase();
		tramosNamesAux.clear();
		for(SelectItem si : listaOrigen){
			if(si.getLabel().toString().contains(match))
				tramosNamesAux.add(si);
		}
		
	 }
	
	public void renderOrigen(String match){
		//tramosNamesAux = tramosNames;
		match = match.toUpperCase();
		origenesNamesAux.clear();
		for(SelectItem si : listaOrigen){
			if(si.getLabel().toString().contains(match))
				origenesNamesAux.add(si);
		}
		origenesNames = origenesNamesAux;
		//System.out.println(origenesNames.size());
	 }
	
	
	public void makeLista(){
		origenList.clear();
		listaOrigen.clear();
		for(Origen or : (List<Origen>)origenDAO.findAll()){
			if(listaOrigen.isEmpty())
				listaOrigen.add(new SelectItem(0, "Seleccione un Origen"));
			listaOrigen.add(new SelectItem(or.getIdOrigen(), or.getNombre()));
			origenList.add(new SelectItem(or.getIdOrigen(), or.getNombre()));
		}		
		//System.out.println("Tama�o: " + listaOrigen.size());
		
	}
	
	
	
	
	
	
	public void loadEstados(){
		estadosList.clear();
		for(Estado e : (List<Estado>)estadoDAO.findAll())
			estadosList.add(new SelectItem(e.getIdEstado(), e.getNombre()));
	}
	
	public List<SelectItem> getEstadosList() {
		return estadosList;
	}

	public void setEstadosList(List<SelectItem> estadosList) {
		this.estadosList = estadosList;
	}

	public List<SelectItem> getCarreteraList() {
		return carreteraList;
	}

	public void setCarreteraList(List<SelectItem> carreteraList) {
		this.carreteraList = carreteraList;
	}

	public List<SelectItem> getTramosList() {
		return tramosList;
	}

	public void setTramosList(List<SelectItem> tramosList) {
		this.tramosList = tramosList;
	}

	
	public EstadoDAO getEstadoDAO() {
		return estadoDAO;
	}

	public void setEstadoDAO(EstadoDAO estadoDAO) {
		this.estadoDAO = estadoDAO;
	}

	public TramoDAO getTramoDAO() {
		return tramoDAO;
	}

	public void setTramoDAO(TramoDAO tramoDAO) {
		this.tramoDAO = tramoDAO;
	}

	public void cambioT(){		
		tramosList.clear();		
	}
	
	public void cambioTB(){		
		tramosListB.clear();		
	}
	
	

	public List<SelectItem> getTramosListB() {
		return tramosListB;
	}

	public void setTramosListB(List<SelectItem> tramosListB) {
		this.tramosListB = tramosListB;
	}

	public List<SelectItem> getCarreteraListB() {
		return carreteraListB;
	}

	public void setCarreteraListB(List<SelectItem> carreteraListB) {
		this.carreteraListB = carreteraListB;
	}

	

	public Tramo getTramo() {
		return tramo;
	}

	public void setTramo(Tramo tramo) {
		this.tramo = tramo;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public List<SelectItem> getTipoList() {
		return tipoList;
	}

	public void setTipoList(List<SelectItem> tipoList) {
		this.tipoList = tipoList;
	}

	public List<SelectItem> getTramosNames() {
		return tramosNames;
	}

	public void setTramosNames(List<SelectItem> tramosNames) {
		this.tramosNames = tramosNames;
	}

	public List<SelectItem> getOrigenesNames() {
		return origenesNames;
	}

	public void setOrigenesNames(List<SelectItem> origenesNames) {
		this.origenesNames = origenesNames;
	}
	
	public List<SelectItem> getListaOrigen() {
		return listaOrigen;
	}

	public void setListaOrigen(List<SelectItem> listaOrigen) {
		this.listaOrigen = listaOrigen;
	}

	public List<SelectItem> getListaTP() {
		return listaTP;
	}

	public void setListaTP(List<SelectItem> listaTP) {
		this.listaTP = listaTP;
	}

	public List<SelectItem> getListaTD() {
		return listaTD;
	}

	public void setListaTD(List<SelectItem> listaTD) {
		this.listaTD = listaTD;
	}

	public OrigenDAO getOrigenDAO() {
		return origenDAO;
	}

	public void setOrigenDAO(OrigenDAO origenDAO) {
		this.origenDAO = origenDAO;
	}

	public TipoestructuraDAO getTipoEstructuraDAO() {
		return tipoEstructuraDAO;
	}

	public void setTipoEstructuraDAO(TipoestructuraDAO tipoEstructuraDAO) {
		this.tipoEstructuraDAO = tipoEstructuraDAO;
	}
	
	public TipodocumentoDAO getTdDAO() {
		return tdDAO;
	}

	public void setTdDAO(TipodocumentoDAO tdDAO) {
		this.tdDAO = tdDAO;
	}

	public List<SelectItem> getListaProyecto() {
		return listaProyecto;
	}

	public void setListaProyecto(List<SelectItem> listaProyecto) {
		this.listaProyecto = listaProyecto;
	}

	public DocumentoDAO getDocumentoDAO() {
		return documentoDAO;
	}

	public void setDocumentoDAO(DocumentoDAO documentoDAO) {
		this.documentoDAO = documentoDAO;
	}

	public ProyectoDAO getProyectoDAO() {
		return proyectoDAO;
	}

	public void setProyectoDAO(ProyectoDAO proyectoDAO) {
		this.proyectoDAO = proyectoDAO;
	}

	public Tramo getCurrentTramo() {
		return currentTramo;
	}

	public void setCurrentTramo(Tramo currentTramo) {
		this.currentTramo = currentTramo;
	}

	public Validador getValidador() {
		return validador;
	}

	public void setValidador(Validador validador) {
		this.validador = validador;
	}

	public boolean isModalRendered() {
		return modalRendered;
	}

	public void setModalRendered(boolean modalRendered) {
		this.modalRendered = modalRendered;
	}

	public String getErrores() {
		return errores;
	}

	public void setErrores(String errores) {
		this.errores = errores;
	}

	public List<SelectItem> getOrigenList() {
		return origenList;
	}

	public void setOrigenList(List<SelectItem> origenList) {
		this.origenList = origenList;
	}

	public Origen getCurrentOrigen() {
		return currentOrigen;
	}

	public void setCurrentOrigen(Origen currentOrigen) {
		this.currentOrigen = currentOrigen;
	}

	public String getSustituirP() {
		return sustituirP;
	}

	public void setSustituirP(String sustituirP) {
		this.sustituirP = sustituirP;
	}

	public String getSustitutoP() {
		return sustitutoP;
	}

	public void setSustitutoP(String sustitutoP) {
		this.sustitutoP = sustitutoP;
	}

	public String getMsgSustituirP() {
		return msgSustituirP;
	}

	public void setMsgSustituirP(String msgSustituirP) {
		this.msgSustituirP = msgSustituirP;
	}

	public String getMsgSustitutoP() {
		return msgSustitutoP;
	}

	public void setMsgSustitutoP(String msgSustitutoP) {
		this.msgSustitutoP = msgSustitutoP;
	}

	public List<SelectItem> getProyectoList() {
		return proyectoList;
	}

	public void setProyectoList(List<SelectItem> proyectoList) {
		this.proyectoList = proyectoList;
	}

	public Proyecto getCurrentProyecto() {
		return currentProyecto;
	}

	public void setCurrentProyecto(Proyecto currentProyecto) {
		this.currentProyecto = currentProyecto;
	}

	public Tipodocumento getCurrentTipoDocumento() {
		return currentTipoDocumento;
	}

	public void setCurrentTipoDocumento(Tipodocumento currentTipoDocumento) {
		this.currentTipoDocumento = currentTipoDocumento;
	}

	public List<SelectItem> getTipoDocumentoList() {
		return tipoDocumentoList;
	}

	public void setTipoDocumentoList(List<SelectItem> tipoDocumentoList) {
		this.tipoDocumentoList = tipoDocumentoList;
	}

	public List<SelectItem> getTramosListC() {
		return tramosListC;
	}

	public void setTramosListC(List<SelectItem> tramosListC) {
		this.tramosListC = tramosListC;
	}

	public List<Proyecto> getProyectoListC() {
		return proyectoListC;
	}

	public void setProyectoListC(List<Proyecto> proyectoListC) {
		this.proyectoListC = proyectoListC;
	}





	public String getSustituirO() {
		return sustituirO;
	}





	public void setSustituirO(String sustituirO) {
		this.sustituirO = sustituirO;
	}





	public String getSustitutoO() {
		return sustitutoO;
	}





	public void setSustitutoO(String sustitutoO) {
		this.sustitutoO = sustitutoO;
	}





	public String getMsgSustituirO() {
		return msgSustituirO;
	}





	public void setMsgSustituirO(String msgSustituirO) {
		this.msgSustituirO = msgSustituirO;
	}





	public String getMsgSustitutoO() {
		return msgSustitutoO;
	}





	public void setMsgSustitutoO(String msgSustitutoO) {
		this.msgSustitutoO = msgSustitutoO;
	}

	public String getSustituirT() {
		return sustituirT;
	}

	public void setSustituirT(String sustituirT) {
		this.sustituirT = sustituirT;
	}

	public String getSustitutoT() {
		return sustitutoT;
	}

	public void setSustitutoT(String sustitutoT) {
		this.sustitutoT = sustitutoT;
	}

	public String getMsgSustituirT() {
		return msgSustituirT;
	}

	public void setMsgSustituirT(String msgSustituirT) {
		this.msgSustituirT = msgSustituirT;
	}

	public String getMsgSustitutoT() {
		return msgSustitutoT;
	}

	public void setMsgSustitutoT(String msgSustitutoT) {
		this.msgSustitutoT = msgSustitutoT;
	}

	public List<SelectItem> getTipoEstructuraList() {
		return tipoEstructuraList;
	}

	public void setTipoEstructuraList(List<SelectItem> tipoEstructuraList) {
		this.tipoEstructuraList = tipoEstructuraList;
	}

	public int getIdTipoEst() {
		return idTipoEst;
	}

	public void setIdTipoEst(int idTipoEst) {
		this.idTipoEst = idTipoEst;
	}

	public List<SelectItem> getCarreterasList() {
		return carreterasList;
	}

	public void setCarreterasList(List<SelectItem> carreterasList) {
		this.carreterasList = carreterasList;
	}

	public int getIdCarretera() {
		return idCarretera;
	}

	public void setIdCarretera(int idCarretera) {
		this.idCarretera = idCarretera;
	}



	
	

	
}
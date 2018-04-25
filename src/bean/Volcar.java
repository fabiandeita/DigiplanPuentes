/**
 * 
 */
package bean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.EmptyStackException;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.hibernate.Transaction;

import conf.Configuration;
import dao.Archivo;
import dao.ArchivoDAO;
import dao.Carretera;
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

import archivos.Uploader;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * @author Fabian De Ita
 * 
 */
public class Volcar {
	private String inputFile = "c:/temp/baseDIGIPLANPUENTES.xls";
	private Uploader archivo = new Uploader();
	private String msgArchivo = "";
	private PopupBean popup = new PopupBean();
	private Documento doc;
	private DocumentoDAO docDAO = new DocumentoDAO();
	private ArchivoDAO archivoDAO = new ArchivoDAO();
	private EstadoDAO estadoDAO = new EstadoDAO();
	private TramoDAO tramoDAO = new TramoDAO();
	private ProyectoDAO proyectoDAO = new ProyectoDAO();
	private OrigenDAO origenDAO = new OrigenDAO();
	private TipodocumentoDAO tdDAO = new TipodocumentoDAO();
	private TipoestructuraDAO teDAO = new TipoestructuraDAO();
	private CarreteraDAO caDAO = new CarreteraDAO();
	private int seQuedo = 1, mes = 0, fila = 0;
	private String error = "";
	private Calendar calendario = new GregorianCalendar();
	private int hora, minutos, segundos;
	private Transaction t;
	private Configuration conf = new Configuration();
	PrintWriter writeLog = null;
	public Volcar() {
		try{
			
			writeLog = new PrintWriter("c:/temp/log.txt");
		}catch(Exception e){
			
		}
	}
	
	public String cotejar(){
		File f;
		
		f = new File("c:/temp/prueba.txt");
		
		 
	
		//Escritura
	
		try{
			PrintWriter salida = null;
			 salida = new PrintWriter("c:/temp/datos.txt");			
			 docDAO.getSession().beginTransaction();
			 for(Documento doc : (List<Documento>)docDAO.findAllActive()){
				 for(Archivo archivo : (List<Archivo>)archivoDAO.findByIdDocumentoOrderByDesc(doc.getIdDocumento())){
					 //System.out.println(archivo.getArchivo());
					File plano = new File(conf.getBasePath() + conf.getResourcePath() + archivo.getArchivo());		
					//System.out.println(plano.getAbsolutePath());
					if(!plano.exists()){
						salida.println(doc.getIdDocumento() + " -- " + archivo.getArchivo());
						doc.setActivo(new Short("0"));	
						System.out.println(doc.getIdDocumento());
						docDAO.getSession().update(doc);			
					}	
					break;
				 }
			}
			 
			docDAO.getSession().getTransaction().commit();
			 salida.close();
		}catch(IOException e){e.printStackTrace();};

	
		
		return "";
	}
	
	@SuppressWarnings("deprecation")
	public void read() {
		try {
			archivo.saveFile();
			File inputWorkbook = archivo.getCurrentFile().getFile();
			Workbook w;

			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);
			// Loop over first 10 column and lines

			System.out.println("Columnas: " + sheet.getColumns());
			System.out.println("Filas: " + sheet.getRows());

			int filas = sheet.getRows();
			Date fecha = new Date();
			System.out.println("Inicio: " + fecha);
			seQuedo = 1;
			int cc = 0;
			String numeroProyecto = "";
			PrintWriter salida;
			try {
				salida = new PrintWriter("c:/temp/log.txt");
				for (int i = 1; i < filas; i++) {
					cc++;
					doc = new Documento();
					System.out.println("Inicio: " + i + " De: " + filas);
					Cell cell = sheet.getCell(1, i);
					fila = Integer.parseInt(cell.getContents());
					
					
					// Numero de proyecto y Nombre del proyecto
					cell = sheet.getCell(0, i);				
					numeroProyecto = cell.getContents();
					
					doc.getProyecto().setNumProy(numeroProyecto);
					
					cell = sheet.getCell(5, i);
					Proyecto p = getProyecto(numeroProyecto,cell.getContents().toUpperCase(), i, salida);
					if(p == null)
						continue;
					doc.setProyecto(p);
					
					// System.out.println(cell.getContents());	

					// Estado								
					cell = sheet.getCell(3, i);
					doc.setEstado(estadoDAO.findById(Integer.parseInt(cell.getContents())));
					
					// Carretera
					cell = sheet.getCell(6, i);
					Carretera ca = getCarretera(cell.getContents());
					// Tramo
					cell = sheet.getCell(7, i);
					doc.setTramo(getTramo(doc.getEstado(), cell.getContents(),ca));

					// Km inicial
					cell = sheet.getCell(8, i);
					doc.setKmInicial(cell.getContents().replace(".", "+").trim());
					// System.out.println(cell.getContents());
					// Km Final
					cell = sheet.getCell(9, i);
					doc.setKmFinal(cell.getContents().replace(".", "+").trim());
					// System.out.println(cell.getContents());
					// Origen
					cell = sheet.getCell(10, i);
					doc.setOrigen(getOrigen(cell.getContents()));

					// Fecha creaci�n
					cell = sheet.getCell(11, i);
					// System.out.println("Fecha: " + cell.getContents());
					doc.setFechaCreacion(isDate(cell.getContents()));

					// Tipo de documento terraceria, proceso electronico
					cell = sheet.getCell(12, i);
					doc.setTipodocumento(getTipoDocumento(cell.getContents()));

					// Nombre de archivo
					cell = sheet.getCell(14, i);
					doc.setArchivo(cell.getContents());
					// Tipo de Estructura
					cell = sheet.getCell(13, i);
					doc.setTipoEstructura(getTipoEstructura(cell.getContents()));
					// Observaciones
					cell = sheet.getCell(15, i);
					doc.setObservaciones(cell.getContents().toUpperCase());
					
					docDAO.getSession().beginTransaction();
					docDAO.save(doc);
					docDAO.getSession().getTransaction().commit();

					// System.out.println("Fila: " + seQuedo);
					
					
					archivoDAO.getSession().beginTransaction();
					Archivo archivo = new Archivo();
					archivo.setArchivo(doc.getArchivo());
					archivo.setDocumento(doc);
					archivoDAO.save(archivo);
					archivoDAO.getSession().getTransaction().commit();

				}
				salida.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			
			
			Date fechaFin = new Date();
			System.out.println("Fin: " + fechaFin);
		} catch (Exception e) {
			//System.out.println("Se quedo en: " + seQuedo);
			System.out.println("Fila: " + fila);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public Date isDate(String date) {
		try {
			if (!date.equals("No indica fecha")) {
				int indice = 0, anio = 0, dia = 0;
				Date fecha = new Date();
				mes = 0;
				String mesName = "";
				String diaName = "";
				indice = date.indexOf("-");
				if (indice != -1) {
					anio = Integer.parseInt(date.substring(0, 4)) - 1900;
					mes = Integer.parseInt(date.substring(6, 7)) - 1;
					dia = Integer.parseInt(date.substring(8, date.length()));
					// dia = Integer.parseInt(date.substring(8));
					return new Date(anio, mes, dia);
				} else if (indice == -1) {
					// Obtenemos el a�o
					anio = Integer.parseInt(date.substring(0, 4));
					// Obtenemos el mes
					mesName = date.substring(5, date.length());
					indice = mesName.indexOf(" ");
					if (indice == -1) {
						setMes(mesName);
						dia = 1;
					} else {
						// Se asigna el mes
						indice = mesName.indexOf(" ");
						if (indice != -1) {
							// Si no solo esta el mes y hay dia
							setMes(mesName.substring(0, indice));

							// Se asigna el dia
							diaName = mesName.substring(indice + 1,
									mesName.length());
							if (diaName.isEmpty() || diaName.equals(" ")
									|| diaName.equals("  ")
									|| diaName.equals("   ")) { // Espacio en
																// blanco
																// despues del
																// mes
								dia = 1;
							} else {
								// Si inicia con CERO
								indice = diaName.indexOf("0");
								if (indice == 0)
									dia = Integer
											.parseInt(diaName.substring(1));
								else
									dia = Integer.parseInt(diaName);
							}
						} else {
							// Si solo hay mes
							setMes(mesName.substring(0, mesName.length()));
							dia = 1;
						}
					}
					// fecha.setYear(anio);
					return new Date(anio - 1900, mes, dia);
				}
				return null;
			} else {
				//System.out.println("Date: " + date);
				return null;

			}
		} catch (Exception e) {
			return null;
		}
	}

	public void setMes(String mesName) {
		if (mesName.equalsIgnoreCase("Enero")
				|| mesName.equalsIgnoreCase("enero"))
			mes = 0;
		else if (mesName.equalsIgnoreCase("Febrero")
				|| mesName.equalsIgnoreCase("febrero"))
			mes = 1;
		else if (mesName.equalsIgnoreCase("Marzo")
				|| mesName.equalsIgnoreCase("marzo"))
			mes = 2;
		else if (mesName.equalsIgnoreCase("Abril")
				|| mesName.equalsIgnoreCase("abril"))
			mes = 3;
		else if (mesName.equalsIgnoreCase("Mayo")
				|| mesName.equalsIgnoreCase("mayo"))
			mes = 4;
		else if (mesName.equalsIgnoreCase("Junio")
				|| mesName.equalsIgnoreCase("junio"))
			mes = 5;
		else if (mesName.equalsIgnoreCase("Julio")
				|| mesName.equalsIgnoreCase("julio"))
			mes = 6;
		else if (mesName.equalsIgnoreCase("Agosto")
				|| mesName.equalsIgnoreCase("agosto"))
			mes = 7;
		else if (mesName.equalsIgnoreCase("Septiembre")
				|| mesName.equalsIgnoreCase("septiembre"))
			mes = 8;
		else if (mesName.equalsIgnoreCase("Octubre")
				|| mesName.equalsIgnoreCase("octubre"))
			mes = 9;
		else if (mesName.equalsIgnoreCase("Noviembre")
				|| mesName.equalsIgnoreCase("noviembre"))
			mes = 10;
		else if (mesName.equalsIgnoreCase("Diciembre")
				|| mesName.equalsIgnoreCase("diciembre"))
			mes = 11;
		else {
			System.out.println("Mes no encontrado!: " + mesName + " en "
					+ seQuedo);
			throw new EmptyStackException();
		}
	}

	public Object returnObjectContext(String object) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext()
				.getSession(false);
		return session.getAttribute(object);
	}

	public String getNombreArchivo(String num) {

		return "d10_" + (docDAO.findAll().size() + 1) + "_" + num + "_W.jpg";

	}

	public Proyecto getProyecto(String numero, String nombreProyecto,int numeroFola, PrintWriter salida) {
		Proyecto p = proyectoDAO.findByNumeroProyecto(numero);		
		
		if (p != null){			
			if(!p.getNombre().equalsIgnoreCase(nombreProyecto)){
				
					salida.println("Nombre del proyecto ya guardado:" + p.getNombre());
					salida.println("Nombre del proyecto diferente  :" + nombreProyecto);
					salida.println("En Fila  :" + numero);
						
				
				return null;
			}else
				return p;
			
		} else {
			p = new Proyecto();
			p.setNombre(spaceInit(nombreProyecto.toUpperCase()));
			p.setNumProy(numero);
			proyectoDAO.getSession().getTransaction().begin();
			proyectoDAO.save(p);
			proyectoDAO.getSession().getTransaction().commit();
			return p;
		}

	}
	
	public Tramo getTramo(Estado e, String nombreTramo, Carretera ca) {
		List<Tramo> posibleTramo = tramoDAO.findByNombre(nombreTramo);
		if (!posibleTramo.isEmpty()) {
			return posibleTramo.get(0);
		} else {
			Tramo t = new Tramo();
			t.setCarretera(ca);
			t.setNombre(spaceInit(nombreTramo.toUpperCase()));
			t.setEstado(e);
			tramoDAO.getSession().getTransaction().begin();
			tramoDAO.save(t);
			tramoDAO.getSession().getTransaction().commit();
			return t;
		}

	}

	public Origen getOrigen(String nombreOrigen) {
		List<Origen> posibleOrigen = origenDAO.findByNombre(nombreOrigen);
		if (!posibleOrigen.isEmpty()) {
			return posibleOrigen.get(0);
		} else {
			Origen o = new Origen();
			o.setNombre(spaceInit(nombreOrigen.toUpperCase()));
			origenDAO.getSession().getTransaction().begin();
			origenDAO.save(o);
			origenDAO.getSession().getTransaction().commit();
			return o;
		}

	}

	
	public Carretera getCarretera(String nombreCarretera) {
		List<Carretera> posibleCarretera = caDAO.findByNombre(nombreCarretera);
		if (!posibleCarretera.isEmpty()) {
			return posibleCarretera.get(0);
		} else {
			Carretera ca = new Carretera();			
			ca.setNombre(spaceInit(nombreCarretera));
			caDAO.getSession().getTransaction().begin();
			caDAO.save(ca);
			caDAO.getSession().getTransaction().commit();
			return ca;
		}

	}
	
	public Tipoestructura getTipoEstructura(String nombreTipoEstructura) {
		List<Tipoestructura> posibleTipoEstructura = teDAO.findByNombre(nombreTipoEstructura);
		if (!posibleTipoEstructura.isEmpty()) {
			return posibleTipoEstructura.get(0);
		} else {
			Tipoestructura te = new Tipoestructura();			
			te.setNombre(spaceInit(nombreTipoEstructura));
			teDAO.getSession().getTransaction().begin();
			teDAO.save(te);
			teDAO.getSession().getTransaction().commit();
			return te;
		}

	}
	
	public Tipodocumento getTipoDocumento(String nombreTipoDocumento) {
		List<Tipodocumento> posibleTipodocumento = tdDAO.findByNombre(nombreTipoDocumento);
		if (!posibleTipodocumento.isEmpty()) {
			return posibleTipodocumento.get(0);
		} else {
			Tipodocumento td = new Tipodocumento();			
			td.setNombre(spaceInit(nombreTipoDocumento));
			tdDAO.getSession().getTransaction().begin();
			tdDAO.save(td);
			tdDAO.getSession().getTransaction().commit();
			return td;
		}

	}
	public String spaceInit(String name){
		if(name.indexOf(' ') == 0)
			return name.substring(1, name.length()-1).toUpperCase();
		else
			return name.toUpperCase(); 
	}
	public Uploader getArchivo() {
		return archivo;
	}

	public void setArchivo(Uploader archivo) {
		this.archivo = archivo;
	}

	public String getMsgArchivo() {
		return msgArchivo;
	}

	public void setMsgArchivo(String msgArchivo) {
		this.msgArchivo = msgArchivo;
	}

	public PopupBean getPopup() {
		return popup;
	}

	public void setPopup(PopupBean popup) {
		this.popup = popup;
	}

	public TipoestructuraDAO getTeDAO() {
		return teDAO;
	}

	public void setTeDAO(TipoestructuraDAO teDAO) {
		this.teDAO = teDAO;
	}

	
}

/**
 * 
 */
package bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.icesoft.faces.webapp.xmlhttp.PersistentFacesState;

import dao.Archivo;
import dao.ArchivoDAO;
import dao.Documento;
import dao.DocumentoDAO;
import dao.EstadoDAO;

/**
 * @author SistemasSigesys
 *
 */
public class Exportar {
	private DocumentoDAO docDAO = new DocumentoDAO();
	private EstadoDAO estado = new EstadoDAO();
	private List<Documento> lista = new ArrayList<Documento>();
	//private String pathOrigen = "Users/fabian/Workspaces/MyEclipse 2016 CI/DIGIPLAN/DIGIPLAN";
	private String pathOrigen = "E:\\DIGIPLANPUENTESxEstados\\AntesdeD4\\planos\\";
	private String pathDestino = "F:\\Planos\\";
	//private String pathDestino = "Users/fabian/Workspaces/MyEclipse 2016 CI/DIGIPLAN/DIGIPLAN/Planos/";
	private boolean barraProgresoExportar = false;
	private int percent = 0;
	protected Thread progressThread;
	protected PersistentFacesState state;
	private String message = "";
	
	
	public Exportar() {
	}
	
	public void exportar(){
		barraProgresoExportar = true;
		state = PersistentFacesState.getInstance();
		progressThread = new Thread(new Runnable() {
		int con = 0;
			public void run() {
				try {
					int c = 0;
					message = "Archivos no encontrados: ";
					ArchivoDAO archivoDAO = new ArchivoDAO();
					for(Documento doc : lista){
						
						List<Archivo> archivos = archivoDAO.findLastByIdDocumento(doc.getIdDocumento());
						if(archivos != null && archivos.size() > 0)
							doc.setArchivo(archivos.get(0).getArchivo());
						if(doc.isExportar()){
							File archivo = new File(pathOrigen + doc.getArchivo());
							if(archivo.exists()){
								File archivoDestino = new File(pathDestino + doc.getArchivo());
								if(!archivoDestino.exists()){
									String archi = crearDirectorioDestino(pathDestino, doc) + crearNombreArchivo(doc);
									FileCopy(pathOrigen + doc.getArchivo(), pathDestino + archi);
									c++;
								}
							}else{
								c++;
								message += doc.getArchivo() + ". ";
							}
							updatePercent(con++ * 100 / lista.size());
							
						}
					}
					if(c != 0)
						message = "Registros exportados con exito.";
					else
						message = "copiados: " +c + " De: " + lista.size();
					updatePercent(100);
				}catch(Exception e){
					message = "Ocurrio un problema al exportar. ";
					e.printStackTrace();
				}
			
			}
		});
		progressThread.setDaemon(true);
		progressThread.start();
		
	}
	
	public String crearNombreArchivo(Documento doc){
		String archivo = "";
		if(doc.getTipodocumento().getNombre().length() > 20)
			archivo = doc.getTipodocumento().getNombre().substring(0, 19);
		else
			archivo = doc.getTipodocumento().getNombre();
		
		
		archivo += "_" + doc.getKmInicial() + "-" + doc.getKmFinal();
		archivo += "_" + doc.getArchivo();						
		
		return archivo;
	}
	
	public String crearDirectorioDestino(String rutaAbsoluta, Documento doc){
		String directorio = "";
		if(doc.getProyecto().getNombre().length() > 40)
			directorio = doc.getProyecto().getNombre().substring(0, 39);
		else
			directorio = doc.getProyecto().getNombre();
		
		if(doc.getTramo().getNombre().length() > 40)
			directorio += "/" + doc.getTramo().getNombre().substring(0, 39);
		else
			directorio += "/" + doc.getTramo().getNombre();
		
		directorio += "/";
		
		File directorioDestino = new File(rutaAbsoluta + directorio);
		if(!directorioDestino.exists())
			directorioDestino.mkdirs();
		
		return directorio;
	}
	public void abrirCarpeta(){
		String comando = "C:/Busqueda/Busqueda.xls";
		try {
			
			Runtime.getRuntime().exec("cmd /c start " + pathDestino); 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String closePopup(){
		barraProgresoExportar = false;
		percent = 0;
		return "";
	}
	
	public void updatePercent(int pct) {		
		try {
			percent = pct;
			if (percent >= 100) {
				//isRunning = false;
				
			}// if
			state.renderLater();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
		public void FileCopy(String sourceFile, String destinationFile) {
			System.out.println("Desde: " + sourceFile);
			System.out.println("Hacia: " + destinationFile);
			try {
				File inFile = new File(sourceFile);
				File outFile = new File(destinationFile);

				InputStream in = new FileInputStream(inFile);
				OutputStream out = new FileOutputStream(outFile);

				byte[] buf = new byte[1024];
				int len;
				
				while ((len = in.read(buf)) > 0) {  
					out.write(buf, 0, len);
				}

				in.close();
				out.close();
				inFile = null;
				outFile = null;
			} catch(IOException e) {
				System.err.println("Hubo un error de entrada/salida!!!");
				e.printStackTrace();
			}
		}

		public List<Documento> getLista() {
			return lista;
		}

		public void setLista(List<Documento> lista) {
			this.lista = lista;
		}

		public String getPathOrigen() {
			return pathOrigen;
		}

		public void setPathOrigen(String pathOrigen) {
			this.pathOrigen = pathOrigen;
		}

		public String getPathDestino() {
			return pathDestino;
		}

		public void setPathDestino(String pathDestino) {
			this.pathDestino = pathDestino;
		}

		public int getPercent() {
			return percent;
		}

		public void setPercent(int percent) {
			this.percent = percent;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public boolean isBarraProgresoExportar() {
			return barraProgresoExportar;
		}

		public void setBarraProgresoExportar(boolean barraProgresoExportar) {
			this.barraProgresoExportar = barraProgresoExportar;
		}

		
	

}

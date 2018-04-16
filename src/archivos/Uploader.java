package archivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.EventObject;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import bean.Captacion;
import com.icesoft.faces.component.inputfile.FileInfo;
import com.icesoft.faces.component.inputfile.InputFile;
import conf.Configuration;



public class Uploader{
	private int fileProgress = 0;
	private List fileList = Collections.synchronizedList(new ArrayList());
	private boolean subidaPdf = true;
	private InputFileData currentFile;
	public InputFileData getCurrentFile() {
		return currentFile;
	}
	public void setCurrentFile(InputFileData currentFile) {
		this.currentFile = currentFile;
	}
	private String ruta = "";
	private String archivo = "";
	private Calendar calendario = new GregorianCalendar();
	private Configuration conf = new Configuration();
	int hora;
	int minutos;
	int segundos;
	
	public  Uploader(){
		
	}
	//Subida de SEMARNAT
		public void saveFile(){
			try{
				hora = calendario.get(Calendar.HOUR);
				minutos = calendario.get(Calendar.MINUTE);
				segundos = calendario.get(Calendar.SECOND);
				subidaPdf = true;
				
				if(!fileList.isEmpty()){
					//generarPath();
					archivo = currentFile.getFile().getName();
					//System.out.println("Guardo");
					//File file = new File(conf.getBasePath()+ conf.getResourcePath() + generarPath());
					//System.out.println(conf.getBasePath()+ conf.getResourcePath() +ruta);
					//file.mkdirs();
					//System.out.println("Guardo");
					//FileOutputStream fos = new FileOutputStream(conf.getBasePath()+ conf.getResourcePath() + ruta + archivo );
					//System.out.println("Guardo");
			    	//fos.write(getBytesFromFile(currentFile.getFile()));
			    	//System.out.println("Guardo");
			    	//fos.close();	
			    	//System.out.println("Guardo");
			    	
				}else
					System.out.println("No Guardo");
			}catch(Exception e){
				System.out.println("Ocurrio un Error minimo al Guardar el Registro de archivo: " +e.toString());
			}
		}
		
		public String generarPath(){			
			return ruta;
		}
		
		public void setPath(String ruta){
			this.ruta = ruta;						
		}
		
		public void uploadFile(ActionEvent event) {
			try{
				InputFile inputFile = (InputFile) event.getSource();
		        FileInfo fileInfo = inputFile.getFileInfo();
		        if (fileInfo.getStatus() == FileInfo.SAVED) {
		        	String contentType = fileInfo.getContentType();
		           
			            currentFile = new InputFileData(fileInfo);     
			            //fileList.clear();
			            fileList.add(currentFile);
			            this.setSubidaPdf(false);
			            setFileProgress(0);
		            
		        }
			}catch(Exception e){
				System.out.println("Error en carga de fichero y es: " + e.toString());
			}
		}
		
		public void uploadFileExcel(ActionEvent event) {
			try{
				InputFile inputFile = (InputFile) event.getSource();
		        FileInfo fileInfo = inputFile.getFileInfo();
		        if (fileInfo.getStatus() == FileInfo.SAVED) {
		        	String contentType = fileInfo.getContentType();
		            Pattern pattern = Pattern.compile("application/vnd.ms-excel");
		            Matcher matcher = pattern.matcher(contentType.toLowerCase());
		            
			            currentFile = new InputFileData(fileInfo);     
			            fileList.clear();
			            fileList.add(currentFile);
			            this.setSubidaPdf(false);
			            setFileProgress(0);
		            
		            	fileProgress = 0;
		            
		        }
			}catch(Exception e){
				System.out.println("Error en carga de fichero y es: " + e.toString());
			}
		}
		
		public static byte[] getBytesFromFile(File file) throws IOException {
			InputStream is = new FileInputStream(file);
			// Get the size of the file
			long length = file.length();
			// You cannot create an array using a long type.
			// It needs to be an int type.
			// Before converting to an int type, check
			// to ensure that file is not larger than Integer.MAX_VALUE.
			if (length > Integer.MAX_VALUE) {
				System.out.println("Archivo Demasiado Largo");
				// File is too large
			}
			// Create the byte array to hold the data
			byte[] bytes = new byte[(int) length];
			// Read in the bytes
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
			// Ensure all the bytes have been read in
			if (offset < bytes.length) {
				throw new IOException("Could not completely read file "+ file.getName());
			}
			// Close the input stream and return bytes
			is.close();
			return bytes;
		}

		
		public void fileUploadProgress(EventObject event) {
			InputFile ifile = (InputFile) event.getSource();
			setFileProgress(ifile.getFileInfo().getPercent());
		}
		
		@SuppressWarnings("unchecked")
		public void removeUploadedFile(ActionEvent event) {
			// Get the inventory item ID from the context.
			FacesContext context = FacesContext.getCurrentInstance();
			Map map = context.getExternalContext().getRequestParameterMap();
			String fileName = (String) map.get("fileName");
			
			synchronized (fileList) {
				List temp  = Collections.synchronizedList(new ArrayList());
				for(InputFileData up : (List<InputFileData>)fileList){
					if(!up.getFile().getPath().equals(fileName))
						temp.add(up);
				}
				fileList = temp;
				//fileList.clear();
				currentFile = new InputFileData(new FileInfo());
				setFileProgress(0);
			}
			setSubidaPdf(true);
		}
		public void removeFile() {
			// Get the inventory item ID from the context.			
			synchronized (fileList) {
				InputFileData inputFileData;
				fileList.clear();
				currentFile = new InputFileData(new FileInfo());
				setFileProgress(0);
			}
			setSubidaPdf(true);
		}
		public boolean isSubidaPdf() {
			return subidaPdf;
		}

		public void setSubidaPdf(boolean subidaPdf) {
			this.subidaPdf = subidaPdf;
		}

		public int getFileProgress() {
			return fileProgress;
		}

		public void setFileProgress(int fileProgress) {
			this.fileProgress = fileProgress;
		}

		public List getFileList() {
			return fileList;
		}
		public String getRuta() {
			return ruta;
		}
		public void setRuta(String ruta) {
			this.ruta = ruta;
		}
		public String getArchivo() {
			return archivo;
		}
		public void setArchivo(String archivo) {
			this.archivo = archivo;
		}
}
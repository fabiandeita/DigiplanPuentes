package bean;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.Documento;

import jxl.Cell;
import jxl.CellType;
import jxl.CellView;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.DateFormat;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableHyperlink;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;



public class Excel{
	private WritableCellFormat timesBoldUnderline;
	private WritableCellFormat times;
	private Cell celda;
	private String inputFile = "";
	private String rutaAbsoluta = "";
	private WritableCellFormat numberFormat = new WritableCellFormat(new NumberFormat("###,###,##0.000"));		
	WritableFont arial10N = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD );
	WritableFont arial10NR = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD );
	
	DateFormat customDateFormat = new DateFormat ("d/m/yy");              
	WritableCellFormat dateFormat = new WritableCellFormat (customDateFormat); 
	
	
	private List<Documento> lista = new ArrayList<Documento>();
	public Excel(){
		try{
			arial10N.setColour(Colour.BLACK);
			arial10NR.setColour(Colour.RED);
		}catch(Exception e){
			
		}
	}
	
	
	
	public void setOutputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	
	
	public void writeExcelJurisdiccion(){
		try{
			System.out.println("Entro al write" + inputFile);
			File file = new File(inputFile);
			File espejo = new File("c:\\Servidor\\webapps\\DIGIPLANPUENTES\\resources\\Excel\\Plantilla.xls");
			Workbook workbook = null;
			workbook = Workbook.getWorkbook(espejo);			
			WritableWorkbook copy = Workbook.createWorkbook(file, workbook);
			WritableSheet libro = copy.getSheet(0); 
			libro.setName("Busqueda");
			
			System.out.println("Lo creo");			

			
			
			//Crear Contenido
			createContentJurisdiccion(libro);
			System.out.println("Creo el Content");
			
			copy.write(); 
			copy.close();
		}catch(Exception e){
			e.printStackTrace();
		}
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
	
	private void createContentJurisdiccion(WritableSheet sheet) throws WriteException, RowsExceededException {
		// Write a few number
		int fila = 9;
		for(Documento doc : lista){
			if(doc.isExportar()){
				addLabelNegritas(sheet, 0, fila, doc.getProyecto().getNumProy());
				addLabelNegritas(sheet, 1, fila, doc.getEstado().getNombre());
				addLabelNegritas(sheet, 2, fila, doc.getProyecto().getNombre());
				addLabelNegritas(sheet, 3, fila, doc.getTramo().getCarretera().getNombre());
				addLabelNegritas(sheet, 4, fila, doc.getTramo().getNombre());
				addLabelNegritas(sheet, 5, fila, doc.getOrigen().getNombre());
				
				addLabelNegritas(sheet, 6, fila, doc.getTipodocumento().getNombre());
				addLabelNegritas(sheet, 7, fila, doc.getTipoEstructura().getNombre());
				addLabelNegritas(sheet, 8, fila, doc.getKmInicial());
				addLabelNegritas(sheet, 9, fila, doc.getKmFinal());
				addFecha(sheet, 10, fila, doc.getFechaCreacion());
				addLabelNegritas(sheet, 11, fila, doc.getObservaciones());
				String archivo =  crearDirectorioDestino(rutaAbsoluta, doc)  + crearNombreArchivo(doc);
				addLinkFile(sheet, 12, fila, new File (archivo));
				fila++;
			}
		}							
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
	
	private void addLinkFile(WritableSheet sheet, int column, int row, File archivo){
		try        
		{
			dateFormat.setFont(arial10N);
			sheet.addHyperlink(new WritableHyperlink(column, row, archivo));
		}catch(Exception e){
			
		}
	}
	
	private void addFecha(WritableSheet sheet, int column, int row, Date fecha){
		try        
		{
			dateFormat.setFont(arial10N);
			sheet.addCell(new jxl.write.DateTime(column, row, fecha, dateFormat));
		}catch(Exception e){
			
		}
	}
	
	private void addCaption(WritableSheet sheet, int column, int row, String s)	throws RowsExceededException, WriteException {
		Label label;
		label = new Label(column, row, s, timesBoldUnderline);
		sheet.addCell(label);
	}

	private void addNumberIntInactivo(WritableSheet sheet, int column, int row,
			Integer integer) throws WriteException, RowsExceededException {
				
	
		WritableCellFormat fArial10NBordeJus = new WritableCellFormat ();			
		fArial10NBordeJus.setFont(arial10NR);
		
		Number number;
		number = new Number(column, row, integer, fArial10NBordeJus);
		sheet.addCell(number);
	}
	
	private void addNumberInt(WritableSheet sheet, int column, int row,
			Integer integer) throws WriteException, RowsExceededException {
		Number number;
		number = new Number(column, row, integer);
		sheet.addCell(number);
	}

	private void addNumberDouble(WritableSheet sheet, int column, int row,
			Double doble) throws WriteException, RowsExceededException {				
		
		sheet.addCell(new Number(column, row, doble, numberFormat));
	}
	
	private void addNumberDoubleInactivo(WritableSheet sheet, int column, int row,
			Double doble) throws WriteException, RowsExceededException {		
		
		
		
		Number number;				
		numberFormat.setFont(arial10NR);
		number = new Number(column, row, doble, numberFormat);
		sheet.addCell(number);
	}
	
	private void addNumberDoubleNegritas(WritableSheet sheet, int column, int row,
			Double doble) throws WriteException, RowsExceededException {
		Number number;	
		WritableFont arial10N = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);		
		numberFormat.setFont(arial10N);
		number = new Number(column, row, doble, numberFormat);
		sheet.addCell(number);
	}
	
	private void addLabel(WritableSheet sheet, int column, int row, String s)
			throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, s);
		sheet.addCell(label);
	}
	private void addLabelInactivo(WritableSheet sheet, int column, int row, String s)
			throws WriteException, RowsExceededException {
		
	
		WritableCellFormat fArial10NBordeJus = new WritableCellFormat ();			
		fArial10NBordeJus.setFont(arial10NR);
		
		Label label;
		label = new Label(column, row, s, fArial10NBordeJus);
		sheet.addCell(label);
	}
	private void addLabelNegritas(WritableSheet sheet, int column, int row, String s)
			throws WriteException, RowsExceededException {
		
				
		WritableCellFormat fArial10NBordeJus = new WritableCellFormat ();			
		fArial10NBordeJus.setFont(arial10N);		
		//fArial10NBordeJus.setWrap(true); aJUSTA EL TEXTO
		
		Label label;
		label = new Label(column, row, s, fArial10NBordeJus);
		sheet.addCell(label);
	}
	public void leerExcel2(){
		try{
			Workbook w = Workbook.getWorkbook(new File("C:/Chiapas/volcado/chiapas.xls"));
			Sheet sheet = w.getSheet(0);
			int numFilas = 0, tableroId = 0;
			Double altitud = 0.0, latitud = 0.0, longitud = 0.0, valor1 = 0.0, valor2 = 0.0, valor3 = 0.0, valor4 = 0.0, retro1 = 0.0, retro2 = 0.0, retro3 = 0.0, retro4 = 0.0, dt = 0.0, alt = 0.0;
			String cambio;
			numFilas = sheet.getRows()-1;	
			
			System.out.println("Numero de columnas: "+sheet.getColumns());
			System.out.println("Numero de filas: "+ numFilas);
			
			for(int fila = 9; fila<= numFilas; fila++){
				
				
				///GPS
				//Altitud
				celda = sheet.getCell(2, fila);		
    			if(celda.getType() != CellType.EMPTY){
					altitud = Double.parseDouble(celda.getContents());
					
					System.out.println(altitud);	
				}
    			//Latitud
    			celda = sheet.getCell(3, fila);		
    			if(celda.getType() != CellType.EMPTY){
					latitud = Double.parseDouble(celda.getContents());
					
					System.out.println(latitud);	
				}
    			//Longitud
    			celda = sheet.getCell(4, fila);		
    			if(celda.getType() != CellType.EMPTY){
					longitud = Double.parseDouble(celda.getContents());
					System.out.println(longitud);		
				}
    			
    			//Retro 1
				celda = sheet.getCell(17, fila);		
    			if(celda.getType() != CellType.EMPTY){
    				valor1 = Double.parseDouble(celda.getContents());
					
					System.out.println(valor1);	
				}
    			//Retro 2
    			celda = sheet.getCell(18, fila);		
    			if(celda.getType() != CellType.EMPTY){
    				valor2 = Double.parseDouble(celda.getContents());
					
					System.out.println(valor2);	
				}
    			//Retro 3
    			celda = sheet.getCell(19, fila);		
    			if(celda.getType() != CellType.EMPTY){
    				valor3 = Double.parseDouble(celda.getContents());
					System.out.println(valor3);		
				}
				
    			//Retro 4
    			celda = sheet.getCell(20, fila);		
    			if(celda.getType() != CellType.EMPTY){
    				valor4 = Double.parseDouble(celda.getContents());
					System.out.println(valor4);		
				}
    			
    			//Retro 1
				celda = sheet.getCell(22, fila);		
    			if(celda.getType() != CellType.EMPTY){
    				retro1 = Double.parseDouble(celda.getContents());
					
					System.out.println(retro1);	
				}
    			//Retro 2
    			celda = sheet.getCell(23, fila);		
    			if(celda.getType() != CellType.EMPTY){
    				retro2 = Double.parseDouble(celda.getContents());
					
					System.out.println(retro2);	
				}
    			//Retro 3
    			celda = sheet.getCell(24, fila);		
    			if(celda.getType() != CellType.EMPTY){
    				retro3 = Double.parseDouble(celda.getContents());
					System.out.println(retro3);		
				}
				
    			//Retro 4
    			celda = sheet.getCell(25, fila);		
    			if(celda.getType() != CellType.EMPTY){
    				retro4 = Double.parseDouble(celda.getContents());
					System.out.println(retro4);		
				}
    			
    			//Cambio ?
    			celda = sheet.getCell(38, fila);		
    			if(celda.getType() != CellType.EMPTY){
    				cambio = celda.getContents();
    				if(cambio.equals("-"))
    					System.out.println("Se cambia se�alamiento");		
				}
    			
    			if(retro1.equals("") && retro2.equals("") && retro3.equals("") && retro4.equals("") ){
    				System.out.println("Se elimina");
    			}
    			
    			
			}
		}catch(Exception e){
			System.out.println(e.toString());
		}
	
	}
	



	public String getInputFile() {
		return inputFile;
	}



	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}



	public List<Documento> getLista() {
		return lista;
	}



	public void setLista(List<Documento> lista) {
		this.lista = lista;
	}



	public String getRutaAbsoluta() {
		return rutaAbsoluta;
	}



	public void setRutaAbsoluta(String rutaAbsoluta) {
		this.rutaAbsoluta = rutaAbsoluta;
	}




	
}

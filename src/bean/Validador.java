package bean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

public class Validador{
	
	private static final String NAME = "[a-zA-Z-Ñ-ñ-0-9-\\s-,-.]+";
	private static final String CADENAMIENTO  = "{1}[0-9]{3}[.]{1}[0-9]{2}";
	private static final String CADENAMIENTO2 = "{1}[0-9]{3}[.]{1}[0-9]{3}";
	private static final String SUPERFICIE = "[0-9]{1,3}[.]{1}[0-9]{2}";
	private static final String SUPERFICIE2 = "[0-9]{1,3}[,]{1}[0-9]{3}[.]{1}[0-9]{2}";
	private static final String SUPERFICIE3 = "[0-9]{1,3}[,]{1}[0-9]{3}[,]{1}[0-9]{3}[.]{1}[0-9]{2}";

	private static final String IGUALDAD = "[0-9]{3}";
	private static final String LATITUD = "[0-9]{2}[.]{1}[0-9]{6}";
	private static final String LONGITUD = "[-]{1}[0-9]{2}[.]{1}[0-9]{6}";
	private static final String CATALOGO = "[a-zA-Z-Ñ-ñ-0-9-\\s]+";
	private static final String DOCUMENTO = "[a-zA-Z-0-9-\\s-(-)]+";
	private String message = "";
	private boolean update = false;
	
	
	public Validador(){		
	}
	
	public boolean validarNombre(String nombre){
		Pattern pat = Pattern.compile(NAME);
		Matcher matcher = pat.matcher(nombre);
		if (nombre.length() < 1) {
			message = message + "Escriba algun Nombre \n";
			update = false;
		} else if (!(matcher.matches())) {
			message = message + "Error en Nombre \n";
			update = false;
		} else
			update = true;
		return update;
	}

	public boolean validarCadenamiento(String cadenamiento){			
		try{
			Double.parseDouble(cadenamiento);
			return true;
			
		}catch(Exception e){
			return false;
		}
	}

	public boolean validarSuperficie(String superficie){
		Pattern pat = Pattern.compile(SUPERFICIE);
		Matcher matcher = pat.matcher(superficie);
		Pattern pat2 = Pattern.compile(SUPERFICIE2);
		Matcher matcher2 = pat2.matcher(superficie);
		Pattern pat3 = Pattern.compile(SUPERFICIE3);
		Matcher matcher3 = pat3.matcher(superficie);

		if (!(matcher.matches())){
			if(!matcher2.matches()){
					if(!matcher3.matches())
						update = false;
					else
						update = true;
			}else
				update = true;
		}else
			update = true;
		return update;
	}

	/*public boolean validarSuperficie(String superficie){
		Pattern pat = Pattern.compile(SUPERFICIE);
		Matcher matcher = pat.matcher(superficie);
		if (!(matcher.matches())){
			update = false;
		}else
			update = true;
		return update;
	}*/
	
	public boolean validarSuperficie2(String superficie){
		Pattern pat = Pattern.compile(SUPERFICIE2);
		Matcher matcher = pat.matcher(superficie);
		if (!(matcher.matches())){
			update = false;
		}else
			update = true;
		return update;
	}

	public boolean validarSuperficie3(String superficie){
		Pattern pat = Pattern.compile(SUPERFICIE3);
		Matcher matcher = pat.matcher(superficie);
		if (!(matcher.matches())){
			update = false;
		}else
			update = true;
		return update;
	}
	
	public boolean validarIgualdad(String igualdad){		
		Pattern pat = Pattern.compile(IGUALDAD);
		Matcher matcher = pat.matcher(igualdad);
		if (!(matcher.matches())){
			update = false;
		}else
			update = true;
		return update;
	}
	
	public boolean validarLatitud(String latitud){		
		Pattern pat = Pattern.compile(LATITUD);
		Matcher matcher = pat.matcher(latitud);
		if (!(matcher.matches())){
			update = false;
		}else
			update = true;
		return update;
	}

	public boolean validarLongitud(String longitud){		
		Pattern pat = Pattern.compile(LONGITUD);
		Matcher matcher = pat.matcher(longitud);
		if (!(matcher.matches())){
			update = false;
		}else
			update = true;
		return update;
	}
	
	public boolean validarCatalogo(String catalogo){
		Pattern pat = Pattern.compile(CATALOGO);
		Matcher matcher = pat.matcher(catalogo);
		if (catalogo.length() < 1) {
			//message = message + "Escriba algun Nombre \n";
			update = false;
		} else if (!(matcher.matches())) {
			//message = message + "Error en Nombre \n";
			update = false;
		} else
			update = true;
		return update;
	}

	public boolean validarDocumento(String documento){
		Pattern pat = Pattern.compile(DOCUMENTO);
		Matcher matcher = pat.matcher(documento);
		if (documento.length() < 1) {
			//message = message + "Escriba algun Nombre \n";
			update = false;
		} else if (!(matcher.matches())) {
			//message = message + "Error en Nombre \n";
			update = false;
		} else
			update = true;
		return update;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public boolean isUpdate() {
		return update;
	}

	public static String getCatalogo() {
		return CATALOGO;
	}

	public static String getDocumento() {
		return DOCUMENTO;
	}

	public static String getLatitud() {
		return LATITUD;
	}

	public static String getLongitud() {
		return LONGITUD;
	}

	public static String getSuperficie2() {
		return SUPERFICIE2;
	}

	public static String getSuperficie3() {
		return SUPERFICIE3;
	}
}
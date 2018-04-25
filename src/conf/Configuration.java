package conf;

import java.util.TimeZone;


public class Configuration{
	private String systemName = "SISTEMA DE CONSULTA DE PLANOS";
	//private String basePath = "C:\\Servidor\\webapps\\DIGIPLAN\\"; //Donde se encuentra la aplicación
	private String basePath = "C:\\Users\\fdeitaze\\Workspaces\\MyEclipse 2016 CI\\.metadata\\.me_tcat7\\webapps\\DIGIPLANPUENTES\\"; //Donde se encuentra la aplicación
	private String resourcePath = "resources\\";
	
	
	public Configuration(){
		
	}

	public TimeZone getTimeZone() {
		return java.util.TimeZone.getDefault();
	}
	
	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}
	
}
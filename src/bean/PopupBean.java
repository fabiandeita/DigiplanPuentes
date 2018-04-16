package bean;


import javax.faces.event.ActionEvent;
import java.io.Serializable;


public class PopupBean implements Serializable {
  
    private String modalMessage;

    private String titulo = "";
    private String mensajePrincipal = "";
    
    private boolean mostrar = false;
    private boolean modalRendered = false;
    private boolean centrado = true;

    public void cerrar(){
    	setMostrar(false);
    }

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setMensajePrincipal(String mensajePrincipal) {
		this.mensajePrincipal = mensajePrincipal;
	}

	public String getMensajePrincipal() {
		return mensajePrincipal;
	}

	public void setMostrar(boolean mostrar) {
		this.mostrar = mostrar;
	}

	public boolean isMostrar() {
		return mostrar;
	}

	public void setModalRendered(boolean modalRendered) {
		this.modalRendered = modalRendered;
	}

	public boolean isModalRendered() {
		return modalRendered;
	}

	public void setCentrado(boolean centrado) {
		this.centrado = centrado;
	}

	public boolean isCentrado() {
		return centrado;
	}

	public void setModalMessage(String modalMessage) {
		this.modalMessage = modalMessage;
	}

	public String getModalMessage() {
		return modalMessage;
	}
    
   
}

/**
 * 
 */
package bean;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import dao.Usuario;
import dao.UsuarioDAO;

/**
 * @author Fabian De Ita
 *
 */
public class Logeo {
	private Usuario us = new Usuario();
	private UsuarioDAO usDAO = new UsuarioDAO();
	private boolean showPopup = true;
	
	public Logeo() {
		us.setNombre("DIGIPLANPUENTES");
	}

	public void login(){
		try{
			System.out.println("Acceso Login");
			if(!us.getUsuario().isEmpty() && !us.getContrasena().isEmpty()){
				us = (Usuario)usDAO.logeo(us.getUsuario(), us.getContrasena()).get(0); 
				if(us != null)
					showPopup = false;
				else
					showPopup = true;
			}
		}catch(Exception e){
			e.printStackTrace();
			showPopup = true;
		}
	}

	public Usuario getUs() {
		return us;
	}


	public void setUs(Usuario us) {
		this.us = us;
	}

	public boolean isShowPopup() {
		return showPopup;
	}

	public void setShowPopup(boolean showPopup) {
		this.showPopup = showPopup;
	}
	
	public String logout() {
		us = new Usuario();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.invalidate();
		return "toLogin";
	}
	
}

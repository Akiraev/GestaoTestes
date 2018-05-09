package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.Login;
import dominio.Usuario;
import util.Mensagem;

@ManagedBean(name = "loginBean")
@ViewScoped
public class LoginBean {
	Usuario usuario;

	public LoginBean() {
		usuario = new Usuario();
	}

	public String logar() {
		if (Login.logar(usuario)) {
			Mensagem.sucesso("Logado");
			return "/cadastrousuario.xhtml?faces-redirect=true";
			
		} else {
			Mensagem.falha("Não logou");
			return "";
		}
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}

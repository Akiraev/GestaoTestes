package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.DaoLogin;
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
		if (DaoLogin.logar(usuario)) {
			Mensagem.sucesso("Logado");
			return "/dashboard.xhtml?faces-redirect=true";
			
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

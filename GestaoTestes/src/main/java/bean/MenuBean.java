package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "menuBean")
@ViewScoped
public class MenuBean {

	public String menuUsuario() {
		return "/CadastroDeUsuario.xhtml?faces-redirect=true";
	}
	public String menuProjeto() {
		return "/CadastroDeProjetos.xhtml?faces-redirect=true";
	}
	public String menuCliente() {
		return "/CadastroDeClientes.xhtml?faces-redirect=true";
	}
	public String menuProjetoHistorico() {
		return "/cadastroDeProjetoHistorico.xhtml?faces-redirect=true";
	}
	
	public String menuDash() {
		return "/dashboard.xhtml?faces-redirect=true";
	}
}

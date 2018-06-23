package bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dominio.Usuario;
import enumeradores.AtributesSession;
import factory.SessionContextFactory;

@ManagedBean(name = "menuBean")
@ViewScoped
public class MenuBean {
	private String usuarioLogado;

	@PostConstruct
	public void init() {
		Usuario usuario = (Usuario) SessionContextFactory.getInstance()
				.getAttribute(AtributesSession.USUARIO_LOGADO.getValue());

		if (usuario != null) {
			String[] nomes = usuario.getNomeUsuario().split(" ");

			if (nomes[0].isEmpty() || nomes[0] == null)
				this.usuarioLogado = "Bom dia!";
			
			else
				this.usuarioLogado = nomes[0];

		} else {
			this.usuarioLogado = "Bom dia!";

		}

	}

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

	public String menuSair() {
		SessionContextFactory.getInstance().removeSessionMap();
		SessionContextFactory.getInstance().encerrarSessao();
		SessionContextFactory.setNulInstanceSessionContext();
		return "/login.xhtml?faces-redirect=true";
	}

	public String getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(String usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
}

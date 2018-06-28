package bean;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.imageio.ImageIO;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import dominio.Usuario;
import enumeradores.AtributesSession;
import factory.SessionContextFactory;

@ManagedBean(name = "menuBean")
@ViewScoped
public class MenuBean {
	private String usuarioLogado;
	private StreamedContent imagen;

	@PostConstruct
	public void init() {
		Usuario usuario = (Usuario) SessionContextFactory.getInstance()
				.getAttribute(AtributesSession.USUARIO_LOGADO.getValue());

		if (usuario != null) {
			String[] nomes = usuario.getNomeUsuario().split(" ");
			if (usuario.getImagen() != null) {
				this.imagen = new DefaultStreamedContent(new ByteArrayInputStream(usuario.getImagen()));
				
			} else {
				

			}

			if (nomes[0].isEmpty() || nomes[0] == null)
				this.usuarioLogado = "Bem vindo!";

			else
				this.usuarioLogado = "Olá, " + nomes[0] + "!";

		} else {
			this.usuarioLogado = "Bem vindo!";

		}

	}

	public BufferedImage img(byte[] imagen) {
		try {
			String nomeDaImagem = "img" + System.currentTimeMillis() + ".jpg";
			BufferedImage img = null;
			img = ImageIO.read(new ByteArrayInputStream(imagen));
			ImageIO.write(img, "JPG", new File("C:\\idoctor\\" + nomeDaImagem));
			return img;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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

	public StreamedContent getImagen() {
		return imagen;
	}

	public void setImagen(StreamedContent imagen) {
		this.imagen = imagen;
	}
}

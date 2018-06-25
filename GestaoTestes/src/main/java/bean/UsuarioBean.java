package bean;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.DaoUsuario;
import dominio.Usuario;
import enumeradores.CargoUsuario;
import enumeradores.DireitoUsuario;
import enumeradores.NivelUsuario;
import enumeradores.Status;
import util.Mensagem;
import util.PDF;

@ManagedBean(name = "cadastroUsuarioBean")
@ViewScoped
public class UsuarioBean {
	private Usuario usuario;
	private ArrayList<Usuario> usuarios;
	private List<Usuario> usuariosPesquisados;
	private String usuarioPesquisado;

	public UsuarioBean() {
		this.usuario = new Usuario();
	}

	@PostConstruct
	public void init() {
		this.usuarios = (ArrayList<Usuario>) DaoUsuario.listarUsuarios();
	}

	public void salvarUsuario() {
		boolean jaExisteEmail = false;
		boolean jaExisteNome = false;
		try {
			for (Usuario us : this.usuarios) {
				if (us.getEmail().equals(usuario.getEmail())) {
					jaExisteEmail = true;
					break;
				}
				if (us.getNomeUsuario().equals(usuario.getNomeUsuario())) {
					jaExisteNome = true;
					break;
				}
			}
			if (jaExisteEmail && usuario.getCodUsuario() == null) {
				Mensagem.falha(
						"Esse email já está cadastrado\npesquise caso deseje altera-lo\nNão foi possível salvar");
			} else if (jaExisteNome) {
				Mensagem.falha("Esse nome já está cadastrado\npesquise caso deseje altera-lo\nNão foi possível salvar");
			} else {
				DaoUsuario.salvar(this.usuario);
				this.limparUsuario();
				this.usuarios = DaoUsuario.listarUsuarios();
				Mensagem.sucesso("Cadastro salvo com sucesso!");
			}

		} catch (Exception e) {
			Mensagem.falha("Não foi possível salvar/n" + e.toString());
		}
	}

	public void gerarPdfUsuario() {
		if (PDF.relatorioUsuario(this.usuario)) {
			Mensagem.sucesso("PDF gerado");
		} else {
			Mensagem.falha("Não gerou PDF");
		}
	}

	public void buscarUsuarios() {
		this.usuariosPesquisados = new ArrayList<Usuario>();
		if (this.usuarioPesquisado.length() < 1 || this.usuarioPesquisado == null) {
			Mensagem.falha("Prencha o nome do usuario para realizar a pesquisa");
		} else {

			for (Usuario nomes : this.usuarios) {
				if (nomes.getNomeUsuario().toLowerCase().contains(this.usuarioPesquisado.toLowerCase())) {
					Usuario usua = new Usuario();
					usua = nomes;
					usuariosPesquisados.add(usua);
				}
			}
			if (usuariosPesquisados.size() == 0) {
				Mensagem.falha("Usuário não existe");
			}
			usuarioPesquisado = null;
		}
	}

	public void limparUsuario() {
		this.usuario = new Usuario();
		this.usuariosPesquisados = null;
	}

	@SuppressWarnings("unlikely-arg-type")
	@Transient
	public static boolean permisao(Usuario usuario) {
		if (usuario.getDireitoUsuario().getDireito().equals(DireitoUsuario.ANALISTA))
			return false;
		return true;
	}

	public ArrayList<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Status[] getStatus() {
		return Status.values();
	}

	public DireitoUsuario[] getDireito() {
		return DireitoUsuario.values();
	}

	public CargoUsuario[] getCargo() {
		return CargoUsuario.values();
	}
	
	public NivelUsuario[] getNivel() {
		return NivelUsuario.values();
	}


	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuariosPesquisados() {
		return usuariosPesquisados;
	}

	public void setUsuariosPesquisados(List<Usuario> usuariosPesquisados) {
		this.usuariosPesquisados = usuariosPesquisados;
	}

	public String getUsuarioPesquisado() {
		return usuarioPesquisado;
	}

	public void setUsuarioPesquisado(String usuarioPesquisado) {
		this.usuarioPesquisado = usuarioPesquisado;
	}

}

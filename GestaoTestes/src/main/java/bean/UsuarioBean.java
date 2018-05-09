package bean;

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

@ManagedBean(name = "CadastroUsuarioBean")
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
		try {
			for (Usuario us : this.usuarios) {
				if (us.getEmail().equals(usuario.getEmail())) {
					jaExisteEmail = true;
					break;
				}
			}
			if (jaExisteEmail && usuario.getCodUsuario() == null) {
				Mensagem.falha("Esse email ja está cadastrado\nNão foi possível salvar");
			} else {
				DaoUsuario.salvar(this.usuario);
				// this.usuarios = null;
				Usuario.limparUsuario(this.usuario);
				this.usuarios = DaoUsuario.listarUsuarios();
				Mensagem.sucesso("Cadastro salvo com sucesso!");
			}

		} catch (Exception e) {
			Mensagem.falha("Não foi possível salvar/n" + e.toString());
		}
	}
	
	public void gerarPdfUsuario() {
		if(PDF.usuario(this.usuario)) {
			Mensagem.sucesso("PDF gerado");
		}else {
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
			if(usuariosPesquisados.size() == 0) {
				Mensagem.falha("Usuário não existe");
			}
			usuarioPesquisado = null;
		}
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

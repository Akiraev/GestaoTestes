package bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import dao.DaoCliente;
import dao.DaoProjeto;
import dao.DaoUsuario;
import dominio.Cliente;
import dominio.Projeto;
import dominio.Usuario;
import enumeradores.DireitoUsuario;
import enumeradores.Status;
import relatorios.Relatorio;
import util.Mensagem;

@ManagedBean(name = "cadastroProjetoBean")
@ViewScoped
public class ProjetoBean {
	private StreamedContent file;
	private Projeto projeto;
	private String projetoPesquisado;
	private List<Projeto> projetosPesquisados;
	private List<Projeto> projetos;
	private List<Cliente> clientesAutoComplete;
	private List<Usuario> usuariosAutoComplete;

	public ProjetoBean() {
		this.projeto = new Projeto();
	}

	@PostConstruct
	public void init() {
		this.projetos = DaoProjeto.listarProjetos();
		this.baixarRelatorio();
		this.clientesAutoComplete = DaoCliente.listarClientesAtivos();
		this.usuariosAutoComplete = DaoUsuario.listarUsuariosAtivos();
	}

	public void limparCampo() {
		this.projeto = new Projeto();
		this.projetosPesquisados = null;
	}

	public void salvarProjeto() {
		boolean nomeProjetoJaExiste = false;
		if (this.projeto.getAnalista() == null) {
			Mensagem.falha("Cadastre um analista responsável para esse projeto ");
		} else if (this.projeto.getGerente() == null) {
			Mensagem.falha("Cadastre um gerente responsável para esse projeto ");
		} else if (this.projeto.getCliente() == null) {
			Mensagem.falha("Cadastre um cliente para esse projeto");
		} else {
			try {
				for (Projeto p : this.projetos) {
					if (p.getNomeProjeto().equals(this.projeto.getNomeProjeto())) {
						nomeProjetoJaExiste = true;
						break;
					}
				}
				if (nomeProjetoJaExiste && projeto.getCodProjeto() == null) {
					Mensagem.falha(
							"Esse projeto já esta cadastrado\ncaso deseje altera-lo faça uma busca\nNão foi possível salvar projeto");
				} else {
					DaoProjeto.salvar(this.projeto);
					this.limparCampo();
					this.projetos = DaoProjeto.listarProjetos();

					Mensagem.sucesso("Cadastro salvo com sucesso!");
				}

			} catch (Exception e) {
				Mensagem.falha("Não foi possível salvar/n" + e.toString());
			}
		}
	}

	public void buscarProjetos() {
		this.projetosPesquisados = new ArrayList<Projeto>();
		if (this.projetoPesquisado.length() < 1 || this.projetoPesquisado == null) {
			Mensagem.falha("Prencha o nome do projeto para realizar a pesquisa");
		} else {
			for (Projeto nome : this.projetos) {
				if (nome.getNomeProjeto().toLowerCase().contains(this.projetoPesquisado.toLowerCase())) {
					Projeto projeto = new Projeto();
					projeto = nome;
					projetosPesquisados.add(projeto);
				}
			}
			if (projetosPesquisados.size() == 0) {
				Mensagem.falha("Projeto não existe");
			}
			projetoPesquisado = null;
		}
	}

	public List<Cliente> clienteAutoComplete(String a) {
		return this.clientesAutoComplete;
	}

	public List<Usuario> usuarioGerenteAutoComplete(String b) {
		List<Usuario> usuariosGerenteAutoComplete = new ArrayList<>();
		for (Usuario usuGe : this.usuariosAutoComplete) {
			if (usuGe.getDireitoUsuario().equals(DireitoUsuario.GERENTE)) {
				Usuario u = new Usuario();
				u = usuGe;
				usuariosGerenteAutoComplete.add(u);
			}
		}
		return usuariosGerenteAutoComplete;
	}

	public List<Usuario> usuarioAnalistaAutoComplete(String c) {
		List<Usuario> usuariosAnalistaAutoComplete = new ArrayList<>();
		for (Usuario usuAna : this.usuariosAutoComplete) {
			if (usuAna.getDireitoUsuario().equals(DireitoUsuario.ANALISTA)) {
				Usuario u = new Usuario();
				u = usuAna;
				usuariosAnalistaAutoComplete.add(u);
			}
		}
		return usuariosAnalistaAutoComplete;

	}

	public void baixarRelatorio() {

		if (this.projetos.size() > 0) {
			try {
				Relatorio.projetos(this.projetos);
				System.out.println("Tamanho do projeto" + this.projetos.size());

				String filePath = new File("RelatórioDeProjetos.pdf").getAbsolutePath();
				String canonicalPath = new File(".").getCanonicalPath();
				System.out.println(filePath);
				System.out.println(canonicalPath);

				InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("RelatórioDeProjetos.pdf");
				System.out.println(stream == null);
				this.file = new DefaultStreamedContent(stream, ".", "RelatórioDeProjetos.pdf");
				System.out.println(file == null);

			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			Mensagem.falha("Sem projetos para download");
		}
	}

	public Status[] getStatus() {
		return Status.values();
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public String getProjetoPesquisado() {
		return projetoPesquisado;
	}

	public void setProjetoPesquisado(String projetoPesquisado) {
		this.projetoPesquisado = projetoPesquisado;
	}

	public StreamedContent getFile() {
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public List<Projeto> getProjetosPesquisados() {
		return projetosPesquisados;
	}

	public void setProjetosPesquisados(List<Projeto> projetosPesquisados) {
		this.projetosPesquisados = projetosPesquisados;
	}

	public List<Projeto> getProjetos() {
		return projetos;
	}

	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}

	public List<Cliente> getClientesAutoComplete() {
		return clientesAutoComplete;
	}

	public void setClientesAutoComplete(List<Cliente> clientesAutoComplete) {
		this.clientesAutoComplete = clientesAutoComplete;
	}

	public List<Usuario> getUsuariosAutoComplete() {
		return usuariosAutoComplete;
	}

	public void setUsuariosAutoComplete(List<Usuario> usuariosAutoComplete) {
		this.usuariosAutoComplete = usuariosAutoComplete;
	}
}
package bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.DaoCheckPoint;
import dao.DaoHistorico;
import dao.DaoProjeto;
import dao.DaoUsuario;
import dominio.CheckPoint;
import dominio.Historico;
import dominio.Projeto;
import dominio.Usuario;
import enumeradores.AcaoHistorico;
import enumeradores.Status;
import enumeradores.StatusHistorioco;
import enumeradores.TipoAcaoHistorico;
import util.Mensagem;

@ManagedBean(name = "projetoHistoricoBean")
@ViewScoped
public class ProjetoHistoricoBean {
	private String nomeProjeto;
	private String nomeCliente;
	private Historico historico;
	private List<Usuario> usuariosAutoComplete;
	private List<Projeto> projetosPesquisado;
	private List<Projeto> projetos;
	private List<Historico> historicos;
	private Projeto projeto;

	@PostConstruct
	public void init() {
		this.historico = new Historico();
		this.projetos = DaoProjeto.listarProjetosAtivos();
		this.usuariosAutoComplete = DaoUsuario.listarUsuariosAtivos();
	}

	public void buscarProjeto() {
		if ((this.nomeCliente.isEmpty() || this.nomeCliente == null)
				&& (this.nomeProjeto.isEmpty() || this.nomeProjeto == null)) {
			Mensagem.falha("Necessário preencher nome do projeto ou\n cliente para realizar busca.");

		} else {

			if (this.projetos == null || this.projetos.size() <= 0) {
				Mensagem.falha("Não existe projetos cadastrados");

			} else {

				this.projetosPesquisado = new ArrayList<>();

				if (!(this.nomeCliente.isEmpty() || this.nomeCliente == null)
						&& !(this.nomeProjeto.isEmpty() || this.nomeProjeto == null)) {

					for (Projeto p : this.projetos) {

						if (p.getCliente().getNomeCliente().toLowerCase().contains(this.nomeCliente.toLowerCase())
								|| p.getNomeProjeto().toLowerCase().contains(this.nomeProjeto.toLowerCase())) {

							p = DaoProjeto.buscaProjetoLazy(p);

							if (p.getCheckPoint() == null)
								p.setCheckPoint(new CheckPoint());

							this.projetosPesquisado.add(p);
						}
					}

				} else if (!(this.nomeCliente.isEmpty() || this.nomeCliente == null)) {

					for (Projeto p : this.projetos) {

						if (p.getCliente().getNomeCliente().toLowerCase().contains(this.nomeCliente.toLowerCase())) {
							p = DaoProjeto.buscaProjetoLazy(p);

							if (p.getCheckPoint() == null)
								p.setCheckPoint(new CheckPoint());

							this.projetosPesquisado.add(p);

						}
					}

				} else if (!(this.nomeProjeto.isEmpty() || this.nomeProjeto == null)) {

					for (Projeto p : this.projetos) {

						if (p.getNomeProjeto().toLowerCase().contains(this.nomeProjeto.toLowerCase())) {
							p = DaoProjeto.buscaProjetoLazy(p);

							if (p.getCheckPoint() == null)
								p.setCheckPoint(new CheckPoint());

							this.projetosPesquisado.add(p);

						}
					}
				}

				if (this.projetosPesquisado.size() <= 0) {

					Mensagem.falha("Não existe projetos ou clientes com esse nome.");

				}

				this.nomeCliente = null;
				this.nomeProjeto = null;
			}
		}
	}

	public void salvaProjetoHistorico() {
		this.projeto.getCheckPoint().setProjeto(this.projeto);
		if (DaoCheckPoint.salvaCheckPoint(this.projeto.getCheckPoint())) {
			
			if (DaoProjeto.salvar(this.projeto)) {
				Mensagem.sucesso("Projeto salvo com sucesso!");

			} else {
				Mensagem.falha("Não foi possível salvar este Projeto/Historico");
				
			}

		} else
			Mensagem.falha("Não foi possível salvar o checkpoint");
		
	}

	public void excluirHistorico(Historico historico) {
		if (DaoHistorico.excluirHistorico(historico)) {
			this.projeto = DaoProjeto.buscaProjetoLazy(this.projeto);

			if (this.projeto.getCheckPoint() == null)
				this.projeto.setCheckPoint(new CheckPoint());

			out: for (Projeto p : this.projetosPesquisado) {
				if (p.getCodProjeto().equals(this.projeto.getCodProjeto())) {
					this.projetosPesquisado.remove(p);
					this.projetosPesquisado.add(this.projeto);
					break out;
				}
			}

			Mensagem.sucesso("Histórico excluido com sucesso.");

		} else {
			Mensagem.sucesso("Não foi possível excluir o projeto.");
		}
	}

	public void adicionaHistorico() {

		if (DaoHistorico.salvaHistorico(this.historico)) {

			this.projeto = DaoProjeto.buscaProjetoLazy(this.projeto);

			if (this.projeto.getCheckPoint() == null)
				this.projeto.setCheckPoint(new CheckPoint());

			out: for (Projeto p : this.projetosPesquisado) {
				if (p.getCodProjeto().equals(this.projeto.getCodProjeto())) {
					this.projetosPesquisado.remove(p);
					this.projetosPesquisado.add(this.projeto);
					break out;
				}
			}

			Mensagem.sucesso("Registro realizado com sucesso");

		} else {
			Mensagem.sucesso("Não foi possível realizar o registro");

		}

	}

	public void iniciaHistorico() {
		this.historico = new Historico();
		this.historico.setProjeto(this.projeto);
	}

	public void limparCampos() {
		this.projeto = null;
		this.nomeProjeto = null;
		this.nomeCliente = null;
		this.projetosPesquisado = null;
		this.historico = new Historico();
		this.projetos = DaoProjeto.listarProjetosAtivos();
		this.usuariosAutoComplete = DaoUsuario.listarUsuariosAtivos();
	}

	public List<Usuario> usuarioAutoComplete(String complete) {
		return this.usuariosAutoComplete;
	}

	public Status[] getStatus() {
		return Status.values();
	}

	public StatusHistorioco[] getStatusHistorico() {
		return StatusHistorioco.values();
	}

	public TipoAcaoHistorico[] getTipoAcaoHistorico() {
		return TipoAcaoHistorico.values();
	}

	public AcaoHistorico[] getAcaoHistorico() {
		return AcaoHistorico.values();
	}

	public Historico getHistorico() {
		return historico;
	}

	public void setHistorico(Historico historico) {
		this.historico = historico;
	}

	public String getNomeProjeto() {
		return nomeProjeto;
	}

	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public List<Projeto> getProjetos() {
		return projetos;
	}

	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public List<Projeto> getProjetosPesquisado() {
		return projetosPesquisado;
	}

	public void setProjetosPesquisado(List<Projeto> projetosPesquisado) {
		this.projetosPesquisado = projetosPesquisado;
	}

	public List<Historico> getHistoricos() {
		return historicos;
	}

	public void setHistoricos(List<Historico> historicos) {
		this.historicos = historicos;
	}

}

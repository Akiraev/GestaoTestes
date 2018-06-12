package bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.DaoCheckPoint;
import dao.DaoHistorico;
import dao.DaoProjeto;
import dao.DaoProjetoHistorico;
import dao.DaoUsuario;
import dominio.CheckPoint;
import dominio.Historico;
import dominio.Projeto;
import dominio.ProjetoHistorico;
import dominio.Usuario;
import enumeradores.AcaoHistorico;
import enumeradores.DireitoUsuario;
import enumeradores.Status;
import enumeradores.StatusHistorioco;
import enumeradores.TipoAcaoHistorico;
import util.Mensagem;

@ManagedBean(name = "projetoHistoricoBean")
@ViewScoped
public class ProjetoHistoricoBean {
	private Long id = 0l;
	private boolean novoHistorico = false;
	private boolean velhoHistorico = false;
	private String nomeProjeto;
	private String nomeCliente;
	private Historico historico;
	private CheckPoint checkPoint;
	private ProjetoHistorico projetoHistorico;
	private List<Historico> historicoAlterado;
	private List<Historico> historicoNovo;
	private List<Usuario> usuariosAutoComplete;
	private List<Projeto> projetosAutoComplete;

	@PostConstruct
	public void init() {
		this.historico = new Historico();
		this.checkPoint = new CheckPoint();
		this.historicoNovo = new ArrayList<>();
		this.historicoAlterado = new ArrayList<>();
		this.projetoHistorico = new ProjetoHistorico();
		this.projetosAutoComplete = DaoProjeto.listarProjetosAtivos();
		this.usuariosAutoComplete = DaoUsuario.listarUsuariosAtivos();
	}

	public List<Projeto> projetoAutoComplete(String complete) {
		if (this.projetosAutoComplete.size() > 0) {
			System.out.println(this.projetosAutoComplete.get(0).getNomeProjeto());
		} else {
			System.out.println("esta vazio");
		}
		return this.projetosAutoComplete;
	}

	public List<Usuario> usuarioGerenteAutoComplete(String complete) {
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

	public List<Usuario> usuarioAnalistaAutoComplete(String complete) {
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

	public void adicionaNovoHistorico() {

		Historico historico = new Historico();
		historico = this.historico;
		this.historico = new Historico();

		this.id = this.id - 1l;
		historico.setId(this.id);
		this.historicoNovo.add(historico);

		if (this.projetoHistorico.getHistoricos() == null) {

			List<Historico> p = new ArrayList<>();
			p.add(historico);
			this.projetoHistorico.setHistoricos(p);

		} else {

			this.projetoHistorico.getHistoricos().add(historico);

		}
		this.historico = null;
		this.novoHistorico = false;

	}

	public void alteraHistorico(Historico historicoSelecionado) {
		Historico historico = new Historico();
		List<Historico> historicos = new ArrayList<Historico>();
		historico = this.historico;

		if (historico.getId() == null) {
			this.id = this.id - 1l;
			historico.setId(this.id);
			this.historicoNovo.add(historico);
			historicos.add(historico);
		}

		if (this.projetoHistorico.getHistoricos() != null) {

			for (Historico h : this.projetoHistorico.getHistoricos()) {

				if (h.getId() == historico.getId() && h.getId() > 0) {

					this.historicoAlterado.add(historico);
					this.projetoHistorico.getHistoricos().remove(h);
					historicos.add(historico);

				} else if (h.getId() == historico.getId() && h.getId() < 0) {

					this.projetoHistorico.getHistoricos().remove(h);
					historicos.add(historico);

				} else {

					historicos.add(h);

				}
			}
		}

		this.projetoHistorico.setHistoricos(historicos);
		this.historico = null;

	}

	public void salvaProjetoHistorico() {
		Projeto projeto = DaoProjeto.buscaProjeto(this.projetoHistorico.getProjeto().getNomeProjeto());

		if (projeto.getProjetoHistorico() != null && this.projetoHistorico.getId() == null) {

			Mensagem.falha("Esse projeto já possui um histórioco./nFaça uma busca para altera-lo");

		} else {

			for (Historico h : this.projetoHistorico.getHistoricos()) {
				if (h.getId() < 0) {
					this.projetoHistorico.getHistoricos().remove(h);
				}
			}

			DaoProjetoHistorico.salvaHistoricoProjeto(this.projetoHistorico);

			ProjetoHistorico provisorio = DaoProjetoHistorico
					.getProjetoHistoricoPorProjeto(this.projetoHistorico.getProjeto());

			if (provisorio != null) {

				if (this.checkPoint.getId() != null) {

					DaoCheckPoint.salvaCheckPoint(this.checkPoint);

				} else {

					this.checkPoint.setProjetoHistorico(provisorio);
					DaoCheckPoint.salvaCheckPoint(this.checkPoint);
				}

				if (this.historicoAlterado.size() > 0) {
					for (Historico h : this.historicoAlterado) {
						DaoHistorico.salvaHistorico(h);
					}
				}

				if (this.historicoNovo.size() > 0) {
					for (Historico h : this.historicoNovo) {
						h.setProjetoHistorico(provisorio);
						DaoHistorico.salvaHistorico(h);
					}
				}
			}
			this.limparCampos();
		}
	}

	public void limparCampos() {
		this.id = 0l;
		this.nomeProjeto = null;
		this.nomeCliente = null;
		this.checkPoint = new CheckPoint();
		this.projetoHistorico = new ProjetoHistorico();
		this.historicoAlterado = new ArrayList<>();
		this.historicoNovo = new ArrayList<>();
	}

	public void novoHistorico() {
		this.novoHistorico = true;
		this.velhoHistorico = false;
		this.historico = new Historico();

	}

	public void atualizaHistorico() {
		this.novoHistorico = false;
		this.velhoHistorico = true;
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

	public ProjetoHistorico getProjetoHistorico() {
		return projetoHistorico;
	}

	public void setProjetoHistorico(ProjetoHistorico projetoHistorico) {
		this.projetoHistorico = projetoHistorico;
	}

	public Historico getHistorico() {
		return historico;
	}

	public void setHistorico(Historico historico) {
		this.historico = historico;
	}

	public List<Historico> getHistoricoAlterado() {
		return historicoAlterado;
	}

	public void setHistoricoAlterado(List<Historico> historicoAlterado) {
		this.historicoAlterado = historicoAlterado;
	}

	public CheckPoint getCheckPoint() {
		return checkPoint;
	}

	public void setCheckPoint(CheckPoint checkPoint) {
		this.checkPoint = checkPoint;
	}

	public List<Historico> getHistoricoNovo() {
		return historicoNovo;
	}

	public void setHistoricoNovo(List<Historico> historicoNovo) {
		this.historicoNovo = historicoNovo;
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

	public boolean isNovoHistorico() {
		return novoHistorico;
	}

	public void setNovoHistorico(boolean novoHistorico) {
		this.novoHistorico = novoHistorico;
	}

	public boolean isVelhoHistorico() {
		return velhoHistorico;
	}

	public void setVelhoHistorico(boolean velhoHistorico) {
		this.velhoHistorico = velhoHistorico;
	}
}

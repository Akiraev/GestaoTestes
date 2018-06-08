package bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.DaoProjeto;
import dao.DaoUsuario;
import dominio.CheckPoint;
import dominio.Historico;
import dominio.Projeto;
import dominio.ProjetoHistorico;
import dominio.Usuario;
import enumeradores.DireitoUsuario;
import enumeradores.Status;

@ManagedBean(name = "projetoHistoricoBean")
@ViewScoped
public class ProjetoHistoricoBean {
	private String descricaoChkPoint;
	private Historico historico;
	private ProjetoHistorico projetoHistorico;
	private List<Usuario> usuariosAutoComplete;
	private List<Projeto> projetosAutoComplete;

	@PostConstruct
	public void init() {
		this.projetoHistorico = new ProjetoHistorico();
		this.projetosAutoComplete = DaoProjeto.listarProjetosAtivos();
		this.usuariosAutoComplete = DaoUsuario.listarUsuariosAtivos();
	}

	public List<Projeto> projetoAutoComplete(String complete) {
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

	public void adicionaChkPoint() {

		List<CheckPoint> chks = new ArrayList<>();
		CheckPoint chk = new CheckPoint();
		chk.setDescricao(this.descricaoChkPoint);
		chk.setStatus(false);

		if (this.projetoHistorico.getCheckPoints() != null) {
			for (CheckPoint c : this.projetoHistorico.getCheckPoints()) {
				chks.add(c);
			}
		}

		chks.add(chk);
		this.projetoHistorico.setCheckPoints(chks);
		this.descricaoChkPoint = "";
	}

	public void adicionaHistorico() {

		List<Historico> historicos = new ArrayList<>();
		Historico historico = this.historico;

		if (this.projetoHistorico.getHistoricos() != null) {
			for (Historico h : this.projetoHistorico.getHistoricos()) {
				historicos.add(h);
			}
		}

		historicos.add(historico);
		this.projetoHistorico.setHistoricos(historicos);
		
	}

	public void iniciaHistorioco() {
		this.historico = new Historico();
	}

	public Status[] getStatus() {
		return Status.values();
	}

	public ProjetoHistorico getProjetoHistorico() {
		return projetoHistorico;
	}

	public void setProjetoHistorico(ProjetoHistorico projetoHistorico) {
		this.projetoHistorico = projetoHistorico;
	}

	public String getDescricaoChkPoint() {
		return descricaoChkPoint;
	}

	public void setDescricaoChkPoint(String descricaoChkPoint) {
		this.descricaoChkPoint = descricaoChkPoint;
	}

	public Historico getHistorico() {
		return historico;
	}

	public void setHistorico(Historico historico) {
		this.historico = historico;
	}

}

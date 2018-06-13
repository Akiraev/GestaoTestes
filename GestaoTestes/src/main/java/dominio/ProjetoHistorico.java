package dominio;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import enumeradores.Status;

@Entity
public class ProjetoHistorico {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Usuario analista;

	@ManyToOne
	private Usuario Gerente;

	@Enumerated(EnumType.STRING)
	@Column
	private Status statusProjetoHistorico;

	@OneToOne
	private Projeto projeto;

	@OneToOne(mappedBy="projetoHistorico")
	private CheckPoint checkPoint;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "projetoHistorico")
	private List<Historico> historicos;

	@Column(name = "data_inicio")
	private Date dataInicio;

	@Column
	private String Observacoes;
	
	
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getAnalista() {
		return analista;
	}

	public void setAnalista(Usuario analista) {
		this.analista = analista;
	}

	public Usuario getGerente() {
		return Gerente;
	}

	public void setGerente(Usuario gerente) {
		Gerente = gerente;
	}

	public Status getStatusProjetoHistorico() {
		return statusProjetoHistorico;
	}

	public void setStatusProjetoHistorico(Status statusProjetoHistorico) {
		this.statusProjetoHistorico = statusProjetoHistorico;
	}	

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public CheckPoint getCheckPoint() {
		return checkPoint;
	}

	public void setCheckPoint(CheckPoint checkPoint) {
		this.checkPoint = checkPoint;
	}

	public List<Historico> getHistoricos() {
		return historicos;
	}

	public void setHistoricos(List<Historico> historicos) {
		this.historicos = historicos;
	}

	public String getObservacoes() {
		return Observacoes;
	}

	public void setObservacoes(String observacoes) {
		Observacoes = observacoes;
	}

}

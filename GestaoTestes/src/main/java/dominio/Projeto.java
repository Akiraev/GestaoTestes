package dominio;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import enumeradores.Status;

@Entity(name = "Projeto")
public class Projeto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codProjeto;

	@Column(nullable = false, length = 60)
	private String nomeProjeto;

	@ManyToOne
	private Cliente cliente;

	@ManyToOne
	private Usuario analista;

	@ManyToOne
	private Usuario gerente;

	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private Status statusProjeto;

	@Column(nullable = false)
	private Date dataProjetoInicio;

	@Column(nullable = false)
	private Date dataProjetoFim;

	@Column
	private Date dataFimEfetivo;

	@OneToOne(mappedBy="projeto")
	private ProjetoHistorico projetoHistorico;

	public Projeto() {

	}

	public Long getCodProjeto() {
		return codProjeto;
	}

	public void setCodProjeto(Long codProjeto) {
		this.codProjeto = codProjeto;
	}

	public String getNomeProjeto() {
		return nomeProjeto;
	}

	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Status getStatusProjeto() {
		return statusProjeto;
	}

	public void setStatusProjeto(Status statusProjeto) {
		this.statusProjeto = statusProjeto;
	}

	public Date getDataProjetoInicio() {
		return dataProjetoInicio;
	}

	public void setDataProjetoInicio(Date dataProjetoInicio) {
		this.dataProjetoInicio = dataProjetoInicio;
	}

	public Date getDataProjetoFim() {
		return dataProjetoFim;
	}

	public void setDataProjetoFim(Date dataProjetoFim) {
		this.dataProjetoFim = dataProjetoFim;
	}

	public Date getDataFimEfetivo() {
		return dataFimEfetivo;
	}

	public void setDataFimEfetivo(Date dataFimEfetivo) {
		this.dataFimEfetivo = dataFimEfetivo;
	}

	public Usuario getAnalista() {
		return analista;
	}

	public void setAnalista(Usuario analista) {
		this.analista = analista;
	}

	public Usuario getGerente() {
		return gerente;
	}

	public void setGerente(Usuario gerente) {
		this.gerente = gerente;
	}

	public ProjetoHistorico getProjetoHistorico() {
		return projetoHistorico;
	}

	public void setProjetoHistorico(ProjetoHistorico projetoHistorico) {
		this.projetoHistorico = projetoHistorico;
	}

}

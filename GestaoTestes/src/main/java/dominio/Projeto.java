package dominio;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import enumeradores.Status;

@Entity(name = "Projeto")
public class Projeto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codProjeto;

	@Column(nullable = false, length = 60)
	private String nomeProjeto;

	@ManyToOne(cascade = CascadeType.ALL)
	private Cliente cliente;

	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private Status statusProjeto;

	@Column(nullable = false)
	private Date dataProjeto;

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

	public Date getDataProjeto() {
		return dataProjeto;
	}

	public void setDataProjeto(Date dataProjeto) {
		this.dataProjeto = dataProjeto;
	}
	
	
}

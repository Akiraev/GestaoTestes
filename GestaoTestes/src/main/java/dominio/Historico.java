package dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Historico {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String descricao;
	
	@Column
	private String conteudo;
	
	@ManyToOne
	private ProjetoHistorico projetoHistorico;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getConteudo() {
		return conteudo;
	}
	
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
	public ProjetoHistorico getProjetoHistorico() {
		return projetoHistorico;
	}
	
	public void setProjetoHistorico(ProjetoHistorico projetoHistorico) {
		this.projetoHistorico = projetoHistorico;
	}
	
}

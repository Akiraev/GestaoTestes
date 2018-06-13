package util;

import dao.DaoProjeto;
import dao.DaoProjetoHistorico;
import dao.DaoUsuario;
import dominio.Projeto;
import dominio.ProjetoHistorico;
import dominio.Usuario;

public class Main {
	public static void main(String[] args) {
		Projeto p = DaoProjeto.buscaProjeto("Teste de servidor TIM");
		
		ProjetoHistorico pj = DaoProjetoHistorico.getProjetoHistoricoPorProjeto(p);
		
		Usuario analista = DaoUsuario.buscaUsuario("roberto@gmail.com");
		Usuario gerente = DaoUsuario.buscaUsuario("terry@gmail.com");
		pj.setAnalista(analista);
		pj.setGerente(gerente);
		DaoProjetoHistorico.salvaHistoricoProjeto(pj);
		
	}
}

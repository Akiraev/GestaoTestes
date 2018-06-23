package util;

import java.util.List;

import dao.DaoHistorico;
import dao.DaoProjeto;
import dao.DaoUsuario;
import dominio.Historico;
import dominio.Projeto;
import dominio.Usuario;
import enumeradores.StatusHistorioco;
import enumeradores.TipoAcaoHistorico;

public class Main {
	public static void main(String[] args) {
		Projeto p = DaoProjeto.buscaProjeto("Teste de servidor TIM");
		List<Historico> h = DaoHistorico.listaHistoricoPorProjeto(p);
		for(Historico hv : h) {
			System.out.println(hv.getId());
		}
		Historico hs = DaoHistorico.buscarHistorico(1l);
		System.out.println(hs.getId());
		hs.setStatusHistorico(StatusHistorioco.ABERTO);
		hs.setTipoAcao(TipoAcaoHistorico.EMAIL);
		Usuario u = DaoUsuario.buscaUsuario("roberto@gmail.com");
		hs.setResponsavel(u);
		DaoHistorico.salvaHistorico(hs);
		
	}
}

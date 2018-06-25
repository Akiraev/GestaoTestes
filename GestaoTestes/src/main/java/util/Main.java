package util;

import java.io.File;
import java.io.IOException;

import dao.DaoProjeto;
import dao.DaoUsuario;
import dominio.Projeto;
import dominio.Usuario;

public class Main {
	public static void main(String[] args) throws IOException {
		String path = new File(".").getCanonicalPath()+"/Ola";
		System.out.println(path);
		
		Usuario usuario = DaoUsuario.buscaUsuario("roberto@gmail.com");
		PDF.relatorioUsuario(usuario);
		
		Projeto projeto = DaoProjeto.buscaProjeto("SCIELO teste de segurança");
		PDF.relatorioHistorico(projeto);
		
		
	}
}

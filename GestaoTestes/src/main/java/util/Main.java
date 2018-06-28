package util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import dao.DaoProjeto;
import dominio.Projeto;
import relatorios.Relatorio;

public class Main {
	public static void main(String[] args) throws IOException {
List<Projeto> projetos = DaoProjeto.listarProjetos();
		
		if (projetos.size() > 0) {
			System.out.println(projetos.size());
			Relatorio.projetos(projetos);
		}
		
		//exibe no terminal o local onde o arquivo se encontra
		System.out.print(new File("Relatório de projetos.pdf").getAbsolutePath());
		System.out.print("\n");
		System.out.print(new File(".").getCanonicalPath());
		
		
	}
}

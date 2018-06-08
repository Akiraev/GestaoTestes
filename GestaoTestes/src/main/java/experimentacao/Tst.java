package experimentacao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import dao.DaoCliente;
import dao.DaoProjeto;
import dao.DaoUsuario;
import dominio.Cliente;
import dominio.Projeto;
import dominio.Usuario;
import factory.ManageFactory;

public class Tst {

	public static void main(String[] args) throws ParseException {
		EntityManager emf = ManageFactory.getEntityManager();
		if (emf != null) {
			ArrayList<Cliente> cli = (ArrayList<Cliente>) DaoCliente.listarClientesAtivos();
			for (Cliente c : cli) {
				System.out.println("\nCliente da lista: " + c.getNomeCliente());
			}
			Cliente c = DaoCliente.buscaClientePeloNome("TIM");
			System.out.println("\nCliente buscado pelo nome: " + c.getCidadeCliente());

			Usuario u = DaoUsuario.buscaUsuarioPeloNome("Roberto Floro");
			System.out.println("\nUsuário buscado pelo nome: " + u.getEmail());

			List<Projeto> projetos = DaoProjeto.listarProjetos();
			for (Projeto p : projetos) {
				System.out.println("\nProjetos da lista: " + p.getNomeProjeto() + "\nCliente: "
						+ p.getCliente().getNomeCliente() + "\nAnalista: " + p.getAnalista().getNomeUsuario()
						+ "\nGerente: " + p.getGerente().getNomeUsuario());
			}
			//Date data = new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyyy");
			//String dataString = "11/12/1988";
			//simpleDateFormat.parse(dataString);
			System.out.println(simpleDateFormat.format(new Date()));
			

			/*
			 * System.out.println("-CONECTOU-"); Status status = Status.ATIVO; UF uf =
			 * UF.AC; Cliente cli = new Cliente(); cli.setBairroCliente("Bairro");
			 * cli.setCepCliente("12345-123"); cli.setCidadeCliente("cidade");
			 * cli.setDataCadastro(new Date()); cli.setEnderecoCliente("endereco");
			 * cli.setNomeCliente("nome cliente"); cli.setNumEndCliente((long) 123);
			 * cli.setObsCLiente("Obs"); cli.setStatusCliente(status );
			 * cli.setTelCliente("(11)1111-1111"); cli.setUf(uf);
			 * 
			 * DaoCliente.salvar(cli);
			 */
		} else {
			System.out.println("Sem conexão");
		}
	}

}

package experimentacao;

import java.util.Date;

import javax.persistence.EntityManager;

import dao.DaoCliente;
import dao.DaoUsuario;
import dominio.Cliente;
import dominio.Usuario;
import enumeradores.Status;
import enumeradores.UF;
import factory.ManageFactory;
import util.PDF;

public class Tst {

	public static void main(String[] args) {
		
		EntityManager emf = ManageFactory.getEntityManager();
		
		if (emf != null) {
			
			System.out.println("-CONECTOU-");
			Status status = Status.ATIVO;
			UF uf = UF.AC;
			Cliente cli = new Cliente();
			cli.setBairroCliente("Bairro");
			cli.setCepCliente("12345-123");
			cli.setCidadeCliente("cidade");
			cli.setDataCadastro(new Date());
			cli.setEnderecoCliente("endereco");
			cli.setNomeCliente("nome cliente");
			cli.setNumEndCliente((long) 123);
			cli.setObsCLiente("Obs");
			cli.setStatusCliente(status );
			cli.setTelCliente("(11)1111-1111");
			cli.setUf(uf);
			
			DaoCliente.salvar(cli);
		
		} else {	
		
			System.out.println("Sem conexão");
		}
	}

}

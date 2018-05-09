package bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.DaoCliente;
import dominio.Cliente;
import enumeradores.Status;
import enumeradores.UF;
import util.Mensagem;

@ManagedBean(name = "cadastroClienteBean")
@ViewScoped
public class ClienteBean {
	private Cliente cliente;
	private String clientePesquisado;
	private List<Cliente> resultCliSrc;
	private List<Cliente> clientes;

	public ClienteBean() {
		this.cliente = new Cliente();
	}

	@PostConstruct
	public void init() {
		this.clientes = (ArrayList<Cliente>) DaoCliente.listarClientes();
	}

	public void salvarCliente() {
		boolean jaExisteNomeCliente = false;
		try {
			for (Cliente cli : this.clientes) {
				if (cli.getNomeCliente().equals(cliente.getNomeCliente())) {
					jaExisteNomeCliente = true;
					break;
				}
			}
			if (jaExisteNomeCliente && cliente.getCodCliente() == null) {
				Mensagem.falha("Já existe um cliente ja está cadastrado com esse nome\nNão foi possível salvar");
			} else {
				DaoCliente.salvar(this.cliente);
				Cliente.limparCliente(this.cliente);
				this.clientes = DaoCliente.listarClientes();

				Mensagem.sucesso("Cadastro salvo com sucesso!");
			}

		} catch (Exception e) {
			Mensagem.falha("Não foi possível salvar/n" + e.toString());
		}
	}

	public void limparCampo() {
		Cliente.limparCliente(this.cliente);
	}

	public void buscarClientes() {
		this.resultCliSrc = new ArrayList<Cliente>();
		System.out.println("1");
		if (this.clientePesquisado.length() < 1 || this.clientePesquisado == null) {
			Mensagem.falha("Prencha o nome do usuario para realizar a pesquisa");
			System.out.println("2");
		} else {
			System.out.println("3");
			for (Cliente nome : this.clientes) {
				if (nome.getNomeCliente().toLowerCase().contains(this.clientePesquisado.toLowerCase())) {
					Cliente cli = new Cliente();
					cli = nome;
					resultCliSrc.add(cli);
				}
			}
			if (resultCliSrc.size() == 0) {
				Mensagem.falha("Usuário não existe");
			}
			clientePesquisado = null;
		}
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getClientePesquisado() {
		return clientePesquisado;
	}

	public void setClientePesquisado(String clientePesquisado) {
		this.clientePesquisado = clientePesquisado;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Cliente> getResultCliSrc() {
		return resultCliSrc;
	}

	public void setResultCliSrc(List<Cliente> resultCliSrc) {
		this.resultCliSrc = resultCliSrc;
	}

	public Status[] getStatus() {
		return Status.values();
	}

	public UF[] getUF() {
		return UF.values();
	}

}

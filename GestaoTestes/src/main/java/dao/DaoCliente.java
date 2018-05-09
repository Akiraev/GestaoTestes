package dao;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import dominio.Cliente;
import factory.ManageFactory;

public class DaoCliente {

	public static void salvar(Cliente cliente)
	{
		if (cliente.getCodCliente() != null)
			DaoCliente.alterar(cliente);
		else
			DaoCliente.persist(cliente);
	}

	public static boolean persist(Cliente cliente) {
		EntityManager entityManager = ManageFactory.getEntityManager();

		if (cliente.getNomeCliente() != null) {
			try {
				entityManager.getTransaction().begin();
				entityManager.persist(cliente);
				entityManager.getTransaction().commit();
				entityManager.close();
				return true;

			} catch (Exception ex) {
				ex.printStackTrace();
				entityManager.getTransaction().rollback();
			}
		}

		return false;

	}

	public static boolean alterar(Cliente cliente) {
		EntityManager em = ManageFactory.getEntityManager();
		em.find(Cliente.class, cliente.getCodCliente());
		try {
			em.getTransaction().begin();
			em.merge(cliente);
			em.getTransaction().commit();
			em.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
		return false;
	}

	public static void excluir(String nomeCliente) {
		EntityManager em = ManageFactory.getEntityManager();

		try {
			String busca = "Select a from Cliente a where a.nomecliente = :nomecliente";
			Query query = em.createQuery(busca);

			query.setParameter("nomecliente", nomeCliente);
			Cliente cliente = (Cliente) query.getSingleResult();

			em.getTransaction().begin();
			em.remove(cliente);
			em.getTransaction().commit();
			em.close();

		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	public static Cliente buscaUsuario(String nomeCliente) {
		EntityManager em = ManageFactory.getEntityManager();
		Cliente cliente = new Cliente();

		try {
			Query query = em.createQuery("Select cl from Cliente cl where cl.nomeCliente = :nomeCliente", Cliente.class);
			query.setParameter("nomeCliente", nomeCliente);

			cliente = (Cliente) query.getSingleResult();
			em.close();
			return cliente;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Cliente> listarClientes() {
		EntityManager em = ManageFactory.getEntityManager();
		ArrayList<Cliente> clientes;

		try {

			Query q = em.createQuery("select cli from Cliente cli", Cliente.class);
			clientes = (ArrayList<Cliente>) q.getResultList();
			em.close();

			return clientes;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ultima linha da busca");
		}
		System.out.println("terminou a busca");
		return null;
	}

}

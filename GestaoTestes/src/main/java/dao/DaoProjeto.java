package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import dominio.Projeto;
import dominio.ProjetoHistorico;
import enumeradores.Status;
import factory.ManageFactory;

public class DaoProjeto {

	public static void salvar(Projeto projeto) {
		if (projeto.getCodProjeto() != null)
			DaoProjeto.alterar(projeto);
		else
			DaoProjeto.persist(projeto);
	}

	public static boolean persist(Projeto projeto) {
		EntityManager entityManager = ManageFactory.getEntityManager();

		if (projeto.getNomeProjeto() != null) {
			try {

				entityManager.getTransaction().begin();
				entityManager.persist(projeto);
				entityManager.getTransaction().commit();

				return true;

			} catch (Exception ex) {
				ex.printStackTrace();
				entityManager.getTransaction().rollback();
			} finally {
				entityManager.close();
			}
		}

		return false;

	}

	public static boolean alterar(Projeto projeto) {
		EntityManager entityManager = ManageFactory.getEntityManager();
		entityManager.find(Projeto.class, projeto.getCodProjeto());

		try {
			entityManager.getTransaction().begin();
			entityManager.merge(projeto);
			entityManager.getTransaction().commit();

			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();

		} finally {
			entityManager.close();
		}
		return false;
	}

	public static Projeto buscaProjeto(String nomeProjeto) {
		EntityManager entityManager = ManageFactory.getEntityManager();
		Projeto projeto = new Projeto();

		try {
			Query query = entityManager.createQuery("Select a from Projeto a where a.nomeProjeto = :nome",
					Projeto.class);
			query.setParameter("nome", nomeProjeto);

			projeto = (Projeto) query.getSingleResult();
			
			return projeto;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
		return null;
	}

	public static ProjetoHistorico buscaProjetoHistorico() {
		EntityManager entityManager = ManageFactory.getEntityManager();
		ProjetoHistorico projetoHistorico = new ProjetoHistorico();

		try {			
			Query query = entityManager.createQuery("Select a from ProjetoHistorico",
					Projeto.class);			

			projetoHistorico = (ProjetoHistorico) query.getSingleResult();
			return projetoHistorico;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Projeto> listarProjetos() {
		EntityManager entityManager = ManageFactory.getEntityManager();
		ArrayList<Projeto> usuarios;

		try {

			Query q = entityManager.createQuery("select pr from Projeto pr", Projeto.class);
			usuarios = (ArrayList<Projeto>) q.getResultList();

			return usuarios;

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			entityManager.close();
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public static List<Projeto> listarProjetosAtivos() {
		EntityManager entityManager = ManageFactory.getEntityManager();

		try {
			Query q = entityManager.createQuery("select proj from Projeto proj where proj.statusProjeto = :status",
					Projeto.class);
			q.setParameter("status", Status.ATIVO);
			return q.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
		return null;
	}

}

package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Hibernate;

import dominio.Projeto;
import enumeradores.Status;
import factory.HibernateManageFactory;

public class DaoProjeto {

	public static boolean salvar(Projeto projeto) {
		if (projeto.getCodProjeto() != null)
			return DaoProjeto.alterar(projeto);
		else
			return DaoProjeto.persist(projeto);
	}

	private static boolean persist(Projeto projeto) {
		EntityManager entityManager = HibernateManageFactory.getEntityManager();

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

	private static boolean alterar(Projeto projeto) {
		EntityManager entityManager = HibernateManageFactory.getEntityManager();
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
		EntityManager entityManager = HibernateManageFactory.getEntityManager();
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

	@SuppressWarnings("unchecked")
	public static ArrayList<Projeto> listarProjetos() {
		EntityManager entityManager = HibernateManageFactory.getEntityManager();
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
		EntityManager entityManager = HibernateManageFactory.getEntityManager();

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

	public static List<Projeto> listarProjetosAtivosHistoricos() {
		EntityManager entityManager = HibernateManageFactory.getEntityManager();

		try {
			Query q = entityManager.createQuery("select proj from Projeto proj where proj.statusProjeto = :status",
					Projeto.class);
			q.setParameter("status", Status.ATIVO);

			@SuppressWarnings("unchecked")
			List<Projeto> projetos = q.getResultList();
			for (int i = 0; i < projetos.size(); i++) {
				Hibernate.initialize(projetos.get(i).getHistoricos());
			}
			return projetos;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
		return null;
	}

	public static Projeto buscaProjetoLazy(Projeto projetoLazy) {
		EntityManager entityManager = HibernateManageFactory.getEntityManager();

		try {
			Projeto projetoEager = entityManager.find(Projeto.class, projetoLazy.getCodProjeto());
			Hibernate.initialize(projetoEager.getHistoricos());
			return projetoEager;

		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();

		} finally {
			entityManager.close();
		}
		return null;

	}

}

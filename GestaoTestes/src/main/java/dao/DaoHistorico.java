package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import dominio.Historico;
import dominio.Projeto;
import factory.HibernateManageFactory;

public class DaoHistorico {

	public static boolean salvaHistorico(Historico historico) {
		if (historico.getId() != null) {
			return DaoHistorico.alterar(historico);

		} else {
			return DaoHistorico.persist(historico);
		}
	}

	private static boolean persist(Historico historico) {

		EntityManager entityManager = HibernateManageFactory.getEntityManager();

		try {
			entityManager.getTransaction().begin();
			entityManager.persist(historico);
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

	private static boolean alterar(Historico historico) {
		EntityManager entityManager = HibernateManageFactory.getEntityManager();
		entityManager.find(Historico.class, historico.getId());

		try {
			entityManager.getTransaction().begin();
			entityManager.merge(historico);
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

	public static Historico buscarHistorico(Long historicoId) {
		EntityManager entityManager = HibernateManageFactory.getEntityManager();

		try {
			Query query = entityManager.createQuery("SELECT h FROM Historico h WHERE h.id = :historicoId",
					Historico.class);
			query.setParameter("historicoId", historicoId);
			Historico historico = (Historico) query.getSingleResult();
			
			return historico;

		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();

		} finally {
			entityManager.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static List<Historico> listaHistoricoPorProjeto(Projeto projeto) {
		EntityManager entityManager = HibernateManageFactory.getEntityManager();
		try {
			Query q = entityManager.createQuery("select h from Historico h where h.projeto = :projeto",
					Historico.class);
			q.setParameter("projeto", projeto);
			return q.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
		return null;

	}

	public static boolean excluirHistorico(Historico historico) {
		EntityManager entityManager = HibernateManageFactory.getEntityManager();

		try {
			
			Historico historicoEx = entityManager.getReference(Historico.class, historico.getId());
			entityManager.getTransaction().begin();
			entityManager.remove(historicoEx);
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
}

package dao;

import javax.persistence.EntityManager;

import dominio.Historico;
import factory.ManageFactory;

public class DaoHistorico {
	
	public static void salvaHistorico(Historico historico) {
		if (historico.getId() != null)
			DaoHistorico.alterar(historico);
		else
			DaoHistorico.persist(historico);

	}

	public static boolean persist(Historico historico) {

		EntityManager entityManager = ManageFactory.getEntityManager();

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

	public static boolean alterar(Historico historico) {
		EntityManager entityManager = ManageFactory.getEntityManager();
		entityManager.find(Historico.class, historico);

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
}

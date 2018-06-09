package dao;

import javax.persistence.EntityManager;

import dominio.ProjetoHistorico;
import factory.ManageFactory;

public class DaoProjetoHistorico {
	public static boolean persist(ProjetoHistorico projetoHistorico) {
		EntityManager entityManager = ManageFactory.getEntityManager();
		
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(projetoHistorico);
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

	public static boolean alterar(ProjetoHistorico projetoHistorico) {
		EntityManager entityManager = ManageFactory.getEntityManager();
		entityManager.find(ProjetoHistorico.class, projetoHistorico);

		try {
			entityManager.getTransaction().begin();
			entityManager.merge(projetoHistorico);
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

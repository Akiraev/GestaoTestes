package dao;

import javax.persistence.EntityManager;

import dominio.CheckPoint;
import factory.ManageFactory;

public class DaoCheckPoint {
	
	public static void salvaCheckPoint(CheckPoint checkPoint) {
		if (checkPoint.getId() != null)
			DaoCheckPoint.alterar(checkPoint);
		else
			DaoCheckPoint.persist(checkPoint);

	}

	public static boolean persist(CheckPoint checkPoint) {

		EntityManager entityManager = ManageFactory.getEntityManager();

		try {
			entityManager.getTransaction().begin();
			entityManager.persist(checkPoint);
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

	public static boolean alterar(CheckPoint checkPoint) {
		EntityManager entityManager = ManageFactory.getEntityManager();
		entityManager.find(CheckPoint.class, checkPoint);

		try {
			entityManager.getTransaction().begin();
			entityManager.merge(checkPoint);
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

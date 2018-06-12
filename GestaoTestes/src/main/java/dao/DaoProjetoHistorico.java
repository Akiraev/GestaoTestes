package dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import dominio.Projeto;
import dominio.ProjetoHistorico;
import factory.ManageFactory;

public class DaoProjetoHistorico {

	public static void salvaHistoricoProjeto(ProjetoHistorico projetoHistorico) {
		if (projetoHistorico.getId() != null)
			DaoProjetoHistorico.alterar(projetoHistorico);
		else
			DaoProjetoHistorico.persist(projetoHistorico);

	}

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
	
	public static ProjetoHistorico getProjetoHistoricoPorProjeto(Projeto projeto) {
		EntityManager entityManager = ManageFactory.getEntityManager();
		ProjetoHistorico projetoHistorico = new ProjetoHistorico();

		try {
			Query query = entityManager.createQuery("Select a from ProjetoHistorico a where a.projeto.codProjeto = :projetoId",
					ProjetoHistorico.class);
			query.setParameter("projetoId", projeto.getCodProjeto());

			projetoHistorico = (ProjetoHistorico) query.getSingleResult();
			
			return projetoHistorico;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
		return null;
	}
}

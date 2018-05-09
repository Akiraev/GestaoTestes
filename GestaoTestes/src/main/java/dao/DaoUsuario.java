package dao;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import dominio.Usuario;
import factory.ManageFactory;

public class DaoUsuario {

	public static void salvar(Usuario usuario) {
		if (usuario.getCodUsuario() != null)
			DaoUsuario.alterar(usuario);
		else
			DaoUsuario.persist(usuario);
	}

	public static boolean persist(Usuario usuario) {
		EntityManager entityManager = ManageFactory.getEntityManager();

		if (usuario.getEmail() != null) {
			try {

				entityManager.getTransaction().begin();
				entityManager.persist(usuario);
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

	public static boolean alterar(Usuario usuario) {
		EntityManager em = ManageFactory.getEntityManager();
		em.find(Usuario.class, usuario.getCodUsuario());
		try {
			em.getTransaction().begin();
			em.merge(usuario);
			em.getTransaction().commit();
			em.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
		return false;
	}

	public static void excluir(String email) {
		EntityManager em = ManageFactory.getEntityManager();

		try {

			String busca = "Select a from Usuario a where a.email = :email";
			Query query = em.createQuery(busca);

			query.setParameter("email", email);
			Usuario usuario = (Usuario) query.getSingleResult();

			em.getTransaction().begin();
			em.remove(usuario);
			em.getTransaction().commit();
			em.close();

		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	public static Usuario buscaUsuario(String email) {
		EntityManager em = ManageFactory.getEntityManager();
		Usuario usuario = new Usuario();

		try {
			Query query = em.createQuery("Select a from Usuario a where a.email = :email", Usuario.class);
			query.setParameter("email", email);

			usuario = (Usuario) query.getSingleResult();
			em.close();
			return usuario;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Usuario> listarUsuarios() {
		EntityManager em = ManageFactory.getEntityManager();
		ArrayList<Usuario> usuarios;

		try {

			Query q = em.createQuery("select usu from Usuario usu", Usuario.class);
			usuarios = (ArrayList<Usuario>) q.getResultList();
			em.close();

			return usuarios;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ultima linha da busca");
		}
		System.out.println("terminou a busca");
		return null;
	}

}

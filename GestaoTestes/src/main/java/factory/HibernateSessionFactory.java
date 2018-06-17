package factory;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateSessionFactory {
	private static SessionFactory sessionFactory;
	private static Configuration configuration;
	private static ServiceRegistry serviceRegistry;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory != null) {
			return sessionFactory;

		} else {
			return createSessionFactory(getConfiguracao());
		}
	}

	private static SessionFactory createSessionFactory(Configuration configuration) {
		serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		return sessionFactory;
	}

	private static Configuration getConfiguracao() {
		configuration = new Configuration()
				.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/gestao_testes")
				.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
				.setProperty("hibernate.connection.username", "roberto")
				.setProperty("hibernate.connection.password", "jeremias")
				.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL82Dialect")
				.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false")
				.setProperty("hibernate.hbm2ddl.auto", "update");
		;
		return configuration;
	}
}

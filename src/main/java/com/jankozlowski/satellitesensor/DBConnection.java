package com.jankozlowski.satellitesensor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.service.ServiceRegistry;

public class DBConnection {

	private static SessionFactory factory;

	static {
		Configuration cfg = new Configuration();
		cfg.configure("Hibernate.cfg.xml");
		cfg.setNamingStrategy(ImprovedNamingStrategy.INSTANCE);

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties())
				.build();

		factory = cfg.buildSessionFactory(serviceRegistry);
	}

	public Session getSession() {
		return factory.openSession();
	}

	public void doWork() {
		Session session = getSession();
		session.close();
	}

	// Call this during shutdown
	public static void close() {
		factory.close();
	}

}

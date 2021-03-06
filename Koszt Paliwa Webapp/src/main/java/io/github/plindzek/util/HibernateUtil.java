package io.github.plindzek.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * util class ensure static methods to create and close Hibernate sessions
 *
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private HibernateUtil() {
    }

    public static SessionFactory getSessionFactory() { return sessionFactory; }

    private static SessionFactory buildSessionFactory() {
	// A SessionFactory is set up once for an application!
	final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
		.configure() // configures settings from hibernate.cfg.xml
		.build();
	try {
	    return new MetadataSources(registry).buildMetadata().buildSessionFactory();

	} catch (Exception e) {

	    StandardServiceRegistryBuilder.destroy(registry);
	    throw e;
	}
    }

    public static void close() {
		if ( sessionFactory != null ) {
			sessionFactory.close();
		}
}
}

package utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.concurrent.atomic.AtomicReference;

public class HibernateSessionFactorySingleton {
    private static AtomicReference<SessionFactory> sessionFactory = new AtomicReference<>(null);

    private HibernateSessionFactorySingleton() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory.get() == null) {
            synchronized (HibernateSessionFactorySingleton.class) {
                if (sessionFactory.get() == null) {
                    try {
                        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
                        sessionFactory.set(new MetadataSources(registry).buildMetadata().buildSessionFactory());

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        throw new IllegalStateException("Could not create SessionFactory");
                    }
                }
            }
        }
        return sessionFactory.get();
    }

    public static void closeSessionFactory() {
        if (sessionFactory.get() != null) {
            sessionFactory.get().close();
            sessionFactory.set(null);
        }
    }
}
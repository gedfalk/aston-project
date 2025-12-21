package dev.gedfalk.astonproject.utils;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();
        } catch (Exception e) {
            // TODO: заменить на логгер
            System.out.println("_____errrrrr______");
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void disconnect() {
        getSessionFactory().close();
    }
}

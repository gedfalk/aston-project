package dev.gedfalk.astonproject.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Slf4j
public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();
        } catch (Exception e) {
            log.error("Ошибка инициализации", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void disconnect() {
        getSessionFactory().close();
    }
}

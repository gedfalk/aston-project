package dev.gedfalk.astonproject.dao;

import dev.gedfalk.astonproject.entity.User;
import dev.gedfalk.astonproject.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAOHibernate implements UserDAO {
    @Override
    public User save(User user) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            // TODO: логи
            throw new RuntimeException("Ошибка при попытке совершить транзакцию", e);
        }
    }

    @Override
    public Boolean existByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(*) FROM User WHERE email = :email", Long.class);
            query.setParameter("email", email);
            return query.getSingleResult() > 0;
        }
    }

    @Override
    public List<User> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery(
                    "FROM User", User.class);
            return query.list();
        }
    }
}

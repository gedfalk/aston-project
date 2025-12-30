package dev.gedfalk.astonproject;

import dev.gedfalk.astonproject.utils.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class PostgresContainerTest extends AbstractPostgresContainerTest {

    @Test
    @DisplayName("Запущен ли контейнер")
    void containerRunning() {
        assertTrue(postgres.isRunning());
    }

    @Test
    @DisplayName("Контейнер коннектится к базе данных")
    void hibernateConnectedToPostgres() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            assertNotNull(session);
            assertTrue(session.isConnected());
        }
    }

    @Test
    @DisplayName("Таблица создаётся")
    void shouldSeeUsersTable() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Object result = session.createNativeQuery("""
            SELECT table_name
            FROM information_schema.tables
            WHERE table_name = 'users'
        """).getSingleResult();

            assertNotNull(result);
        }
    }
}

package dev.gedfalk.astonproject;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresContainerTest {

    @Test
    void testPostgresContainer() {
        try (PostgreSQLContainer
                     postgres = new PostgreSQLContainer<>("postgres:15")) {
            // Запускаем контейнер
            postgres.start();

            // Выводим данные контейнера для проверки
            System.out.println("Postgres URL: " + postgres.getJdbcUrl());
            System.out.println("Username: " + postgres.getUsername());
            System.out.println("Password: " + postgres.getPassword());

            // Здесь можно подключиться к базе через JDBC и выполнить тестовые действия
        }
    }
}

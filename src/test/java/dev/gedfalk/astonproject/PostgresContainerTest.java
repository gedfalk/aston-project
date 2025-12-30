package dev.gedfalk.astonproject;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Slf4j
@Testcontainers
public class PostgresContainerTest {

    @BeforeAll
    static void setUp() {
        log.info("началооооооооооооооооооооооооооооооось");
    }


//    static {
////        TestcontainersConfiguration.getInstance()
////                .updateUserConfig("ryuk.container.timeout", "30");
//        // или отключите Ryuk (осторожно - контейнеры не будут удаляться автоматически)
//         TestcontainersConfiguration.getInstance()
//             .updateUserConfig("ryuk.disabled", "true");
//    }

//    @Container
//    private static final PostgreSQLContainer<?> postgres =
//            new PostgreSQLContainer<>("postgres:15")
//                    .withDatabaseName("testdb")
//                    .withUsername("test")
//                    .withPassword("test");
//
//    @Test
//    public void testPostgresContainer() throws Exception {
//        String jdbcUrl = postgres.getJdbcUrl();
//        String username = postgres.getUsername();
//        String password = postgres.getPassword();
//
//        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery("SELECT 1")) {
//
//            assert rs.next();
//            assert rs.getInt(1) == 1;
//        }
//    }



    @Test
    void testPostgresContainer() {
        log.info("Зааааааапууууууускааааааем тест");
        try (PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:15")) {
            // Запускаем контейнер
            postgres.start();

            // Выводим данные контейнера для проверки
            System.out.println("Postgres URL: " + postgres.getJdbcUrl());
            System.out.println("Username: " + postgres.getUsername());
            System.out.println("Password: " + postgres.getPassword());

            log.info("_________ создали postgres успешно ______ \n{}", postgres);
            // Здесь можно подключиться к базе через JDBC и выполнить тестовые действия
//        } catch (Exception e) {
//            log.info("???????????? выкинуло во время создания postgres\n__________{}________", e.getMessage());
//           }

        }
    }
}

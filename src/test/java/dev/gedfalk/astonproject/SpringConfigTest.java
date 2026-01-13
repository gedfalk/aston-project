package dev.gedfalk.astonproject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SpringConfigTest {

    @Autowired
    private Environment environment;

    @Autowired
    private DataSource dataSource;

    @Test
    @DisplayName("Проверка загрузки контекста")
    void contextLoads() {}

    @Test
    @DisplayName("Проверка установленных полей бд")
    void databasePropertiesAreSet() {
        assertThat(environment.getProperty("spring.datasource.url"))
                .isEqualTo("jdbc:postgresql://localhost:5432/aston_project_db");

        assertThat(environment.getProperty("spring.datasource.username"))
                .isEqualTo("postgres");
    }

    @Test
    @DisplayName("Проверка коннекта к бд")
    void databaseIsReachable() throws Exception {
        try (var connection = dataSource.getConnection()) {
            assertThat(connection.isValid(1)).isTrue();
        }
    }
}

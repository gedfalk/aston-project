package dev.gedfalk.astonproject.dao;

import dev.gedfalk.astonproject.AbstractPostgresContainerTest;
import dev.gedfalk.astonproject.entity.User;
import dev.gedfalk.astonproject.utils.HibernateUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserDAOHibernateTest extends AbstractPostgresContainerTest {

    private UserDAO userDAO;

    @BeforeEach
    void setup() {
        userDAO = new UserDAOHibernate();
    }

    @Test
    void save_shoudSaveUser() {
        User user = User.builder()
                .name("Eugene")
                .email("test@gmail.com")
                .age(30)
                .createdAt(LocalDateTime.now())
                .build();

        User saved = userDAO.save(user);

        assertNotNull(saved.getId());
    }
}

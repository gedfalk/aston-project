package dev.gedfalk.astonproject.dao;

import dev.gedfalk.astonproject.AbstractPostgresContainerTest;
import dev.gedfalk.astonproject.entity.User;
import dev.gedfalk.astonproject.utils.HibernateUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOHibernateTest extends AbstractPostgresContainerTest {

    private UserDAO userDAO;

    @BeforeEach
    void setup() {
        userDAO = new UserDAOHibernate();
    }

    @Test
    @DisplayName("Сохранение юзера - позитивный кейс")
    void save_shoudSaveUser() {
        User user = User.builder()
                .name("Eugene")
                .email("test@gmail.com")
                .age(30)
                .createdAt(LocalDateTime.now())
                .build();

        User saved = userDAO.save(user);

        assertNotNull(saved.getId());
        assertEquals("Eugene", saved.getName());
    }

    @Test
    @DisplayName("Поиск юзера по id - позитивный кейс")
    void findById_returnUserIfExists() {
        User user = userDAO.save(createTestUser("Eugene1", "test1@gmail.com", 33));

        Optional<User> found = userDAO.findById(user.getId());

        assertTrue(found.isPresent());
        assertEquals("Eugene1", found.get().getName());
    }

    @Test
    @DisplayName("Поиск юзера по id - негативный кейс")
    void findById_returnUserIfDoesntExist() {
        Optional<User> found = userDAO.findById(666);

        assertTrue(found.isEmpty());
    }

    private User createTestUser(String name, String email, Integer age) {
        return User.builder()
                .name(name)
                .email(email)
                .age(age)
                .createdAt(LocalDateTime.now())
                .build();
    }
}

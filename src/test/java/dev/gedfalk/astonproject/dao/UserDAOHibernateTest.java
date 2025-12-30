package dev.gedfalk.astonproject.dao;

import dev.gedfalk.astonproject.AbstractPostgresContainerTest;
import dev.gedfalk.astonproject.entity.User;
import dev.gedfalk.astonproject.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOHibernateTest extends AbstractPostgresContainerTest {

    private UserDAO userDAO;

    @BeforeEach
    void setup() {
        userDAO = new UserDAOHibernate();
    }

    @AfterEach
    void rollback() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM User").executeUpdate();
        transaction.commit();
        session.close();
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

    @Test
    @DisplayName("Обновление несуществующего юзера - негативный кейс")
    void update_exceptionBecauseUserDoesntExist() {
        User newUser = createTestUser("Eugene's ghost", "xxx@xxx.xxx", 1);

        assertThrows(
                RuntimeException.class,
                () -> userDAO.update(99, newUser)
        );
    }

    @Test
    @DisplayName("Обновление существующего юзер - позитивный кейс")
    void update_shouldUpdateExistingUser() {
        User user = userDAO.save(createTestUser("OldEugene", "test@gmail.com", 30));
        User newUser = createTestUser("NewEugene", "newtest@gmail.com", 29);

        User updated = userDAO.update(user.getId(), newUser);

        assertEquals("NewEugene", updated.getName());
        assertEquals("newtest@gmail.com", updated.getEmail());
    }

    @Test
    @DisplayName("Возврат всех юзеров из таблицы - позитивный кейс")
    void findAll_returnAllUsers() {
        userDAO.save(createTestUser("Eugene1", "test1@gmail.com", 33));
        userDAO.save(createTestUser("Eugene2", "test2@gmail.com", 33));

        List<User> users = userDAO.findAll();

        assertEquals(2, users.size());
    }

    @Test
    @DisplayName("Поиск юзера email - негативный кейс")
    void existByEmail_returnFalseIfDoesntExist() {
        Boolean result = userDAO.existByEmail("someStrangeEmail@gmail.com");

        assertFalse(result);
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

package dev.gedfalk.astonproject.service;

import dev.gedfalk.astonproject.dao.UserDAO;
import dev.gedfalk.astonproject.entity.User;
import lombok.Value;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserDAO userDao;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Создание пользователя с валидными данными")
    void createUser_ValidData_Success() {

        when(userDao.existByEmail("test@gmail.com")).thenReturn(false);
        User user = User.builder()
                .name("Eugene")
                .email("test@gmail.com")
                .age(30)
                .build();
        when(userDao.save(any(User.class))).thenReturn(user);

        String name = "    Eugene ";
        String email = "   test@gmail.com   ";
        Integer age = 30;
        User result = userService.createUser(name, email, age);

        assertNotNull(result);
        assertEquals("Eugene", result.getName());
        assertEquals("test@gmail.com", result.getEmail());
        assertEquals(30, result.getAge());

        verify(userDao).save(any(User.class));
    }

    @Test
    @DisplayName("Создание пользователя с уже существующим мылом")
    void createUser_emailExists_Exception() {

        when(userDao.existByEmail("test@gmail.com")).thenReturn(true);

        String name = "    Eugene ";
        String email = "   test@gmail.com   ";
        Integer age = 30;
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser(name, email, age)
        );

        assertEquals("Пользователь с мылом test@gmail.com уже есть", exception.getMessage());

        verify(userDao, never()).save(any());
    }

    @Test
    @DisplayName("Поиск по id - успешный кейс")
    void findById_success() {

        Integer id = 13;
        User existingUser = User.builder()
                .name("Kolya")
                .email("kolya@gmail.com")
                .age(25)
                .build();
        when(userDao.findById(id)).thenReturn(Optional.of(existingUser));

        User result = userService.findById(id);

        assertEquals("Kolya", result.getName());
        assertEquals("kolya@gmail.com", result.getEmail());
        assertEquals(25, result.getAge());
    }

    @Test
    @DisplayName("Поиск по id - негативный кейс")
    void findById_notFound_Exception() {

        Integer id = 42;
        when(userDao.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.findById(id)
        );

        assertEquals("Пользователь с ID 42 не найден", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, -1, 121})
    @DisplayName("Валидация возраста - негативные кейсы")
    void createUser_invalidAge_Exception(int age) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser("Eugene", "test@gmail.com", age)
        );

        if (age < 0) {
            assertEquals("Возраст должен быть больше 0", exception.getMessage());
        } else {
            assertEquals("Возраст должен быть меньше 120", exception.getMessage());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "' ', Имя не может быть пустым",
            "X, Имя должно быть больше 2 символов",
            "'Дейнерис Бурерожденная из дома Таргариенов', Слииишком длинное имя"
    })
    @DisplayName("Валидация имени - негативные кейсы")
    void createUser_invalidName_Exception(String name, String message) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser(name, "test@gmail.com", 30)
        );

        assertEquals(message, exception.getMessage());
    }
}

package dev.gedfalk.astonproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gedfalk.astonproject.dto.UserRequestDto;
import dev.gedfalk.astonproject.dto.UserResponseDto;
import dev.gedfalk.astonproject.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private UserResponseDto testUser;
    private UserRequestDto testRequest;

    @BeforeEach
    void setup() {
        testUser = UserResponseDto.builder()
                .id(1)
                .name("Eugene")
                .email("xxx@gmail.com")
                .age(33)
                .createdAt(LocalDateTime.now())
                .build();

        testRequest = UserRequestDto.builder()
                .name("Eugene")
                .email("xxx@gmail.com")
                .age(33)
                .build();
    }

    @Test
    @DisplayName("Поиск пользователя по id - GET /api/users/id/{id}")
    void testGetUserById() throws Exception {
        when(userService.getUserById(1)).thenReturn(testUser);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Eugene"))
                .andExpect(jsonPath("$.email").value("xxx@gmail.com"));
    }

    @Test
    @DisplayName("Создание пользователя - POST /api/users")
    void createUser() throws Exception {
        when(userService.createUser(any(UserRequestDto.class)))
                .thenReturn(testUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRequest)))
                        .andExpect(status().isCreated())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id", is(1)))
                        .andExpect(jsonPath("$.name", is("Eugene")))
                        .andExpect(jsonPath("$.email", is("xxx@gmail.com")))
                        .andExpect(jsonPath("$.age", is(33)));
    }

    @Test
    @DisplayName("Удаление пользователя - DELETE /api/users/{id}")
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Обновление пользователя - PUT /api/users/{id}")
    void updateUser() throws Exception {
        UserResponseDto updatedUser = UserResponseDto.builder()
                .id(1)
                .name("Eugene The Great")
                .email("yyy@gmail.com")
                .age(30)
                .createdAt(LocalDateTime.now())
                .build();

        when(userService.updateUser(eq(1), any(UserRequestDto.class)))
                .thenReturn(updatedUser);

        UserRequestDto updateRequest = UserRequestDto.builder()
                .name("Eugene The Great")
                .email("yyy@gmail.com")
                .age(30)
                .build();

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name", is("Eugene The Great")))
                        .andExpect(jsonPath("$.email", is("yyy@gmail.com")))
                        .andExpect(jsonPath("$.age", is(30)));
    }
}

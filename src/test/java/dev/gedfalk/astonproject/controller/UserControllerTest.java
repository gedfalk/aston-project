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
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
}

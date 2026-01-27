package dev.gedfalk.astonproject.controller;

import dev.gedfalk.astonproject.dto.UserRequestDto;
import dev.gedfalk.astonproject.dto.UserResponseDto;
import dev.gedfalk.astonproject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Операции над пользователями", description = "Базовые CRUD-операции")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Создание нового пользователя",
            description = "Создаёт пользователя и отправляет событие в Кафку"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Пользователь успешно создан"),
            @ApiResponse(responseCode = "404", description = "Неверные данные, валидация не пройдена")
    })
    public UserResponseDto createUser(@Valid @RequestBody UserRequestDto requestDto) {
        return userService.createUser(requestDto);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение пользователя по ID",
            description = "Возвращает пользователя по уникальному идентификатору"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь успешно найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public UserResponseDto getUserById(
            @Parameter(
                    description = "Уникальный идентификатор пользователя",
                    example = "666",
                    required = true
            )
            @PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @GetMapping
    @Operation(
            summary = "Получение списка всех пользователей",
            description = "Возвращает список всех существующих пользователей"
    )
    @ApiResponse(responseCode = "200", description = "Список пользователей успешно получен")
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Удаление пользователя",
            description = "Удаляет пользователя по идентификатору и отправляет событие в Кафку"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Пользователь успешно удален"),
            @ApiResponse(responseCode = "404", description = "Удаление не прошло, пользователь не найден")
    })
    public void deleteUser(
            @Parameter(
                    description = "Уникальный идентификатор пользователя",
                    example = "666",
                    required = true
            )
            @PathVariable Integer id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновление пользователя",
            description = "Обновляет пользователя по идентификатору, если такой существует"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Данные пользоваетля успешно обновлены"),
            @ApiResponse(responseCode = "404", description = "Обновление не прошло, пользователь не найден"),
            @ApiResponse(responseCode = "400", description = "Обновление не прошло, неверные входные данные")
    })
    public UserResponseDto updateUser(
            @Parameter(
                    description = "Уникальный идентификатор пользователя",
                    example = "666",
                    required = true
            )
            @PathVariable Integer id,
            @Valid @RequestBody UserRequestDto requestDto) {
        return userService.updateUser(id, requestDto);
    }
}

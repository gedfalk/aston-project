package dev.gedfalk.astonproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    @Schema(description = "Уникальный идентификатор пользователя", example = "666")
    private Integer id;

    @Schema(description = "Имя пользователя", example = "Джо Роган")
    private String name;

    @Schema(description = "Почтовый ящик пользователя", example = "joerogan@gmail.com")
    private String email;

    @Schema(description = "Возраст пользователя", example = "66")
    private Integer age;

    @Schema(description = "Дата и время создания пользователя. Задается автоматически системой")
    private LocalDateTime createdAt;
}

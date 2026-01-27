package dev.gedfalk.astonproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "Требуется ввести имя")
    @Schema(description = "Имя пользователя", example = "Джо Роган")
    private String name;

    @NotBlank(message = "Требуется ввести почтовый ящик")
    @Email(message = "Некорректный формат почтового ящика")
    @Schema(description = "Почтовый ящик пользователя", example = "joerogan@gmail.com")
    private String email;

    @NotNull(message = "Требуется ввести возраст")
    @Positive(message = "Возраст должен быть больше нуля")
    @Schema(
            description = "Возраст пользователя",
            example = "66",
            minimum = "1"
    )
    private Integer age;

}

package dev.gedfalk.astonproject.dto;

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
    private String name;

    @NotBlank(message = "Требуется ввести почтовый ящик")
    @Email(message = "Некорректный формат почтового ящика")
    private String email;

    @NotNull(message = "Требуется ввести возраст")
    @Positive(message = "Возраст должен быть больше нуля")
    private Integer age;

}

package com.example.PPProejct.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "dto для изменений в личном кабинете")
public class UserDto {
    @Schema(description = "Ваше ФИО", example = "Иванов Иван Иванович")
    @Size(min = 5, max = 50, message = "ФИО должно содержать от 5 до 50 символов")
    @NotBlank(message = "Поле ФИО не может быть пустым")
    private String username;

    @Schema(description = "Адрес электронной почты", example = "johndoe@gmail.com")
    @Size(min = 5, max = 100, message = "Адрес электронной почты должен содержать от 5 до 100 символов")
    @NotBlank(message = "Поле email не может быть пустым")
    @Email(message = "Email должен быть в формате user@example.com")
    private String email;

    @Schema(description = "Пароль", example = "my_secret_password")
    @Size(min = 5, max = 100, message = "Пароль должен содержать от 5 до 100 символов")
    @NotBlank(message = "Поле пароля не может быть пустым")
    private String password;

    @Schema(description = "Стек разработки, роль и задачи в команде", example = "Front-end разработчик на JS и react, пишу клиент и UI")
    @Size(min = 30, max = 250)
//    @NotBlank(message = "обязательно опишите свою деятельность")
    private String description;

    @Schema(description = "Ваше фото")
    @NotBlank
    private String image;
}

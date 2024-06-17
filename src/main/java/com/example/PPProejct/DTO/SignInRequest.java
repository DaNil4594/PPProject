package com.example.PPProejct.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на аутентификацию")
public class SignInRequest {

    //добавить фото в принципе везде

    @Schema(description = "Ваше ФИО", example = "John")
    @Size(min = 5, max = 50, message = "ФИО пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "ФИО не может быть пустым")
    private String username;

    @Schema(description = "Пароль", example = "my_secret_password")
    @Size(min = 5, max = 100, message = "Пароль должен содержать от 5 до 100 символов")
    @NotBlank(message = "Поле пароля не может быть пустым")
    private String password;
}
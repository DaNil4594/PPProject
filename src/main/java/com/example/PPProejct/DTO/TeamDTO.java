package com.example.PPProejct.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "DTO для создания команды")
public class TeamDTO {
    @Schema(description = "Название команды", example = "RND Crushers")
    @Size(min = 5, max = 20, message = "Название должно содержать от 5 до 20 символов")
    @NotBlank(message = "Поле названия команды не может быть пустым")
    private String teamName;

    @Schema(description = "Состав команды на хакатон")
//    @NotBlank(message = "Состав команды не может быть пустым")
    @NotNull
    @NotEmpty
    private List<String> rosterTeam;

    @Schema(description = "Баннер команды")
    @NotBlank
    private String banner;
}

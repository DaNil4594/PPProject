package com.example.PPProejct.Controller;

import com.example.PPProejct.DTO.TeamDTO;
import com.example.PPProejct.Entity.Team;
import com.example.PPProejct.Service.TeamService;
import com.example.PPProejct.Service.UserService;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Короче, после аутентификации")
public class AfterAuthController {

    private final UserService userService;
    private final TeamService teamService;

//просто протестить регу, потом удалить
    @GetMapping("/ex")
    @Operation(summary = "Доступен только авторизированным пользователям")
    public String example(){
        return "Hello, world!";

    }

//протестить роли

    @PostMapping("/create-team")
    @Operation(summary = "Доступен только авторизированным пользователям, с ролью TEAMLEAD")
    @PreAuthorize("hasRole('TEAMLEAD')")
    public ResponseEntity<Team> createTeam(@RequestBody @Valid TeamDTO teamDTO){
        log.info("здесь мы получаем в метод TeamDto(по идее) и создаем команду");
        Team needTeam = teamService.toEntity(teamDTO);
        teamService.save(needTeam);
        return ResponseEntity.ok(needTeam);
    }




    //протестить роли
    @GetMapping("/admin")
    @Operation(summary = "Доступен только авторизированным пользователям, с ролью ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    public String exampleAdmin(){
        return "Hello, admin!";

    }

    //доработать получение роли, сделать провреку кодовом словом
    @GetMapping("/get-admin")
    @Operation(summary = "Получить роль ADMIN(для демонстрации)")
    public void getAdmin(){
        userService.getAdmin();
    }


    @GetMapping("/get-lead")
    @Operation(summary = "Получить роль TEAMLEAD(для демонстрации)")
    public void getTeamLead(){
        userService.getTeamLead();
    }

}

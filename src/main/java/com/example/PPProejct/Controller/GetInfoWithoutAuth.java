package com.example.PPProejct.Controller;

import com.example.PPProejct.Entity.Team;
import com.example.PPProejct.Entity.User;
import com.example.PPProejct.Service.TeamService;
import com.example.PPProejct.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/no-auth")
@RequiredArgsConstructor
@Tag(name = "Контроллер для просмотра зарегистрированных команд и разработчиков, не авторизированным пользователем")
public class GetInfoWithoutAuth {
    private final TeamService teamService;
    private final UserService userService;

    @Operation(summary = "Информация о зарегистрированных командах")
    @GetMapping("/teams")
    public ResponseEntity<List<Team>> getInfoAuthTeams(){
        List<Team> resList = teamService.getAllTeams();
        return ResponseEntity.ok(resList);
    }

    @Operation(summary = "Информация о зарегистрированных разработчиках")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getInfoAuthUsers(){
        List<User> resList = userService.getAllUsers();
        return ResponseEntity.ok(resList);
    }

}

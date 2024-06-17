package com.example.PPProejct.Controller;


import com.example.PPProejct.Entity.Team;
import com.example.PPProejct.Entity.User;
import com.example.PPProejct.Service.TeamService;
import com.example.PPProejct.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin-panel")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Контроллер для удаления/изменения состава команд/разработчиков. Этот endpoint будет доступен только админам")
public class ManageController {
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

    @Operation(summary = "Удаление команды по ее названию")
    @PostMapping("/del-team-by-name")
    public void deleteTeamByTeamName(String teamName) throws Exception {
        teamService.deleteByTeamName(teamName);
    }

    @Operation(summary = "Удаление разработчика по его имени")
    @PostMapping("/del-user-by-name")
    public void deleteUserByUserName(String userName) throws Exception {
        userService.deleteByUsername(userName);
    }
}

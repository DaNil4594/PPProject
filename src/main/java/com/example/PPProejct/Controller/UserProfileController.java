package com.example.PPProejct.Controller;


import com.example.PPProejct.DTO.SignInRequest;
import com.example.PPProejct.DTO.SignUpRequest;
import com.example.PPProejct.DTO.TeamDTO;
import com.example.PPProejct.DTO.UserDto;
import com.example.PPProejct.Entity.Team;
import com.example.PPProejct.Entity.User;
import com.example.PPProejct.Service.TeamService;
import com.example.PPProejct.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/lk")
@RequiredArgsConstructor
@Tag(name = "Личный кабинет пользователя")
public class UserProfileController {
    private final TeamService teamService;
    private final UserService userService;

    @Operation(summary = "Информация о разработчике в личном кабинете")
    @GetMapping("/me")
    public ResponseEntity<User> infoAboutMe(){

        User me = userService.getCurrentUser();

        return ResponseEntity.ok(me);

    }
    @Operation(summary = "Информация о команде разработчика в личном кабинете")
    @GetMapping("/my-team")
    public ResponseEntity<String> myTeam(){
        User me = userService.getCurrentUser();

        List<String> names = new ArrayList<>();
        List<Team> teamList=teamService.getAllTeams();
        for (Team t :teamList) {
            List<User> everyUser =t.getRosterTeam();
            for (User u:everyUser) {
                if (me.getUsername().equals(u.getUsername())){
                    String inf = ("Я состою в команде:"+t.getTeamName());
                    return ResponseEntity.ok(inf);
                }
            }
        }
        String noInf = "\n Вы не состоите в команде";
        return ResponseEntity.ok(noInf);
    }

    @PostMapping("/edit-user")
    @Operation(summary = "Изменения данных пользователя")
    public ResponseEntity<User> editUser(@RequestBody @Valid UserDto userDto){

        log.info("здесь мы обновляем профиль");
        User updateUser = userService.toEntity(userDto);
        userService.save(updateUser);
        return ResponseEntity.ok(updateUser);
    }

    @PostMapping("/edit-team")
    @PreAuthorize("hasRole('TEAMLEAD')")
    @Operation(summary = "Изменения данных команды")
    public ResponseEntity<Team> editTeam(@RequestBody @Valid TeamDTO teamDTO){

            log.info("здесь мы обновляем команду");
            Team needTeam = teamService.toEntity(teamDTO);
            teamService.save(needTeam);
            return ResponseEntity.ok(needTeam);
    }
}

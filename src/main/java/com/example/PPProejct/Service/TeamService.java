package com.example.PPProejct.Service;


import com.example.PPProejct.DTO.TeamDTO;
import com.example.PPProejct.Entity.Team;
import com.example.PPProejct.Entity.User;
import com.example.PPProejct.Repository.TeamRepository;
import com.example.PPProejct.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserService userService;


    public Team save(Team team){
        if(teamRepository.existsByTeamName(team.getTeamName())){
            throw new RuntimeException("Команда с таким названием уже заявлена");
        }

        List<User> checkList = team.getRosterTeam();
        if(teamRepository.existsByRosterTeam(checkList)){
            throw new RuntimeException("Команда с таким составом уже заявлена");
        }
        log.info("Это метод сохранения пробую через него");
        return teamRepository.save(team);}

    public Team toEntity(TeamDTO teamDTO){
        log.info("Здесь  стартует метод toEntity");
        List<User> roster = userService.findByUsernames(teamDTO.getRosterTeam());
        // Проверка, найдены ли все пользователи
        if (roster.size() != teamDTO.getRosterTeam().size()) {
            // Обработайте ошибку, например, верните сообщение об ошибке
            throw new IllegalArgumentException("Не все пользователи из состава команды найдены");
        }
        var team = Team.builder()
                .teamName(teamDTO.getTeamName())
                .rosterTeam(roster)
                .banner(teamDTO.getBanner())
                .build();

        log.info("Тот же метод toEntity, но почти завыершенный, лог перед строкой return team");
        return team;

    }
    public void deleteByTeamName(String teamName) throws Exception {
        Team delTeam = teamRepository.findByTeamName(teamName).orElseThrow(()-> new Exception("Команды с таким названием не найдено"));
        teamRepository.delete(delTeam);
    }

    public Team create(Team team){
        if(teamRepository.existsByTeamName(team.getTeamName())){
            throw new RuntimeException("Команда с таким названием уже заявлена");
        }

        List<User> checkList = team.getRosterTeam();
        if(teamRepository.existsByRosterTeam(checkList)){
            throw new RuntimeException("Команда с таким составом уже заявлена");
        }

        log.info("А это метод create");
        return teamRepository.save(team);
    }


    //метод загрузки баннера, мб придется переделать
    public void uploadBanner(Long id, String base64Photo) throws IOException {
        Optional<Team> existingTeam = teamRepository.findById(id);
        if (existingTeam.isPresent()) {
            Team team = existingTeam.get();
            team.setBanner(base64Photo);
            teamRepository.save(team);
        } else {
            throw new RuntimeException("Team not found");
        }
    }
    
    public Team getByTeamName(String teamName) throws Exception {
        return teamRepository.findByTeamName(teamName).orElseThrow(() -> new Exception("Команды с таким названием не найдено"));


    }


    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }
}

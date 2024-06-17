package com.example.PPProejct.Repository;

import com.example.PPProejct.Entity.Team;
import com.example.PPProejct.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,Long> {

    Optional<Team> findByTeamName(String teamName);

    boolean existsByTeamName(String teamName);
    boolean existsByRosterTeam(List<User> roster);



}

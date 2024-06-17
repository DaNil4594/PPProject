package com.example.PPProejct.Repository;

import com.example.PPProejct.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String username);

//    void deleteByUsername(String username);
//
//    void updateByUsername(String username);
}

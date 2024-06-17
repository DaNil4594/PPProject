package com.example.PPProejct.Service;


import com.example.PPProejct.DTO.UserDto;
import com.example.PPProejct.Entity.Role;
import com.example.PPProejct.Entity.User;
import com.example.PPProejct.Repository.UserRepository;
import jakarta.el.ELException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User save(User user){

        return repository.save(user);
    }

    public User create(User user){
        if (repository.existsByUsername(user.getUsername())){
            throw new RuntimeException("Участник с таким именем уже существует");
        }

        if (repository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Участник с такой почтой уже существует");
        }

        return save(user);
    }
    public User toEntity(UserDto userDto){
        var user = User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password((userDto.getPassword()))
                .description(userDto.getDescription())
                .imagePath(userDto.getImage())
                .build();
        return user;
    }

    public List<User> findByUsernames(List<String> userNames) {
        return userNames.stream()
                .map(repository::findUserByUsername)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }



    //метод загрузки фото, надо с ним внимательнее
    public void uploadPhoto(Long id, String base64Photo) throws IOException {
        Optional<User> existingUser = repository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setImagePath(base64Photo);
            repository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }


    public User getByUserName(String username){
        return repository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Участник с таки именем не найден  "));
    }


    public UserDetailsService userDetailsService(){return this::getByUserName;}


    public User getCurrentUser(){
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUserName(username);
    }
    @Deprecated
    public void getAdmin(){
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }

    @Deprecated
    public void getTeamLead(){
        var user = getCurrentUser();
        user.setRole(Role.ROLE_TEAMLEAD);
        save(user);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public void deleteByUsername(String userName) throws Exception {
        repository.findUserByUsername(userName).orElseThrow(()->new Exception());
    }
}

package com.example.PPProejct.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username", unique = true,nullable = false)
    private String username;//В силу спецификации здесь будет фул фио

    @Column(name = "email", unique = true,nullable = false)
    private String email;

    @Column(name = "description", unique = true,nullable = false)
    private String description;//'о себе'

    @Column(name = "password",nullable = false)
    private String password;

    @Lob
    @Column(name = "imagePath", unique = true,nullable = false)
    private String imagePath;

    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = false)
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

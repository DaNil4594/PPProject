package com.example.PPProejct.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "teamName", unique = true,nullable = false)
    private String teamName;

    @OneToMany
    @Column(name = "rosterTeam", unique = true,nullable = false)
    private List<User> rosterTeam;

    @Lob
    @Column(name = "bannerPath", unique = true,nullable = false)
    private String banner;
}

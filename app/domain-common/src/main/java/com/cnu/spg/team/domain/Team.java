package com.cnu.spg.team.domain;

import com.cnu.spg.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @Column(length = 150, unique = true)
    private String teamName;

    @OneToMany(mappedBy = "team")
    private List<TeamElement> teamElement = new ArrayList<>();

    public Team(String teamName) {
        this.teamName = teamName;
    }

    public void addNewMember(TeamElement teamElement) {
        this.teamElement.add(teamElement);
        teamElement.changeTeam(this);
    }

    public static Team createNewTeam(User owner, TeamElement teamElement, String teamName) {
        Team newTeam = new Team(teamName);

        newTeam.addNewMember(teamElement);
        owner.joinNewTeam(teamElement);

        return newTeam;
    }
}

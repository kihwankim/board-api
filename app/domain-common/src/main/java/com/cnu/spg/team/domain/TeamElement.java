package com.cnu.spg.team.domain;

import com.cnu.spg.domain.BaseEntity;
import com.cnu.spg.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamElement extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_join_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    public void changeTeam(Team team) {
        this.team = team;
    }

    public void changeMember(User member) {
        this.member = member;
    }
}

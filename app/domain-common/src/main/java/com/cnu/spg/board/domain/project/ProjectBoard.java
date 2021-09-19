package com.cnu.spg.board.domain.project;

import com.cnu.spg.board.domain.Board;
import com.cnu.spg.board.domain.BoardType;
import com.cnu.spg.team.domain.Team;
import com.cnu.spg.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DiscriminatorValue(value = BoardType.Values.PROJECT)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectBoard extends Board {

//    @OneToMany(mappedBy = "eduBoard", cascade = CascadeType.ALL)
//    private List<ProjectJoinUser> projectJoinUsers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "project_category_id")
    ProjectCategory projectCategory;

    @Builder
    public ProjectBoard(User user, String title, String content) {
        super(user, title, content);
    }
}
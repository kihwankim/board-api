package com.cnu.spg.board.domain.project;

import com.cnu.spg.domain.BaseEntity;
import com.cnu.spg.user.domain.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ProjectReference extends BaseEntity {
    @Id
    @Column(name = "project_reference")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_user_id")
    private User referenceUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_board_id")
    private ProjectBoard projectBoard;
}

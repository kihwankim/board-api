package com.cnu.spg.board.domain.project;

import com.cnu.spg.domain.BaseEntity;
import com.cnu.spg.user.domain.User;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class ProjectCategory extends BaseEntity {
    @Id
    @Column(name = "project_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 75, nullable = false)
    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_category_id")
    private ProjectCategory parent;

    @OneToMany(mappedBy = "parent")
    private List<ProjectCategory> children;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_category_onwer"))
    private User categoryOwner;

    @OneToMany
    private List<ProjectBoard> projectBoards;
}

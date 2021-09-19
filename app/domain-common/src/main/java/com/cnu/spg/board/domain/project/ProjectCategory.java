package com.cnu.spg.board.domain.project;

import com.cnu.spg.domain.BaseEntity;
import com.cnu.spg.user.domain.User;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "category_name_user_id",
                columnNames = {"categoryName", "category_owner", "parent_id"}
        )
})
public class ProjectCategory extends BaseEntity {
    @Id
    @Column(name = "project_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 75, nullable = false)
    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ProjectCategory parent;

    @OneToMany(mappedBy = "parent")
    private List<ProjectCategory> children;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_owner")
    private User categoryOwner;

    @OneToMany
    private List<ProjectBoard> projectBoards;
}

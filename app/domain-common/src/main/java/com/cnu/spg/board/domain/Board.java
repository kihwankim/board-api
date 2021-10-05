package com.cnu.spg.board.domain;

import com.cnu.spg.domain.BaseEntity;
import com.cnu.spg.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(length = 40)
    private String title;

    private Long writerId;

    @Column(length = 20)
    private String writerName;

    @Lob
    private String content;

    @Version
    private Long version;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_user_id")
    private User user;

    protected Board(User user, String title, String content) {
        this.title = title;
        this.writerId = user.getId();
        this.writerName = user.getName();
        this.content = content;
        this.user = user;
    }
}

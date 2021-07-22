package com.cnu.spg.board.domain;

import com.cnu.spg.domain.BaseEntity;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "board_id")
    Long id;

    @Column(length = 40)
    private String title;

    private Long writerId;

    @Column(length = 20)
    private String writerName;

    @Lob
    private String content;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    private Board(User user, String title, String content) {
        this.title = title;
        this.writerId = user.getId();
        this.writerName = user.getName();
        this.content = content;
        this.user = user;
        this.user.getBoards().add(this);
    }
}

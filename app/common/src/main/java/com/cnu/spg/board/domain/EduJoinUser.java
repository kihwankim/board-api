package com.cnu.spg.board.domain;

import com.cnu.spg.domain.BaseEntity;
import com.cnu.spg.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EduJoinUser extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "edu_join_user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private EduBoard eduBoard;

    public static void registerNewUserInEduBoard(User user, EduBoard eduBoard) {
        EduJoinUser createdEduJoinBoard = new EduJoinUser(user, eduBoard);
        eduBoard.getEduJoinUsers().add(createdEduJoinBoard);
    }

    protected EduJoinUser(User user, EduBoard eduBoard) {
        this.user = user;
        this.eduBoard = eduBoard;
    }
}

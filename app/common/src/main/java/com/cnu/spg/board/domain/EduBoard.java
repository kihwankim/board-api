package com.cnu.spg.board.domain;

import com.cnu.spg.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DiscriminatorValue("E")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EduBoard extends Board {

    @OneToMany(mappedBy = "eduBoard", cascade = CascadeType.ALL)
    List<EduJoinUser> eduJoinUsers = new ArrayList<>();

    @Builder
    public EduBoard(User user, String title, String content) {
        super(user, title, content);
    }
}

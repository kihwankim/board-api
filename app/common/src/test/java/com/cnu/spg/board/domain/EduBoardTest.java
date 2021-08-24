package com.cnu.spg.board.domain;

import com.cnu.spg.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EduBoardTest {

    @Test
    @DisplayName("eduboard에 user 추가")
    void registUserInEduBoard() throws Exception {
        // given
        final String registerName = "regi";
        final String registerUsername = "regi@gmail.com";
        final String registerPlainPassword = "regPlainPassword";
        User registeUser = User.createUser(registerName, registerUsername, registerPlainPassword);

        final String name = "kkh";
        final String username = "kkh@gmail.com";
        final String plainPassword = "password";
        User joinToEduBoardUser = User.createUser(name, username, plainPassword);

        EduBoard eduBoard = EduBoard.builder()
                .user(registeUser)
                .content("hello")
                .title("title")
                .build();

        // when
        EduJoinUser.registerNewUserInEduBoard(joinToEduBoardUser, eduBoard);

        // then
        assertThat(eduBoard.getEduJoinUsers())
                .extracting("user")
                .extracting("username")
                .contains(username);
    }

    @Test
    @DisplayName("join element가 존재하지 않는 경우")
    void notFound_InEduBoard() throws Exception {
        // given
        final String registerName = "regi";
        final String registerUsername = "regi@gmail.com";
        final String registerPlainPassword = "regPlainPassword";
        User registeUser = User.createUser(registerName, registerUsername, registerPlainPassword);

        final String name = "kkh";
        final String username = "kkh@gmail.com";
        final String plainPassword = "password";
        User joinToEduBoardUser = User.createUser(name, username, plainPassword);

        EduBoard eduBoard = EduBoard.builder()
                .user(registeUser)
                .content("hello")
                .title("title")
                .build();

        // when
        EduJoinUser.registerNewUserInEduBoard(joinToEduBoardUser, eduBoard);

        // then
        assertThat(eduBoard.getEduJoinUsers())
                .extracting("user")
                .extracting("username")
                .doesNotContain(registerName);
    }
}
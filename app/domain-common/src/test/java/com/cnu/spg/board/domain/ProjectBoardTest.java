package com.cnu.spg.board.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProjectBoardTest {

    @Test
    @DisplayName("eduboard에 user 추가")
    void registUserInEduBoard() throws Exception {
//        // given
//        final String registerName = "regi";
//        final String registerUsername = "regi@gmail.com";
//        final String registerPlainPassword = "regPlainPassword";
//        User registeUser = User.createUser(registerName, registerUsername, registerPlainPassword);
//
//        final String name = "kkh";
//        final String username = "kkh@gmail.com";
//        final String plainPassword = "password";
//        User joinToEduBoardUser = User.createUser(name, username, plainPassword);
//
//        ProjectBoard projectBoard = ProjectBoard.builder()
//                .user(registeUser)
//                .content("hello")
//                .title("title")
//                .build();
//
//        // when
//        ProjectReference.registerNewUserInEduBoard(joinToEduBoardUser, projectBoard);
//
//        // then
//        assertThat(projectBoard.getProjectJoinUsers())
//                .extracting("user")
//                .extracting("username")
//                .contains(username);
    }

//    @Test
//    @DisplayName("join element가 존재하지 않는 경우")
//    void notFound_InEduBoard() throws Exception {
//        // given
//        final String registerName = "regi";
//        final String registerUsername = "regi@gmail.com";
//        final String registerPlainPassword = "regPlainPassword";
//        User registeUser = User.createUser(registerName, registerUsername, registerPlainPassword);
//
//        final String name = "kkh";
//        final String username = "kkh@gmail.com";
//        final String plainPassword = "password";
//        User joinToEduBoardUser = User.createUser(name, username, plainPassword);
//
//        ProjectBoard projectBoard = ProjectBoard.builder()
//                .user(registeUser)
//                .content("hello")
//                .title("title")
//                .build();
//
//        // when
//        ProjectReference.registerNewUserInEduBoard(joinToEduBoardUser, projectBoard);
//
//        // then
//        assertThat(projectBoard.getProjectJoinUsers())
//                .extracting("user")
//                .extracting("username")
//                .doesNotContain(registerName);
//    }
}
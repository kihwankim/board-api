package com.cnu.spg.user.service;

import com.cnu.spg.security.service.LoginService;
import com.cnu.spg.user.domain.User;
import com.cnu.spg.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LoginServiceTest {
    private static final String username = "kkh@gmail.com";

    LoginService loginService;
    UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        loginService = new LoginService(userRepository);


        User john = User.createUser("john", "john@gmail.com", "Abc123!");
        User kkh = User.createUser("kkh", "kkh@gmail.com", "Abc123!");
        User hello = User.createUser("hello", "hello@gmail.com", "Abc123!");

        given(userRepository.findByUsername(john.getUsername()))
                .willReturn(Optional.of(john));
        given(userRepository.findByUsername(kkh.getUsername()))
                .willReturn(Optional.of(kkh));
        given(userRepository.findByUsername(hello.getUsername()))
                .willReturn(Optional.of(hello));
    }

    @Test
    @DisplayName("username으로 회원 조회")
    public void userLoadingTest() throws Exception {
        // given

        // when
        UserDetails userDetails = loginService.loadUserByUsername(username);

        // then
        assertEquals(userDetails.getUsername(), username);
    }

    @Test
    @DisplayName("username으로 회원 조회 실패")
    void userNotExistTest() throws Exception {
        // given
        final String notExistUsername = "notexistUsername";
        given(userRepository.findByUsername(notExistUsername))
                .willReturn(Optional.empty());

        // when

        // then
        assertThrows(UsernameNotFoundException.class, () -> loginService.loadUserByUsername(notExistUsername));
    }
}
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
    LoginService loginService;
    UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        loginService = new LoginService(userRepository);
    }

    @Test
    @DisplayName("username으로 회원 조회")
    void userLoadingTest() throws Exception {
        // given

        User kkh = User.createUser("kkh", "kkh@gmail.com", "Abc123!");
        given(userRepository.findByUsername(kkh.getUsername()))
                .willReturn(Optional.of(kkh));

        // when
        UserDetails userDetails = loginService.loadUserByUsername("kkh@gmail.com");

        // then
        assertEquals("kkh@gmail.com", userDetails.getUsername());
    }

    @Test
    @DisplayName("username으로 회원 조회 실패")
    void userNotExistTest() throws Exception {
        // given
        given(userRepository.findByUsername("notexistUsername"))
                .willReturn(Optional.empty());

        // when

        // then
        assertThrows(UsernameNotFoundException.class, () -> loginService.loadUserByUsername("notexistUsername"));
    }
}
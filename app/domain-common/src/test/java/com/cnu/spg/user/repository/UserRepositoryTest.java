package com.cnu.spg.user.repository;

import com.cnu.spg.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("user db 처리 관련 테스트")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager em;

    private static final String name = "kkh";
    private static final String username = "kkh@gmail.com";
    private static final String password = "password";


    @BeforeEach
    void beforeUserTest() {
        User user = User.createUser(name, username, password);

        em.persist(user);

        em.flush();
        em.clear();
    }

    @Test
    void findByCorrectUsernameTest() {
        // given
        final String findedUsername = username;

        // when
        User findedUser = userRepository.findByUsername(findedUsername)
                .orElseThrow(() -> new UsernameNotFoundException("exception"));

        // then
        assertEquals(findedUsername, findedUser.getUsername());
        assertEquals(password, findedUser.getPassword());
        assertEquals(name, findedUser.getName());
    }

    @Test
    void findByNotExistUsername() {
        // given
        final String findedUsername = "error";

        // when


        // then
        assertThrows(UsernameNotFoundException.class, () -> userRepository.findByUsername(findedUsername)
                .orElseThrow(() -> new UsernameNotFoundException("exception")));
    }

    @Test
    void deleteByUsername() throws Exception {
        // given

        // when
        userRepository.deleteByUsername(username);

        // then
        assertThrows(UsernameNotFoundException.class, () -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("not found")));
    }

    @Test
    void isNotExistUsernameTest() throws Exception {
        // given
        final String notExistUsername = "notExist";

        // when
        boolean result = userRepository.existsByUsername(notExistUsername);

        // then
        assertFalse(result);
    }

    @Test
    void isExistUsernameTest() throws Exception {
        // given

        // when
        boolean result = userRepository.existsByUsername(username);

        // then
        assertTrue(result);
    }
}
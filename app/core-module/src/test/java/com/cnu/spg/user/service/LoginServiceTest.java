package com.cnu.spg.user.service;

import com.cnu.spg.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class LoginServiceTest {

    @Autowired
    LoginService loginService;

    @Autowired
    EntityManager em;

    @Test
    public void userLoadingTest() throws Exception {
        // given
        final String username = "kkh@gmail.com";
        User john = User.createUser("john", "john@gmail.com", "fun123");
        User kkh = User.createUser("kkh", "kkh@gmail.com", "fun123");
        User hello = User.createUser("hello", "hello@gmail.com", "fun123");

        em.persist(john);
        em.persist(kkh);
        em.persist(hello);

        // when
        UserDetails userDetails = loginService.loadUserByUsername(username);

        // then
        assertEquals(userDetails.getUsername(), username);
    }
}
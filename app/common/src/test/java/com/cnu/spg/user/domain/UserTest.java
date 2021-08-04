package com.cnu.spg.user.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserTest {

    @Test
    void createUserTest() {
        // given
        final String name = "kkh";
        final String username = "kkh@gmail.com";
        final String password = "newPassword";

        // when
        User user = User.createUser(name, username, password);

        // then
        assertEquals(password, user.getPassword());
        assertEquals(username, user.getUsername());
        assertEquals(name, user.getName());
    }

    @Test
    void changePasswordTest() {
        // given
        final String name = "kkh";
        final String username = "kkh@gmail.com";
        final String password = "password";
        User user = User.createUser(name, username, password);

        final String newEncryptPassword = "newpassword";

        // when
        user.changePassword(newEncryptPassword);

        // then
        assertEquals(newEncryptPassword, user.getPassword());
    }

    @Test
    void changePasswordAndCheckPrevPassTest() {
        // given
        final String name = "kkh";
        final String username = "kkh@gmail.com";
        final String password = "password";
        User user = User.createUser(name, username, password);

        final String newEncryptPassword = "newpassword";

        // when
        user.changePassword(newEncryptPassword);

        // then
        assertNotEquals(password, user.getPassword());
    }

    @Test
    void changeNameTest() {
        // given
        final String name = "kkh";
        final String username = "kkh@gmail.com";
        final String password = "password";
        User user = User.createUser(name, username, password);

        final String newName = "newName";

        // when
        user.changeName(newName);

        // then
        assertEquals(newName, user.getName());
    }

    @Test
    void checkPrevNameTest() {
        final String name = "kkh";
        final String username = "kkh@gmail.com";
        final String password = "password";
        User user = User.createUser(name, username, password);

        final String newName = "newName";

        // when
        user.changeName(newName);

        // then
        assertNotEquals(name, user.getName());
    }
}
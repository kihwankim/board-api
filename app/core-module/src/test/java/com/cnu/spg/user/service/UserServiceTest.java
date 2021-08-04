package com.cnu.spg.user.service;

import com.cnu.spg.user.domain.Role;
import com.cnu.spg.user.domain.RoleName;
import com.cnu.spg.user.domain.User;
import com.cnu.spg.user.dto.UserRegisterDto;
import com.cnu.spg.user.exception.UsernameAlreadyExistException;
import com.cnu.spg.user.repository.RoleRepository;
import com.cnu.spg.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        Role admin = new Role(RoleName.ROLE_ADMIN);
        Role student = new Role(RoleName.ROLE_STUDENT);
        Role unAuth = new Role(RoleName.ROLE_UNAUTH);

        roleRepository.save(admin);
        roleRepository.save(student);
        roleRepository.save(unAuth);

        String password = "fun123";

        User john = User.createUser("john", "john", passwordEncoder.encode(password), admin);
        User susan = User.createUser("susan", "susan", passwordEncoder.encode(password), unAuth);
        User amanda = User.createUser("amanda", "amanda", passwordEncoder.encode(password), admin, student);

        userRepository.save(john);
        userRepository.save(susan);
        userRepository.save(amanda);
    }

    @Test
    @DisplayName("회원 가입")
    void regiesterUser() {
    }

    @Test
    @DisplayName("이미 존재하는 username 입력시 에러 발생")
    void registerUserFail_For_ExistUsername() throws Exception {
        // given
        String existUsername = "john";
        String password = "fun123";

        // when
        UserRegisterDto userRegisterDto = UserRegisterDto.builder()
                .userName(existUsername)
                .name("name")
                .password(password)
                .matchingPassword(password)
                .build();

        // then
        Assertions.assertThrows(UsernameAlreadyExistException.class, () -> userService.regiesterUser(userRegisterDto));
    }

    @Test
    void findByUserName() {
    }

    @Test
    void deleteByUserName() {
    }

    @Test
    void changeUserPassword() {
    }

    @Test
    void testChangeUserPassword() {
    }

    @Test
    void searchUserInfo() {
    }

    @Test
    void checkNowPassword() {
    }

    @Test
    void updateUsernameAndName() {
    }
}
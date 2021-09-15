package com.cnu.spg.user.service;

import com.cnu.spg.user.domain.Role;
import com.cnu.spg.user.domain.RoleName;
import com.cnu.spg.user.domain.User;
import com.cnu.spg.user.dto.UserPasswordChangingDto;
import com.cnu.spg.user.dto.UserRegisterDto;
import com.cnu.spg.user.exception.PasswordNotMatchException;
import com.cnu.spg.user.exception.ResourceNotFoundException;
import com.cnu.spg.user.exception.UsernameAlreadyExistException;
import com.cnu.spg.user.repository.RoleRepository;
import com.cnu.spg.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

    String existUsername = "john";
    String existUserPassword = "fun123";

    @BeforeEach
    void setUp() {
        Role admin = new Role(RoleName.ROLE_ADMIN);
        Role student = new Role(RoleName.ROLE_STUDENT);
        Role unAuth = new Role(RoleName.ROLE_UNAUTH);

        roleRepository.save(admin);
        roleRepository.save(student);
        roleRepository.save(unAuth);

        String password = existUserPassword;

        User john = User.createUser(existUsername, existUsername, passwordEncoder.encode(password), admin);
        User susan = User.createUser("susan", "susan", passwordEncoder.encode(password), unAuth);
        User amanda = User.createUser("amanda", "amanda", passwordEncoder.encode(password), admin, student);

        userRepository.save(john);
        userRepository.save(susan);
        userRepository.save(amanda);
    }

    @Test
    @DisplayName("회원 가입 성공")
    void regiesterUser() {
        // givne
        String newUsername = "newuser";
        String userPassword = "password";
        String newName = "name";
        UserRegisterDto userRegisterDto = UserRegisterDto.builder()
                .userName(newUsername)
                .name(newName)
                .password(userPassword)
                .matchingPassword(userPassword)
                .build();

        // when
        Long userId = userService.regiesterUser(userRegisterDto);
        User user = userRepository.findById(userId)
                .orElseThrow(IllegalArgumentException::new);

        // then
        assertEquals(newUsername, user.getUsername());
        assertTrue(passwordEncoder.matches(userPassword, user.getPassword()));
        assertEquals(newName, user.getName());
        assertEquals(1, user.getRoles().size());
        assertThat(user.getRoles()).extracting(Role::getName).containsExactly(RoleName.ROLE_UNAUTH);
    }

    @Test
    @DisplayName("이미 존재하는 username 입력시 에러 발생")
    void registerUserFail_For_ExistUsername() throws Exception {
        // given
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
    @DisplayName("아이디로 회원 조회")
    void findByUserName() {
        // given
        String findUsername = existUsername;
        String findPassowrd = existUserPassword;

        // when
        User findUser = userService.findByUserName(findUsername);

        // then
        assertEquals(findUsername, findUser.getUsername());
        assertTrue(passwordEncoder.matches(findPassowrd, findUser.getPassword()));
    }

    @Test
    @DisplayName("잘못된 아이디로 회원 찾기 실패 테스트")
    void findFail_By_Username() throws Exception {
        // given
        String notExistUseranme = "notexist";

        // when

        // then
        assertThrows(ResourceNotFoundException.class, () -> userService.findByUserName(notExistUseranme));
    }

    @Test
    @DisplayName("user 이름으로 사용자 제거")
    void delete_Exist_Username_Test() {
        // given
        String existUsername = this.existUsername;

        // when
        userService.withdrawMemberShip(existUsername);

        // then
        assertThrows(ResourceNotFoundException.class,
                () -> userService.findByUserName(existUsername));
    }

    @Test
    @DisplayName("존재하지 않는 user 회원 탈퇴 요청")
    void delete_NotExist_User_Test() throws Exception {
        // given
        String notExistUsername = "notexist";

        // when

        // then
        assertThrows(UsernameNotFoundException.class,
                () -> userService.withdrawMemberShip(notExistUsername));
    }

    @Test
    @DisplayName("회원 변경 정상 작동")
    void changeUserPassword_Test() {
        // given
        String existUsername = this.existUsername;
        String existPassword = this.existUserPassword;

        String newPassword = "newpassword";
        String newPasswordMatching = "newpassword";

        UserPasswordChangingDto passwordChangingDto = new UserPasswordChangingDto(existPassword, newPassword, newPasswordMatching);

        // when
        User user = userService.changeUserPassword(existUsername, passwordChangingDto);

        // then
        assertTrue(passwordEncoder.matches(newPassword, user.getPassword()));
        assertEquals(newPassword, newPasswordMatching);
        assertEquals(existUsername, user.getUsername());
    }

    @Test
    @DisplayName("이전 password가 일치 하지 않을 경우 에러 발생")
    void failChangin_Cuz_Not_Matched_PrevisouPassword_Test() throws Exception {
        // given
        String existUsername = this.existUsername;
        String notMatchedPassword = "notMatchedPassword";

        String newPassword = "newpassword";
        String newPasswordMatching = "newpassword";

        UserPasswordChangingDto passwordChangingDto = new UserPasswordChangingDto(notMatchedPassword, newPassword, newPasswordMatching);

        // when

        // then
        assertThrows(PasswordNotMatchException.class, () -> userService.changeUserPassword(existUsername, passwordChangingDto));
    }

    @Test
    void failChanging_User_IsNot_Exist_Test() throws Exception {
        // given
        String existUsername = "notExistuseranme";
        String existPassword = this.existUserPassword;

        String newPassword = "newpassword";
        String newPasswordMatching = "newpassword";

        UserPasswordChangingDto passwordChangingDto = new UserPasswordChangingDto(existPassword, newPassword, newPasswordMatching);

        // when

        // then
        assertThrows(UsernameNotFoundException.class, () -> userService.changeUserPassword(existUsername, passwordChangingDto));
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

    @Test
    void getAllTeamGroup() throws Exception {
        // given
        
        // when

        // then
    }
}
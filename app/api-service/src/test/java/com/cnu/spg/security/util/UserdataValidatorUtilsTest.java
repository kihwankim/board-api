package com.cnu.spg.security.util;

import com.cnu.spg.security.exception.LoginRequestParamterNotValidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserdataValidatorUtilsTest {
    @Test
    @DisplayName("username 정상 케이스")
    void isUsernameLengthValid() {
        // given
        String username = "abcde";

        // when

        // then
        assertTrue(UserdataValidatorUtils.isUsernameValid(username));
    }

    @Test
    @DisplayName("username이 짧아서 에러나는 경우")
    void isUsernameLengthValidError() throws Exception {
        // given
        String username = "abcd";

        // when

        // then
        assertThrows(LoginRequestParamterNotValidException.class, () -> UserdataValidatorUtils.isUsernameValid(username));
    }

    @Test
    @DisplayName("username이 길어서 에러나는 경우")
    void isUsernameLengthLongValidError() throws Exception {
        // given
        String usernamePart = "a";

        // when
        String username = usernamePart.repeat(81);

        // then
        assertThrows(LoginRequestParamterNotValidException.class, () -> UserdataValidatorUtils.isUsernameValid(username));
    }

    @Test
    @DisplayName("passoword 정상 동작")
    void isPasswordValid() {
        // given
        String passowrd = "Abc123!";

        // when
        boolean result = UserdataValidatorUtils.isPasswordValid(passowrd);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("길이가 작은 경우 에러")
    void isPasswordValidLessThanMinLength() throws Exception {
        // given

        // when

        // then
        assertThrows(LoginRequestParamterNotValidException.class, () -> UserdataValidatorUtils.isPasswordValid("aB1!"));
    }

    @Test
    @DisplayName("길이가 길어서 에러 나는 경우")
    void isPasswordValidHigherThanMaxLength() throws Exception {
        // given
        String password = "aB1!".repeat(20) + "1"; // length == 81

        // when

        // then
        assertThrows(LoginRequestParamterNotValidException.class, () -> UserdataValidatorUtils.isPasswordValid(password));
    }

    @Test
    @DisplayName("소문자만 사용한 경우")
    void isPasswordValidUseSmallCharacter() throws Exception {
        // given

        // when

        // then
        assertThrows(LoginRequestParamterNotValidException.class, () -> UserdataValidatorUtils.isPasswordValid("abcdef"));
    }

    @Test
    @DisplayName("대문자만 사용한 경우")
    void isPasswordValidOnlyUserUpperCaseCharacter() throws Exception {
        // given

        // when

        // then
        assertThrows(LoginRequestParamterNotValidException.class, () -> UserdataValidatorUtils.isPasswordValid("ABCDEF"));
    }

    @Test
    @DisplayName("문자열만 사용한 경우")
    void isPasswordValidUserOnlyCharacters() throws Exception {
        // given

        // when

        // then
        assertThrows(LoginRequestParamterNotValidException.class, () -> UserdataValidatorUtils.isPasswordValid("aAbdfacda"));
    }

    @Test
    @DisplayName("숫자만 사용한 경우")
    void isPasswordValidUserOnlyDigit() throws Exception {
        // given

        // when

        // then
        assertThrows(LoginRequestParamterNotValidException.class, () -> UserdataValidatorUtils.isPasswordValid("1231424"));
    }

    @Test
    @DisplayName("숫자와 문자열을 같이 사용한 경우")
    void isPasswordValidUserDigitWithChar() throws Exception {
        // given

        // when

        // then
        assertThrows(LoginRequestParamterNotValidException.class, () -> UserdataValidatorUtils.isPasswordValid("abAsd1231424"));
    }

    @Test
    @DisplayName("특수문자만 사용한 경우")
    void isPasswordValidOnlySpeicalChar() throws Exception {
        // given

        // when

        // then
        assertThrows(LoginRequestParamterNotValidException.class, () -> UserdataValidatorUtils.isPasswordValid("!@#%**"));
    }

    @Test
    @DisplayName("특수문자와 숫자만 사용한 경우")
    void isPasswordValidOnlySpeicalCharWithDigit() throws Exception {
        // given

        // when

        // then
        assertThrows(LoginRequestParamterNotValidException.class, () -> UserdataValidatorUtils.isPasswordValid("!@#%**123"));
    }

    @Test
    @DisplayName("특수 문자와 문자열만 상용한 경우")
    void isPasswordValidOnlySpeicalCharWithChar() throws Exception {
        // given

        // when

        // then
        assertThrows(LoginRequestParamterNotValidException.class, () -> UserdataValidatorUtils.isPasswordValid("!@#%**avdA"));
    }
}
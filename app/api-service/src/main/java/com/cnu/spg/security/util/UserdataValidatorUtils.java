package com.cnu.spg.security.util;

import com.cnu.spg.security.exception.LoginRequestParamterNotValidException;

public class UserdataValidatorUtils {
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$#!%*?&])[A-Za-z\\d@$#!%*?&]{5,80}$";
    private static final int MAX_USERNAME_LEN = 80;
    private static final int MIN_USERNAME_LEN = 5;

    private static final String USERNAME_IS_NOT_VALID_MSG = "ID is not valid";
    private static final String PASSWORD_IS_NOT_VALID_MSG = "password is not valid";

    public static boolean isUsernameValid(String username) {
        if (MIN_USERNAME_LEN <= username.length() && username.length() <= MAX_USERNAME_LEN) {
            return true;
        }

        throw new LoginRequestParamterNotValidException(USERNAME_IS_NOT_VALID_MSG);
    }

    public static boolean isPasswordValid(String password) {
        if (password.matches(PASSWORD_REGEX)) {
            return true;
        }

        throw new LoginRequestParamterNotValidException(PASSWORD_IS_NOT_VALID_MSG);
    }
}

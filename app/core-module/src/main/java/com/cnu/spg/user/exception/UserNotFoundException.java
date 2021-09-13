package com.cnu.spg.user.exception;

public class UserNotFoundException extends RuntimeException {
    private static final String USER_NOT_FOUND_EXCEPTION_MSG = "user name not found exception";

    public UserNotFoundException() {
        super(USER_NOT_FOUND_EXCEPTION_MSG);
    }
}

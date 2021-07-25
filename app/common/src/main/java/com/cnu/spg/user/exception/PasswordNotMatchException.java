package com.cnu.spg.user.exception;

public class PasswordNotMatchException extends RuntimeException {
    private static final String PASSWORD_IS_NOT_MATCHED_MSG = "password is not matched";

    public PasswordNotMatchException() {
        super(PASSWORD_IS_NOT_MATCHED_MSG);
    }
}

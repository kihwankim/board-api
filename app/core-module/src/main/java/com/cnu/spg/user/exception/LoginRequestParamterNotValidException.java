package com.cnu.spg.user.exception;

public class LoginRequestParamterNotValidException extends RuntimeException {
    private static final String PARAMTER_NOT_VALID_MSG = "login parameter is not valid";

    public LoginRequestParamterNotValidException() {
        super(PARAMTER_NOT_VALID_MSG);
    }
}

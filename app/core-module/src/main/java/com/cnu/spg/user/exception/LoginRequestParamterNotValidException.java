package com.cnu.spg.user.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginRequestParamterNotValidException extends AuthenticationException {
    private static final String PARAMTER_NOT_VALID_MSG = "login parameter is not valid";

    public LoginRequestParamterNotValidException() {
        super(PARAMTER_NOT_VALID_MSG);
    }

    public LoginRequestParamterNotValidException(String msg) {
        super(msg);
    }
}

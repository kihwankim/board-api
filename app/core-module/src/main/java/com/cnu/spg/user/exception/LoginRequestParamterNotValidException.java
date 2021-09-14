package com.cnu.spg.user.exception;

public class LoginRequestParamterNotValidException extends RuntimeException {
    private static final String PARAMTER_NOT_VALID_MSG = "login paramter가 적절하지 못합니다.";

    public LoginRequestParamterNotValidException() {
        super(PARAMTER_NOT_VALID_MSG);
    }
}

package com.cnu.spg.user.exception;

public class TokenIsNotValidException extends RuntimeException {
    private static final String TOKEN_IS_NOT_VALID_MSG = "token is not valid";

    public TokenIsNotValidException() {
        super(TOKEN_IS_NOT_VALID_MSG);
    }
}

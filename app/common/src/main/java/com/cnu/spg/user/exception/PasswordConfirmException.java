package com.cnu.spg.user.exception;

public class PasswordConfirmException extends RuntimeException {
    public PasswordConfirmException(String error) {
        super(error);
    }
}

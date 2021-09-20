package com.cnu.spg.user.exception;

public class PasswordNotConfirmException extends RuntimeException {
    private static final String PASSWORD_IS_NOT_COMFIRM_MSG = "password is not cofirm";

    public PasswordNotConfirmException() {
        super(PASSWORD_IS_NOT_COMFIRM_MSG);
    }
}

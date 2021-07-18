package com.cnu.spg.user.exception;

public class UsernameAlreadyExistException extends RuntimeException {
    public UsernameAlreadyExistException(String username) {
        super(String.format("%s is already existed", username));
    }
}

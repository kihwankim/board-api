package com.cnu.spg.user.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserTypeIsNotValid extends RuntimeException {
    private static final String USER_TYPE_ERROR_MSG = "is not valid type";

    public UserTypeIsNotValid(String  className) {
        super(USER_TYPE_ERROR_MSG);
        log.error("user type error class type : {}", className);
    }
}

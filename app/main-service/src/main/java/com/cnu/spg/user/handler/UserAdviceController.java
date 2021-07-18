package com.cnu.spg.user.handler;

import com.cnu.spg.user.dto.UserRegisterDto;
import com.cnu.spg.user.exception.UsernameAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class UserAdviceController {
    @ExceptionHandler(UsernameAlreadyExistException.class)
    protected String saveUserFailHandler(UsernameAlreadyExistException exception, Model model) {
        model.addAttribute("user", new UserRegisterDto());
        model.addAttribute("registrationError", "User name already exists.");
        log.warn(exception.getMessage());

        return "register";
    }
}

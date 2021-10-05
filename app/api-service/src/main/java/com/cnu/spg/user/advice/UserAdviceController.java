package com.cnu.spg.user.advice;

import com.cnu.spg.comon.exception.NotFoundException;
import com.cnu.spg.user.exception.PasswordNotMatchException;
import com.cnu.spg.user.exception.RoleNotFoundException;
import com.cnu.spg.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class UserAdviceController {
    /**
     * 404 Not Found
     * board 정보를 받을 수 없음
     */
    @ExceptionHandler({RoleNotFoundException.class, UserNotFoundException.class})
    protected ResponseEntity<String> handleNotFoundException(final NotFoundException exception) {
        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    /**
     * 400 Bad Request
     * password 정보 에러
     */
    @ExceptionHandler(PasswordNotMatchException.class)
    protected ResponseEntity<String> handlePasswordMatchException(final PasswordNotMatchException exception) {
        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}

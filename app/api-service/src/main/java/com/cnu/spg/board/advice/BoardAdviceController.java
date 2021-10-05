package com.cnu.spg.board.advice;

import com.cnu.spg.board.exception.BoardNotFoundException;
import com.cnu.spg.board.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.InvalidParameterException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class BoardAdviceController {
    /**
     * 404 Not Found
     * board 정보를 받을 수 없음
     */
    @ExceptionHandler({BoardNotFoundException.class, CategoryNotFoundException.class})
    protected ResponseEntity<String> handleBoardNotFoundException(final BoardNotFoundException exception) {
        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    /**
     * 400 Bad Request
     * board type 정보 이슈
     */
    @ExceptionHandler(InvalidParameterException.class)
    protected ResponseEntity<String> handleInvalidBoardTypeException(final InvalidParameterException exception) {
        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}

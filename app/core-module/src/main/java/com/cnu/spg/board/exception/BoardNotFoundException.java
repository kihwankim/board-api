package com.cnu.spg.board.exception;

import java.util.NoSuchElementException;

public class BoardNotFoundException extends NoSuchElementException {
    private static final String BOARD_IS_NOT_EXIST_MSG = "존재하지 않는 board 입니다.";

    public BoardNotFoundException() {
        super(BOARD_IS_NOT_EXIST_MSG);
    }
}

package com.cnu.spg.board.exception;

public class NotExistBoardTypeException extends RuntimeException {
    private static final String BOARD_TYPE_NOT_EXIST_EXCEPTION = "없는 타입의 board 입니다";

    public NotExistBoardTypeException() {
        super(BOARD_TYPE_NOT_EXIST_EXCEPTION);
    }
}

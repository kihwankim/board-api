package com.cnu.spg.board.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum BoardType {
    BOARD(Values.BOARD),
    PROJECT(Values.PROJECT);

    private final String value;

    BoardType(String value) {
        if (!name().equals(value)) {
            throw new IllegalArgumentException("Incorrect use of type name");
        }

        this.value = value;
    }

    public static Optional<BoardType> findBoardTypeByKey(String key) {
        return Arrays.stream(BoardType.values())
                .filter(boardType -> boardType.getValue().equals(key))
                .findFirst();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Values {
        public static final String BOARD = "BOARD";
        public static final String PROJECT = "PROJECT";
    }
}

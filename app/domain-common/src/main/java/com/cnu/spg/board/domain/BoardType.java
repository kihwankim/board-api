package com.cnu.spg.board.domain;

import lombok.Getter;

@Getter
public enum BoardType {
    EDU(Values.EDU);

    private final String boardTypeName;

    BoardType(String boardTypeName) {
        if (!name().equals(boardTypeName)) {
            throw new IllegalArgumentException("Incorrect use of type name");
        }

        this.boardTypeName = boardTypeName;
    }

    public static class Values {
        public static final String EDU = "EDU";
    }
}

package com.cnu.spg.board.exception;

import com.cnu.spg.comon.exception.NotFoundException;

public class CategoryNotFoundException extends NotFoundException {
    private static final String CATEGORY_NOT_FOUND_EXCEPTION_MSG = "카테고리를 찾을 수 없습니다";

    public CategoryNotFoundException() {
        super(CATEGORY_NOT_FOUND_EXCEPTION_MSG);
    }
}

package com.cnu.spg.board.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class BoardDashBoardDto {
    Page<BoardDto> boards;

    public BoardDashBoardDto(Page<BoardDto> boards) {
        this.boards = boards;
    }
}

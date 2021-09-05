package com.cnu.spg.board.dto.response;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class BoardDashBoardResponseDto {
    Page<BoardResponseDto> boards;

    public BoardDashBoardResponseDto(Page<BoardResponseDto> boards) {
        this.boards = boards;
    }
}

package com.cnu.spg.board.dto.response;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class BoardDashBoardResponse {
    Page<BoardResponse> boards;

    public BoardDashBoardResponse(Page<BoardResponse> boards) {
        this.boards = boards;
    }
}

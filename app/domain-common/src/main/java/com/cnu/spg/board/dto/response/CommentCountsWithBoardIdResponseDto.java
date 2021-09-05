package com.cnu.spg.board.dto.response;

import lombok.Data;

@Data
public class CommentCountsWithBoardIdResponseDto {
    private Long boardId;
    private long numberOfComments;

    public CommentCountsWithBoardIdResponseDto(Long boardId, long numberOfComments) {
        this.boardId = boardId;
        this.numberOfComments = numberOfComments;
    }
}

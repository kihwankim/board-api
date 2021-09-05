package com.cnu.spg.board.repository.query;

import com.cnu.spg.board.dto.response.CommentCountsWithBoardIdResponseDto;

import java.util.List;

public interface CommentDtoQueryRepository {
    List<CommentCountsWithBoardIdResponseDto> findCountListAndBoardIdBulk(List<Long> boardIdx);
}

package com.cnu.spg.board.repository.query;

import com.cnu.spg.board.dto.reponse.CommentCountsWithBoardIdResponseDto;

import java.util.List;

public interface CommentDtoQueryRepository {
    List<CommentCountsWithBoardIdResponseDto> findCountListAndBoardIdBulk(List<Long> boardIdx);
}

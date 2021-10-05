package com.cnu.spg.board.repository.query;

import com.cnu.spg.board.dto.reponse.CommentCountsWithBoardIdDto;

import java.util.List;

public interface CommentDtoQueryRepository {
    List<CommentCountsWithBoardIdDto> findCountListAndBoardIdBulk(List<Long> boardIdx);
}

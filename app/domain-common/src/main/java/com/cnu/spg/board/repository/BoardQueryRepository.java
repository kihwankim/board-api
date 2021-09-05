package com.cnu.spg.board.repository;

import com.cnu.spg.board.domain.Board;
import com.cnu.spg.board.dto.request.BoardSearchConditionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardQueryRepository {
    List<Long> findIdsFromPaginationWithKeyword(BoardSearchConditionRequest boardSearchConditionRequest, Pageable pageable);

    Page<Board> findPageDataFromBoardByIds(List<Long> ids, BoardSearchConditionRequest boardSearchConditionRequest, Pageable pageable);
}

package com.cnu.spg.board.repository;

import com.cnu.spg.board.domain.Board;
import com.cnu.spg.board.dto.condition.BoardSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardQueryRepository {
    List<Long> findIdsFromPaginationWithKeyword(BoardSearchCondition boardSearchCondition, Pageable pageable);

    Page<Board> findPageDataFromBoardByIds(List<Long> ids, BoardSearchCondition boardSearchCondition, Pageable pageable);
}

package com.cnu.spg.board.repository;

import com.cnu.spg.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardQueryRepository {
    List<Long> findIdsFromPaginationWithKeyword(String keyword, String partOfContent, Pageable pageable);

    Page<Board> findPageDataFromBoardByIds(List<Long> ids, Pageable pageable);
}

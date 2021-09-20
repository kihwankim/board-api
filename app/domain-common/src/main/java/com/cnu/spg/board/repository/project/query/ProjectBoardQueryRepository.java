package com.cnu.spg.board.repository.project.query;

import com.cnu.spg.board.domain.Board;
import com.cnu.spg.board.domain.project.ProjectCategory;
import com.cnu.spg.board.dto.condition.ProjectBoardCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectBoardQueryRepository {
    List<Long> findProjectBoardIdsFromPaginationWithKeyword(ProjectBoardCondition projectBoardCondition, ProjectCategory category, Pageable pageable);

    Page<Board> findProjectPageDataFromBoardByIds(List<Long> ids, ProjectBoardCondition projectBoardCondition, ProjectCategory category, Pageable pageable);
}

package com.cnu.spg.board.repository;

import com.cnu.spg.board.domain.Board;
import com.cnu.spg.board.repository.project.query.ProjectBoardQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardQueryRepository, ProjectBoardQueryRepository {
}

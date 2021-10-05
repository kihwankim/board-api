package com.cnu.spg.board.repository.project;

import com.cnu.spg.board.domain.project.ProjectCategory;
import com.cnu.spg.board.repository.project.query.ProjectCategoryQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectCategoryRepository extends JpaRepository<ProjectCategory, Long>, ProjectCategoryQueryRepository {
}

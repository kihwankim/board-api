package com.cnu.spg.board.repository.project.query;

import com.cnu.spg.board.domain.project.ProjectCategory;

import java.util.List;

public interface ProjectCategoryQueryRepository {
    List<ProjectCategory> findByCategoriesById(Long userId);
}

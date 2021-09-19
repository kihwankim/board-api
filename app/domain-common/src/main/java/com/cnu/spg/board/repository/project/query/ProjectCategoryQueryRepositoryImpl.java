package com.cnu.spg.board.repository.project.query;

import com.cnu.spg.board.domain.project.ProjectCategory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cnu.spg.board.domain.project.QProjectCategory.projectCategory;

@Repository
@RequiredArgsConstructor
public class ProjectCategoryQueryRepositoryImpl implements ProjectCategoryQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProjectCategory> findByCategoriesById(Long userId) {
        return queryFactory.selectFrom(projectCategory)
                .where(eqOwnerUserId(userId))
                .fetch();
    }

    private BooleanExpression eqOwnerUserId(Long userId) {
        return userId != null ? projectCategory.categoryOwner.id.eq(userId) : null;
    }
}

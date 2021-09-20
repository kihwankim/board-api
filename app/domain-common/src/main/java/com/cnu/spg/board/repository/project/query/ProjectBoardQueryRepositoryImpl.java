package com.cnu.spg.board.repository.project.query;

import com.cnu.spg.board.domain.Board;
import com.cnu.spg.board.domain.project.ProjectCategory;
import com.cnu.spg.board.dto.condition.ProjectBoardCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.cnu.spg.board.domain.project.QProjectBoard.projectBoard;

@Repository
@RequiredArgsConstructor
public class ProjectBoardQueryRepositoryImpl implements ProjectBoardQueryRepository {
    private final JPAQueryFactory queryFactory;

    private BooleanExpression likeContent(String partOfContent) {
        return partOfContent == null ? null : projectBoard.content.contains(partOfContent);
    }

    private BooleanExpression likeTitle(String partOfTitle) {
        return partOfTitle == null ? null : projectBoard.title.contains(partOfTitle);
    }

    private BooleanExpression eqWriterName(String writerName) {
        return writerName == null ? null : projectBoard.writerName.eq(writerName);
    }

    private BooleanExpression eqCategoryId(ProjectCategory projectCategory) {
        if (projectCategory == null) {
            return null;
        }

        return projectBoard.projectCategory.eq(projectCategory);
    }

    @Override
    public List<Long> findProjectBoardIdsFromPaginationWithKeyword(ProjectBoardCondition projectBoardCondition, ProjectCategory category, Pageable pageable) {
        return queryFactory
                .select(projectBoard.id)
                .from(projectBoard)
                .where(
                        eqWriterName(projectBoardCondition.getWriterName()),
                        likeContent(projectBoardCondition.getContentPart()),
                        likeTitle(projectBoardCondition.getTitlePart()),
                        eqCategoryId(category)
                )
                .orderBy(projectBoard.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    @Override
    public Page<Board> findProjectPageDataFromBoardByIds(List<Long> ids, ProjectBoardCondition projectBoardCondition, ProjectCategory category, Pageable pageable) {
        if (CollectionUtils.isEmpty(ids)) {
            return new PageImpl<>(new ArrayList<>());
        }

        List<Board> content = queryFactory
                .selectFrom(projectBoard)
                .where(projectBoard.id.in(ids))
                .fetch()
                .stream()
                .sorted((first, second) -> (int) (-first.getId() + second.getId()))
                .collect(Collectors.toList());

        JPAQuery<? extends Board> countQuery = queryFactory
                .selectFrom(projectBoard)
                .where(
                        eqWriterName(projectBoardCondition.getWriterName()),
                        likeContent(projectBoardCondition.getContentPart()),
                        likeTitle(projectBoardCondition.getTitlePart()),
                        eqCategoryId(category)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }
}

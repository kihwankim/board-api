package com.cnu.spg.board.repository.query;

import com.cnu.spg.board.dto.reponse.CommentCountsWithBoardIdDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.cnu.spg.board.domain.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CommentDtoQueryRepositoryImpl implements CommentDtoQueryRepository {

    private final JPAQueryFactory queryFactory;

    private BooleanExpression inBoardId(List<Long> boardIds) {
        return CollectionUtils.isEmpty(boardIds) ? null : comment.board.id.in(boardIds);
    }

    @Override
    public List<CommentCountsWithBoardIdDto> findCountListAndBoardIdBulk(List<Long> boardIdx) {
        return queryFactory.select(
                        Projections.constructor(CommentCountsWithBoardIdDto.class,
                                comment.board.id, comment.count()
                        ))
                .from(comment)
                .where(inBoardId(boardIdx))
                .groupBy(comment.board.id)
                .fetch();
    }
}

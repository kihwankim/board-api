package com.cnu.spg.board.repository;

import com.cnu.spg.board.domain.Board;
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

import static com.cnu.spg.board.domain.QBoard.board;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepositoryImpl implements BoardQueryRepository {
    private final JPAQueryFactory queryFactory;

    private BooleanExpression likeContent(String content) {
        return content == null ? null : board.content.like(content);
    }

    private BooleanExpression eqWriterName(String writerName) {
        return writerName == null ? null : board.writerName.eq(writerName);
    }


    @Override
    public List<Long> findIdsFromPaginationWithKeyword(String witerName, String content, Pageable pageable) {
        return queryFactory
                .select(board.id)
                .from(board)
                .where(
                        eqWriterName(witerName),
                        likeContent(content)
                )
                .orderBy(board.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    @Override
    public Page<Board> findPageDataFromBoardByIds(List<Long> ids, Pageable pageable) {
        if (CollectionUtils.isEmpty(ids)) {
            return new PageImpl<>(new ArrayList<>());
        }

        JPAQuery<Board> results = queryFactory
                .selectFrom(board)
                .where(board.id.in(ids));

        List<Board> content = results.fetch()
                .stream()
                .sorted((first, second) -> (int) (-first.getId() + second.getId()))
                .collect(Collectors.toList());

        return PageableExecutionUtils.getPage(content, pageable, results::fetchCount);
    }
}

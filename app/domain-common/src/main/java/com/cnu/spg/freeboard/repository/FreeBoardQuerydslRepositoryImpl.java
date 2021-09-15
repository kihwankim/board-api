package com.cnu.spg.freeboard.repository;

import com.cnu.spg.freeboard.domain.FreeBoard;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.cnu.spg.freeboard.domain.QFreeBoard.freeBoard;

@Repository
@RequiredArgsConstructor
public class FreeBoardQuerydslRepositoryImpl implements FreeBoardQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> findIdsByDynamicWriterNameWithPagination(String writerKeyword, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        return queryFactory
                .select(freeBoard.id)
                .from(freeBoard)
                .where(likeWriterName(writerKeyword))
                .orderBy(freeBoard.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    @Override
    public Page<FreeBoard> findByIdsWithPagination(List<Long> ids, int pageNo, int pageSize) {
        if (CollectionUtils.isEmpty(ids)) {
            return new PageImpl<>(new ArrayList<>());
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        JPAQuery<FreeBoard> results = queryFactory
                .selectFrom(freeBoard)
                .where(freeBoard.id.in(ids));

        List<FreeBoard> content = results.fetch()
                .stream()
                .sorted((first, second) -> (int) (-first.getId() + second.getId()))
                .collect(Collectors.toList());

        return PageableExecutionUtils.getPage(content, pageable, results::fetchCount);
    }

    private BooleanExpression likeWriterName(String writerKeyword) {
        return writerKeyword != null ? freeBoard.writerName.like(writerKeyword) : null;
    }
}

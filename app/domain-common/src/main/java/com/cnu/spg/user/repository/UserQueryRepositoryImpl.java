package com.cnu.spg.user.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.cnu.spg.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final JPAQueryFactory queryFactory;

    private BooleanExpression eqUsername(String username) {
        if (username == null) return null;

        return user.username.eq(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        Integer fetchFirst = queryFactory.
                selectOne()
                .from(user)
                .where(eqUsername(username))
                .fetchFirst();

        return fetchFirst != null;
    }
}

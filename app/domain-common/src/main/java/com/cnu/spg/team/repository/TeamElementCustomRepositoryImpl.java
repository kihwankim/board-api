package com.cnu.spg.team.repository;

import com.cnu.spg.team.domain.TeamElement;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cnu.spg.team.domain.QTeam.team;
import static com.cnu.spg.team.domain.QTeamElement.teamElement;

@Repository
@RequiredArgsConstructor
public class TeamElementCustomRepositoryImpl implements TeamElementCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<TeamElement> findAllTeamMembersByUserId(Long userId) {
        return queryFactory
                .selectFrom(teamElement)
                .join(team)
                .where(eqUserId(userId))
                .fetch();
    }

    private BooleanExpression eqUserId(Long userId) {
        return userId == null ? null : teamElement.member.id.eq(userId);
    }
}

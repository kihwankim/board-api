package com.cnu.spg.team.repository;

import com.cnu.spg.team.domain.TeamElement;

import java.util.List;

public interface TeamElementCustomRepository {
    List<TeamElement> findAllTeamMembersByUserId(Long userId);
}

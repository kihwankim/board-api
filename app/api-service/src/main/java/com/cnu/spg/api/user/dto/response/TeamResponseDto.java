package com.cnu.spg.api.user.dto.response;

import com.cnu.spg.team.domain.TeamElement;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class TeamResponseDto {
    private List<String> joinedTeamNames;

    public TeamResponseDto(List<TeamElement> teamElements) {
        this.joinedTeamNames = teamElements
                .stream()
                .map(teamJoinMember -> teamJoinMember.getTeam().getTeamName())
                .collect(Collectors.toList());
    }
}

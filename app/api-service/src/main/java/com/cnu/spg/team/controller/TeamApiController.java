package com.cnu.spg.team.controller;

import com.cnu.spg.team.dto.response.TeamResponseDto;
import com.cnu.spg.team.service.TeamService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TeamApiController {
    private final TeamService teamService;

    @ApiOperation("회원이 속해 있는 모든 Team 가져오기")
    @GetMapping("/api/v1/teams")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    public ResponseEntity<TeamResponseDto> getJoinedTeamNames(Long userId) {
        return ResponseEntity.ok(teamService.fetchJoinedTeamNames(userId));
    }
}

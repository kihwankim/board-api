package com.cnu.spg.team.service;

import com.cnu.spg.team.dto.response.TeamResponseDto;
import com.cnu.spg.team.domain.TeamElement;
import com.cnu.spg.team.repository.TeamElementRepository;
import com.cnu.spg.user.domain.User;
import com.cnu.spg.user.exception.UserNotFoundException;
import com.cnu.spg.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamElementRepository teamElementRepository;
    private final UserRepository userRepository;

    public TeamResponseDto fetchJoinedTeamNames(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        List<TeamElement> allTeamMembersByUserId = teamElementRepository.findAllTeamMembersByUserId(user.getId());

        return new TeamResponseDto(allTeamMembersByUserId);
    }
}

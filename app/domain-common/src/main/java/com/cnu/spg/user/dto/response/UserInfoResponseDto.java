package com.cnu.spg.user.dto.response;

import com.cnu.spg.user.domain.User;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserInfoResponseDto {
    Set<RoleReponseDto> roles = new HashSet<>();

    private String name;

    public UserInfoResponseDto() {
    }

    public UserInfoResponseDto(User user) {
        this.name = user.getName();

        user.getRoles().forEach(role -> roles.add(new RoleReponseDto(role)));
    }
}

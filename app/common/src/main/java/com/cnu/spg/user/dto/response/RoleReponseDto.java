package com.cnu.spg.user.dto.response;

import com.cnu.spg.user.domain.Role;
import com.cnu.spg.user.domain.RoleName;
import lombok.Data;

@Data
public class RoleReponseDto {
    RoleName roleName;

    public RoleReponseDto(Role role) {
        this.roleName = role.getName();
    }
}

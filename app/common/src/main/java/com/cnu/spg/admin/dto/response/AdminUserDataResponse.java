package com.cnu.spg.admin.dto.response;

import com.cnu.spg.user.domain.Role;
import com.cnu.spg.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class AdminUserDataResponse {
    private Long id;
    private String username;
    private String name;
    private Set<Role> roles;

    public AdminUserDataResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.roles = user.getRoles();
    }
}

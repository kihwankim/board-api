package com.cnu.spg.admin.dto.request;

import lombok.Data;

import java.util.Set;

@Data
public class AdminUserRoleModifyRequest {
    private String id;
    private String username;
    private Set<String> roles;
}

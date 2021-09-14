package com.cnu.spg.user.repository;

import com.cnu.spg.user.domain.Role;
import com.cnu.spg.user.domain.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}

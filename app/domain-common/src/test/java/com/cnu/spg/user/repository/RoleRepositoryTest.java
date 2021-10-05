package com.cnu.spg.user.repository;

import com.cnu.spg.user.domain.Role;
import com.cnu.spg.user.domain.RoleName;
import com.cnu.spg.user.exception.RoleNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@DisplayName("user role test")
class RoleRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    RoleRepository roleRepository;

    @BeforeEach
    void beforeRoleTest() {
        Role adminRole = new Role(RoleName.ROLE_ADMIN);
        Role studentRole = new Role(RoleName.ROLE_STUDENT);
        em.persist(adminRole);
        em.persist(studentRole);

        em.flush();
        em.clear();
    }

    @Test
    void findNotExistRoleNameExceptionTest() {
        // given
        RoleName roleUnauth = RoleName.ROLE_UNAUTH;

        // when
        Optional<Role> roleNameOptional = roleRepository.findByName(roleUnauth);

        // then
        assertThrows(RollbackException.class, () -> roleNameOptional
                .orElseThrow(RollbackException::new));
    }

    @Test
    void findExistRoleNameTest() throws Exception {
        // given
        final RoleName adminRoleName = RoleName.ROLE_ADMIN;

        // when
        Role findRole = roleRepository.findByName(adminRoleName)
                .orElseThrow(RoleNotFoundException::new);

        // then
        assertEquals(adminRoleName, findRole.getName());
    }
}
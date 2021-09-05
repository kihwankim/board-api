package com.cnu.spg.user.repository;

import com.cnu.spg.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserQueryRepository {
    Optional<User> findByUsername(String username);

    void deleteByUsername(String username);
}

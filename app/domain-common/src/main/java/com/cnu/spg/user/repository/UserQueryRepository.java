package com.cnu.spg.user.repository;

public interface UserQueryRepository {
    boolean existsByUsername(String username);
}

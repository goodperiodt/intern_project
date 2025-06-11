package com.sparta.authservice.infrastructure.repository;

import com.sparta.authservice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    boolean existsUserByEmail(String email);
    User findUserByEmail(String email);
}
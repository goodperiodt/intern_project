package com.sparta.authservice.domain.repository;

import com.sparta.authservice.domain.entity.User;

public interface UserRepository {
    User register(User user);
    User findByUserEmail(String email);
}
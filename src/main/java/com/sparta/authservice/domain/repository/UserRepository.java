package com.sparta.authservice.domain.repository;

import com.sparta.authservice.domain.entity.User;

public interface UserRepository {
    User save(User user);
}
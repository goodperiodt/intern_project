package com.sparta.authservice.infrastructure.repository;

import com.sparta.authservice.domain.entity.User;
import com.sparta.authservice.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User register(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public User findByUserEmail(String email) {

        if(!userJpaRepository.existsUserByEmail(email))
            throw new IllegalArgumentException("아이디가 존재하지 않습니다.");

        return userJpaRepository.findUserByEmail(email);
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return userJpaRepository.existsUserByEmail(email);
    }
}
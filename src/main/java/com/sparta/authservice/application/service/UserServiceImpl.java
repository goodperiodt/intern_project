package com.sparta.authservice.application.service;

import com.sparta.authservice.application.dto.response.SignUpResponse;
import com.sparta.authservice.common.exception.CustomException;
import com.sparta.authservice.common.exception.ErrorCode;
import com.sparta.authservice.domain.entity.User;
import com.sparta.authservice.infrastructure.repository.UserRepositoryImpl;
import com.sparta.authservice.infrastructure.security.JwtUtil;
import com.sparta.authservice.presentation.dto.request.LoginRequest;
import com.sparta.authservice.presentation.dto.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepositoryImpl userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {

        isDuplicateEmail(request);

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.create(
                request.getEmail(),
                encodedPassword,
                request.getNickname());

        User registered = userRepository.register(user);

        return SignUpResponse.of(
                registered.getId(),
                registered.getEmail(),
                registered.getNickname(),
                registered.getCreatedAt()
        );
    }

    private void isDuplicateEmail(SignUpRequest request) {
        if(userRepository.existsUserByEmail(request.getEmail()))
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
    }

    @Override
    public String login(LoginRequest request) {

        User foundUser = userRepository.findByUserEmail(request.getEmail());
        matchesPassword(
                foundUser,
                request.getPassword()
        );

        return jwtUtil.createAccessToken(
                foundUser.getEmail(),
                foundUser.getRole()
        );
    }

    @Override
    @Transactional
    public void grantAdminRole(Long userId) {
        User user = userRepository.findUserById(userId);
        user.changeRoleToAdmin();
    }

    private void matchesPassword(User foundUser, String password) {

        boolean matches = passwordEncoder.matches(
                password,
                foundUser.getPassword()
        );
        if(!matches)
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);

    }
}

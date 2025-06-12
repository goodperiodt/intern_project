package com.sparta.authservice.application.service;

import com.sparta.authservice.application.dto.response.SignUpResponse;
import com.sparta.authservice.presentation.dto.request.LoginRequest;
import com.sparta.authservice.presentation.dto.request.SignUpRequest;

public interface UserService {

    SignUpResponse signUp(SignUpRequest request);
    String login(LoginRequest request);
    void grantAdminRole(Long userId);
}
package com.sparta.authservice.presentation.controller;

import com.sparta.authservice.application.dto.response.LoginResponse;
import com.sparta.authservice.application.dto.response.SignUpResponse;
import com.sparta.authservice.presentation.dto.request.LoginRequest;
import com.sparta.authservice.presentation.dto.request.SignUpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "인증", description = "회원가입 및 로그인 API")
public interface UserController {

    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 닉네임을 입력받아 회원가입을 처리한다.")
    @ApiResponse(responseCode = "201", description = "회원가입 성공")
    ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request);

    @Operation(summary = "로그인", description = "인증된 회원은 JWT 토큰을 반환한다.")
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request);
}

package com.sparta.authservice.presentation.controller;

import com.sparta.authservice.application.dto.response.LoginResponse;
import com.sparta.authservice.application.dto.response.SignUpResponse;
import com.sparta.authservice.application.service.UserServiceImpl;
import com.sparta.authservice.presentation.dto.request.LoginRequest;
import com.sparta.authservice.presentation.dto.request.SignUpRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j(topic = "UserController")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        SignUpResponse response = userService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = userService.login(request);
        LoginResponse response = LoginResponse.of(token);
        return ResponseEntity.ok().body(response);
    }
}
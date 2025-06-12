package com.sparta.authservice.application.dto.response;

import lombok.Getter;

@Getter
public class LoginResponse {
    private final String token;

    private LoginResponse(String token) {
        this.token = token;
    }

    public static LoginResponse of(String token) {
        return new LoginResponse(token);
    }
}

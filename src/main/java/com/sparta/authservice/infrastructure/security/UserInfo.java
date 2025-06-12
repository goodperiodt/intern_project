package com.sparta.authservice.infrastructure.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfo {
    private String username; // email
    private String role;
}
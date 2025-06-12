package com.sparta.authservice.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "이메일은 필수 값입니다.")
    private String email;

    @NotBlank(message = "패스워드는 필수 값입니다.")
    private String password;
}
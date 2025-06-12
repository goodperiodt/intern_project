package com.sparta.authservice.application.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignUpResponse {
    private final Long id;
    private final String email;
    private final String nickname;
    private final LocalDateTime createAt;

    private SignUpResponse(Long id,
                           String email,
                           String nickname,
                           LocalDateTime createAt) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.createAt = createAt;
    }

    public static SignUpResponse of(Long id,
                                    String email,
                                    String nickname,
                                    LocalDateTime createAt) {
        return new SignUpResponse(id, email, nickname, createAt);
    }
}

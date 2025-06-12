package com.sparta.authservice.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final ErrorDetail error;

    public ErrorResponse(ErrorCode errorCode) {
        this.error = new ErrorDetail(
                errorCode.getCode(),
                errorCode.getMessage()
        );
    }

    @Getter
    @AllArgsConstructor
    public static class ErrorDetail {
        private final String code;
        private final String message;
    }
}

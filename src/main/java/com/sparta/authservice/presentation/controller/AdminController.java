package com.sparta.authservice.presentation.controller;

import com.sparta.authservice.application.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "관리자 기능", description = "사용자 권한 관리 API")
public class AdminController {

    private final UserServiceImpl userService;

    @Operation(summary = "관리자 권한 부여", description = "지정한 사용자에게 관리자(ADMIN) 권한을 부여합니다.")
    @ApiResponse(responseCode = "204", description = "권한 부여 성공")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{userId}")
    public ResponseEntity<Void> grantAdminRole(@PathVariable Long userId) {
        userService.grantAdminRole(userId);
        return ResponseEntity.noContent().build();
    }
}

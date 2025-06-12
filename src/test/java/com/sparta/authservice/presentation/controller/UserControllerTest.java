package com.sparta.authservice.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.authservice.application.dto.response.SignUpResponse;
import com.sparta.authservice.application.service.UserServiceImpl;
import com.sparta.authservice.presentation.dto.request.SignUpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 성공 테스트")
    void signUpTest() throws Exception {
        // given
        SignUpRequest request = new SignUpRequest("test@example.com", "password123", "tester");
        SignUpResponse response = SignUpResponse.of(
                1L,
                "test@example.com",
                "tester",
                LocalDateTime.now()
        );

        Mockito.when(userService.signUp(any(SignUpRequest.class))).thenReturn(response);

        mockMvc.perform(post("/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.nickname").value("tester"))
                .andExpect(jsonPath("$.id").value(1));
    }
}
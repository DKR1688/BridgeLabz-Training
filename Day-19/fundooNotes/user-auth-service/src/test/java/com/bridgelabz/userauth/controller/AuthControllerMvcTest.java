package com.bridgelabz.userauth.controller;

import com.bridgelabz.userauth.dto.AuthResponse;
import com.bridgelabz.userauth.dto.ForgotPasswordRequest;
import com.bridgelabz.userauth.dto.LoginRequest;
import com.bridgelabz.userauth.dto.RegisterRequest;
import com.bridgelabz.userauth.dto.ResetPasswordRequest;
import com.bridgelabz.userauth.security.JwtUtil;
import com.bridgelabz.userauth.service.PasswordRecoveryService;
import com.bridgelabz.userauth.service.TokenCacheService;
import com.bridgelabz.userauth.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Level 2: MVC Layer Tests for AuthController using @WebMvcTest and MockMvc.
 * Validates endpoint routing, @Valid validations, and status codes with mocked service layer.
 */
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordRecoveryService passwordRecoveryService;

    @MockBean
    private TokenCacheService tokenCacheService;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("Level 2: POST /auth/register with valid payload returns 201 Created and AuthResponse")
    void register_withValidBody_returns201() throws Exception {
        RegisterRequest request = new RegisterRequest("john.doe@example.com", "Password@123", "John", "Doe");
        AuthResponse authResponse = new AuthResponse("jwt-token-123", "User registered successfully");

        when(userService.register(any(RegisterRequest.class))).thenReturn(authResponse);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("jwt-token-123"))
                .andExpect(jsonPath("$.message").value("User registered successfully"));

        verify(userService).register(any(RegisterRequest.class));
    }

    @Test
    @DisplayName("Level 2: POST /auth/register with invalid email returns 400 Bad Request (@Valid)")
    void register_withInvalidEmail_returns400() throws Exception {
        RegisterRequest request = new RegisterRequest("invalid-email", "Password@123", "John", "Doe");

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).register(any());
    }

    @Test
    @DisplayName("Level 2: POST /auth/register with weak password returns 400 Bad Request (@Valid)")
    void register_withWeakPassword_returns400() throws Exception {
        RegisterRequest request = new RegisterRequest("john@example.com", "weak", "John", "Doe");

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).register(any());
    }

    @Test
    @DisplayName("Level 2: POST /auth/login with valid body returns 200 OK and JWT token")
    void login_withValidBody_returns200() throws Exception {
        LoginRequest request = new LoginRequest("john.doe@example.com", "Password@123");
        AuthResponse authResponse = new AuthResponse("jwt-token-login", "Login successful");

        when(userService.login(any(LoginRequest.class))).thenReturn(authResponse);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token-login"))
                .andExpect(jsonPath("$.message").value("Login successful"));
    }

    @Test
    @DisplayName("Level 2: POST /auth/forgot-password with valid email returns 200 OK and resetToken")
    void forgotPassword_withValidEmail_returns200() throws Exception {
        ForgotPasswordRequest request = new ForgotPasswordRequest("john.doe@example.com");
        when(passwordRecoveryService.requestPasswordReset(any(ForgotPasswordRequest.class))).thenReturn("reset-token-xyz");

        mockMvc.perform(post("/auth/forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resetToken").value("reset-token-xyz"))
                .andExpect(jsonPath("$.message").value("Password reset instructions sent to your email"));
    }

    @Test
    @DisplayName("Level 2: POST /auth/reset-password with valid token returns 200 OK")
    void resetPassword_withValidToken_returns200() throws Exception {
        ResetPasswordRequest request = new ResetPasswordRequest("reset-token-xyz", "NewPassword@123");
        doNothing().when(passwordRecoveryService).resetPassword(any(ResetPasswordRequest.class));

        mockMvc.perform(post("/auth/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Password reset successful"));
    }
}

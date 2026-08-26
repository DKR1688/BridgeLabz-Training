package com.bridgelabz.userauth.controller;

import com.bridgelabz.userauth.dto.AuthResponse;
import com.bridgelabz.userauth.dto.ForgotPasswordRequest;
import com.bridgelabz.userauth.dto.LoginRequest;
import com.bridgelabz.userauth.dto.RegisterRequest;
import com.bridgelabz.userauth.dto.ResetPasswordRequest;
import com.bridgelabz.userauth.service.PasswordRecoveryService;
import com.bridgelabz.userauth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordRecoveryService passwordRecoveryService;

    public AuthController(UserService userService, PasswordRecoveryService passwordRecoveryService) {
        this.userService = userService;
        this.passwordRecoveryService = passwordRecoveryService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        String token = passwordRecoveryService.requestPasswordReset(request);
        return ResponseEntity.ok(Map.of(
                "message", "Password reset instructions sent to your email",
                "resetToken", token
        ));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        passwordRecoveryService.resetPassword(request);
        return ResponseEntity.ok(Map.of("message", "Password reset successful"));
    }
}

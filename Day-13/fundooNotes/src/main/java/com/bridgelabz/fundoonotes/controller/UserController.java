package com.bridgelabz.fundoonotes.controller;

import com.bridgelabz.fundoonotes.dto.AuthResponse;
import com.bridgelabz.fundoonotes.dto.ForgotPasswordRequest;
import com.bridgelabz.fundoonotes.dto.LoginRequest;
import com.bridgelabz.fundoonotes.dto.RegisterRequest;
import com.bridgelabz.fundoonotes.service.PasswordRecoveryService;
import com.bridgelabz.fundoonotes.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;
    private final PasswordRecoveryService passwordRecoveryService;

    public UserController(UserService userService, PasswordRecoveryService passwordRecoveryService) {
        this.userService = userService;
        this.passwordRecoveryService = passwordRecoveryService;
    }

    @PostMapping("/user/userSignUp")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
        String token = userService.register(
                req.email(),
                req.password(),
                req.resolvedName(),
                req.resolvedFirstName(),
                req.resolvedLastName());
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(token, "User registered successfully"));
    }

    @PostMapping("/user/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        String token = userService.login(req.email(), req.password());
        return ResponseEntity.ok(new AuthResponse(token, "Login successful"));
    }

    @PostMapping("/user/reset")
    public ResponseEntity<Map<String, String>> resetPasswordStub(@Valid @RequestBody ForgotPasswordRequest req) {
        passwordRecoveryService.requestReset(req.email());
        return ResponseEntity
                .ok(Map.of("message", "If that email is registered, a password reset link has been sent."));
    }

    @GetMapping("/api/me")
    public Map<String, Object> currentUser(Authentication authentication) {
        return Map.of("userId", authentication.getName());
    }
}

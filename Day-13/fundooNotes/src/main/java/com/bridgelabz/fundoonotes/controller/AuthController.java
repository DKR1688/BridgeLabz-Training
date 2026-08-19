package com.bridgelabz.fundoonotes.controller;

import com.bridgelabz.fundoonotes.dto.*;
import com.bridgelabz.fundoonotes.service.PasswordRecoveryService;
import com.bridgelabz.fundoonotes.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Map<String, String> RECOVERY_RESPONSE =
            Map.of("message", "If that email is registered, a password reset link has been sent.");
    private final UserService userService;
    private final PasswordRecoveryService passwordRecoveryService;
    public AuthController(UserService userService, PasswordRecoveryService passwordRecoveryService) {
        this.userService = userService; this.passwordRecoveryService = passwordRecoveryService;
    }
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(userService.register(request.email(), request.password(), request.name())));
    }
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return new AuthResponse(userService.login(request.email(), request.password()));
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        // The raw token is intentionally not returned. Send it in an email link through an email-provider adapter.
        passwordRecoveryService.requestReset(request.email());
        return ResponseEntity.accepted().body(RECOVERY_RESPONSE);
    }
    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        passwordRecoveryService.resetPassword(request.token(), request.newPassword());
        return ResponseEntity.noContent().build();
    }
}

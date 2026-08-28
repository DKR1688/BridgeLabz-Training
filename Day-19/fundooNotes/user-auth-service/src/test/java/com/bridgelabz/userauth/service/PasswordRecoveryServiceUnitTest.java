package com.bridgelabz.userauth.service;

import com.bridgelabz.userauth.dto.ForgotPasswordRequest;
import com.bridgelabz.userauth.dto.PasswordResetMessage;
import com.bridgelabz.userauth.dto.ResetPasswordRequest;
import com.bridgelabz.userauth.entity.PasswordResetToken;
import com.bridgelabz.userauth.entity.User;
import com.bridgelabz.userauth.exception.InvalidCredentialsException;
import com.bridgelabz.userauth.exception.UserNotFoundException;
import com.bridgelabz.userauth.repository.PasswordResetTokenRepository;
import com.bridgelabz.userauth.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Level 1: Business Logic Unit Tests for PasswordRecoveryService (Use Case 2 & 8).
 */
@ExtendWith(MockitoExtension.class)
class PasswordRecoveryServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordResetTokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JmsProducerService jmsProducerService;

    @InjectMocks
    private PasswordRecoveryService passwordRecoveryService;

    @Test
    @DisplayName("Level 1: Forgot password for valid user creates token and sends JMS message")
    void requestPasswordReset_forValidUser_generatesTokenAndDispatchesJms() {
        User user = new User();
        user.setUserId(1);
        user.setEmail("user@example.com");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        ForgotPasswordRequest request = new ForgotPasswordRequest("user@example.com");
        String token = passwordRecoveryService.requestPasswordReset(request);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        verify(tokenRepository).deleteByUser(user);
        verify(tokenRepository).save(any(PasswordResetToken.class));
        verify(jmsProducerService).sendPasswordRecoveryMessage(any(PasswordResetMessage.class));
    }

    @Test
    @DisplayName("Level 1: Forgot password for unknown user throws UserNotFoundException")
    void requestPasswordReset_forUnknownUser_throwsUserNotFoundException() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        ForgotPasswordRequest request = new ForgotPasswordRequest("unknown@example.com");
        assertThrows(UserNotFoundException.class, () -> passwordRecoveryService.requestPasswordReset(request));
        verify(jmsProducerService, never()).sendPasswordRecoveryMessage(any());
    }

    @Test
    @DisplayName("Level 1: Reset password with valid token updates password hash and deletes token")
    void resetPassword_withValidToken_updatesPasswordHash() {
        User user = new User();
        user.setUserId(1);
        user.setEmail("user@example.com");

        PasswordResetToken resetToken = new PasswordResetToken("valid-token", user, 15);

        when(tokenRepository.findByToken("valid-token")).thenReturn(Optional.of(resetToken));
        when(passwordEncoder.encode("newPassword123")).thenReturn("newHashedPassword");

        ResetPasswordRequest request = new ResetPasswordRequest("valid-token", "newPassword123");
        passwordRecoveryService.resetPassword(request);

        assertEquals("newHashedPassword", user.getPasswordHash());
        verify(userRepository).save(user);
        verify(tokenRepository).delete(resetToken);
    }

    @Test
    @DisplayName("Level 1: Reset password with invalid token throws InvalidCredentialsException")
    void resetPassword_withInvalidToken_throwsInvalidCredentialsException() {
        when(tokenRepository.findByToken("invalid-token")).thenReturn(Optional.empty());

        ResetPasswordRequest request = new ResetPasswordRequest("invalid-token", "newPassword123");
        assertThrows(InvalidCredentialsException.class, () -> passwordRecoveryService.resetPassword(request));
    }
}

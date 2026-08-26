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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PasswordRecoveryService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JmsProducerService jmsProducerService;

    @Value("${app.password-reset.expiration-minutes:15}")
    private int expirationMinutes;

    public PasswordRecoveryService(
            UserRepository userRepository,
            PasswordResetTokenRepository tokenRepository,
            PasswordEncoder passwordEncoder,
            JmsProducerService jmsProducerService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jmsProducerService = jmsProducerService;
    }

    @Transactional
    public String requestPasswordReset(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + request.getEmail()));

        tokenRepository.deleteByUser(user);

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(token, user, expirationMinutes);
        tokenRepository.save(resetToken);

        PasswordResetMessage message = new PasswordResetMessage(user.getEmail(), token);
        jmsProducerService.sendPasswordRecoveryMessage(message);

        return token;
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        PasswordResetToken resetToken = tokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid or expired password reset token"));

        if (resetToken.isExpired()) {
            tokenRepository.delete(resetToken);
            throw new InvalidCredentialsException("Password reset token has expired");
        }

        User user = resetToken.getUser();
        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        tokenRepository.delete(resetToken);
    }
}

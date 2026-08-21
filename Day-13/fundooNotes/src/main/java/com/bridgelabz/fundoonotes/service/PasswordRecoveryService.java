package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.PasswordResetMessage;
import com.bridgelabz.fundoonotes.entity.PasswordResetToken;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.repository.PasswordResetTokenRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Locale;

@Service
public class PasswordRecoveryService {
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JmsProducerService jmsProducerService;
    private final int expirationMinutes;
    private final SecureRandom secureRandom = new SecureRandom();

    public PasswordRecoveryService(UserRepository userRepository,
            PasswordResetTokenRepository tokenRepository,
            PasswordEncoder passwordEncoder,
            @Autowired(required = false) JmsProducerService jmsProducerService,
            @Value("${app.password-reset.expiration-minutes:15}") int expirationMinutes) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jmsProducerService = jmsProducerService;
        this.expirationMinutes = expirationMinutes;
    }

    /**
     * Creates a one-time token. An email provider should deliver the returned token
     * in a reset link.
     */
    @Transactional
    public String requestReset(String email) {
        User user = userRepository.findByEmail(email.trim().toLowerCase(Locale.ROOT)).orElse(null);
        if (user == null)
            return null; // Keep public response identical to prevent account enumeration.
        tokenRepository.deleteByUser(user);
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        String rawToken = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        PasswordResetToken token = new PasswordResetToken();
        token.setUser(user);
        token.setTokenHash(hash(rawToken));
        token.setExpiresAt(Instant.now().plus(expirationMinutes, ChronoUnit.MINUTES));
        tokenRepository.save(token);

        // Async JMS notification dispatch
        if (jmsProducerService != null) {
            jmsProducerService.sendPasswordResetMessage(new PasswordResetMessage(user.getEmail(), rawToken));
        }

        return rawToken;
    }

    @Transactional
    public void resetPassword(String rawToken, String newPassword) {
        PasswordResetToken token = tokenRepository.findByTokenHash(hash(rawToken))
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired reset token"));
        if (token.getUsedAt() != null || !token.getExpiresAt().isAfter(Instant.now()))
            throw new IllegalArgumentException("Invalid or expired reset token");
        token.getUser().setPasswordHash(passwordEncoder.encode(newPassword));
        token.setUsedAt(Instant.now());
    }

    private String hash(String value) {
        try {
            return Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-256")
                    .digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to secure recovery token", exception);
        }
    }
}

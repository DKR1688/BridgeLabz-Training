package com.bridgelabz.fundoonotes;

import com.bridgelabz.fundoonotes.security.JwtUtil;
import com.bridgelabz.fundoonotes.service.PasswordRecoveryService;
import com.bridgelabz.fundoonotes.service.UserService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthFlowIntegrationTest {
    @Autowired private UserService userService;
    @Autowired private PasswordRecoveryService passwordRecoveryService;
    @Autowired private JwtUtil jwtUtil;

    @Test
    void registrationLoginJwtAndDuplicateEmailWork() {
        String email = "priya" + System.nanoTime() + "@example.com";
        String registrationToken = userService.register(email, "SecurePass123", "Priya Mehta");
        Claims claims = jwtUtil.parseSignedClaims(registrationToken);
        assertEquals(email, claims.get("email", String.class));
        assertNotNull(claims.getSubject());
        assertDoesNotThrow(() -> userService.login(email, "SecurePass123"));
        assertThrows(IllegalArgumentException.class, () -> userService.register(email, "AnotherPass123", "Priya"));
    }

    @Test
    void recoveryTokenCanBeUsedOnlyOnce() {
        String email = "reset" + System.nanoTime() + "@example.com";
        userService.register(email, "SecurePass123", "Reset User");
        String resetToken = passwordRecoveryService.requestReset(email);
        passwordRecoveryService.resetPassword(resetToken, "ChangedPass123");
        assertDoesNotThrow(() -> userService.login(email, "ChangedPass123"));
        assertThrows(IllegalArgumentException.class,
                () -> passwordRecoveryService.resetPassword(resetToken, "AnotherPass123"));
    }
}

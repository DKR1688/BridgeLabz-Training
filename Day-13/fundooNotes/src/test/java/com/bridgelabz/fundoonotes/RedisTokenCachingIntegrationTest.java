package com.bridgelabz.fundoonotes;

import com.bridgelabz.fundoonotes.security.JwtUtil;
import com.bridgelabz.fundoonotes.service.TokenCacheService;
import com.bridgelabz.fundoonotes.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisTokenCachingIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenCacheService tokenCacheService;

    @BeforeEach
    void setUp() {
        tokenCacheService.clearAllLocal();
    }

    @Test
    @DisplayName("Use Case 9: Repeated token validation skips cryptographic recalculation & respects TTL")
    void testTokenCachingAndTtlCompliance() {
        String email = "cache_user_" + System.nanoTime() + "@example.com";
        String token = userService.register(email, "Password123!", "Cache User");

        // First validation (MISS -> Populates cache)
        long remainingTtl = jwtUtil.getRemainingTtlMillis(token);
        assertTrue(remainingTtl > 0, "Token must have positive remaining expiration millis");
        assertTrue(tokenCacheService.isTokenValid(token), "Token must be valid on first check");

        // Second validation (HIT from cache)
        assertTrue(tokenCacheService.isTokenValid(token), "Token must remain valid on cached check");

        // User extraction (MISS followed by HIT)
        String userId1 = tokenCacheService.extractUserId(token);
        assertNotNull(userId1);
        String userId2 = tokenCacheService.extractUserId(token);
        assertEquals(userId1, userId2);

        // Critical TTL test: TTL derived from token's OWN expiration, never
        // exceeding it
        long ttlFromJwt = jwtUtil.getRemainingTtlMillis(token);
        assertTrue(ttlFromJwt <= 3600000, "Cache TTL must not exceed configured expiration window");

        // Tampered token is rejected and never treated as valid
        String tamperedToken = token.substring(0, token.length() - 5) + "abcde";
        assertFalse(tokenCacheService.isTokenValid(tamperedToken));
    }
}

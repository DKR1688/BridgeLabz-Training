package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class TokenCacheService {

    private static final Logger logger = LoggerFactory.getLogger(TokenCacheService.class);
    private static final String VALIDITY_PREFIX = "jwt:valid:";
    private static final String USER_PREFIX = "jwt:user:";

    private final JwtUtil jwtUtil;
    private final StringRedisTemplate redisTemplate;

    // Resilient in-memory fallback for local dev / tests when standalone Redis is not active
    private final ConcurrentHashMap<String, CacheEntry<Boolean>> localValidityCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheEntry<String>> localUserCache = new ConcurrentHashMap<>();

    @Autowired
    public TokenCacheService(JwtUtil jwtUtil, @Autowired(required = false) StringRedisTemplate redisTemplate) {
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
    }

    private static class CacheEntry<T> {
        final T value;
        final long expiresAtMillis;

        CacheEntry(T value, long expiresAtMillis) {
            this.value = value;
            this.expiresAtMillis = expiresAtMillis;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiresAtMillis;
        }
    }

    public boolean isTokenValid(String token) {
        String cacheKey = VALIDITY_PREFIX + token;

        // Try Redis cache first
        if (redisTemplate != null) {
            try {
                String cached = redisTemplate.opsForValue().get(cacheKey);
                if (cached != null) {
                    logger.debug("Redis Token Cache HIT for key={}: isValid={}", cacheKey, cached);
                    return Boolean.parseBoolean(cached);
                }
                logger.debug("Redis Token Cache MISS for key={}", cacheKey);
            } catch (Exception e) {
                logger.warn("Redis connection unavailable, falling back to in-memory token cache: {}", e.getMessage());
            }
        }

        // Check in-memory fallback cache
        CacheEntry<Boolean> localEntry = localValidityCache.get(cacheKey);
        if (localEntry != null && !localEntry.isExpired()) {
            logger.debug("Local Token Cache HIT for key={}: isValid={}", cacheKey, localEntry.value);
            return localEntry.value;
        }

        // Cache MISS: Perform cryptographic JWT validation
        boolean isValid = jwtUtil.isTokenValid(token);
        long remainingTtlMillis = jwtUtil.getRemainingTtlMillis(token);

        if (remainingTtlMillis > 0) {
            // Cache with TTL derived strictly from the token's OWN remaining expiry
            if (redisTemplate != null) {
                try {
                    redisTemplate.opsForValue().set(cacheKey, String.valueOf(isValid), remainingTtlMillis,
                            TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    logger.debug("Failed to set in Redis: {}", e.getMessage());
                }
            }
            localValidityCache.put(cacheKey,
                    new CacheEntry<>(isValid, System.currentTimeMillis() + remainingTtlMillis));
        }

        return isValid;
    }

    public String extractUserId(String token) {
        String cacheKey = USER_PREFIX + token;

        // Try Redis cache first
        if (redisTemplate != null) {
            try {
                String cached = redisTemplate.opsForValue().get(cacheKey);
                if (cached != null) {
                    logger.debug("Redis Token User Cache HIT for key={}: userId={}", cacheKey, cached);
                    return cached;
                }
                logger.debug("Redis Token User Cache MISS for key={}", cacheKey);
            } catch (Exception e) {
                logger.warn("Redis connection unavailable for user cache, using fallback: {}", e.getMessage());
            }
        }

        // Check local fallback
        CacheEntry<String> localEntry = localUserCache.get(cacheKey);
        if (localEntry != null && !localEntry.isExpired()) {
            logger.debug("Local Token User Cache HIT for key={}: userId={}", cacheKey, localEntry.value);
            return localEntry.value;
        }

        // Cryptographic extraction
        String userId = jwtUtil.extractUserId(token);
        long remainingTtlMillis = jwtUtil.getRemainingTtlMillis(token);

        if (userId != null && remainingTtlMillis > 0) {
            if (redisTemplate != null) {
                try {
                    redisTemplate.opsForValue().set(cacheKey, userId, remainingTtlMillis, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    logger.debug("Failed to set user in Redis: {}", e.getMessage());
                }
            }
            localUserCache.put(cacheKey, new CacheEntry<>(userId, System.currentTimeMillis() + remainingTtlMillis));
        }

        return userId;
    }

    public void clearAllLocal() {
        localValidityCache.clear();
        localUserCache.clear();
    }
}

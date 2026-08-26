package com.bridgelabz.userauth.service;

import com.bridgelabz.userauth.security.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class TokenCacheService {

    private static final Logger logger = LoggerFactory.getLogger(TokenCacheService.class);
    private static final String REDIS_PREFIX = "token:valid:";
    private static final String REDIS_USER_PREFIX = "token:user:";

    @Autowired(required = false)
    private StringRedisTemplate redisTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    private final Map<String, Long> inMemoryCache = new ConcurrentHashMap<>();
    private final Map<String, Integer> inMemoryUserCache = new ConcurrentHashMap<>();

    public boolean isTokenValid(String token) {
        if (redisTemplate != null) {
            try {
                String cached = redisTemplate.opsForValue().get(REDIS_PREFIX + token);
                if (cached != null) {
                    return Boolean.parseBoolean(cached);
                }
            } catch (Exception e) {
                logger.warn("Redis connection unavailable, falling back to in-memory cache: {}", e.getMessage());
            }
        }

        Long expiry = inMemoryCache.get(token);
        if (expiry != null) {
            if (System.currentTimeMillis() < expiry) {
                return true;
            } else {
                inMemoryCache.remove(token);
                inMemoryUserCache.remove(token);
            }
        }

        boolean isValid = jwtUtil.validateToken(token);
        if (isValid) {
            long remainingTtl = getRemainingTtlSeconds(token);
            if (remainingTtl > 0) {
                cacheToken(token, remainingTtl);
            }
        }
        return isValid;
    }

    public int extractUserId(String token) {
        if (redisTemplate != null) {
            try {
                String cachedUserId = redisTemplate.opsForValue().get(REDIS_USER_PREFIX + token);
                if (cachedUserId != null) {
                    return Integer.parseInt(cachedUserId);
                }
            } catch (Exception e) {
                logger.warn("Redis connection unavailable for user cache, using fallback: {}", e.getMessage());
            }
        }

        Integer cachedInMemory = inMemoryUserCache.get(token);
        if (cachedInMemory != null) {
            return cachedInMemory;
        }

        int userId = jwtUtil.extractUserId(token);
        long remainingTtl = getRemainingTtlSeconds(token);
        if (remainingTtl > 0) {
            cacheUser(token, userId, remainingTtl);
        }
        return userId;
    }

    public void cacheToken(String token, long ttlSeconds) {
        if (ttlSeconds <= 0) return;
        if (redisTemplate != null) {
            try {
                redisTemplate.opsForValue().set(REDIS_PREFIX + token, "true", ttlSeconds, TimeUnit.SECONDS);
            } catch (Exception e) {
                logger.debug("Failed to set in Redis: {}", e.getMessage());
            }
        }
        inMemoryCache.put(token, System.currentTimeMillis() + (ttlSeconds * 1000L));
    }

    public void cacheUser(String token, int userId, long ttlSeconds) {
        if (ttlSeconds <= 0) return;
        if (redisTemplate != null) {
            try {
                redisTemplate.opsForValue().set(REDIS_USER_PREFIX + token, String.valueOf(userId), ttlSeconds, TimeUnit.SECONDS);
            } catch (Exception e) {
                logger.debug("Failed to set user in Redis: {}", e.getMessage());
            }
        }
        inMemoryUserCache.put(token, userId);
    }

    public void invalidateToken(String token) {
        if (redisTemplate != null) {
            try {
                redisTemplate.delete(REDIS_PREFIX + token);
                redisTemplate.delete(REDIS_USER_PREFIX + token);
            } catch (Exception e) {
                logger.debug("Failed to delete token in Redis: {}", e.getMessage());
            }
        }
        inMemoryCache.remove(token);
        inMemoryUserCache.remove(token);
    }

    private long getRemainingTtlSeconds(String token) {
        try {
            Claims claims = jwtUtil.extractAllClaims(token);
            Date expiration = claims.getExpiration();
            if (expiration == null) {
                return 0;
            }
            long diffMillis = expiration.getTime() - System.currentTimeMillis();
            return Math.max(0, diffMillis / 1000);
        } catch (Exception e) {
            return 0;
        }
    }
}

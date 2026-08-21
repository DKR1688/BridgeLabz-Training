package com.bridgelabz.fundoonotes.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey signingKey;
    private final long expirationMillis;

    public JwtUtil(@Value("${app.jwt.secret}") String base64Secret,
            @Value("${app.jwt.expiration-millis}") long expirationMillis) {
        this.signingKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Secret));
        this.expirationMillis = expirationMillis;
    }

    public String generateToken(String userId, String email) {
        Instant now = Instant.now();
        return Jwts.builder().subject(userId).claim("email", email)
                .issuedAt(Date.from(now)).expiration(Date.from(now.plusMillis(expirationMillis)))
                .signWith(signingKey).compact();
    }

    public Claims parseSignedClaims(String token) {
        return Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token).getPayload();
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = parseSignedClaims(token);
            Date expiration = claims.getExpiration();
            return expiration != null && expiration.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUserId(String token) {
        try {
            return parseSignedClaims(token).getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public Date extractExpiration(String token) {
        try {
            return parseSignedClaims(token).getExpiration();
        } catch (Exception e) {
            return null;
        }
    }

    public long getRemainingTtlMillis(String token) {
        try {
            Date expiration = extractExpiration(token);
            if (expiration == null)
                return 0;
            long remaining = expiration.getTime() - System.currentTimeMillis();
            return Math.max(0, remaining);
        } catch (Exception e) {
            return 0;
        }
    }
}

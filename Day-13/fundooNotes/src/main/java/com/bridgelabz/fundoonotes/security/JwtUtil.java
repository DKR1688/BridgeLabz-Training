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
}

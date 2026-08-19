package com.bridgelabz.fundoonotes.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String uri = request.getRequestURI();

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.debug("JwtAuthFilter: URI={} | No Bearer token present, continuing unauthenticated", uri);
            chain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        try {
            if (jwtUtil.isTokenValid(token)) {
                String userId = jwtUtil.extractUserId(token);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId,
                        null, Collections.emptyList());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.debug("JwtAuthFilter: URI={} | Valid token found for userId={}", uri, userId);
            } else {
                SecurityContextHolder.clearContext();
                logger.debug("JwtAuthFilter: URI={} | Token validation returned false", uri);
            }
        } catch (JwtException | IllegalArgumentException e) {
            SecurityContextHolder.clearContext();
            logger.debug("JwtAuthFilter: URI={} | Corrupted/Invalid token error: {}", uri, e.getMessage());
        }

        chain.doFilter(request, response);
    }
}

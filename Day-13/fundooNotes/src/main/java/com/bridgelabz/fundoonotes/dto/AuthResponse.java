package com.bridgelabz.fundoonotes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthResponse(
        String token,
        String message,
        String userId,
        String email) {
    public AuthResponse(String token) {
        this(token, "Success", null, null);
    }

    public AuthResponse(String token, String message) {
        this(token, message, null, null);
    }
}

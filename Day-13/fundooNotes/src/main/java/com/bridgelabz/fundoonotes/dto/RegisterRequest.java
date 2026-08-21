package com.bridgelabz.fundoonotes.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RegisterRequest(
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6, max = 72) String password,
        String name,
        String firstName,
        String lastName) {
    public RegisterRequest(String email, String password, String name) {
        this(email, password, name, null, null);
    }

    public String resolvedName() {
        if (name != null && !name.isBlank()) {
            return name.trim();
        }
        String f = firstName != null ? firstName.trim() : "";
        String l = lastName != null ? lastName.trim() : "";
        String combined = (f + " " + l).trim();
        return combined.isEmpty() ? "User" : combined;
    }

    public String resolvedFirstName() {
        if (firstName != null && !firstName.isBlank()) {
            return firstName.trim();
        }
        if (name != null && !name.isBlank()) {
            String[] parts = name.trim().split("\\s+", 2);
            return parts[0];
        }
        return "User";
    }

    public String resolvedLastName() {
        if (lastName != null && !lastName.isBlank()) {
            return lastName.trim();
        }
        if (name != null && !name.isBlank()) {
            String[] parts = name.trim().split("\\s+", 2);
            return parts.length > 1 ? parts[1] : "";
        }
        return "";
    }
}

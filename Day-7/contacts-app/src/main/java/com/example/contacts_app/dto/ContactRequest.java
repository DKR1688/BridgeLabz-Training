package com.example.contacts_app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ContactRequest(
        @NotBlank(message = "First name is required") @Size(max = 50) String firstName,
        @NotBlank(message = "Last name is required") @Size(max = 50) String lastName,
        @NotBlank(message = "Email is required") @Email(message = "Email must be valid") String email,
        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^[0-9+() -]{7,20}$", message = "Phone number must be valid") String phoneNumber,
        @Size(max = 250) String address) {
}

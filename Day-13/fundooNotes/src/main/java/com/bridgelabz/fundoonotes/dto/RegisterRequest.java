package com.bridgelabz.fundoonotes.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(@NotBlank @Email String email,
                              @NotBlank @Size(min = 8, max = 72) String password,
                              @NotBlank @Size(max = 100) String name) { }

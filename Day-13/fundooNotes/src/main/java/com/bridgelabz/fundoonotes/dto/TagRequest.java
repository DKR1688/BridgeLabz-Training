package com.bridgelabz.fundoonotes.dto;

import jakarta.validation.constraints.NotBlank;

public record TagRequest(
        @NotBlank(message = "Tag name cannot be blank")
        String name
) {}

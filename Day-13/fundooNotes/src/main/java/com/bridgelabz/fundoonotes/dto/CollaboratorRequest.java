package com.bridgelabz.fundoonotes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CollaboratorRequest(
                @JsonProperty("email") String email,
                @JsonProperty("userId") Integer userId) {
}

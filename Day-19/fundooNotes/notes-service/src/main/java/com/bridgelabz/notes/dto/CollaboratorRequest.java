package com.bridgelabz.notes.dto;

import jakarta.validation.constraints.NotNull;

public class CollaboratorRequest {

    @NotNull(message = "Collaborator user ID is required")
    private Integer userId;

    public CollaboratorRequest() {
    }

    public CollaboratorRequest(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

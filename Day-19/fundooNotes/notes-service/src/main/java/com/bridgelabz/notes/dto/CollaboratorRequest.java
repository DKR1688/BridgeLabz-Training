package com.bridgelabz.notes.dto;

import com.bridgelabz.notes.entity.NoteCollaborator;
import jakarta.validation.constraints.NotNull;

public class CollaboratorRequest {

    @NotNull(message = "Collaborator user ID is required")
    private Integer userId;

    private NoteCollaborator.Role role = NoteCollaborator.Role.VIEWER;

    public CollaboratorRequest() {
    }

    public CollaboratorRequest(Integer userId) {
        this.userId = userId;
        this.role = NoteCollaborator.Role.VIEWER;
    }

    public CollaboratorRequest(Integer userId, NoteCollaborator.Role role) {
        this.userId = userId;
        this.role = (role != null) ? role : NoteCollaborator.Role.VIEWER;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public NoteCollaborator.Role getRole() {
        return role;
    }

    public void setRole(NoteCollaborator.Role role) {
        this.role = (role != null) ? role : NoteCollaborator.Role.VIEWER;
    }
}

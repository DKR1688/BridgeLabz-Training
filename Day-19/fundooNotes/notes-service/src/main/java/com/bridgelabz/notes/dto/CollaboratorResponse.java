package com.bridgelabz.notes.dto;

import com.bridgelabz.notes.entity.NoteCollaborator;

public class CollaboratorResponse {

    private int userId;
    private String email;
    private String name;
    private NoteCollaborator.Role role = NoteCollaborator.Role.VIEWER;

    public CollaboratorResponse() {
    }

    public CollaboratorResponse(int userId) {
        this.userId = userId;
    }

    public CollaboratorResponse(int userId, NoteCollaborator.Role role) {
        this.userId = userId;
        this.role = role;
    }

    public CollaboratorResponse(int userId, String email, String name) {
        this.userId = userId;
        this.email = email;
        this.name = name;
    }

    public CollaboratorResponse(int userId, String email, String name, NoteCollaborator.Role role) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NoteCollaborator.Role getRole() {
        return role;
    }

    public void setRole(NoteCollaborator.Role role) {
        this.role = role;
    }
}

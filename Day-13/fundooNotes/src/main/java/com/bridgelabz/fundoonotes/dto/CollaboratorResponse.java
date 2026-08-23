package com.bridgelabz.fundoonotes.dto;

import com.bridgelabz.fundoonotes.entity.User;

public record CollaboratorResponse(
        int userId,
        String email,
        String name,
        String firstName,
        String lastName) {
    public static CollaboratorResponse fromEntity(User user) {
        if (user == null)
            return null;
        return new CollaboratorResponse(
                user.getUserId(),
                user.getEmail(),
                user.getName(),
                user.getFirstName(),
                user.getLastName());
    }
}

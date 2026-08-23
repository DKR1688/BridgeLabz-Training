package com.bridgelabz.fundoonotes.dto;

import java.io.Serializable;

public record NoteSharedMessage(
                int noteId,
                String noteTitle,
                int ownerId,
                String ownerEmail,
                int collaboratorId,
                String collaboratorEmail,
                String action,
                String timestamp) implements Serializable {
}

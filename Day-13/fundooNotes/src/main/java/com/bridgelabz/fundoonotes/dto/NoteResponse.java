package com.bridgelabz.fundoonotes.dto;

import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.entity.Tag;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public record NoteResponse(
        int noteId,
        String title,
        String content,
        LocalDateTime createdAt,
        int ownerId,
        String ownerEmail,
        Note.NoteState state,
        boolean pinned,
        Set<String> tags) {
    public static NoteResponse fromEntity(Note note) {
        Set<String> tagNames = (note.getTags() != null)
                ? note.getTags().stream().map(Tag::getName).collect(Collectors.toSet())
                : Collections.emptySet();

        int ownerId = (note.getOwner() != null) ? note.getOwner().getUserId() : 0;
        String ownerEmail = (note.getOwner() != null) ? note.getOwner().getEmail() : null;

        return new NoteResponse(
                note.getNoteId(),
                note.getTitle(),
                note.getContent(),
                note.getCreatedAt(),
                ownerId,
                ownerEmail,
                note.getState(),
                note.isPinned(),
                tagNames);
    }
}

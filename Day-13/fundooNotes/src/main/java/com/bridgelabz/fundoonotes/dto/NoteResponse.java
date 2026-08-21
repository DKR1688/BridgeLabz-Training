package com.bridgelabz.fundoonotes.dto;

import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.entity.Tag;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record NoteResponse(
        int noteId,
        int id,
        String title,
        String content,
        String description,
        LocalDateTime createdAt,
        int ownerId,
        String userId,
        String ownerEmail,
        Note.NoteState state,
        boolean pinned,
        boolean isPined,
        boolean isArchived,
        boolean isDeleted,
        String color,
        String typeOfNote,
        String imageUrl,
        String linkUrl,
        Set<String> tags,
        Set<String> labels,
        List<LocalDateTime> reminders) {
    public static NoteResponse fromEntity(Note note) {
        Set<String> tagNames = Collections.emptySet();
        try {
            if (note.getTags() != null && Hibernate.isInitialized(note.getTags())) {
                tagNames = note.getTags().stream().map(Tag::getName).collect(Collectors.toSet());
            }
        } catch (Exception ignored) {
        }

        List<LocalDateTime> remindersList = Collections.emptyList();
        try {
            if (note.getReminders() != null && Hibernate.isInitialized(note.getReminders())) {
                remindersList = new ArrayList<>(note.getReminders());
            }
        } catch (Exception ignored) {
        }

        int ownerId = 0;
        String ownerEmail = null;
        try {
            if (note.getOwner() != null && Hibernate.isInitialized(note.getOwner())) {
                ownerId = note.getOwner().getUserId();
                ownerEmail = note.getOwner().getEmail();
            }
        } catch (Exception ignored) {
        }

        String userIdStr = String.valueOf(ownerId);

        return new NoteResponse(
                note.getNoteId(),
                note.getNoteId(),
                note.getTitle(),
                note.getContent(),
                note.getContent(),
                note.getCreatedAt(),
                ownerId,
                userIdStr,
                ownerEmail,
                note.getState(),
                note.isPinned(),
                note.isPinned(),
                note.isArchived(),
                note.isDeleted(),
                note.getColor(),
                note.getTypeOfNote(),
                note.getImageUrl(),
                note.getLinkUrl(),
                tagNames,
                tagNames,
                remindersList);
    }
}

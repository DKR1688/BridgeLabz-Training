package com.bridgelabz.fundoonotes.dto;

import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.entity.Tag;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
        List<LocalDateTime> reminders,
        Set<CollaboratorResponse> collaborators,
        List<CheckListResponse> noteCheckLists) {

    public static NoteResponse fromEntity(Note note) {
        if (note == null)
            return null;

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

        Set<CollaboratorResponse> collaboratorResponses = Collections.emptySet();
        try {
            if (note.getCollaborators() != null && Hibernate.isInitialized(note.getCollaborators())) {
                collaboratorResponses = note.getCollaborators().stream()
                        .map(CollaboratorResponse::fromEntity)
                        .collect(Collectors.toSet());
            }
        } catch (Exception ignored) {
        }

        List<CheckListResponse> checkListResponses = Collections.emptyList();
        try {
            if (note.getCheckLists() != null && Hibernate.isInitialized(note.getCheckLists())) {
                checkListResponses = note.getCheckLists().stream()
                        .filter(item -> !item.isDeleted())
                        .map(CheckListResponse::fromEntity)
                        .toList();
            }
        } catch (Exception ignored) {
        }

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
                remindersList,
                collaboratorResponses,
                checkListResponses);
    }
}

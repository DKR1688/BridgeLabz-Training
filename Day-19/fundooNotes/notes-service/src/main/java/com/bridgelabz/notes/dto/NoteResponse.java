package com.bridgelabz.notes.dto;

import com.bridgelabz.notes.entity.Note;
import com.bridgelabz.notes.entity.NoteCheckList;
import com.bridgelabz.notes.entity.Tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NoteResponse {

    private int noteId;
    private String title;
    private String content;
    private String description;
    private LocalDateTime createdAt;
    private String state;
    private boolean pinned;
    private boolean isPined;
    private boolean isArchived;
    private boolean isDeleted;
    private String color;
    private String typeOfNote;
    private String imageUrl;
    private String linkUrl;
    private int ownerId;
    private Set<String> tags = new HashSet<>();
    private Set<String> labels = new HashSet<>();
    private Set<Integer> collaboratorIds = new HashSet<>();
    private List<CheckListResponse> checkLists = new ArrayList<>();
    private List<LocalDateTime> reminders = new ArrayList<>();

    public NoteResponse() {
    }

    public static NoteResponse fromEntity(Note note) {
        if (note == null) return null;

        NoteResponse res = new NoteResponse();
        res.setNoteId(note.getNoteId());
        res.setTitle(note.getTitle());
        res.setContent(note.getContent());
        res.setDescription(note.getContent());
        res.setCreatedAt(note.getCreatedAt());
        res.setState(note.getState() != null ? note.getState().name() : "ACTIVE");
        res.setPinned(note.isPinned());
        res.setIsPined(note.isPinned());
        res.setIsArchived(note.isArchived());
        res.setIsDeleted(note.isDeleted());
        res.setColor(note.getColor());
        res.setTypeOfNote(note.getTypeOfNote());
        res.setImageUrl(note.getImageUrl());
        res.setLinkUrl(note.getLinkUrl());
        res.setOwnerId(note.getOwnerId());

        if (note.getTags() != null) {
            Set<String> tagNames = note.getTags().stream().map(Tag::getName).collect(Collectors.toSet());
            res.setTags(tagNames);
            res.setLabels(tagNames);
        }

        if (note.getCollaboratorIds() != null) {
            res.setCollaboratorIds(new HashSet<>(note.getCollaboratorIds()));
        }

        if (note.getCheckLists() != null) {
            List<CheckListResponse> list = note.getCheckLists().stream()
                    .map(CheckListResponse::fromEntity)
                    .collect(Collectors.toList());
            res.setCheckLists(list);
        }

        if (note.getReminders() != null) {
            res.setReminders(new ArrayList<>(note.getReminders()));
        }

        return res;
    }

    public int getNoteId() { return noteId; }
    public void setNoteId(int noteId) { this.noteId = noteId; }
    public int getId() { return noteId; }
    public void setId(int id) { this.noteId = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public boolean isPinned() { return pinned; }
    public void setPinned(boolean pinned) { this.pinned = pinned; }
    public boolean getIsPined() { return isPined; }
    public void setIsPined(boolean isPined) { this.isPined = isPined; }
    public boolean getIsArchived() { return isArchived; }
    public void setIsArchived(boolean isArchived) { this.isArchived = isArchived; }
    public boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getTypeOfNote() { return typeOfNote; }
    public void setTypeOfNote(String typeOfNote) { this.typeOfNote = typeOfNote; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getLinkUrl() { return linkUrl; }
    public void setLinkUrl(String linkUrl) { this.linkUrl = linkUrl; }
    public int getOwnerId() { return ownerId; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }
    public Set<String> getTags() { return tags; }
    public void setTags(Set<String> tags) { this.tags = tags; }
    public Set<String> getLabels() { return labels; }
    public void setLabels(Set<String> labels) { this.labels = labels; }
    public Set<Integer> getCollaboratorIds() { return collaboratorIds; }
    public void setCollaboratorIds(Set<Integer> collaboratorIds) { this.collaboratorIds = collaboratorIds; }
    public List<CheckListResponse> getCheckLists() { return checkLists; }
    public void setCheckLists(List<CheckListResponse> checkLists) { this.checkLists = checkLists; }
    public List<LocalDateTime> getReminders() { return reminders; }
    public void setReminders(List<LocalDateTime> reminders) { this.reminders = reminders; }
}

package com.bridgelabz.notes.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

public class NoteRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String content;
    private String description;
    private String color;
    private String typeOfNote;
    private String imageUrl;
    private String linkUrl;
    private Boolean isPined;
    private Boolean isArchived;
    private Boolean isDeleted;
    private Set<String> tags;
    private List<String> checklist;

    public NoteRequest() {
    }

    public NoteRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content != null ? content : description;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description != null ? description : content;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTypeOfNote() {
        return typeOfNote;
    }

    public void setTypeOfNote(String typeOfNote) {
        this.typeOfNote = typeOfNote;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Boolean getIsPined() {
        return isPined;
    }

    public void setIsPined(Boolean isPined) {
        this.isPined = isPined;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public List<String> getChecklist() {
        return checklist;
    }

    public void setChecklist(List<String> checklist) {
        this.checklist = checklist;
    }
}

package com.bridgelabz.fundoonotes.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NoteRequest(
        @NotBlank(message = "Title must not be blank") @Size(max = 255, message = "Title must be at most 255 characters") String title,

        @Size(max = 2000, message = "Content must be at most 2000 characters") String content,

        String description,

        Boolean isPined,
        Boolean pinned,

        String color,
        String typeOfNote,
        String imageUrl,
        String linkUrl,

        Set<String> tags,
        Set<String> labelNames,

        List<LocalDateTime> reminders,
        String reminder) {
    public NoteRequest(String title, String content) {
        this(title, content, null, null, null, null, null, null, null, null, null, null, null);
    }

    public NoteRequest(String title, String content, Set<String> tags) {
        this(title, content, null, null, null, null, null, null, null, tags, null, null, null);
    }

    public String resolvedContent() {
        if (description != null && !description.isBlank()) {
            return description;
        }
        return content != null ? content : "";
    }

    public boolean resolvedPinned() {
        if (isPined != null)
            return isPined;
        if (pinned != null)
            return pinned;
        return false;
    }

    public Set<String> resolvedTags() {
        if (labelNames != null && !labelNames.isEmpty()) {
            return labelNames;
        }
        return tags;
    }
}

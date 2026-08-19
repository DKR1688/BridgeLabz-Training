package com.bridgelabz.fundoonotes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record NoteRequest(
        @NotBlank(message = "Title must not be blank") @Size(max = 255, message = "Title must be at most 255 characters") String title,

        @Size(max = 2000, message = "Content must be at most 2000 characters") String content,

        Set<String> tags) {
    public NoteRequest(String title, String content) {
        this(title, content, null);
    }
}

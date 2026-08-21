package com.bridgelabz.fundoonotes.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LabelRequest(
        @NotBlank(message = "Label name must not be blank") @Size(max = 100, message = "Label must be at most 100 characters") String label,
        String name) {
    public LabelRequest(String label) {
        this(label, null);
    }

    public String resolvedLabel() {
        if (label != null && !label.isBlank()) {
            return label.trim();
        }
        if (name != null && !name.isBlank()) {
            return name.trim();
        }
        return "";
    }
}

package com.bridgelabz.notes.dto;

import jakarta.validation.constraints.NotBlank;

public class LabelRequest {

    @NotBlank(message = "Label name is required")
    private String label;

    public LabelRequest() {
    }

    public LabelRequest(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return label;
    }

    public void setName(String name) {
        this.label = name;
    }
}

package com.bridgelabz.notes.dto;

import com.bridgelabz.notes.entity.Tag;

public class LabelResponse {

    private int id;
    private String label;
    private boolean isDeleted;

    public LabelResponse() {
    }

    public LabelResponse(int id, String label, boolean isDeleted) {
        this.id = id;
        this.label = label;
        this.isDeleted = isDeleted;
    }

    public static LabelResponse fromEntity(Tag tag) {
        if (tag == null) return null;
        return new LabelResponse(tag.getTagId(), tag.getName(), false);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}

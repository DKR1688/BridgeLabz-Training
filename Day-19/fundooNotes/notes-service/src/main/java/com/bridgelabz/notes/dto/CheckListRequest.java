package com.bridgelabz.notes.dto;

import jakarta.validation.constraints.NotBlank;

public class CheckListRequest {

    @NotBlank(message = "Item text is required")
    private String item;

    private Boolean isDone;

    public CheckListRequest() {
    }

    public CheckListRequest(String item) {
        this.item = item;
        this.isDone = false;
    }

    public CheckListRequest(String item, Boolean isDone) {
        this.item = item;
        this.isDone = isDone;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }
}

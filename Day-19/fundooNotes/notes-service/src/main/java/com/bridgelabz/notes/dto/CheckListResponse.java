package com.bridgelabz.notes.dto;

import com.bridgelabz.notes.entity.NoteCheckList;

public class CheckListResponse {

    private int id;
    private String item;
    private boolean isDone;

    public CheckListResponse() {
    }

    public CheckListResponse(int id, String item, boolean isDone) {
        this.id = id;
        this.item = item;
        this.isDone = isDone;
    }

    public static CheckListResponse fromEntity(NoteCheckList checkList) {
        if (checkList == null) return null;
        return new CheckListResponse(checkList.getId(), checkList.getItem(), checkList.isDone());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}

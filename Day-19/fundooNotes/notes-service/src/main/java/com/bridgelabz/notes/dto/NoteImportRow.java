package com.bridgelabz.notes.dto;

public class NoteImportRow {

    private String title;
    private String content;

    public NoteImportRow() {
    }

    public NoteImportRow(String title, String content) {
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
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

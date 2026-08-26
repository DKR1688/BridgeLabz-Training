package com.bridgelabz.notes.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ReminderMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private int noteId;
    private int userId;
    private LocalDateTime reminderTime;

    public ReminderMessage() {
    }

    public ReminderMessage(int noteId, int userId, LocalDateTime reminderTime) {
        this.noteId = noteId;
        this.userId = userId;
        this.reminderTime = reminderTime;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(LocalDateTime reminderTime) {
        this.reminderTime = reminderTime;
    }
}

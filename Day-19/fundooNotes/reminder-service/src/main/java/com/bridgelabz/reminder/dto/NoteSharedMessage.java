package com.bridgelabz.reminder.dto;

import java.io.Serializable;

public class NoteSharedMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private int noteId;
    private int ownerId;
    private int collaboratorId;

    public NoteSharedMessage() {
    }

    public NoteSharedMessage(int noteId, int ownerId, int collaboratorId) {
        this.noteId = noteId;
        this.ownerId = ownerId;
        this.collaboratorId = collaboratorId;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getCollaboratorId() {
        return collaboratorId;
    }

    public void setCollaboratorId(int collaboratorId) {
        this.collaboratorId = collaboratorId;
    }
}

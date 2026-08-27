package com.bridgelabz.notes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "note_collaborators")
public class NoteCollaborator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id", nullable = false)
    @JsonIgnore
    private Note note;

    @Column(name = "user_id", nullable = false)
    private int collaboratorId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.VIEWER;

    public enum Role {
        VIEWER,
        EDITOR
    }

    public NoteCollaborator() {
    }

    public NoteCollaborator(Note note, int collaboratorId, Role role) {
        this.note = note;
        this.collaboratorId = collaboratorId;
        this.role = (role != null) ? role : Role.VIEWER;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public int getCollaboratorId() {
        return collaboratorId;
    }

    public void setCollaboratorId(int collaboratorId) {
        this.collaboratorId = collaboratorId;
    }

    public int getUserId() {
        return collaboratorId;
    }

    public void setUserId(int userId) {
        this.collaboratorId = userId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = (role != null) ? role : Role.VIEWER;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteCollaborator that = (NoteCollaborator) o;
        return collaboratorId == that.collaboratorId &&
                Objects.equals(note != null ? note.getNoteId() : null, that.note != null ? that.note.getNoteId() : null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(note != null ? note.getNoteId() : null, collaboratorId);
    }
}

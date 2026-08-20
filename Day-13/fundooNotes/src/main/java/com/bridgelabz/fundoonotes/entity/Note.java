package com.bridgelabz.fundoonotes.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "note")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noteId;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();

    public enum NoteState {
        ACTIVE,
        ARCHIVED,
        TRASHED
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NoteState state = NoteState.ACTIVE;

    @Column(nullable = false)
    private boolean pinned = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({ "notes", "passwordHash" })
    private User owner;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinTable(name = "note_tags", joinColumns = @JoinColumn(name = "note_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    public Note() {
    }

    public Note(String title, String content, User owner) {
        this.title = title;
        this.content = content;
        this.owner = owner;
        this.state = NoteState.ACTIVE;
        this.pinned = false;
        this.createdAt = LocalDateTime.now();
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public NoteState getState() {
        return state;
    }

    public void setState(NoteState state) {
        this.state = state;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.getNotes().add(this);
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getNotes().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Note note = (Note) o;
        return noteId != 0 && noteId == note.noteId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(noteId);
    }
}

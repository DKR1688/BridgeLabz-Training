package com.bridgelabz.fundoonotes.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @Column(length = 50)
    private String color;

    @Column(length = 50)
    private String typeOfNote = "TEXT";

    @Column(length = 500)
    private String imageUrl;

    @Column(length = 500)
    private String linkUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({ "notes", "passwordHash" })
    private User owner;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinTable(name = "note_tags", joinColumns = @JoinColumn(name = "note_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "note_collaborators", joinColumns = @JoinColumn(name = "note_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnoreProperties({ "notes", "passwordHash" })
    private Set<User> collaborators = new HashSet<>();

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<NoteCheckList> checkLists = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "note_reminders", joinColumns = @JoinColumn(name = "note_id"))
    @Column(name = "reminder_time")
    private List<LocalDateTime> reminders = new ArrayList<>();

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

    public int getId() {
        return noteId;
    }

    public void setId(int id) {
        this.noteId = id;
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

    public String getDescription() {
        return content;
    }

    public void setDescription(String description) {
        this.content = description;
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

    public Set<Tag> getLabels() {
        return tags;
    }

    public void setLabels(Set<Tag> labels) {
        this.tags = labels;
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

    public boolean isPined() {
        return pinned;
    }

    public void setPined(boolean pined) {
        this.pinned = pined;
    }

    public boolean isArchived() {
        return this.state == NoteState.ARCHIVED;
    }

    public void setArchived(boolean archived) {
        if (archived) {
            this.state = NoteState.ARCHIVED;
            this.pinned = false;
        } else if (this.state == NoteState.ARCHIVED) {
            this.state = NoteState.ACTIVE;
        }
    }

    public boolean isDeleted() {
        return this.state == NoteState.TRASHED;
    }

    public void setDeleted(boolean deleted) {
        if (deleted) {
            this.state = NoteState.TRASHED;
            this.pinned = false;
        } else if (this.state == NoteState.TRASHED) {
            this.state = NoteState.ACTIVE;
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTypeOfNote() {
        return typeOfNote;
    }

    public void setTypeOfNote(String typeOfNote) {
        this.typeOfNote = typeOfNote;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public List<LocalDateTime> getReminders() {
        return reminders;
    }

    public void setReminders(List<LocalDateTime> reminders) {
        this.reminders = (reminders != null) ? reminders : new ArrayList<>();
    }

    public void addReminder(LocalDateTime reminder) {
        if (reminder != null && !this.reminders.contains(reminder)) {
            this.reminders.add(reminder);
        }
    }

    public void removeReminder(LocalDateTime reminder) {
        this.reminders.remove(reminder);
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.getNotes().add(this);
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getNotes().remove(this);
    }

    public Set<User> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(Set<User> collaborators) {
        this.collaborators = (collaborators != null) ? collaborators : new HashSet<>();
    }

    public void addCollaborator(User user) {
        if (user != null) {
            this.collaborators.add(user);
        }
    }

    public void removeCollaborator(User user) {
        if (user != null) {
            this.collaborators.remove(user);
        }
    }

    public List<NoteCheckList> getCheckLists() {
        return checkLists;
    }

    public void setCheckLists(List<NoteCheckList> checkLists) {
        this.checkLists = (checkLists != null) ? checkLists : new ArrayList<>();
    }

    public void addCheckList(NoteCheckList item) {
        if (item != null) {
            this.checkLists.add(item);
            item.setNote(this);
        }
    }

    public void removeCheckList(NoteCheckList item) {
        if (item != null) {
            this.checkLists.remove(item);
            item.setNote(null);
        }
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

package com.bridgelabz.notes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "note_checklist")
public class NoteCheckList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id", nullable = false)
    @JsonIgnore
    private Note note;

    @Column(nullable = false, length = 500)
    private String item;

    @Column(nullable = false)
    private boolean isDone = false;

    public NoteCheckList() {
    }

    public NoteCheckList(Note note, String item) {
        this.note = note;
        this.item = item;
        this.isDone = false;
    }

    public NoteCheckList(Note note, String item, boolean isDone) {
        this.note = note;
        this.item = item;
        this.isDone = isDone;
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

package com.bridgelabz.fundoonotes.controller;

import com.bridgelabz.fundoonotes.dto.NoteRequest;
import com.bridgelabz.fundoonotes.dto.NoteResponse;
import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    private int currentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == null) {
            throw new IllegalArgumentException("Unauthorized");
        }
        return Integer.parseInt(principal.toString());
    }

    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody NoteRequest request) {
        Note note = noteService.createNote(
                currentUserId(),
                request.title(),
                request.content(),
                request.tags());
        return ResponseEntity.status(HttpStatus.CREATED).body(NoteResponse.fromEntity(note));
    }

    @GetMapping
    public List<NoteResponse> getMyNotes() {
        return noteService.findByOwner(currentUserId())
                .stream()
                .map(NoteResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable int id) {
        return noteService.getNoteById(id, currentUserId())
                .map(note -> ResponseEntity.ok(NoteResponse.fromEntity(note)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(@PathVariable int id, @Valid @RequestBody NoteRequest request) {
        return noteService.updateNote(
                id,
                currentUserId(),
                request.title(),
                request.content(),
                request.tags())
                .map(note -> ResponseEntity.ok(NoteResponse.fromEntity(note)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable int id) {
        boolean deleted = noteService.deleteNote(id, currentUserId());
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

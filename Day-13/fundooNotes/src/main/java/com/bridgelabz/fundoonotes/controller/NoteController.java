package com.bridgelabz.fundoonotes.controller;

import com.bridgelabz.fundoonotes.dto.NoteRequest;
import com.bridgelabz.fundoonotes.dto.NoteResponse;
import com.bridgelabz.fundoonotes.dto.TagRequest;
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
    public List<NoteResponse> getNotes(
            @RequestParam(required = false) String state,
            @RequestParam(required = false) Boolean pinned,
            @RequestParam(required = false) String tag) {
        int userId = currentUserId();

        if (state != null && !state.isBlank()) {
            Note.NoteState noteState = Note.NoteState.valueOf(state.toUpperCase());
            return noteService.findByOwnerAndState(userId, noteState)
                    .stream()
                    .map(NoteResponse::fromEntity)
                    .toList();
        }

        if (pinned != null && pinned) {
            return noteService.findPinnedByOwner(userId)
                    .stream()
                    .map(NoteResponse::fromEntity)
                    .toList();
        }

        if (tag != null && !tag.isBlank()) {
            return noteService.findByOwnerAndTag(userId, tag)
                    .stream()
                    .map(NoteResponse::fromEntity)
                    .toList();
        }

        return noteService.findActiveByOwner(userId)
                .stream()
                .map(NoteResponse::fromEntity)
                .toList();
    }

    /**
     * Problem 3 Dynamic Search Endpoint:
     * Accepts optional title, state, tag, and pinned query parameters.
     * When no filters are supplied, returns all notes belonging to the authenticated user across all states.
     */
    @GetMapping("/search")
    public List<NoteResponse> searchNotes(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) Boolean pinned) {
        int userId = currentUserId();
        Note.NoteState noteState = (state != null && !state.isBlank())
                ? Note.NoteState.valueOf(state.toUpperCase())
                : null;

        return noteService.search(userId, title, noteState, tag, pinned)
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

    @PatchMapping("/{id}/archive")
    public ResponseEntity<NoteResponse> archiveNote(@PathVariable int id) {
        Note note = noteService.archiveNote(id, currentUserId());
        return ResponseEntity.ok(NoteResponse.fromEntity(note));
    }

    @PatchMapping("/{id}/trash")
    public ResponseEntity<NoteResponse> trashNote(@PathVariable int id) {
        Note note = noteService.trashNote(id, currentUserId());
        return ResponseEntity.ok(NoteResponse.fromEntity(note));
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<NoteResponse> restoreNote(@PathVariable int id) {
        Note note = noteService.restoreNote(id, currentUserId());
        return ResponseEntity.ok(NoteResponse.fromEntity(note));
    }

    @PatchMapping("/{id}/pin")
    public ResponseEntity<NoteResponse> pinNote(@PathVariable int id) {
        Note note = noteService.pinNote(id, currentUserId());
        return ResponseEntity.ok(NoteResponse.fromEntity(note));
    }

    @PatchMapping("/{id}/unpin")
    public ResponseEntity<NoteResponse> unpinNote(@PathVariable int id) {
        Note note = noteService.unpinNote(id, currentUserId());
        return ResponseEntity.ok(NoteResponse.fromEntity(note));
    }

    @PostMapping("/{id}/tags")
    public ResponseEntity<NoteResponse> addTagToNote(
            @PathVariable int id,
            @Valid @RequestBody TagRequest request) {
        Note note = noteService.addTagToNote(id, currentUserId(), request.name());
        return ResponseEntity.ok(NoteResponse.fromEntity(note));
    }
}

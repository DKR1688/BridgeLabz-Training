package com.bridgelabz.fundoonotes.controller;

import com.bridgelabz.fundoonotes.dto.NoteRequest;
import com.bridgelabz.fundoonotes.dto.NoteResponse;
import com.bridgelabz.fundoonotes.dto.ReminderRequest;
import com.bridgelabz.fundoonotes.dto.TagRequest;
import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    // ==========================================
    // Use Case 4: Notes CRUD with Ownership
    // ==========================================

    @PostMapping({ "/addNotes", "" })
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody NoteRequest request) {
        Note note = noteService.createNote(
                currentUserId(),
                request.title(),
                request.resolvedContent(),
                request.color(),
                request.typeOfNote(),
                request.imageUrl(),
                request.linkUrl(),
                request.resolvedTags(),
                request.reminders());
        if (request.resolvedPinned()) {
            note = noteService.pinNote(note.getNoteId(), currentUserId());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(NoteResponse.fromEntity(note));
    }

    @GetMapping({ "/getNotesList", "" })
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

    @GetMapping({ "/getNotesDetail/{noteId}", "/{id}" })
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable(name = "noteId", required = false) Integer noteId,
            @PathVariable(name = "id", required = false) Integer id) {
        int targetId = (noteId != null) ? noteId : id;
        return noteService.getNoteById(targetId, currentUserId())
                .map(note -> ResponseEntity.ok(NoteResponse.fromEntity(note)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/updateNotes")
    public ResponseEntity<NoteResponse> updateNotesViaPost(
            @RequestParam(required = false) Integer noteId,
            @RequestBody Map<String, Object> body) {
        int targetId = noteId != null ? noteId
                : (body.containsKey("noteId") ? Integer.parseInt(body.get("noteId").toString())
                        : (body.containsKey("id") ? Integer.parseInt(body.get("id").toString()) : 0));
        String title = (String) body.get("title");
        String content = body.containsKey("description") ? (String) body.get("description")
                : (String) body.get("content");
        String color = (String) body.get("color");
        String typeOfNote = (String) body.get("typeOfNote");
        String imageUrl = (String) body.get("imageUrl");
        String linkUrl = (String) body.get("linkUrl");

        return noteService
                .updateNote(targetId, currentUserId(), title, content, color, typeOfNote, imageUrl, linkUrl, null, null)
                .map(note -> ResponseEntity.ok(NoteResponse.fromEntity(note)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(@PathVariable int id, @Valid @RequestBody NoteRequest request) {
        return noteService.updateNote(
                id,
                currentUserId(),
                request.title(),
                request.resolvedContent(),
                request.color(),
                request.typeOfNote(),
                request.imageUrl(),
                request.linkUrl(),
                request.resolvedTags(),
                request.reminders())
                .map(note -> ResponseEntity.ok(NoteResponse.fromEntity(note)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable int id) {
        boolean deleted = noteService.deleteNote(id, currentUserId());
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // ==========================================
    // Use Case 5: Pin / Archive / Trash
    // ==========================================

    @PostMapping("/pinUnpinNotes")
    public ResponseEntity<NoteResponse> pinUnpinNotes(
            @RequestParam(required = false) Integer noteId,
            @RequestBody(required = false) Map<String, Object> body) {
        int targetId = (noteId != null) ? noteId
                : (body != null && body.containsKey("noteId")
                        ? Integer.parseInt(body.get("noteId").toString())
                        : 0);
        Note note = noteService.togglePinNote(targetId, currentUserId());
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

    @PostMapping("/archiveNotes")
    public ResponseEntity<NoteResponse> archiveNotesPost(
            @RequestParam(required = false) Integer noteId,
            @RequestBody(required = false) Map<String, Object> body) {
        int targetId = (noteId != null) ? noteId
                : (body != null && body.containsKey("noteId")
                        ? Integer.parseInt(body.get("noteId").toString())
                        : 0);
        Note note = noteService.archiveNote(targetId, currentUserId());
        return ResponseEntity.ok(NoteResponse.fromEntity(note));
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<NoteResponse> archiveNote(@PathVariable int id) {
        Note note = noteService.archiveNote(id, currentUserId());
        return ResponseEntity.ok(NoteResponse.fromEntity(note));
    }

    @PostMapping("/trashNotes")
    public ResponseEntity<NoteResponse> trashNotesPost(
            @RequestParam(required = false) Integer noteId,
            @RequestBody(required = false) Map<String, Object> body) {
        int targetId = (noteId != null) ? noteId
                : (body != null && body.containsKey("noteId")
                        ? Integer.parseInt(body.get("noteId").toString())
                        : 0);
        Note note = noteService.trashNote(targetId, currentUserId());
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

    @PostMapping("/deleteForeverNotes")
    public ResponseEntity<Void> deleteForeverNotesPost(
            @RequestParam(required = false) Integer noteId,
            @RequestBody(required = false) Map<String, Object> body) {
        int targetId = (noteId != null) ? noteId
                : (body != null && body.containsKey("noteId")
                        ? Integer.parseInt(body.get("noteId").toString())
                        : 0);
        boolean deleted = noteService.deleteForeverNote(targetId, currentUserId());
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/deleteForeverNotes")
    public ResponseEntity<Void> deleteForeverNote(@PathVariable int id) {
        boolean deleted = noteService.deleteForeverNote(id, currentUserId());
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/getArchiveNotesList")
    public List<NoteResponse> getArchiveNotesList() {
        return noteService.getArchiveNotesList(currentUserId())
                .stream()
                .map(NoteResponse::fromEntity)
                .toList();
    }

    @GetMapping("/getTrashNotesList")
    public List<NoteResponse> getTrashNotesList() {
        return noteService.getTrashNotesList(currentUserId())
                .stream()
                .map(NoteResponse::fromEntity)
                .toList();
    }

    // ==========================================
    // Use Case 6: Label Association
    // ==========================================

    @PostMapping("/{noteId}/addLabelToNotes/{labelId}/add")
    public ResponseEntity<NoteResponse> addLabelToNotes(
            @PathVariable int noteId,
            @PathVariable int labelId) {
        Note note = noteService.addLabelToNote(noteId, currentUserId(), labelId);
        return ResponseEntity.ok(NoteResponse.fromEntity(note));
    }

    @PostMapping("/{noteId}/addLabelToNotes/{labelId}/remove")
    public ResponseEntity<NoteResponse> removeLabelFromNotes(
            @PathVariable int noteId,
            @PathVariable int labelId) {
        Note note = noteService.removeLabelFromNote(noteId, currentUserId(), labelId);
        return ResponseEntity.ok(NoteResponse.fromEntity(note));
    }

    @PostMapping("/{id}/tags")
    public ResponseEntity<NoteResponse> addTagToNote(
            @PathVariable int id,
            @Valid @RequestBody TagRequest request) {
        Note note = noteService.addTagToNote(id, currentUserId(), request.name());
        return ResponseEntity.ok(NoteResponse.fromEntity(note));
    }

    // ==========================================
    // Use Case 7: Search & Filter Specification
    // ==========================================

    @GetMapping("/search")
    public List<NoteResponse> searchNotes(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String labelName,
            @RequestParam(required = false) Boolean pinned) {
        int userId = currentUserId();
        String resolvedLabel = (labelName != null && !labelName.isBlank()) ? labelName : tag;
        return noteService.search(userId, title, state, resolvedLabel)
                .stream()
                .map(NoteResponse::fromEntity)
                .toList();
    }

    @GetMapping("/getNotesListByLabel/{labelName}")
    public List<NoteResponse> getNotesListByLabel(@PathVariable String labelName) {
        return noteService.findByOwnerAndTag(currentUserId(), labelName)
                .stream()
                .map(NoteResponse::fromEntity)
                .toList();
    }

    // ==========================================
    // Use Case 8: Reminders via JMS
    // ==========================================

    @PostMapping("/addUpdateReminderNotes")
    public ResponseEntity<NoteResponse> addUpdateReminderNotes(
            @RequestParam(required = false) Integer noteId,
            @RequestBody(required = false) Map<String, Object> body) {
        int targetId = (noteId != null) ? noteId
                : (body != null && body.containsKey("noteId")
                        ? Integer.parseInt(body.get("noteId").toString())
                        : 0);
        LocalDateTime reminderTime = LocalDateTime.now().plusDays(1);
        if (body != null && body.containsKey("reminder")) {
            Object r = body.get("reminder");
            if (r instanceof List<?> list && !list.isEmpty()) {
                reminderTime = LocalDateTime.parse(list.get(0).toString());
            } else if (r != null) {
                reminderTime = LocalDateTime.parse(r.toString());
            }
        }
        Note note = noteService.addUpdateReminder(targetId, currentUserId(), reminderTime);
        return ResponseEntity.ok(NoteResponse.fromEntity(note));
    }

    @PostMapping("/removeReminderNotes")
    public ResponseEntity<NoteResponse> removeReminderNotes(
            @RequestParam(required = false) Integer noteId,
            @RequestBody(required = false) Map<String, Object> body) {
        int targetId = (noteId != null) ? noteId
                : (body != null && body.containsKey("noteId")
                        ? Integer.parseInt(body.get("noteId").toString())
                        : 0);
        LocalDateTime reminderTime = null;
        if (body != null && body.containsKey("reminder") && body.get("reminder") != null) {
            reminderTime = LocalDateTime.parse(body.get("reminder").toString());
        }
        Note note = noteService.removeReminder(targetId, currentUserId(), reminderTime);
        return ResponseEntity.ok(NoteResponse.fromEntity(note));
    }

    @GetMapping("/getReminderNotesList")
    public List<NoteResponse> getReminderNotesList() {
        return noteService.getReminderNotesList(currentUserId())
                .stream()
                .map(NoteResponse::fromEntity)
                .toList();
    }
}

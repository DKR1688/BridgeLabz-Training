package com.bridgelabz.fundoonotes.controller;

import com.bridgelabz.fundoonotes.dto.*;
import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.exception.NoteNotFoundException;
import com.bridgelabz.fundoonotes.service.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;
    private final CheckListService checkListService;
    private final CollaboratorService collaboratorService;
    private final NoteBatchService noteBatchService;
    private final NoteExportService noteExportService;

    public NoteController(NoteService noteService,
            CheckListService checkListService,
            CollaboratorService collaboratorService,
            NoteBatchService noteBatchService,
            NoteExportService noteExportService) {
        this.noteService = noteService;
        this.checkListService = checkListService;
        this.collaboratorService = collaboratorService;
        this.noteBatchService = noteBatchService;
        this.noteExportService = noteExportService;
    }

    private int currentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == null) {
            throw new IllegalArgumentException("Unauthorized");
        }
        return Integer.parseInt(principal.toString());
    }

    // Notes CRUD with Ownership
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
    public ResponseEntity<NoteResponse> getNoteById(
            @PathVariable(name = "noteId", required = false) Integer noteId,
            @PathVariable(name = "id", required = false) Integer id) {
        int targetId = (noteId != null) ? noteId : id;
        return noteService.getNoteById(targetId, currentUserId())
                .map(note -> ResponseEntity.ok(NoteResponse.fromEntity(note)))
                .orElseThrow(() -> new NoteNotFoundException("Note not found"));
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
                .orElseThrow(() -> new NoteNotFoundException("Note not found"));
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
                .orElseThrow(() -> new NoteNotFoundException("Note not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable int id) {
        boolean deleted = noteService.deleteNote(id, currentUserId());
        if (!deleted) {
            throw new NoteNotFoundException("Note not found");
        }
        return ResponseEntity.noContent().build();
    }

    // Pin / Archive / Trash
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
        if (!deleted) {
            throw new NoteNotFoundException("Note not found");
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/deleteForeverNotes")
    public ResponseEntity<Void> deleteForeverNote(@PathVariable int id) {
        boolean deleted = noteService.deleteForeverNote(id, currentUserId());
        if (!deleted) {
            throw new NoteNotFoundException("Note not found");
        }
        return ResponseEntity.noContent().build();
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

    // Label Association
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

    // Search & Filter Specification
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

    // Reminders via JMS
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

    // Checklist Items on Notes
    @GetMapping("/{id}/noteCheckLists")
    public List<CheckListResponse> getCheckLists(@PathVariable int id) {
        return checkListService.getCheckLists(id, currentUserId());
    }

    @PostMapping("/{id}/noteCheckLists")
    public ResponseEntity<CheckListResponse> addCheckListItem(
            @PathVariable int id,
            @Valid @RequestBody CheckListRequest request) {
        CheckListResponse response = checkListService.addCheckListItem(id, currentUserId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/noteCheckLists/{fk}")
    public ResponseEntity<CheckListResponse> updateCheckListItem(
            @PathVariable int id,
            @PathVariable int fk,
            @RequestBody CheckListRequest request) {
        CheckListResponse response = checkListService.updateCheckListItem(id, fk, currentUserId(), request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/noteCheckLists/{fk}")
    public ResponseEntity<Void> deleteCheckListItem(
            @PathVariable int id,
            @PathVariable int fk) {
        checkListService.deleteCheckListItem(id, fk, currentUserId());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/noteCheckLists/completeAll")
    public List<CheckListResponse> completeAllCheckListItems(@PathVariable int id) {
        return checkListService.bulkCompleteAll(id, currentUserId());
    }

    @PutMapping("/{id}/noteCheckLists/completeAll")
    public List<CheckListResponse> completeAllCheckListItemsPut(@PathVariable int id) {
        return checkListService.bulkCompleteAll(id, currentUserId());
    }

    // Collaborators on Notes
    @PostMapping("/{id}/AddcollaboratorsNotes")
    public ResponseEntity<CollaboratorResponse> addCollaborator(
            @PathVariable int id,
            @RequestParam(required = false) String email,
            @RequestBody(required = false) Map<String, Object> body) {
        String targetEmail = (email != null && !email.isBlank()) ? email
                : (body != null && body.containsKey("email") ? body.get("email").toString() : null);
        Integer targetUserId = (body != null && body.containsKey("userId"))
                ? Integer.parseInt(body.get("userId").toString())
                : null;

        CollaboratorResponse response = collaboratorService.addCollaborator(id, currentUserId(), targetEmail,
                targetUserId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/collaborators")
    public ResponseEntity<CollaboratorResponse> addCollaboratorRestful(
            @PathVariable int id,
            @RequestBody CollaboratorRequest request) {
        CollaboratorResponse response = collaboratorService.addCollaborator(id, currentUserId(), request.email(),
                request.userId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}/removeCollaboratorsNotes/{collaboratorUserId}")
    public ResponseEntity<Void> removeCollaborator(
            @PathVariable int id,
            @PathVariable int collaboratorUserId) {
        boolean removed = collaboratorService.removeCollaborator(id, currentUserId(), collaboratorUserId);
        if (!removed) {
            throw new NoteNotFoundException("Collaborator not found or note not found");
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/collaborators/{collaboratorUserId}")
    public ResponseEntity<Void> removeCollaboratorRestful(
            @PathVariable int id,
            @PathVariable int collaboratorUserId) {
        boolean removed = collaboratorService.removeCollaborator(id, currentUserId(), collaboratorUserId);
        if (!removed) {
            throw new NoteNotFoundException("Collaborator not found or note not found");
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/collaborators")
    public List<CollaboratorResponse> getCollaborators(@PathVariable int id) {
        return collaboratorService.getCollaborators(id, currentUserId());
    }

    // Spring Batch Import / POI Export
    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BatchJobResponse> importNotes(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new BatchJobResponse(0, 0, 0, "FAILED", "Uploaded file is empty"));
        }

        try (InputStream is = file.getInputStream()) {
            BatchJobResponse response = noteBatchService.importNotes(currentUserId(), is);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BatchJobResponse(0, 0, 0, "FAILED", "Import error: " + e.getMessage()));
        }
    }

    @GetMapping({ "/export", "/exportExcel" })
    public ResponseEntity<byte[]> exportNotes() {
        byte[] excelBytes = noteExportService.exportUserNotesToExcel(currentUserId());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"fundoo_notes.xlsx\"")
                .contentType(
                        MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excelBytes);
    }
}

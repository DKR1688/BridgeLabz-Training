package com.bridgelabz.notes.controller;

import com.bridgelabz.notes.dto.*;
import com.bridgelabz.notes.entity.Note;
import com.bridgelabz.notes.service.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;
    private final CheckListService checkListService;
    private final CollaboratorService collaboratorService;
    private final LabelService labelService;
    private final NoteBatchService noteBatchService;
    private final NoteExportService noteExportService;

    public NoteController(
            NoteService noteService,
            CheckListService checkListService,
            CollaboratorService collaboratorService,
            LabelService labelService,
            NoteBatchService noteBatchService,
            NoteExportService noteExportService) {
        this.noteService = noteService;
        this.checkListService = checkListService;
        this.collaboratorService = collaboratorService;
        this.labelService = labelService;
        this.noteBatchService = noteBatchService;
        this.noteExportService = noteExportService;
    }

    private int getUserId(Authentication authentication) {
        return (int) authentication.getPrincipal();
    }

    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody NoteRequest request, Authentication authentication) {
        int userId = getUserId(authentication);
        NoteResponse response = noteService.createNote(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<NoteResponse>> getNotes(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Note.NoteState state,
            @RequestParam(required = false) Boolean pinned,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String color,
            Authentication authentication) {
        int userId = getUserId(authentication);
        if (query != null || state != null || pinned != null || tag != null || color != null) {
            return ResponseEntity.ok(noteService.searchNotes(userId, query, state, pinned, tag, color));
        }
        return ResponseEntity.ok(noteService.getAllNotes(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable("id") int noteId, Authentication authentication) {
        int userId = getUserId(authentication);
        return ResponseEntity.ok(noteService.getNoteById(noteId, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(
            @PathVariable("id") int noteId,
            @RequestBody NoteRequest request,
            Authentication authentication) {
        int userId = getUserId(authentication);
        return ResponseEntity.ok(noteService.updateNote(noteId, request, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteNotePermanently(
            @PathVariable("id") int noteId,
            Authentication authentication) {
        int userId = getUserId(authentication);
        noteService.deleteNotePermanently(noteId, userId);
        return ResponseEntity.ok(Map.of("message", "Note deleted permanently"));
    }

    @PatchMapping("/{id}/pin")
    public ResponseEntity<NoteResponse> togglePin(@PathVariable("id") int noteId, Authentication authentication) {
        int userId = getUserId(authentication);
        return ResponseEntity.ok(noteService.togglePin(noteId, userId));
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<NoteResponse> toggleArchive(@PathVariable("id") int noteId, Authentication authentication) {
        int userId = getUserId(authentication);
        return ResponseEntity.ok(noteService.toggleArchive(noteId, userId));
    }

    @PatchMapping("/{id}/trash")
    public ResponseEntity<NoteResponse> toggleTrash(@PathVariable("id") int noteId, Authentication authentication) {
        int userId = getUserId(authentication);
        return ResponseEntity.ok(noteService.toggleTrash(noteId, userId));
    }

    @PostMapping("/{id}/tags")
    public ResponseEntity<NoteResponse> addTag(
            @PathVariable("id") int noteId,
            @RequestBody TagRequest request,
            Authentication authentication) {
        int userId = getUserId(authentication);
        return ResponseEntity.ok(noteService.addTagToNote(noteId, request.getName(), userId));
    }

    @DeleteMapping("/{id}/tags/{tagName}")
    public ResponseEntity<Map<String, String>> removeTag(
            @PathVariable("id") int noteId,
            @PathVariable("tagName") String tagName,
            Authentication authentication) {
        int userId = getUserId(authentication);
        labelService.removeTagFromNote(noteId, tagName, userId);
        return ResponseEntity.ok(Map.of("message", "Tag removed successfully"));
    }

    @GetMapping("/label/{labelName}")
    public ResponseEntity<List<NoteResponse>> getNotesByLabel(
            @PathVariable("labelName") String labelName,
            Authentication authentication) {
        int userId = getUserId(authentication);
        return ResponseEntity.ok(noteService.getNotesByLabel(userId, labelName));
    }

    @PostMapping("/{id}/reminders")
    public ResponseEntity<NoteResponse> addReminder(
            @PathVariable("id") int noteId,
            @RequestBody ReminderRequest request,
            Authentication authentication) {
        int userId = getUserId(authentication);
        return ResponseEntity.ok(noteService.addReminder(noteId, request.getReminder(), userId));
    }

    @DeleteMapping("/{id}/reminders")
    public ResponseEntity<NoteResponse> removeReminder(
            @PathVariable("id") int noteId,
            @RequestBody ReminderRequest request,
            Authentication authentication) {
        int userId = getUserId(authentication);
        return ResponseEntity.ok(noteService.removeReminder(noteId, request.getReminder(), userId));
    }

    @PostMapping("/{id}/checklist")
    public ResponseEntity<CheckListResponse> addCheckListItem(
            @PathVariable("id") int noteId,
            @Valid @RequestBody CheckListRequest request,
            Authentication authentication) {
        int userId = getUserId(authentication);
        CheckListResponse response = checkListService.addCheckListItem(noteId, request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}/checklist")
    public ResponseEntity<List<CheckListResponse>> getCheckList(
            @PathVariable("id") int noteId,
            Authentication authentication) {
        int userId = getUserId(authentication);
        return ResponseEntity.ok(checkListService.getCheckListItems(noteId, userId));
    }

    @PutMapping("/{id}/checklist/{itemId}")
    public ResponseEntity<CheckListResponse> updateCheckListItem(
            @PathVariable("id") int noteId,
            @PathVariable("itemId") int itemId,
            @RequestBody CheckListRequest request,
            Authentication authentication) {
        int userId = getUserId(authentication);
        return ResponseEntity.ok(checkListService.updateCheckListItem(noteId, itemId, request, userId));
    }

    @DeleteMapping("/{id}/checklist/{itemId}")
    public ResponseEntity<Map<String, String>> deleteCheckListItem(
            @PathVariable("id") int noteId,
            @PathVariable("itemId") int itemId,
            Authentication authentication) {
        int userId = getUserId(authentication);
        checkListService.deleteCheckListItem(noteId, itemId, userId);
        return ResponseEntity.ok(Map.of("message", "Checklist item deleted successfully"));
    }

    @PostMapping("/{id}/collaborators")
    public ResponseEntity<Map<String, String>> addCollaborator(
            @PathVariable("id") int noteId,
            @Valid @RequestBody CollaboratorRequest request,
            Authentication authentication) {
        int userId = getUserId(authentication);
        collaboratorService.addCollaborator(noteId, request, userId);
        return ResponseEntity.ok(Map.of("message", "Collaborator added successfully"));
    }

    @GetMapping("/{id}/collaborators")
    public ResponseEntity<List<CollaboratorResponse>> getCollaborators(
            @PathVariable("id") int noteId,
            Authentication authentication) {
        int userId = getUserId(authentication);
        return ResponseEntity.ok(collaboratorService.getCollaborators(noteId, userId));
    }

    @DeleteMapping("/{id}/collaborators/{collaboratorId}")
    public ResponseEntity<Map<String, String>> removeCollaborator(
            @PathVariable("id") int noteId,
            @PathVariable("collaboratorId") int collaboratorId,
            Authentication authentication) {
        int userId = getUserId(authentication);
        collaboratorService.removeCollaborator(noteId, collaboratorId, userId);
        return ResponseEntity.ok(Map.of("message", "Collaborator removed successfully"));
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BatchJobResponse> importNotes(
            @RequestParam("file") MultipartFile file,
            Authentication authentication) {
        int userId = getUserId(authentication);
        BatchJobResponse response = noteBatchService.importNotes(file, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportNotes(Authentication authentication) throws IOException {
        int userId = getUserId(authentication);
        byte[] excelBytes = noteExportService.exportUserNotesToExcel(userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "fundoo_notes_export.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelBytes);
    }
}

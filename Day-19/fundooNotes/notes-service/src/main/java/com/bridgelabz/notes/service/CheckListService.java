package com.bridgelabz.notes.service;

import com.bridgelabz.notes.dto.CheckListRequest;
import com.bridgelabz.notes.dto.CheckListResponse;
import com.bridgelabz.notes.entity.Note;
import com.bridgelabz.notes.entity.NoteCheckList;
import com.bridgelabz.notes.exception.CheckListItemNotFoundException;
import com.bridgelabz.notes.exception.NoteNotFoundException;
import com.bridgelabz.notes.exception.UnauthorizedActionException;
import com.bridgelabz.notes.repository.NoteCheckListRepository;
import com.bridgelabz.notes.repository.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckListService {

    private final NoteRepository noteRepository;
    private final NoteCheckListRepository checkListRepository;
    private final NoteService noteService;

    public CheckListService(
            NoteRepository noteRepository,
            NoteCheckListRepository checkListRepository,
            NoteService noteService) {
        this.noteRepository = noteRepository;
        this.checkListRepository = checkListRepository;
        this.noteService = noteService;
    }

    @Transactional
    public CheckListResponse addCheckListItem(int noteId, CheckListRequest request, int userId) {
        Note note = noteRepository.findAccessibleNote(noteId, userId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found or inaccessible: " + noteId));

        noteService.requireEditorAccess(note, userId);

        NoteCheckList item = new NoteCheckList(note, request.getItem());
        if (request.getIsDone() != null) {
            item.setDone(request.getIsDone());
        }
        note.addCheckList(item);
        NoteCheckList saved = checkListRepository.save(item);
        return CheckListResponse.fromEntity(saved);
    }

    public List<CheckListResponse> getCheckListItems(int noteId, int userId) {
        Note note = noteRepository.findAccessibleNote(noteId, userId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found or inaccessible: " + noteId));

        return checkListRepository.findByNote(note).stream()
                .map(CheckListResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public CheckListResponse updateCheckListItem(int noteId, int itemId, CheckListRequest request, int userId) {
        Note note = noteRepository.findAccessibleNote(noteId, userId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found or inaccessible: " + noteId));

        noteService.requireEditorAccess(note, userId);

        NoteCheckList item = checkListRepository.findById(itemId)
                .orElseThrow(() -> new CheckListItemNotFoundException("Checklist item not found with id: " + itemId));

        if (item.getNote().getNoteId() != note.getNoteId()) {
            throw new UnauthorizedActionException("Checklist item does not belong to this note");
        }

        if (request.getItem() != null) {
            item.setItem(request.getItem());
        }
        if (request.getIsDone() != null) {
            item.setDone(request.getIsDone());
        }

        NoteCheckList updated = checkListRepository.save(item);
        return CheckListResponse.fromEntity(updated);
    }

    @Transactional
    public void deleteCheckListItem(int noteId, int itemId, int userId) {
        Note note = noteRepository.findAccessibleNote(noteId, userId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found or inaccessible: " + noteId));

        noteService.requireEditorAccess(note, userId);

        NoteCheckList item = checkListRepository.findById(itemId)
                .orElseThrow(() -> new CheckListItemNotFoundException("Checklist item not found with id: " + itemId));

        if (item.getNote().getNoteId() != note.getNoteId()) {
            throw new UnauthorizedActionException("Checklist item does not belong to this note");
        }

        note.removeCheckList(item);
        checkListRepository.delete(item);
    }
}

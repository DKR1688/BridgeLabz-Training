package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.CheckListRequest;
import com.bridgelabz.fundoonotes.dto.CheckListResponse;
import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.entity.NoteCheckList;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exception.CheckListItemNotFoundException;
import com.bridgelabz.fundoonotes.exception.NoteNotFoundException;
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.repository.NoteCheckListRepository;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CheckListService {

    private final NoteCheckListRepository checkListRepository;
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public CheckListService(NoteCheckListRepository checkListRepository,
            NoteRepository noteRepository,
            UserRepository userRepository) {
        this.checkListRepository = checkListRepository;
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    private Note getAccessibleNoteOrThrow(int noteId, int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return noteRepository.findByNoteIdAndOwnerOrCollaboratorsContaining(noteId, user, user)
                .orElseThrow(() -> new NoteNotFoundException("Note not found"));
    }

    @Transactional(readOnly = true)
    public List<CheckListResponse> getCheckLists(int noteId, int userId) {
        getAccessibleNoteOrThrow(noteId, userId);
        return checkListRepository.findByNote_NoteIdAndIsDeletedFalse(noteId)
                .stream()
                .map(CheckListResponse::fromEntity)
                .toList();
    }

    public CheckListResponse addCheckListItem(int noteId, int userId, CheckListRequest request) {
        Note note = getAccessibleNoteOrThrow(noteId, userId);
        if ("TEXT".equalsIgnoreCase(note.getTypeOfNote()) || note.getTypeOfNote() == null) {
            note.setTypeOfNote("CHECKLIST");
            noteRepository.save(note);
        }

        String itemName = request.itemName();
        String status = request.resolvedStatus();
        NoteCheckList item = new NoteCheckList(itemName, status, note);
        if (request.isDeleted() != null) {
            item.setDeleted(request.isDeleted());
        }
        NoteCheckList saved = checkListRepository.save(item);
        return CheckListResponse.fromEntity(saved);
    }

    public CheckListResponse updateCheckListItem(int noteId, int itemId, int userId, CheckListRequest request) {
        getAccessibleNoteOrThrow(noteId, userId);
        NoteCheckList item = checkListRepository.findByIdAndNote_NoteId(itemId, noteId)
                .orElseThrow(() -> new CheckListItemNotFoundException("Checklist item not found"));

        if (request.itemName() != null && !request.itemName().isBlank()) {
            item.setItemName(request.itemName().trim());
        }
        if (request.status() != null) {
            item.setStatus(request.status());
        }
        if (request.isDeleted() != null) {
            item.setDeleted(request.isDeleted());
        }

        NoteCheckList saved = checkListRepository.save(item);
        return CheckListResponse.fromEntity(saved);
    }

    public boolean deleteCheckListItem(int noteId, int itemId, int userId) {
        getAccessibleNoteOrThrow(noteId, userId);
        NoteCheckList item = checkListRepository.findByIdAndNote_NoteId(itemId, noteId)
                .orElseThrow(() -> new CheckListItemNotFoundException("Checklist item not found"));
        item.setDeleted(true);
        checkListRepository.save(item);
        return true;
    }

    public List<CheckListResponse> bulkCompleteAll(int noteId, int userId) {
        getAccessibleNoteOrThrow(noteId, userId);
        List<NoteCheckList> items = checkListRepository.findByNote_NoteIdAndIsDeletedFalse(noteId);
        for (NoteCheckList item : items) {
            item.setStatus("DONE");
        }
        List<NoteCheckList> saved = checkListRepository.saveAll(items);
        return saved.stream().map(CheckListResponse::fromEntity).toList();
    }
}

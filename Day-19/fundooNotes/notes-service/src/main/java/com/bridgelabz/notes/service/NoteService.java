package com.bridgelabz.notes.service;

import com.bridgelabz.notes.dto.NoteRequest;
import com.bridgelabz.notes.dto.NoteResponse;
import com.bridgelabz.notes.dto.ReminderMessage;
import com.bridgelabz.notes.entity.Note;
import com.bridgelabz.notes.entity.NoteCheckList;
import com.bridgelabz.notes.entity.NoteCollaborator;
import com.bridgelabz.notes.entity.Tag;
import com.bridgelabz.notes.exception.InvalidNoteStateException;
import com.bridgelabz.notes.exception.NoteNotFoundException;
import com.bridgelabz.notes.exception.UnauthorizedActionException;
import com.bridgelabz.notes.repository.NoteRepository;
import com.bridgelabz.notes.repository.NoteSpecifications;
import com.bridgelabz.notes.repository.TagRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final TagRepository tagRepository;
    private final JmsProducerService jmsProducerService;
    private final RabbitProducerService rabbitProducerService;

    public NoteService(
            NoteRepository noteRepository,
            TagRepository tagRepository,
            JmsProducerService jmsProducerService,
            RabbitProducerService rabbitProducerService) {
        this.noteRepository = noteRepository;
        this.tagRepository = tagRepository;
        this.jmsProducerService = jmsProducerService;
        this.rabbitProducerService = rabbitProducerService;
    }

    public void requireEditorAccess(Note note, int userId) {
        if (note.getOwnerId() == userId) {
            return; // owner always has full access
        }
        if (note.getCollaborators() == null || note.getCollaborators().isEmpty()) {
            throw new NoteNotFoundException("Note not found or inaccessible: " + note.getNoteId());
        }
        NoteCollaborator collab = note.getCollaborators().stream()
                .filter(c -> c.getCollaboratorId() == userId)
                .findFirst()
                .orElseThrow(() -> new NoteNotFoundException("Note not found or inaccessible: " + note.getNoteId()));
        if (collab.getRole() != NoteCollaborator.Role.EDITOR) {
            throw new InvalidNoteStateException("Viewer role cannot modify this note");
        }
    }

    @Transactional
    public NoteResponse createNote(NoteRequest request, int userId) {
        Note note = new Note();
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        note.setOwnerId(userId);
        note.setColor(request.getColor());
        note.setTypeOfNote(request.getTypeOfNote() != null ? request.getTypeOfNote() : "TEXT");
        note.setImageUrl(request.getImageUrl());
        note.setLinkUrl(request.getLinkUrl());
        note.setPinned(Boolean.TRUE.equals(request.getIsPined()));

        if (Boolean.TRUE.equals(request.getIsArchived())) {
            note.setState(Note.NoteState.ARCHIVED);
            note.setPinned(false);
        } else if (Boolean.TRUE.equals(request.getIsDeleted())) {
            note.setState(Note.NoteState.TRASHED);
            note.setPinned(false);
        } else {
            note.setState(Note.NoteState.ACTIVE);
        }

        if (request.getTags() != null) {
            Set<Tag> tags = new HashSet<>();
            for (String tagName : request.getTags()) {
                if (tagName != null && !tagName.trim().isEmpty()) {
                    Tag tag = tagRepository.findByName(tagName.trim())
                            .orElseGet(() -> tagRepository.save(new Tag(tagName.trim())));
                    tags.add(tag);
                }
            }
            note.setTags(tags);
        }

        if (request.getChecklist() != null) {
            for (String itemText : request.getChecklist()) {
                if (itemText != null && !itemText.trim().isEmpty()) {
                    NoteCheckList item = new NoteCheckList(note, itemText.trim());
                    note.addCheckList(item);
                }
            }
        }

        Note saved = noteRepository.save(note);
        NoteResponse response = NoteResponse.fromEntity(saved);
        rabbitProducerService.publishNoteCreated(response);
        return response;
    }

    @Transactional(readOnly = true)
    public NoteResponse getNoteById(int noteId, int userId) {
        Note note = noteRepository.findAccessibleNoteWithDetails(noteId, userId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found or inaccessible: " + noteId));
        return NoteResponse.fromEntity(note);
    }

    @Transactional(readOnly = true)
    public List<NoteResponse> getAllNotes(int userId) {
        return noteRepository.findAllAccessibleNotes(userId).stream()
                .map(NoteResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NoteResponse> getNotesByState(int userId, Note.NoteState state) {
        return noteRepository.findByOwnerIdAndStateOrderByCreatedAtDesc(userId, state).stream()
                .map(NoteResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NoteResponse> searchNotes(int userId, String query, Note.NoteState state, Boolean pinned, String tag, String color) {
        Specification<Note> spec = NoteSpecifications.filterNotes(userId, query, state, pinned, tag, color);
        return noteRepository.findAll(spec).stream()
                .map(NoteResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NoteResponse> getNotesByLabel(int userId, String labelName) {
        return noteRepository.findByUserIdAndLabelName(userId, labelName).stream()
                .map(NoteResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public NoteResponse updateNote(int noteId, NoteRequest request, int userId) {
        Note note = noteRepository.findAccessibleNoteWithDetails(noteId, userId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found or inaccessible: " + noteId));

        requireEditorAccess(note, userId);

        if (request.getTitle() != null) {
            note.setTitle(request.getTitle());
        }
        if (request.getContent() != null) {
            note.setContent(request.getContent());
        }
        if (request.getColor() != null) {
            note.setColor(request.getColor());
        }
        if (request.getTypeOfNote() != null) {
            note.setTypeOfNote(request.getTypeOfNote());
        }
        if (request.getImageUrl() != null) {
            note.setImageUrl(request.getImageUrl());
        }
        if (request.getLinkUrl() != null) {
            note.setLinkUrl(request.getLinkUrl());
        }
        if (request.getIsPined() != null) {
            note.setPinned(request.getIsPined());
        }
        if (request.getIsArchived() != null) {
            note.setArchived(request.getIsArchived());
        }
        if (request.getIsDeleted() != null) {
            note.setDeleted(request.getIsDeleted());
        }

        if (request.getTags() != null) {
            Set<Tag> tags = new HashSet<>();
            for (String tagName : request.getTags()) {
                if (tagName != null && !tagName.trim().isEmpty()) {
                    Tag tag = tagRepository.findByName(tagName.trim())
                            .orElseGet(() -> tagRepository.save(new Tag(tagName.trim())));
                    tags.add(tag);
                }
            }
            note.setTags(tags);
        }

        Note updated = noteRepository.save(note);
        return NoteResponse.fromEntity(updated);
    }

    @Transactional
    public NoteResponse togglePin(int noteId, int userId) {
        Note note = noteRepository.findAccessibleNote(noteId, userId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found or inaccessible: " + noteId));

        requireEditorAccess(note, userId);

        if (note.getState() == Note.NoteState.TRASHED) {
            throw new InvalidNoteStateException("Cannot pin a trashed note");
        }

        note.setPinned(!note.isPinned());
        if (note.isPinned() && note.getState() == Note.NoteState.ARCHIVED) {
            note.setState(Note.NoteState.ACTIVE);
        }
        return NoteResponse.fromEntity(noteRepository.save(note));
    }

    @Transactional
    public NoteResponse toggleArchive(int noteId, int userId) {
        Note note = noteRepository.findAccessibleNote(noteId, userId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found or inaccessible: " + noteId));

        requireEditorAccess(note, userId);

        if (note.getState() == Note.NoteState.TRASHED) {
            throw new InvalidNoteStateException("Cannot archive a trashed note");
        }

        if (note.getState() == Note.NoteState.ARCHIVED) {
            note.setState(Note.NoteState.ACTIVE);
        } else {
            note.setState(Note.NoteState.ARCHIVED);
            note.setPinned(false);
        }
        return NoteResponse.fromEntity(noteRepository.save(note));
    }

    @Transactional
    public NoteResponse toggleTrash(int noteId, int userId) {
        Note note = noteRepository.findAccessibleNote(noteId, userId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found or inaccessible: " + noteId));

        requireEditorAccess(note, userId);

        if (note.getState() == Note.NoteState.TRASHED) {
            note.setState(Note.NoteState.ACTIVE);
        } else {
            note.setState(Note.NoteState.TRASHED);
            note.setPinned(false);
        }
        return NoteResponse.fromEntity(noteRepository.save(note));
    }

    @Transactional
    public void deleteNotePermanently(int noteId, int userId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found: " + noteId));

        if (note.getOwnerId() != userId) {
            throw new UnauthorizedActionException("Only note owner can permanently delete note");
        }

        noteRepository.delete(note);
        rabbitProducerService.publishNoteDeleted(noteId, userId);
        rabbitProducerService.broadcastNoteDeletedFanout(noteId);
    }

    @Transactional
    public NoteResponse addTagToNote(int noteId, String tagName, int userId) {
        Note note = noteRepository.findAccessibleNoteWithDetails(noteId, userId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found or inaccessible: " + noteId));

        requireEditorAccess(note, userId);

        Tag tag = tagRepository.findByName(tagName.trim())
                .orElseGet(() -> tagRepository.save(new Tag(tagName.trim())));
        note.addTag(tag);
        return NoteResponse.fromEntity(noteRepository.save(note));
    }

    @Transactional
    public NoteResponse addReminder(int noteId, LocalDateTime reminderTime, int userId) {
        Note note = noteRepository.findAccessibleNote(noteId, userId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found or inaccessible: " + noteId));

        requireEditorAccess(note, userId);

        note.addReminder(reminderTime);
        Note saved = noteRepository.save(note);

        ReminderMessage msg = new ReminderMessage(noteId, userId, reminderTime);
        jmsProducerService.sendReminderMessage(msg);

        return NoteResponse.fromEntity(saved);
    }

    @Transactional
    public NoteResponse removeReminder(int noteId, LocalDateTime reminderTime, int userId) {
        Note note = noteRepository.findAccessibleNote(noteId, userId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found or inaccessible: " + noteId));

        requireEditorAccess(note, userId);

        note.removeReminder(reminderTime);
        return NoteResponse.fromEntity(noteRepository.save(note));
    }
}

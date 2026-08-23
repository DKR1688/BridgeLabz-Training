package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.ReminderMessage;
import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.entity.Tag;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exception.InvalidNoteStateException;
import com.bridgelabz.fundoonotes.exception.NoteNotFoundException;
import com.bridgelabz.fundoonotes.exception.UnauthorizedActionException;
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.NoteSpecifications;
import com.bridgelabz.fundoonotes.repository.TagRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final JmsProducerService jmsProducerService;
    private final RabbitProducerService rabbitProducerService;

    public NoteService(NoteRepository noteRepository,
            UserRepository userRepository,
            TagRepository tagRepository,
            @Autowired(required = false) JmsProducerService jmsProducerService,
            @Autowired(required = false) RabbitProducerService rabbitProducerService) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.jmsProducerService = jmsProducerService;
        this.rabbitProducerService = rabbitProducerService;
    }

    private Note initializeNote(Note note) {
        if (note != null) {
            if (note.getTags() != null) {
                note.getTags().size();
            }
            if (note.getReminders() != null) {
                note.getReminders().size();
            }
            if (note.getCollaborators() != null) {
                note.getCollaborators().size();
            }
            if (note.getCheckLists() != null) {
                note.getCheckLists().size();
            }
        }
        return note;
    }

    private List<Note> initializeNotes(List<Note> notes) {
        if (notes != null) {
            for (Note n : notes) {
                initializeNote(n);
            }
        }
        return notes;
    }

    public Note createNote(int userId, String title, String content) {
        return createNote(userId, title, content, Collections.emptySet());
    }

    public Note createNote(int userId, String title, String content, Set<String> tagNames) {
        return createNote(userId, title, content, null, null, null, null, tagNames, null);
    }

    public Note createNote(int userId,
            String title,
            String content,
            String color,
            String typeOfNote,
            String imageUrl,
            String linkUrl,
            Set<String> tagNames,
            List<LocalDateTime> reminders) {
        User owner = getOwnerOrThrow(userId);

        Note note = new Note();
        note.setTitle(title != null ? title.trim() : "");
        note.setContent(content != null ? content : "");
        note.setOwner(owner);
        note.setState(Note.NoteState.ACTIVE);
        note.setPinned(false);
        note.setColor(color);
        note.setTypeOfNote(typeOfNote != null ? typeOfNote : "TEXT");
        note.setImageUrl(imageUrl);
        note.setLinkUrl(linkUrl);

        if (tagNames != null && !tagNames.isEmpty()) {
            for (String rawTagName : tagNames) {
                if (rawTagName != null && !rawTagName.trim().isEmpty()) {
                    String tagName = rawTagName.trim();
                    Tag tag = tagRepository.findByNameAndOwner(tagName, owner)
                            .orElseGet(() -> tagRepository.save(new Tag(tagName, owner)));
                    note.addTag(tag);
                }
            }
        }

        if (reminders != null && !reminders.isEmpty()) {
            for (LocalDateTime r : reminders) {
                note.addReminder(r);
            }
        }

        Note savedNote = noteRepository.save(note);
        initializeNote(savedNote);

        // If reminder attached, dispatch async JMS event
        if (reminders != null && !reminders.isEmpty() && jmsProducerService != null) {
            for (LocalDateTime r : reminders) {
                jmsProducerService.sendReminderMessage(
                        new ReminderMessage(savedNote.getNoteId(), userId, r.toString(), savedNote.getTitle()));
            }
        }

        return savedNote;
    }

    @Transactional(readOnly = true)
    public List<Note> findByOwner(int userId) {
        User owner = getOwnerOrThrow(userId);
        return initializeNotes(noteRepository.findByOwnerWithTags(owner));
    }

    @Transactional(readOnly = true)
    public List<Note> findActiveByOwner(int userId) {
        User owner = getOwnerOrThrow(userId);
        return initializeNotes(noteRepository.findByOwnerAndState(owner, Note.NoteState.ACTIVE));
    }

    @Transactional(readOnly = true)
    public List<Note> findByOwnerAndState(int userId, Note.NoteState state) {
        User owner = getOwnerOrThrow(userId);
        return initializeNotes(noteRepository.findByOwnerAndState(owner, state));
    }

    @Transactional(readOnly = true)
    public List<Note> findPinnedByOwner(int userId) {
        User owner = getOwnerOrThrow(userId);
        return initializeNotes(noteRepository.findByOwnerAndPinnedTrueAndStateNot(owner, Note.NoteState.TRASHED));
    }

    @Transactional(readOnly = true)
    public List<Note> findByOwnerAndTag(int userId, String tagName) {
        User owner = getOwnerOrThrow(userId);
        return initializeNotes(noteRepository.findByOwnerAndTagsName(owner, tagName));
    }

    @Transactional(readOnly = true)
    public List<Note> search(int userId, String titleText, Note.NoteState state, String tagName) {
        return search(userId, titleText, state, tagName, null);
    }

    @Transactional(readOnly = true)
    public List<Note> search(int userId, String titleText, Note.NoteState state, String tagName, Boolean pinned) {
        User owner = getOwnerOrThrow(userId);
        Specification<Note> spec = NoteSpecifications.search(owner, titleText, state, tagName, pinned);
        return initializeNotes(noteRepository.findAll(spec));
    }

    @Transactional(readOnly = true)
    public List<Note> search(int userId, String titleText, String state, String tagName) {
        User owner = getOwnerOrThrow(userId);
        Specification<Note> spec = NoteSpecifications.search(owner, titleText, state, tagName);
        return initializeNotes(noteRepository.findAll(spec));
    }

    @Transactional(readOnly = true)
    public Optional<Note> getNoteById(int noteId, int userId) {
        User user = getOwnerOrThrow(userId);
        return noteRepository.findByNoteIdAndOwnerOrCollaboratorsContaining(noteId, user, user)
                .map(this::initializeNote);
    }

    public Optional<Note> updateNote(int noteId, int userId, String title, String content) {
        return updateNote(noteId, userId, title, content, null);
    }

    public Optional<Note> updateNote(int noteId, int userId, String title, String content, Set<String> tagNames) {
        return updateNote(noteId, userId, title, content, null, null, null, null, tagNames, null);
    }

    public Optional<Note> updateNote(int noteId,
            int userId,
            String title,
            String content,
            String color,
            String typeOfNote,
            String imageUrl,
            String linkUrl,
            Set<String> tagNames,
            List<LocalDateTime> reminders) {
        User user = getOwnerOrThrow(userId);

        return noteRepository.findByNoteIdAndOwnerOrCollaboratorsContaining(noteId, user, user)
                .map(note -> {
                    if (title != null) {
                        note.setTitle(title.trim());
                    }
                    if (content != null) {
                        note.setContent(content);
                    }
                    if (color != null) {
                        note.setColor(color);
                    }
                    if (typeOfNote != null) {
                        note.setTypeOfNote(typeOfNote);
                    }
                    if (imageUrl != null) {
                        note.setImageUrl(imageUrl);
                    }
                    if (linkUrl != null) {
                        note.setLinkUrl(linkUrl);
                    }
                    if (tagNames != null) {
                        note.getTags().clear();
                        User tagOwner = note.getOwner();
                        for (String rawTagName : tagNames) {
                            if (rawTagName != null && !rawTagName.trim().isEmpty()) {
                                String tagName = rawTagName.trim();
                                Tag tag = tagRepository.findByNameAndOwner(tagName, tagOwner)
                                        .orElseGet(() -> tagRepository.save(new Tag(tagName, tagOwner)));
                                note.addTag(tag);
                            }
                        }
                    }
                    if (reminders != null) {
                        note.setReminders(reminders);
                    }
                    Note saved = noteRepository.save(note);
                    return initializeNote(saved);
                });
    }

    public boolean deleteNote(int noteId, int requestingUserId) {
        User user = getOwnerOrThrow(requestingUserId);

        // Check if note exists
        Optional<Note> accessibleNote = noteRepository.findByNoteIdAndOwnerOrCollaboratorsContaining(noteId, user,
                user);
        if (accessibleNote.isEmpty()) {
            return false;
        }

        Note note = accessibleNote.get();
        // ONLY the owner can delete the note! Collaborators cannot delete.
        if (note.getOwner().getUserId() != requestingUserId) {
            throw new UnauthorizedActionException("Collaborators cannot delete notes");
        }

        noteRepository.delete(note);

        // Publish RabbitMQ Note-Deleted event
        if (rabbitProducerService != null) {
            rabbitProducerService.sendNoteDeletedEvent(noteId, requestingUserId);
            rabbitProducerService.broadcastNoteDeletedFanout(noteId, requestingUserId);
        }

        return true;
    }

    public boolean deleteForeverNote(int noteId, int requestingUserId) {
        return deleteNote(noteId, requestingUserId);
    }

    public Note archiveNote(int noteId, int userId) {
        Note note = getOwnedNoteOrThrow(noteId, userId);
        note.setState(Note.NoteState.ARCHIVED);
        note.setPinned(false);
        return initializeNote(noteRepository.save(note));
    }

    public Note trashNote(int noteId, int userId) {
        Note note = getOwnedNoteOrThrow(noteId, userId);
        note.setState(Note.NoteState.TRASHED);
        note.setPinned(false);
        return initializeNote(noteRepository.save(note));
    }

    public Note restoreNote(int noteId, int userId) {
        Note note = getOwnedNoteOrThrow(noteId, userId);
        note.setState(Note.NoteState.ACTIVE);
        return initializeNote(noteRepository.save(note));
    }

    public Note pinNote(int noteId, int userId) {
        Note note = getOwnedNoteOrThrow(noteId, userId);
        if (note.getState() == Note.NoteState.TRASHED) {
            throw new InvalidNoteStateException("Cannot pin a note that is in Trash");
        }
        note.setPinned(true);
        return initializeNote(noteRepository.save(note));
    }

    public Note unpinNote(int noteId, int userId) {
        Note note = getOwnedNoteOrThrow(noteId, userId);
        note.setPinned(false);
        return initializeNote(noteRepository.save(note));
    }

    public Note togglePinNote(int noteId, int userId) {
        Note note = getOwnedNoteOrThrow(noteId, userId);
        if (note.getState() == Note.NoteState.TRASHED) {
            throw new InvalidNoteStateException("Cannot pin a note that is in Trash");
        }
        note.setPinned(!note.isPinned());
        return initializeNote(noteRepository.save(note));
    }

    public Note addTagToNote(int noteId, int userId, String tagName) {
        Note note = getOwnedNoteOrThrow(noteId, userId);
        User owner = note.getOwner();
        String cleanName = tagName.trim();
        Tag tag = tagRepository.findByNameAndOwner(cleanName, owner)
                .orElseGet(() -> tagRepository.save(new Tag(cleanName, owner)));
        note.addTag(tag);
        return initializeNote(noteRepository.save(note));
    }

    public Note addLabelToNote(int noteId, int userId, int labelId) {
        Note note = getOwnedNoteOrThrow(noteId, userId);
        User owner = note.getOwner();
        Tag tag = tagRepository.findByTagIdAndOwner(labelId, owner)
                .orElseThrow(() -> new IllegalArgumentException("Label not found"));
        note.addTag(tag);
        return initializeNote(noteRepository.save(note));
    }

    public Note removeLabelFromNote(int noteId, int userId, int labelId) {
        Note note = getOwnedNoteOrThrow(noteId, userId);
        User owner = note.getOwner();
        Tag tag = tagRepository.findByTagIdAndOwner(labelId, owner)
                .orElseThrow(() -> new IllegalArgumentException("Label not found"));
        note.removeTag(tag);
        return initializeNote(noteRepository.save(note));
    }

    public Note addUpdateReminder(int noteId, int userId, LocalDateTime reminderTime) {
        Note note = getOwnedNoteOrThrow(noteId, userId);
        note.addReminder(reminderTime);
        Note saved = noteRepository.save(note);
        initializeNote(saved);

        // Asynchronous JMS dispatch
        if (jmsProducerService != null) {
            jmsProducerService.sendReminderMessage(
                    new ReminderMessage(noteId, userId, reminderTime.toString(), note.getTitle()));
        }

        return saved;
    }

    public Note removeReminder(int noteId, int userId, LocalDateTime reminderTime) {
        Note note = getOwnedNoteOrThrow(noteId, userId);
        if (reminderTime != null) {
            note.removeReminder(reminderTime);
        } else {
            note.getReminders().clear();
        }
        return initializeNote(noteRepository.save(note));
    }

    @Transactional(readOnly = true)
    public List<Note> getReminderNotesList(int userId) {
        User owner = getOwnerOrThrow(userId);
        return initializeNotes(noteRepository.findNotesWithRemindersByOwner(owner));
    }

    @Transactional(readOnly = true)
    public List<Note> getArchiveNotesList(int userId) {
        return findByOwnerAndState(userId, Note.NoteState.ARCHIVED);
    }

    @Transactional(readOnly = true)
    public List<Note> getTrashNotesList(int userId) {
        return findByOwnerAndState(userId, Note.NoteState.TRASHED);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithNotesEagerly(int userId) {
        return userRepository.findByIdWithNotes(userId);
    }

    private Note getOwnedNoteOrThrow(int noteId, int userId) {
        User owner = getOwnerOrThrow(userId);
        return noteRepository.findByNoteIdAndOwner(noteId, owner)
                .orElseThrow(() -> new NoteNotFoundException("Note not found"));
    }

    private User getOwnerOrThrow(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}

package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.entity.Tag;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.NoteSpecifications;
import com.bridgelabz.fundoonotes.repository.TagRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    public NoteService(NoteRepository noteRepository,
            UserRepository userRepository,
            TagRepository tagRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
    }

    public Note createNote(int userId, String title, String content) {
        return createNote(userId, title, content, Collections.emptySet());
    }

    public Note createNote(int userId, String title, String content, Set<String> tagNames) {
        User owner = getOwnerOrThrow(userId);

        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setOwner(owner);
        note.setState(Note.NoteState.ACTIVE);
        note.setPinned(false);

        if (tagNames != null && !tagNames.isEmpty()) {
            for (String rawTagName : tagNames) {
                if (rawTagName != null && !rawTagName.trim().isEmpty()) {
                    String tagName = rawTagName.trim();
                    Tag tag = tagRepository.findByName(tagName)
                            .orElseGet(() -> tagRepository.save(new Tag(tagName)));
                    note.addTag(tag);
                }
            }
        }

        return noteRepository.save(note);
    }

    @Transactional(readOnly = true)
    public List<Note> findByOwner(int userId) {
        User owner = getOwnerOrThrow(userId);
        return noteRepository.findByOwnerWithTags(owner);
    }

    @Transactional(readOnly = true)
    public List<Note> findActiveByOwner(int userId) {
        User owner = getOwnerOrThrow(userId);
        return noteRepository.findByOwnerAndState(owner, Note.NoteState.ACTIVE);
    }

    @Transactional(readOnly = true)
    public List<Note> findByOwnerAndState(int userId, Note.NoteState state) {
        User owner = getOwnerOrThrow(userId);
        return noteRepository.findByOwnerAndState(owner, state);
    }

    @Transactional(readOnly = true)
    public List<Note> findPinnedByOwner(int userId) {
        User owner = getOwnerOrThrow(userId);
        return noteRepository.findByOwnerAndPinnedTrueAndStateNot(owner, Note.NoteState.TRASHED);
    }

    @Transactional(readOnly = true)
    public List<Note> findByOwnerAndTag(int userId, String tagName) {
        User owner = getOwnerOrThrow(userId);
        return noteRepository.findByOwnerAndTagsName(owner, tagName);
    }

    @Transactional(readOnly = true)
    public List<Note> search(int userId, String titleText, Note.NoteState state, String tagName) {
        return search(userId, titleText, state, tagName, null);
    }

    @Transactional(readOnly = true)
    public List<Note> search(int userId, String titleText, Note.NoteState state, String tagName, Boolean pinned) {
        User owner = getOwnerOrThrow(userId);
        Specification<Note> spec = NoteSpecifications.search(owner, titleText, state, tagName, pinned);
        return noteRepository.findAll(spec);
    }

    @Transactional(readOnly = true)
    public Optional<Note> getNoteById(int noteId, int userId) {
        User owner = getOwnerOrThrow(userId);
        return noteRepository.findByNoteIdAndOwnerWithTags(noteId, owner);
    }

    public Optional<Note> updateNote(int noteId, int userId, String title, String content) {
        return updateNote(noteId, userId, title, content, null);
    }

    public Optional<Note> updateNote(int noteId, int userId, String title, String content, Set<String> tagNames) {
        User owner = getOwnerOrThrow(userId);

        return noteRepository.findByNoteIdAndOwner(noteId, owner)
                .map(note -> {
                    if (title != null) {
                        note.setTitle(title);
                    }
                    if (content != null) {
                        note.setContent(content);
                    }
                    if (tagNames != null) {
                        note.getTags().clear();
                        for (String rawTagName : tagNames) {
                            if (rawTagName != null && !rawTagName.trim().isEmpty()) {
                                String tagName = rawTagName.trim();
                                Tag tag = tagRepository.findByName(tagName)
                                        .orElseGet(() -> tagRepository.save(new Tag(tagName)));
                                note.addTag(tag);
                            }
                        }
                    }
                    return noteRepository.save(note);
                });
    }

    public boolean deleteNote(int noteId, int requestingUserId) {
        User owner = getOwnerOrThrow(requestingUserId);

        return noteRepository.findByNoteIdAndOwner(noteId, owner)
                .map(note -> {
                    noteRepository.delete(note);
                    return true;
                })
                .orElse(false);
    }

    public Note archiveNote(int noteId, int userId) {
        Note note = getOwnedNoteOrThrow(noteId, userId);
        note.setState(Note.NoteState.ARCHIVED);
        note.setPinned(false);
        return noteRepository.save(note);
    }

    public Note trashNote(int noteId, int userId) {
        Note note = getOwnedNoteOrThrow(noteId, userId);
        note.setState(Note.NoteState.TRASHED);
        note.setPinned(false);
        return noteRepository.save(note);
    }

    /**
     * Problem 2 State Transition Rule:
     * Restoring a note (from either ARCHIVED or TRASHED state) transitions the note
     * back to ACTIVE state. The pinned flag remains false upon restore to ensure
     * the note returns to the default active unpinned list, requiring an explicit
     * user action to re-pin.
     */
    public Note restoreNote(int noteId, int userId) {
        Note note = getOwnedNoteOrThrow(noteId, userId);
        note.setState(Note.NoteState.ACTIVE);
        return noteRepository.save(note);
    }

    public Note pinNote(int noteId, int userId) {
        Note note = getOwnedNoteOrThrow(noteId, userId);
        if (note.getState() == Note.NoteState.TRASHED) {
            throw new IllegalStateException("Cannot pin a note that is in Trash");
        }
        note.setPinned(true);
        return noteRepository.save(note);
    }

    public Note unpinNote(int noteId, int userId) {
        Note note = getOwnedNoteOrThrow(noteId, userId);
        note.setPinned(false);
        return noteRepository.save(note);
    }

    public Note addTagToNote(int noteId, int userId, String tagName) {
        Note note = getOwnedNoteOrThrow(noteId, userId);
        String cleanName = tagName.trim();
        Tag tag = tagRepository.findByName(cleanName)
                .orElseGet(() -> tagRepository.save(new Tag(cleanName)));
        note.addTag(tag);
        return noteRepository.save(note);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithNotesEagerly(int userId) {
        return userRepository.findByIdWithNotes(userId);
    }

    private Note getOwnedNoteOrThrow(int noteId, int userId) {
        User owner = getOwnerOrThrow(userId);
        return noteRepository.findByNoteIdAndOwner(noteId, owner)
                .orElseThrow(() -> new IllegalArgumentException("Note not found"));
    }

    private User getOwnerOrThrow(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}

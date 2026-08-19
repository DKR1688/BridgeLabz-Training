package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.entity.Tag;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.TagRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
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
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setOwner(owner);

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
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return noteRepository.findByOwnerWithTags(owner);
    }

    @Transactional(readOnly = true)
    public Optional<Note> getNoteById(int noteId, int userId) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return noteRepository.findByNoteIdAndOwnerWithTags(noteId, owner);
    }

    public Optional<Note> updateNote(int noteId, int userId, String title, String content) {
        return updateNote(noteId, userId, title, content, null);
    }

    public Optional<Note> updateNote(int noteId, int userId, String title, String content, Set<String> tagNames) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

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
        User owner = userRepository.findById(requestingUserId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return noteRepository.findByNoteIdAndOwner(noteId, owner)
                .map(note -> {
                    noteRepository.delete(note);
                    return true;
                })
                .orElse(false);
    }

    public Optional<Note> addTagToNote(int noteId, int userId, String tagName) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return noteRepository.findByNoteIdAndOwner(noteId, owner)
                .map(note -> {
                    String cleanName = tagName.trim();
                    Tag tag = tagRepository.findByName(cleanName)
                            .orElseGet(() -> tagRepository.save(new Tag(cleanName)));
                    note.addTag(tag);
                    return noteRepository.save(note);
                });
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithNotesEagerly(int userId) {
        return userRepository.findByIdWithNotes(userId);
    }
}

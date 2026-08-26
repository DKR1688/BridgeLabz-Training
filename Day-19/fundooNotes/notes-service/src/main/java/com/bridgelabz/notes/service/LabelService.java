package com.bridgelabz.notes.service;

import com.bridgelabz.notes.dto.LabelRequest;
import com.bridgelabz.notes.dto.LabelResponse;
import com.bridgelabz.notes.entity.Note;
import com.bridgelabz.notes.entity.Tag;
import com.bridgelabz.notes.exception.LabelNotFoundException;
import com.bridgelabz.notes.exception.NoteNotFoundException;
import com.bridgelabz.notes.repository.NoteRepository;
import com.bridgelabz.notes.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LabelService {

    private final TagRepository tagRepository;
    private final NoteRepository noteRepository;

    public LabelService(TagRepository tagRepository, NoteRepository noteRepository) {
        this.tagRepository = tagRepository;
        this.noteRepository = noteRepository;
    }

    @Transactional
    public LabelResponse createLabel(LabelRequest request) {
        String name = request.getLabel() != null ? request.getLabel().trim() : request.getName().trim();
        Tag tag = tagRepository.findByName(name)
                .orElseGet(() -> tagRepository.save(new Tag(name)));
        return LabelResponse.fromEntity(tag);
    }

    public List<LabelResponse> getAllLabels() {
        return tagRepository.findAll().stream()
                .map(LabelResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public LabelResponse getLabelById(int labelId) {
        Tag tag = tagRepository.findById(labelId)
                .orElseThrow(() -> new LabelNotFoundException("Label not found with id: " + labelId));
        return LabelResponse.fromEntity(tag);
    }

    @Transactional
    public LabelResponse updateLabel(int labelId, LabelRequest request) {
        Tag tag = tagRepository.findById(labelId)
                .orElseThrow(() -> new LabelNotFoundException("Label not found with id: " + labelId));
        String newName = request.getLabel() != null ? request.getLabel().trim() : request.getName().trim();
        tag.setName(newName);
        Tag updated = tagRepository.save(tag);
        return LabelResponse.fromEntity(updated);
    }

    @Transactional
    public void deleteLabel(int labelId) {
        Tag tag = tagRepository.findById(labelId)
                .orElseThrow(() -> new LabelNotFoundException("Label not found with id: " + labelId));
        tagRepository.delete(tag);
    }

    @Transactional
    public void removeTagFromNote(int noteId, String tagName, int userId) {
        Note note = noteRepository.findWithTagsByNoteId(noteId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + noteId));
        note.getTags().removeIf(tag -> tag.getName().equalsIgnoreCase(tagName.trim()));
        noteRepository.save(note);
    }
}

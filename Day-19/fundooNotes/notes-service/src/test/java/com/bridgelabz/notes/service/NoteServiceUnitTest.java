package com.bridgelabz.notes.service;

import com.bridgelabz.notes.dto.NoteRequest;
import com.bridgelabz.notes.dto.NoteResponse;
import com.bridgelabz.notes.entity.Note;
import com.bridgelabz.notes.entity.NoteCollaborator;
import com.bridgelabz.notes.entity.Tag;
import com.bridgelabz.notes.exception.InvalidNoteStateException;
import com.bridgelabz.notes.exception.NoteNotFoundException;
import com.bridgelabz.notes.exception.UnauthorizedActionException;
import com.bridgelabz.notes.repository.NoteRepository;
import com.bridgelabz.notes.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Level 1: Business Logic Unit Tests for NoteService.
 * Completely isolated from Spring Context, HTTP, and database.
 */
@ExtendWith(MockitoExtension.class)
class NoteServiceUnitTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private JmsProducerService jmsProducerService;

    @Mock
    private RabbitProducerService rabbitProducerService;

    @InjectMocks
    private NoteService noteService;

    private Note sampleNote;

    @BeforeEach
    void setUp() {
        sampleNote = new Note();
        sampleNote.setNoteId(10);
        sampleNote.setTitle("Sample Title");
        sampleNote.setContent("Sample Content");
        sampleNote.setOwnerId(1);
        sampleNote.setState(Note.NoteState.ACTIVE);
        sampleNote.setPinned(false);
    }

    @Test
    @DisplayName("Level 1: Pinning a trashed note must throw InvalidNoteStateException")
    void pinNote_onTrashedNote_throwsException() {
        sampleNote.setState(Note.NoteState.TRASHED);
        when(noteRepository.findAccessibleNote(10, 1)).thenReturn(Optional.of(sampleNote));

        assertThrows(InvalidNoteStateException.class, () -> noteService.togglePin(10, 1));
        verify(noteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Level 1: Archiving a pinned note must automatically unpin it")
    void archiveNote_automaticallyUnpinsNote() {
        sampleNote.setPinned(true);
        when(noteRepository.findAccessibleNote(10, 1)).thenReturn(Optional.of(sampleNote));
        when(noteRepository.save(any(Note.class))).thenAnswer(invocation -> invocation.getArgument(0));

        NoteResponse result = noteService.toggleArchive(10, 1);

        assertTrue(result.getIsArchived());
        assertFalse(result.getIsPined());
        verify(noteRepository).save(sampleNote);
    }

    @Test
    @DisplayName("Level 1: Trashing a note must set state to TRASHED and unpin it")
    void toggleTrash_onActiveNote_trashesAndUnpins() {
        sampleNote.setPinned(true);
        when(noteRepository.findAccessibleNote(10, 1)).thenReturn(Optional.of(sampleNote));
        when(noteRepository.save(any(Note.class))).thenAnswer(invocation -> invocation.getArgument(0));

        NoteResponse result = noteService.toggleTrash(10, 1);

        assertTrue(result.getIsDeleted());
        assertFalse(result.getIsPined());
        verify(noteRepository).save(sampleNote);
    }

    @Test
    @DisplayName("Level 1 Use Case 21: Viewer role collaborator attempting PUT/update throws InvalidNoteStateException")
    void updateNote_byViewerCollaborator_throwsInvalidNoteStateException() {
        NoteCollaborator viewer = new NoteCollaborator(sampleNote, 2, NoteCollaborator.Role.VIEWER);
        sampleNote.getCollaborators().add(viewer);

        when(noteRepository.findAccessibleNoteWithDetails(10, 2)).thenReturn(Optional.of(sampleNote));

        NoteRequest updateRequest = new NoteRequest();
        updateRequest.setTitle("Hacked Title");

        InvalidNoteStateException ex = assertThrows(InvalidNoteStateException.class,
                () -> noteService.updateNote(10, updateRequest, 2));

        assertTrue(ex.getMessage().contains("Viewer role cannot modify this note"));
        verify(noteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Level 1 Use Case 21: Editor role collaborator can modify note content")
    void updateNote_byEditorCollaborator_succeeds() {
        NoteCollaborator editor = new NoteCollaborator(sampleNote, 2, NoteCollaborator.Role.EDITOR);
        sampleNote.getCollaborators().add(editor);

        when(noteRepository.findAccessibleNoteWithDetails(10, 2)).thenReturn(Optional.of(sampleNote));
        when(noteRepository.save(any(Note.class))).thenAnswer(invocation -> invocation.getArgument(0));

        NoteRequest updateRequest = new NoteRequest();
        updateRequest.setTitle("Updated by Editor");

        NoteResponse result = noteService.updateNote(10, updateRequest, 2);

        assertEquals("Updated by Editor", result.getTitle());
        verify(noteRepository).save(sampleNote);
    }

    @Test
    @DisplayName("Level 1 Use Case 21: Editor role collaborator CANNOT delete note permanently (owner-only)")
    void deleteNote_byEditorCollaborator_throwsUnauthorizedActionException() {
        NoteCollaborator editor = new NoteCollaborator(sampleNote, 2, NoteCollaborator.Role.EDITOR);
        sampleNote.getCollaborators().add(editor);

        when(noteRepository.findById(10)).thenReturn(Optional.of(sampleNote));

        assertThrows(UnauthorizedActionException.class, () -> noteService.deleteNotePermanently(10, 2));
        verify(noteRepository, never()).delete(any(Note.class));
    }

    @Test
    @DisplayName("Level 1: Owner can permanently delete their own note")
    void deleteNote_byOwner_succeeds() {
        when(noteRepository.findById(10)).thenReturn(Optional.of(sampleNote));

        noteService.deleteNotePermanently(10, 1);

        verify(noteRepository).delete(sampleNote);
        verify(rabbitProducerService).publishNoteDeleted(10, 1);
    }

    @Test
    @DisplayName("Level 1: Adding a tag by Editor collaborator succeeds")
    void addTagToNote_byEditor_succeeds() {
        NoteCollaborator editor = new NoteCollaborator(sampleNote, 2, NoteCollaborator.Role.EDITOR);
        sampleNote.getCollaborators().add(editor);

        when(noteRepository.findAccessibleNoteWithDetails(10, 2)).thenReturn(Optional.of(sampleNote));
        when(tagRepository.findByName("urgent")).thenReturn(Optional.of(new Tag("urgent")));
        when(noteRepository.save(any(Note.class))).thenAnswer(invocation -> invocation.getArgument(0));

        NoteResponse response = noteService.addTagToNote(10, "urgent", 2);

        assertNotNull(response);
        verify(noteRepository).save(sampleNote);
    }

    @Test
    @DisplayName("Level 1: Non-collaborator user gets NoteNotFoundException (404/Day 14 pattern)")
    void accessNote_byUnrelatedUser_throwsNoteNotFound() {
        when(noteRepository.findAccessibleNote(10, 99)).thenReturn(Optional.empty());

        assertThrows(NoteNotFoundException.class, () -> noteService.togglePin(10, 99));
    }
}

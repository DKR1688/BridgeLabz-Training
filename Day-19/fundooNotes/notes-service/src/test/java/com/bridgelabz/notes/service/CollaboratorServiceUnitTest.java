package com.bridgelabz.notes.service;

import com.bridgelabz.notes.client.UserServiceClient;
import com.bridgelabz.notes.dto.CollaboratorRequest;
import com.bridgelabz.notes.dto.CollaboratorResponse;
import com.bridgelabz.notes.entity.Note;
import com.bridgelabz.notes.entity.NoteCollaborator;
import com.bridgelabz.notes.exception.NoteNotFoundException;
import com.bridgelabz.notes.exception.UnauthorizedActionException;
import com.bridgelabz.notes.exception.UserNotFoundException;
import com.bridgelabz.notes.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Level 1: Business Logic Unit Tests for CollaboratorService (Use Cases 13 & 21).
 */
@ExtendWith(MockitoExtension.class)
class CollaboratorServiceUnitTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private UserServiceClient userServiceClient;

    @Mock
    private RabbitProducerService rabbitProducerService;

    @InjectMocks
    private CollaboratorService collaboratorService;

    private Note sampleNote;

    @BeforeEach
    void setUp() {
        sampleNote = new Note();
        sampleNote.setNoteId(10);
        sampleNote.setTitle("Sample Note");
        sampleNote.setOwnerId(1);
    }

    @Test
    @DisplayName("Level 1: Note owner can add collaborator with VIEWER role")
    void addCollaborator_asOwner_withViewerRole_succeeds() {
        when(noteRepository.findById(10)).thenReturn(Optional.of(sampleNote));
        when(userServiceClient.userExists(2)).thenReturn(true);

        CollaboratorRequest request = new CollaboratorRequest(2, NoteCollaborator.Role.VIEWER);
        collaboratorService.addCollaborator(10, request, 1);

        verify(noteRepository).save(sampleNote);
        verify(rabbitProducerService).publishNoteShared(10, 1, 2);

        Optional<NoteCollaborator> collab = sampleNote.getCollaborator(2);
        assertTrue(collab.isPresent());
        assertEquals(NoteCollaborator.Role.VIEWER, collab.get().getRole());
    }

    @Test
    @DisplayName("Level 1: Note owner can add collaborator with EDITOR role")
    void addCollaborator_asOwner_withEditorRole_succeeds() {
        when(noteRepository.findById(10)).thenReturn(Optional.of(sampleNote));
        when(userServiceClient.userExists(2)).thenReturn(true);

        CollaboratorRequest request = new CollaboratorRequest(2, NoteCollaborator.Role.EDITOR);
        collaboratorService.addCollaborator(10, request, 1);

        verify(noteRepository).save(sampleNote);
        Optional<NoteCollaborator> collab = sampleNote.getCollaborator(2);
        assertTrue(collab.isPresent());
        assertEquals(NoteCollaborator.Role.EDITOR, collab.get().getRole());
    }

    @Test
    @DisplayName("Level 1: Changing collaborator role from VIEWER to EDITOR updates role immediately")
    void addCollaborator_updatesExistingRole_immediately() {
        sampleNote.addCollaborator(2, NoteCollaborator.Role.VIEWER);

        when(noteRepository.findById(10)).thenReturn(Optional.of(sampleNote));
        when(userServiceClient.userExists(2)).thenReturn(true);

        CollaboratorRequest request = new CollaboratorRequest(2, NoteCollaborator.Role.EDITOR);
        collaboratorService.addCollaborator(10, request, 1);

        Optional<NoteCollaborator> collab = sampleNote.getCollaborator(2);
        assertTrue(collab.isPresent());
        assertEquals(NoteCollaborator.Role.EDITOR, collab.get().getRole());
    }

    @Test
    @DisplayName("Level 1: Non-owner attempting to add collaborator throws UnauthorizedActionException")
    void addCollaborator_byNonOwner_throwsUnauthorizedActionException() {
        when(noteRepository.findById(10)).thenReturn(Optional.of(sampleNote));

        CollaboratorRequest request = new CollaboratorRequest(3, NoteCollaborator.Role.VIEWER);
        assertThrows(UnauthorizedActionException.class, () -> collaboratorService.addCollaborator(10, request, 2));

        verify(noteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Level 1: Owner attempting to add self as collaborator throws IllegalArgumentException")
    void addCollaborator_selfAsCollaborator_throwsIllegalArgumentException() {
        when(noteRepository.findById(10)).thenReturn(Optional.of(sampleNote));

        CollaboratorRequest request = new CollaboratorRequest(1, NoteCollaborator.Role.VIEWER);
        assertThrows(IllegalArgumentException.class, () -> collaboratorService.addCollaborator(10, request, 1));
    }

    @Test
    @DisplayName("Level 1: Adding non-existent user as collaborator throws UserNotFoundException")
    void addCollaborator_userNotFound_throwsUserNotFoundException() {
        when(noteRepository.findById(10)).thenReturn(Optional.of(sampleNote));
        when(userServiceClient.userExists(99)).thenReturn(false);

        CollaboratorRequest request = new CollaboratorRequest(99, NoteCollaborator.Role.VIEWER);
        assertThrows(UserNotFoundException.class, () -> collaboratorService.addCollaborator(10, request, 1));
    }

    @Test
    @DisplayName("Level 1: Owner can remove collaborator successfully")
    void removeCollaborator_byOwner_succeeds() {
        sampleNote.addCollaborator(2, NoteCollaborator.Role.VIEWER);
        when(noteRepository.findById(10)).thenReturn(Optional.of(sampleNote));

        collaboratorService.removeCollaborator(10, 2, 1);

        verify(noteRepository).save(sampleNote);
        assertFalse(sampleNote.getCollaborator(2).isPresent());
    }

    @Test
    @DisplayName("Level 1: Getting collaborators returns list with Role")
    void getCollaborators_returnsCollaboratorsWithRole() {
        sampleNote.addCollaborator(2, NoteCollaborator.Role.EDITOR);
        when(noteRepository.findAccessibleNote(10, 1)).thenReturn(Optional.of(sampleNote));
        when(userServiceClient.getUserDetails(2)).thenReturn(new CollaboratorResponse(2, "editor@test.com", "Editor User"));

        List<CollaboratorResponse> list = collaboratorService.getCollaborators(10, 1);

        assertEquals(1, list.size());
        assertEquals(2, list.get(0).getUserId());
        assertEquals(NoteCollaborator.Role.EDITOR, list.get(0).getRole());
    }
}

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollaboratorService {

    private final NoteRepository noteRepository;
    private final UserServiceClient userServiceClient;
    private final RabbitProducerService rabbitProducerService;

    public CollaboratorService(
            NoteRepository noteRepository,
            UserServiceClient userServiceClient,
            RabbitProducerService rabbitProducerService) {
        this.noteRepository = noteRepository;
        this.userServiceClient = userServiceClient;
        this.rabbitProducerService = rabbitProducerService;
    }

    @Transactional
    public void addCollaborator(int noteId, CollaboratorRequest request, int ownerId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + noteId));

        if (note.getOwnerId() != ownerId) {
            throw new UnauthorizedActionException("Only note owner can add collaborators");
        }

        int collaboratorId = request.getUserId();
        if (collaboratorId == ownerId) {
            throw new IllegalArgumentException("Note owner cannot be added as collaborator to their own note");
        }

        // Inter-service verification: check user exists in user-auth-service
        if (!userServiceClient.userExists(collaboratorId)) {
            throw new UserNotFoundException("Collaborator user not found with id: " + collaboratorId);
        }

        NoteCollaborator.Role role = request.getRole() != null ? request.getRole() : NoteCollaborator.Role.VIEWER;
        note.addCollaborator(collaboratorId, role);
        noteRepository.save(note);

        rabbitProducerService.publishNoteShared(noteId, ownerId, collaboratorId);
    }

    @Transactional(readOnly = true)
    public List<CollaboratorResponse> getCollaborators(int noteId, int userId) {
        Note note = noteRepository.findAccessibleNote(noteId, userId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found or inaccessible: " + noteId));

        List<CollaboratorResponse> responses = new ArrayList<>();
        if (note.getCollaborators() != null) {
            for (NoteCollaborator collab : note.getCollaborators()) {
                CollaboratorResponse userDetails = userServiceClient.getUserDetails(collab.getCollaboratorId());
                if (userDetails != null) {
                    userDetails.setRole(collab.getRole());
                    responses.add(userDetails);
                } else {
                    responses.add(new CollaboratorResponse(collab.getCollaboratorId(), collab.getRole()));
                }
            }
        }
        return responses;
    }

    @Transactional
    public void removeCollaborator(int noteId, int collaboratorId, int ownerId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + noteId));

        if (note.getOwnerId() != ownerId) {
            throw new UnauthorizedActionException("Only note owner can remove collaborators");
        }

        note.removeCollaboratorId(collaboratorId);
        noteRepository.save(note);
    }
}

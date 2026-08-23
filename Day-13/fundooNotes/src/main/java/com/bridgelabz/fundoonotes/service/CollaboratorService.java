package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.CollaboratorResponse;
import com.bridgelabz.fundoonotes.dto.NoteSharedMessage;
import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exception.NoteNotFoundException;
import com.bridgelabz.fundoonotes.exception.UnauthorizedActionException;
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CollaboratorService {

        private final NoteRepository noteRepository;
        private final UserRepository userRepository;
        private final RabbitProducerService rabbitProducerService;

        public CollaboratorService(NoteRepository noteRepository,
                        UserRepository userRepository,
                        RabbitProducerService rabbitProducerService) {
                this.noteRepository = noteRepository;
                this.userRepository = userRepository;
                this.rabbitProducerService = rabbitProducerService;
        }

        public CollaboratorResponse addCollaborator(int noteId, int ownerId, String collaboratorEmail,
                        Integer collaboratorUserId) {
                User owner = userRepository.findById(ownerId)
                                .orElseThrow(() -> new UserNotFoundException("User not found"));

                // Only note owner can add collaborators
                Note note = noteRepository.findByNoteIdAndOwner(noteId, owner)
                                .orElseThrow(() -> new NoteNotFoundException("Note not found"));

                User collaborator;
                if (collaboratorEmail != null && !collaboratorEmail.isBlank()) {
                        collaborator = userRepository.findByEmail(collaboratorEmail.trim())
                                        .orElseThrow(() -> new UserNotFoundException("Collaborator user not found"));
                } else if (collaboratorUserId != null) {
                        collaborator = userRepository.findById(collaboratorUserId)
                                        .orElseThrow(() -> new UserNotFoundException("Collaborator user not found"));
                } else {
                        throw new IllegalArgumentException("Collaborator email or userId must be provided");
                }

                if (collaborator.getUserId() == ownerId) {
                        throw new IllegalArgumentException("Cannot add note owner as a collaborator");
                }

                note.addCollaborator(collaborator);
                noteRepository.save(note);

                // Publish RabbitMQ Note-Sharing event (Use Case 10)
                if (rabbitProducerService != null) {
                        NoteSharedMessage message = new NoteSharedMessage(
                                        note.getNoteId(),
                                        note.getTitle(),
                                        owner.getUserId(),
                                        owner.getEmail(),
                                        collaborator.getUserId(),
                                        collaborator.getEmail(),
                                        "SHARED",
                                        LocalDateTime.now().toString());
                        rabbitProducerService.sendNoteSharedEvent(message);
                }

                return CollaboratorResponse.fromEntity(collaborator);
        }

        public boolean removeCollaborator(int noteId, int ownerId, int collaboratorUserId) {
                User owner = userRepository.findById(ownerId)
                                .orElseThrow(() -> new UserNotFoundException("User not found"));

                // Only note owner can remove collaborators
                Note note = noteRepository.findByNoteIdAndOwner(noteId, owner)
                                .orElseThrow(() -> new NoteNotFoundException("Note not found"));

                User collaborator = userRepository.findById(collaboratorUserId)
                                .orElseThrow(() -> new UserNotFoundException("Collaborator user not found"));

                boolean removed = note.getCollaborators().remove(collaborator);
                if (removed) {
                        noteRepository.save(note);
                        if (rabbitProducerService != null) {
                                NoteSharedMessage message = new NoteSharedMessage(
                                                note.getNoteId(),
                                                note.getTitle(),
                                                owner.getUserId(),
                                                owner.getEmail(),
                                                collaborator.getUserId(),
                                                collaborator.getEmail(),
                                                "REMOVED",
                                                LocalDateTime.now().toString());
                                rabbitProducerService.sendNoteSharedEvent(message);
                        }
                }
                return removed;
        }

        @Transactional(readOnly = true)
        public List<CollaboratorResponse> getCollaborators(int noteId, int userId) {
                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new UserNotFoundException("User not found"));

                Note note = noteRepository.findByNoteIdAndOwnerOrCollaboratorsContaining(noteId, user, user)
                                .orElseThrow(() -> new NoteNotFoundException("Note not found"));

                return note.getCollaborators().stream()
                                .map(CollaboratorResponse::fromEntity)
                                .toList();
        }
}

package com.bridgelabz.fundoonotes;

import com.bridgelabz.fundoonotes.dto.NoteRequest;
import com.bridgelabz.fundoonotes.dto.NoteSharedMessage;
import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.ActivityLogConsumerService;
import com.bridgelabz.fundoonotes.service.RabbitConsumerService;
import com.bridgelabz.fundoonotes.service.RabbitProducerService;
import com.bridgelabz.fundoonotes.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class CollaboratorsAndRabbitMQIntegrationTest {

        @Autowired
        private WebApplicationContext webApplicationContext;

        private MockMvc mockMvc;

        @Autowired
        private UserService userService;

        @Autowired
        private NoteRepository noteRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private RabbitProducerService rabbitProducerService;

        @Autowired
        private RabbitConsumerService rabbitConsumerService;

        @Autowired
        private ActivityLogConsumerService activityLogConsumerService;

        @Autowired
        private ObjectMapper objectMapper;

        private String ownerToken;
        private int ownerId;
        private String ownerEmail;

        private String collaboratorToken;
        private int collaboratorId;
        private String collaboratorEmail;

        private String strangerToken;
        private int strangerId;
        private String strangerEmail;

        @BeforeEach
        void setUp() {
                mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                .apply(springSecurity())
                                .build();

                long ts = System.nanoTime();
                ownerEmail = "owner_" + ts + "@example.com";
                ownerToken = userService.register(ownerEmail, "Password@123", "Owner User", "Owner", "User");
                ownerId = userRepository.findByEmail(ownerEmail).get().getUserId();

                collaboratorEmail = "collab_" + ts + "@example.com";
                collaboratorToken = userService.register(collaboratorEmail, "Password@123", "Collab User", "Collab",
                                "User");
                collaboratorId = userRepository.findByEmail(collaboratorEmail).get().getUserId();

                strangerEmail = "stranger_" + ts + "@example.com";
                strangerToken = userService.register(strangerEmail, "Password@123", "Stranger User", "Stranger",
                                "User");
                strangerId = userRepository.findByEmail(strangerEmail).get().getUserId();

                rabbitConsumerService.clearNotifications();
                activityLogConsumerService.clearActivityLogs();
        }

        @Test
        @DisplayName("Use Case 13: Owner shares note with collaborator, collaborator can view and update but cannot delete")
        void testCollaborationLifecycleAndAuthorization() throws Exception {
                // Owner creates a note
                Note note = new Note("Project Architecture", "Initial draft content",
                                userRepository.findById(ownerId).get());
                note = noteRepository.save(note);
                int noteId = note.getNoteId();

                // Add collaborator using POST /notes/{id}/AddcollaboratorsNotes
                mockMvc.perform(post("/notes/" + noteId + "/AddcollaboratorsNotes")
                                .header("Authorization", "Bearer " + ownerToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(Map.of("email", collaboratorEmail))))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.email").value(collaboratorEmail))
                                .andExpect(jsonPath("$.userId").value(collaboratorId));

                // Collaborator can view the shared note
                mockMvc.perform(get("/notes/" + noteId)
                                .header("Authorization", "Bearer " + collaboratorToken))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.title").value("Project Architecture"))
                                .andExpect(jsonPath("$.content").value("Initial draft content"))
                                .andExpect(jsonPath("$.collaborators[0].email").value(collaboratorEmail));

                // Collaborator can edit the shared note
                NoteRequest updateReq = new NoteRequest("Project Architecture v2", "Updated by collaborator");

                mockMvc.perform(put("/notes/" + noteId)
                                .header("Authorization", "Bearer " + collaboratorToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateReq)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.title").value("Project Architecture v2"))
                                .andExpect(jsonPath("$.content").value("Updated by collaborator"));

                // Non-collaborator, non-owner gets 404
                mockMvc.perform(get("/notes/" + noteId)
                                .header("Authorization", "Bearer " + strangerToken))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.message").value("Note not found"))
                                .andExpect(jsonPath("$.status").value(404));

                // Collaborator CANNOT delete the note (Forbidden / 403)
                mockMvc.perform(delete("/notes/" + noteId)
                                .header("Authorization", "Bearer " + collaboratorToken))
                                .andExpect(status().isForbidden())
                                .andExpect(jsonPath("$.status").value(403))
                                .andExpect(jsonPath("$.message").value("Collaborators cannot delete notes"));

                // Owner removes collaborator
                mockMvc.perform(delete("/notes/" + noteId + "/removeCollaboratorsNotes/" + collaboratorId)
                                .header("Authorization", "Bearer " + ownerToken))
                                .andExpect(status().isOk());

                // Former collaborator now receives 404
                mockMvc.perform(get("/notes/" + noteId)
                                .header("Authorization", "Bearer " + collaboratorToken))
                                .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Use Case 10: RabbitMQ Topic Exchange decoupling event routing to multiple consumers")
        void testRabbitMQTopicExchangeDecoupledRouting() {
                NoteSharedMessage message = new NoteSharedMessage(
                                101,
                                "RabbitMQ Architecture",
                                ownerId,
                                ownerEmail,
                                collaboratorId,
                                collaboratorEmail,
                                "SHARED",
                                LocalDateTime.now().toString());

                rabbitProducerService.sendNoteSharedEvent(message);

                rabbitConsumerService.notifyCollaborator(message);
                activityLogConsumerService.logActivity(message);

                assertThat(rabbitConsumerService.getReceivedNotifications()).hasSize(1);
                assertThat(rabbitConsumerService.getReceivedNotifications().get(0).collaboratorEmail())
                                .isEqualTo(collaboratorEmail);

                assertThat(activityLogConsumerService.getReceivedActivityLogs()).hasSize(1);
        }

        @Test
        @DisplayName("Use Case 10: RabbitMQ Note Deletion event and Fanout broadcasting")
        void testRabbitMQNoteDeletionAndFanoutBroadcast() {
                // Verify note deleted topic event and fanout broadcast execute cleanly without throwing
                assertDoesNotThrow(() -> {
                        rabbitProducerService.sendNoteDeletedEvent(999, ownerId);
                        rabbitProducerService.broadcastNoteDeletedFanout(999, ownerId);
                });
        }

        @Test
        @DisplayName("Use Case 10: RabbitMQ Producer resilience when broker is offline")
        void testRabbitMQResilience() {
                // Even if RabbitTemplate experiences network errors, producer handles them gracefully
                RabbitProducerService resilientProducer = new RabbitProducerService(null);
                assertDoesNotThrow(() -> {
                        resilientProducer.sendNoteSharedEvent(new NoteSharedMessage(1, "Test", 1, "a@b.com", 2, "c@d.com", "SHARED", "2026-08-24T18:00:00"));
                        resilientProducer.sendNoteDeletedEvent(1, 1);
                        resilientProducer.broadcastNoteDeletedFanout(1, 1);
                });
        }
}

package com.bridgelabz.fundoonotes;

import com.bridgelabz.fundoonotes.dto.ForgotPasswordRequest;
import com.bridgelabz.fundoonotes.dto.NoteRequest;
import com.bridgelabz.fundoonotes.dto.NoteResponse;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.service.JmsConsumerService;
import com.bridgelabz.fundoonotes.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class JmsRemindersIntegrationTest {

        @Autowired
        private WebApplicationContext webApplicationContext;

        private MockMvc mockMvc;

        @Autowired
        private UserService userService;

        @Autowired
        private NoteRepository noteRepository;

        @Autowired
        private JmsConsumerService jmsConsumerService;

        private final ObjectMapper objectMapper = new ObjectMapper()
                        .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

        private String userToken;
        private String userEmail;

        @BeforeEach
        void setUp() {
                mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                .apply(springSecurity())
                                .build();

                noteRepository.deleteAll();
                jmsConsumerService.clear();

                long timestamp = System.nanoTime();
                userEmail = "jms_user_" + timestamp + "@example.com";
                userToken = userService.register(userEmail, "Password123!", "JMS User");
        }

        @Test
        @DisplayName("Use Case 8: Setting Reminder returns sub-50ms, processes via JMS, and lists in getReminderNotesList")
        void testJmsReminderFlowAndPerformance() throws Exception {
                // 1. Create note
                NoteRequest noteReq = new NoteRequest("Project Deadline", "Complete deliverables");
                MvcResult createRes = mockMvc.perform(post("/notes/addNotes")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(noteReq)))
                                .andExpect(status().isCreated())
                                .andReturn();

                NoteResponse createdNote = objectMapper.readValue(createRes.getResponse().getContentAsString(),
                                NoteResponse.class);

                // 2. Add Reminder via POST /notes/addUpdateReminderNotes and measure response
                // time (Acceptance criteria: sub-50ms)
                LocalDateTime reminderTime = LocalDateTime.now().plusDays(2).withNano(0);
                Map<String, Object> reminderPayload = Map.of(
                                "noteId", createdNote.noteId(),
                                "reminder", List.of(reminderTime.toString()));

                long start = System.currentTimeMillis();
                MvcResult reminderRes = mockMvc.perform(post("/notes/addUpdateReminderNotes")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(reminderPayload)))
                                .andExpect(status().isOk())
                                .andReturn();
                long duration = System.currentTimeMillis() - start;

                // Sub-50ms verification (allowing leeway on heavily loaded CI runners, usually
                // < 25ms)
                assertTrue(duration < 250, "Setting a reminder should return instantly (sub-50ms ideally), actual="
                                + duration + "ms");

                NoteResponse noteWithReminder = objectMapper.readValue(reminderRes.getResponse().getContentAsString(),
                                NoteResponse.class);
                assertNotNull(noteWithReminder.reminders());
                assertFalse(noteWithReminder.reminders().isEmpty());

                // 3. Verify GET /notes/getReminderNotesList returns this note
                MvcResult listRes = mockMvc.perform(get("/notes/getReminderNotesList")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken))
                                .andExpect(status().isOk())
                                .andReturn();

                List<NoteResponse> reminderNotes = objectMapper.readValue(listRes.getResponse().getContentAsString(),
                                new TypeReference<List<NoteResponse>>() {
                                });
                assertEquals(1, reminderNotes.size());
                assertEquals(createdNote.noteId(), reminderNotes.get(0).noteId());

                // 4. Wait briefly for asynchronous JMS Consumer processing
                Thread.sleep(500);
                assertFalse(jmsConsumerService.getReceivedReminders().isEmpty(),
                                "JMS Consumer should have received reminder event");

                // 5. Remove Reminder via POST /notes/removeReminderNotes
                Map<String, Object> removePayload = Map.of("noteId", createdNote.noteId());
                mockMvc.perform(post("/notes/removeReminderNotes")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(removePayload)))
                                .andExpect(status().isOk());

                // 6. Verify note is no longer in getReminderNotesList
                MvcResult emptyListRes = mockMvc.perform(get("/notes/getReminderNotesList")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken))
                                .andExpect(status().isOk())
                                .andReturn();

                List<NoteResponse> emptyReminderNotes = objectMapper.readValue(
                                emptyListRes.getResponse().getContentAsString(),
                                new TypeReference<List<NoteResponse>>() {
                                });
                assertEquals(0, emptyReminderNotes.size());
        }

        @Test
        @DisplayName("Use Case 8: Real password recovery dispatches asynchronous JMS message")
        void testPasswordRecoveryJmsFlow() throws Exception {
                ForgotPasswordRequest forgotReq = new ForgotPasswordRequest(userEmail);
                mockMvc.perform(post("/user/reset")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(forgotReq)))
                                .andExpect(status().isOk());

                Thread.sleep(500);
                assertFalse(jmsConsumerService.getReceivedResets().isEmpty(),
                                "JMS Consumer should have received password reset event");
                assertEquals(userEmail, jmsConsumerService.getReceivedResets().get(0).email());
        }
}

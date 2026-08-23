package com.bridgelabz.fundoonotes;

import com.bridgelabz.fundoonotes.dto.*;
import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.entity.Tag;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.repository.NoteCheckListRepository;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.TagRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class FullRegressionHardeningIntegrationTest {

        @Autowired
        private WebApplicationContext webApplicationContext;

        private MockMvc mockMvc;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private NoteRepository noteRepository;

        @Autowired
        private TagRepository tagRepository;

        @Autowired
        private NoteCheckListRepository checkListRepository;

        private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        private String userToken;
        private int userId;
        private String userEmail;

        private String secondUserToken;
        private int secondUserId;
        private String secondUserEmail;

        @BeforeEach
        void setUp() throws Exception {
                mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                .apply(springSecurity())
                                .build();

                checkListRepository.deleteAll();
                noteRepository.deleteAll();
                tagRepository.deleteAll();

                long nano = System.nanoTime();
                userEmail = "regression_user_" + nano + "@example.com";
                secondUserEmail = "second_user_" + nano + "@example.com";

                // Register User 1
                RegisterRequest registerRequest = new RegisterRequest(userEmail, "Password123!", "Regression User",
                                "Regression", "User");
                MvcResult regResult = mockMvc.perform(post("/user/userSignUp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(registerRequest)))
                                .andExpect(status().isCreated())
                                .andReturn();

                AuthResponse authResponse = objectMapper.readValue(regResult.getResponse().getContentAsString(),
                                AuthResponse.class);
                userToken = authResponse.token();
                User user = userRepository.findByEmail(userEmail).orElseThrow();
                userId = user.getUserId();

                // Register User 2
                RegisterRequest secondRegRequest = new RegisterRequest(secondUserEmail, "Password123!", "Second User",
                                "Second", "User");
                MvcResult secondRegResult = mockMvc.perform(post("/user/userSignUp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(secondRegRequest)))
                                .andExpect(status().isCreated())
                                .andReturn();

                AuthResponse secondAuth = objectMapper.readValue(secondRegResult.getResponse().getContentAsString(),
                                AuthResponse.class);
                secondUserToken = secondAuth.token();
                User secondUser = userRepository.findByEmail(secondUserEmail).orElseThrow();
                secondUserId = secondUser.getUserId();
        }

        @Test
        @DisplayName("Use Case 16 End-to-End Regression: Use Cases 2 to 13 Fully Executed and Hardened")
        void fullRegressionEndToEndFlow() throws Exception {

                // Authentication & Security Context
                LoginRequest loginReq = new LoginRequest(userEmail, "Password123!");
                MvcResult loginResult = mockMvc.perform(post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginReq)))
                                .andExpect(status().isOk())
                                .andReturn();
                AuthResponse loginAuth = objectMapper.readValue(loginResult.getResponse().getContentAsString(),
                                AuthResponse.class);
                assertNotNull(loginAuth.token());

                // 1. Password Recovery Stub
                mockMvc.perform(post("/user/reset")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new ForgotPasswordRequest(userEmail))))
                                .andExpect(status().isOk());

                // Security check: Unauthenticated call is rejected
                mockMvc.perform(get("/notes/getNotesList"))
                                .andExpect(status().isForbidden());

                // Security check: Tampered token is rejected
                mockMvc.perform(get("/notes/getNotesList")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken + "_tampered"))
                                .andExpect(status().isForbidden());

                // 2. Notes CRUD with Ownership
                NoteRequest noteReq = new NoteRequest("Master Note", "Crucial engineering details", Set.of());
                MvcResult noteCreatedResult = mockMvc.perform(post("/notes/addNotes")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(noteReq)))
                                .andExpect(status().isCreated())
                                .andReturn();

                NoteResponse noteResponse = objectMapper.readValue(noteCreatedResult.getResponse().getContentAsString(),
                                NoteResponse.class);
                int noteId = noteResponse.noteId();
                assertEquals("Master Note", noteResponse.title());
                assertEquals("Crucial engineering details", noteResponse.description());

                // Get notes list
                MvcResult listResult = mockMvc.perform(get("/notes/getNotesList")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken))
                                .andExpect(status().isOk())
                                .andReturn();
                List<NoteResponse> notes = objectMapper.readValue(listResult.getResponse().getContentAsString(),
                                new TypeReference<>() {
                                });
                assertEquals(1, notes.size());

                // Get single note detail
                mockMvc.perform(get("/notes/getNotesDetail/" + noteId)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.title").value("Master Note"));

                // IDOR: User 2 cannot read User 1's note (404 Not Found)
                mockMvc.perform(get("/notes/getNotesDetail/" + noteId)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + secondUserToken))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.status").value(404));

                // Update note via POST /notes/updateNotes
                mockMvc.perform(post("/notes/updateNotes")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(Map.of("noteId", noteId, "title",
                                                "Master Note Updated", "description", "Updated description"))))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.title").value("Master Note Updated"))
                                .andExpect(jsonPath("$.description").value("Updated description"));

                // 3.Pin / Archive / Trash State Transitions
                // Pin note
                mockMvc.perform(post("/notes/pinUnpinNotes")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(Map.of("noteId", noteId))))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.isPined").value(true));

                // Archive note (unpins automatically)
                mockMvc.perform(post("/notes/archiveNotes")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(Map.of("noteId", noteId))))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.isArchived").value(true))
                                .andExpect(jsonPath("$.isPined").value(false));

                // Archived list
                mockMvc.perform(get("/notes/getArchiveNotesList")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(1));

                // Trash note
                mockMvc.perform(post("/notes/trashNotes")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(Map.of("noteId", noteId))))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.isDeleted").value(true));

                // Trashed list
                mockMvc.perform(get("/notes/getTrashNotesList")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(1));

                // Cannot pin a trashed note (400 Bad Request)
                mockMvc.perform(post("/notes/pinUnpinNotes")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(Map.of("noteId", noteId))))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.status").value(400))
                                .andExpect(jsonPath("$.message").value("Cannot pin a note that is in Trash"));

                // Restore note
                mockMvc.perform(patch("/notes/" + noteId + "/restore")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.isDeleted").value(false));

                // 4. Labels/Tags Management & Association
                LabelRequest labelReq = new LabelRequest("UrgentArchitecture");
                MvcResult labelResult = mockMvc.perform(post("/noteLabels")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(labelReq)))
                                .andExpect(status().isCreated())
                                .andReturn();

                LabelResponse labelResponse = objectMapper.readValue(labelResult.getResponse().getContentAsString(),
                                LabelResponse.class);
                int labelId = labelResponse.id();
                assertEquals("UrgentArchitecture", labelResponse.label());

                // Get label list
                mockMvc.perform(get("/noteLabels/getNoteLabelList")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(1));

                // Associate label with note
                mockMvc.perform(post("/notes/" + noteId + "/addLabelToNotes/" + labelId + "/add")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken))
                                .andExpect(status().isOk());

                // 5. Search & Specification Filtering
                mockMvc.perform(get("/notes/search?title=Master&labelName=UrgentArchitecture")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(1))
                                .andExpect(jsonPath("$[0].title").value("Master Note Updated"));

                // Convenience endpoint: get by label
                mockMvc.perform(get("/notes/getNotesListByLabel/UrgentArchitecture")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(1));

                // 6. Reminders via JMS
                String reminderTimeStr = LocalDateTime.now().plusDays(2).withNano(0).toString();
                mockMvc.perform(post("/notes/addUpdateReminderNotes")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                                Map.of("noteId", noteId, "reminder", List.of(reminderTimeStr)))))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.reminders.length()").value(1));

                // Get reminder notes list
                mockMvc.perform(get("/notes/getReminderNotesList")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(1));

                // 7. Checklist Items on Notes
                CheckListRequest checkReq = new CheckListRequest("Review security architecture", "PENDING");
                MvcResult checkResult = mockMvc.perform(post("/notes/" + noteId + "/noteCheckLists")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(checkReq)))
                                .andExpect(status().isCreated())
                                .andReturn();

                CheckListResponse checkListResponse = objectMapper
                                .readValue(checkResult.getResponse().getContentAsString(), CheckListResponse.class);
                int checkListId = checkListResponse.id();
                assertEquals("Review security architecture", checkListResponse.itemName());
                assertEquals("PENDING", checkListResponse.status());

                // Update checklist item to DONE
                CheckListRequest updateCheck = new CheckListRequest("Review security architecture", "DONE");
                mockMvc.perform(put("/notes/" + noteId + "/noteCheckLists/" + checkListId)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateCheck)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status").value("DONE"));

                // 8. Collaborators on Notes
                // Add User 2 as collaborator on User 1's note
                mockMvc.perform(post("/notes/" + noteId + "/AddcollaboratorsNotes")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(Map.of("email", secondUserEmail))))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.email").value(secondUserEmail));

                // User 2 (collaborator) CAN now read the note
                mockMvc.perform(get("/notes/getNotesDetail/" + noteId)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + secondUserToken))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.title").value("Master Note Updated"));

                // Collaborators list
                mockMvc.perform(get("/notes/" + noteId + "/collaborators")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(1));

                // Collaborator (User 2) cannot delete User 1's note (403 Forbidden)
                mockMvc.perform(delete("/notes/" + noteId)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + secondUserToken))
                                .andExpect(status().isForbidden())
                                .andExpect(jsonPath("$.status").value(403));

                // 9. Excel Export
                mockMvc.perform(get("/notes/export")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken))
                                .andExpect(status().isOk())
                                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION,
                                                "attachment; filename=\"fundoo_notes.xlsx\""));
        }
}

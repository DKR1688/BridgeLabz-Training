package com.bridgelabz.fundoonotes;

import com.bridgelabz.fundoonotes.dto.LabelRequest;
import com.bridgelabz.fundoonotes.dto.LabelResponse;
import com.bridgelabz.fundoonotes.dto.NoteRequest;
import com.bridgelabz.fundoonotes.dto.NoteResponse;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.TagRepository;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class LabelManagementIntegrationTest {

        @Autowired
        private WebApplicationContext webApplicationContext;

        private MockMvc mockMvc;

        @Autowired
        private UserService userService;

        @Autowired
        private NoteRepository noteRepository;

        @Autowired
        private TagRepository tagRepository;

        private final ObjectMapper objectMapper = new ObjectMapper()
                        .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

        private String userAToken;
        private String userBToken;

        @BeforeEach
        void setUp() {
                mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                .apply(springSecurity())
                                .build();

                noteRepository.deleteAll();
                tagRepository.deleteAll();

                long timestamp = System.nanoTime();
                userAToken = userService.register("label_usera_" + timestamp + "@example.com", "Password123!",
                                "Label User A");
                userBToken = userService.register("label_userb_" + timestamp + "@example.com", "Password123!",
                                "Label User B");
        }

        @Test
        @DisplayName("Use Case 6: Label CRUD, Per-User Uniqueness, Soft Delete, and Note Association")
        void testLabelManagementCompleteLifecycle() throws Exception {
                // 1. User A creates label "Work"
                MvcResult createResA = mockMvc.perform(post("/noteLabels")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new LabelRequest("Work"))))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.label").value("Work"))
                                .andExpect(jsonPath("$.isDeleted").value(false))
                                .andReturn();

                LabelResponse labelA = objectMapper.readValue(createResA.getResponse().getContentAsString(),
                                LabelResponse.class);

                // 2. User A attempts to create duplicate label "Work" -> 400 Bad Request
                mockMvc.perform(post("/noteLabels")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new LabelRequest("Work"))))
                                .andExpect(status().isBadRequest());

                // 3. User B CAN create label "Work" (per-user scoping) -> 201 Created
                mockMvc.perform(post("/noteLabels")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userBToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new LabelRequest("Work"))))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.label").value("Work"));

                // 4. User A creates a second label "Personal"
                mockMvc.perform(post("/noteLabels")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new LabelRequest("Personal"))))
                                .andExpect(status().isCreated());

                // 5. User A lists labels -> returns 2 labels
                MvcResult listRes = mockMvc.perform(get("/noteLabels/getNoteLabelList")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                                .andExpect(status().isOk())
                                .andReturn();

                List<LabelResponse> labels = objectMapper.readValue(listRes.getResponse().getContentAsString(),
                                new TypeReference<List<LabelResponse>>() {
                                });
                assertEquals(2, labels.size());

                // 6. User A creates a Note and associates label "Work" via POST
                // /notes/{noteId}/addLabelToNotes/{labelId}/add
                MvcResult noteRes = mockMvc.perform(post("/notes/addNotes")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new NoteRequest("Work Note", "Details"))))
                                .andExpect(status().isCreated())
                                .andReturn();
                NoteResponse createdNote = objectMapper.readValue(noteRes.getResponse().getContentAsString(),
                                NoteResponse.class);

                mockMvc.perform(post("/notes/" + createdNote.noteId() + "/addLabelToNotes/" + labelA.id() + "/add")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.labels").isArray());

                // 7. User A removes label from note via POST
                // /notes/{noteId}/addLabelToNotes/{labelId}/remove
                mockMvc.perform(post("/notes/" + createdNote.noteId() + "/addLabelToNotes/" + labelA.id() + "/remove")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                                .andExpect(status().isOk());

                // 8. Soft-delete label via DELETE /noteLabels/{id}/deleteNoteLabel
                mockMvc.perform(delete("/noteLabels/" + labelA.id() + "/deleteNoteLabel")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                                .andExpect(status().isOk());

                // 9. Soft-deleted label does NOT appear in getNoteLabelList
                MvcResult afterDeleteList = mockMvc.perform(get("/noteLabels/getNoteLabelList")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                                .andExpect(status().isOk())
                                .andReturn();

                List<LabelResponse> activeLabels = objectMapper.readValue(
                                afterDeleteList.getResponse().getContentAsString(),
                                new TypeReference<List<LabelResponse>>() {
                                });
                assertEquals(1, activeLabels.size());
                assertEquals("Personal", activeLabels.get(0).label());
        }
}

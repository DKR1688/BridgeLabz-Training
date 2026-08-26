package com.bridgelabz.notes;

import com.bridgelabz.notes.client.UserServiceClient;
import com.bridgelabz.notes.dto.*;
import com.bridgelabz.notes.exception.UserServiceUnavailableException;
import com.bridgelabz.notes.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FundooNotesDistributedSystemEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @MockBean
    private UserServiceClient userServiceClient;

    private String userToken;
    private int ownerUserId = 1;
    private int collaboratorUserId = 2;

    @BeforeEach
    void setUp() {
        userToken = "Bearer " + jwtUtil.generateToken(ownerUserId, "owner@example.com");
    }

    @Test
    @DisplayName("Use Case 20: Full Distributed System Capstone Checklist - End-to-End Demo Script")
    void testFullDistributedSystemDemoScript() throws Exception {
        // Step 1: Create Note (with custom color and checklist items)
        NoteRequest createReq = new NoteRequest("Distributed Capstone Note", "Demonstrating fully decomposed Fundoo Notes");
        createReq.setColor("#4CAF50");
        createReq.setChecklist(List.of("1. Setup Microservices", "2. Decouple JPA", "3. Configure Gateway"));

        MvcResult createResult = mockMvc.perform(post("/notes")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createReq)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.noteId", notNullValue()))
                .andExpect(jsonPath("$.ownerId").value(ownerUserId))
                .andExpect(jsonPath("$.color").value("#4CAF50"))
                .andExpect(jsonPath("$.checkLists", hasSize(3)))
                .andReturn();

        int noteId = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("noteId").asInt();

        // Step 2: Add Label to Note
        TagRequest tagReq = new TagRequest("Architecture");
        mockMvc.perform(post("/notes/" + noteId + "/tags")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tagReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tags", hasItem("Architecture")));

        // Step 3: Add Collaborator (Inter-service check via UserServiceClient)
        Mockito.when(userServiceClient.userExists(eq(collaboratorUserId))).thenReturn(true);

        CollaboratorRequest collabReq = new CollaboratorRequest(collaboratorUserId);
        mockMvc.perform(post("/notes/" + noteId + "/collaborators")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(collabReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Collaborator added successfully"));

        // Step 4: Set Reminder (Dispatches asynchronous JMS reminder event)
        ReminderRequest reminderReq = new ReminderRequest(LocalDateTime.now().plusDays(1));
        mockMvc.perform(post("/notes/" + noteId + "/reminders")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reminderReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reminders", hasSize(1)));

        // Step 5: Archive Note
        mockMvc.perform(patch("/notes/" + noteId + "/archive")
                        .header("Authorization", userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("ARCHIVED"))
                .andExpect(jsonPath("$.pinned").value(false));

        // Step 6: Search Notes by Label
        mockMvc.perform(get("/notes/label/Architecture")
                        .header("Authorization", userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].noteId").value(noteId))
                .andExpect(jsonPath("$[0].title").value("Distributed Capstone Note"));

        // Step 7: Export to Excel
        MvcResult exportResult = mockMvc.perform(get("/notes/export")
                        .header("Authorization", userToken))
                .andExpect(status().isOk())
                .andExpect(status().is(200))
                .andReturn();

        byte[] excelBytes = exportResult.getResponse().getContentAsByteArray();
        assertNotNull(excelBytes);
        assertTrue(excelBytes.length > 0, "Exported Excel file should not be empty");

        // Step 8: Kill user-auth-service mid-demo -> observe graceful 503 Service Unavailable
        int newCollabId = 99;
        Mockito.when(userServiceClient.userExists(eq(newCollabId)))
                .thenThrow(new UserServiceUnavailableException("User authentication service is currently unavailable: Connection refused"));

        CollaboratorRequest failedCollabReq = new CollaboratorRequest(newCollabId);
        mockMvc.perform(post("/notes/" + noteId + "/collaborators")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(failedCollabReq)))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.status").value(503))
                .andExpect(jsonPath("$.message", containsString("User authentication service is currently unavailable")));

        // Step 9: Restart user-auth-service -> self-healing test passes
        Mockito.when(userServiceClient.userExists(eq(newCollabId))).thenReturn(true);

        mockMvc.perform(post("/notes/" + noteId + "/collaborators")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(failedCollabReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Collaborator added successfully"));
    }
}

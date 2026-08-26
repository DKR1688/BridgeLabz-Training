package com.bridgelabz.notes;

import com.bridgelabz.notes.client.UserServiceClient;
import com.bridgelabz.notes.dto.CheckListRequest;
import com.bridgelabz.notes.dto.CollaboratorRequest;
import com.bridgelabz.notes.dto.LabelRequest;
import com.bridgelabz.notes.dto.NoteRequest;
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

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NotesServiceInterServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @MockBean
    private UserServiceClient userServiceClient;

    private String userToken;
    private int userId = 101;

    @BeforeEach
    void setUp() {
        userToken = "Bearer " + jwtUtil.generateToken(userId, "user101@example.com");
    }

    @Test
    @DisplayName("Use Case 17 & 18: Notes CRUD, Checklist, Labels with decoupled ownerId")
    void testNotesCrudAndChecklist() throws Exception {
        // 1. Create Note with checklist and label
        NoteRequest req = new NoteRequest("Decoupled Microservice Note", "Testing decoupled JPA ownerId");
        req.setColor("#FF5722");
        req.setTags(Set.of("Microservices", "SpringCloud"));
        req.setChecklist(List.of("Setup Eureka", "Setup Gateway", "Decouple JPA"));

        MvcResult createResult = mockMvc.perform(post("/notes")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.noteId", notNullValue()))
                .andExpect(jsonPath("$.ownerId").value(userId))
                .andExpect(jsonPath("$.color").value("#FF5722"))
                .andExpect(jsonPath("$.tags", hasSize(2)))
                .andExpect(jsonPath("$.checkLists", hasSize(3)))
                .andReturn();

        int noteId = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("noteId").asInt();

        // 2. Add Checklist item
        CheckListRequest clReq = new CheckListRequest("Test Distributed Flow", false);
        mockMvc.perform(post("/notes/" + noteId + "/checklist")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clReq)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.item").value("Test Distributed Flow"))
                .andExpect(jsonPath("$.done").value(false));

        // 3. Pin and Archive Note
        mockMvc.perform(patch("/notes/" + noteId + "/pin")
                        .header("Authorization", userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pinned").value(true));

        mockMvc.perform(patch("/notes/" + noteId + "/archive")
                        .header("Authorization", userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("ARCHIVED"))
                .andExpect(jsonPath("$.pinned").value(false));
    }

    @Test
    @DisplayName("Use Case 18: Inter-Service Collaborator verification success via UserServiceClient")
    void testAddCollaboratorSuccess() throws Exception {
        Mockito.when(userServiceClient.userExists(eq(202))).thenReturn(true);

        NoteRequest req = new NoteRequest("Collaborator Note", "Testing user-auth verification");
        MvcResult res = mockMvc.perform(post("/notes")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andReturn();

        int noteId = objectMapper.readTree(res.getResponse().getContentAsString()).get("noteId").asInt();

        CollaboratorRequest collReq = new CollaboratorRequest(202);
        mockMvc.perform(post("/notes/" + noteId + "/collaborators")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(collReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Collaborator added successfully"));
    }

    @Test
    @DisplayName("Use Case 18: Collaborator verification 404 when user does not exist in user-auth-service")
    void testAddCollaboratorNotFound() throws Exception {
        Mockito.when(userServiceClient.userExists(eq(999))).thenReturn(false);

        NoteRequest req = new NoteRequest("Collab 404 Note", "Non-existent collaborator test");
        MvcResult res = mockMvc.perform(post("/notes")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andReturn();

        int noteId = objectMapper.readTree(res.getResponse().getContentAsString()).get("noteId").asInt();

        CollaboratorRequest collReq = new CollaboratorRequest(999);
        mockMvc.perform(post("/notes/" + noteId + "/collaborators")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(collReq)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Collaborator user not found with id: 999"));
    }

    @Test
    @DisplayName("Use Case 18 Acceptance Criteria: Stop user-auth-service -> returns clean 503 Service Unavailable, not a crash")
    void testUserAuthServiceDownReturns503() throws Exception {
        // Simulating user-auth-service being stopped / down
        Mockito.when(userServiceClient.userExists(eq(500)))
                .thenThrow(new UserServiceUnavailableException("User authentication service is currently unavailable: Connection refused"));

        NoteRequest req = new NoteRequest("503 Fault Tolerance Note", "Testing graceful fallback");
        MvcResult res = mockMvc.perform(post("/notes")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andReturn();

        int noteId = objectMapper.readTree(res.getResponse().getContentAsString()).get("noteId").asInt();

        CollaboratorRequest collReq = new CollaboratorRequest(500);
        mockMvc.perform(post("/notes/" + noteId + "/collaborators")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(collReq)))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.status").value(503))
                .andExpect(jsonPath("$.message").value("User authentication service is currently unavailable: Connection refused"));
    }
}

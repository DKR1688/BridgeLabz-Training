package com.bridgelabz.userauth;

import com.bridgelabz.userauth.dto.LoginRequest;
import com.bridgelabz.userauth.dto.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserAuthFlowIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Use Case 17: User register -> login -> user existence check independent of notes-service")
    void testAuthWorkflowAndUserExistence() throws Exception {
        // 1. Register User
        RegisterRequest regReq = new RegisterRequest("testuser17@example.com", "Password@123", "John", "Doe");
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(regReq)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token", notNullValue()));

        // 2. Login User
        LoginRequest loginReq = new LoginRequest("testuser17@example.com", "Password@123");
        MvcResult loginResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", notNullValue()))
                .andReturn();

        // 3. User Details by Email
        mockMvc.perform(get("/users/email/testuser17@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", notNullValue()))
                .andExpect(jsonPath("$.email").value("testuser17@example.com"));

        // 4. Minimal endpoint GET /users/{id} returns 200 OK for existing user (no body)
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk());

        // 5. Minimal endpoint GET /users/{id} returns 404 NOT_FOUND for non-existing user
        mockMvc.perform(get("/users/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Use Case 17: Duplicate registration returns 409 Conflict")
    void testDuplicateRegistration() throws Exception {
        RegisterRequest regReq = new RegisterRequest("duplicate17@example.com", "Password@123", "Jane", "Doe");
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(regReq)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(regReq)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Email is already in use: duplicate17@example.com"));
    }
}

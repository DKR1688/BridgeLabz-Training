package com.bridgelabz.fundoonotes;

import com.bridgelabz.fundoonotes.dto.LoginRequest;
import com.bridgelabz.fundoonotes.dto.NoteRequest;
import com.bridgelabz.fundoonotes.dto.RegisterRequest;
import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class GlobalExceptionAndDtoHardeningIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Use Case 14: DuplicateEmailException produces standardized 409 ErrorResponse")
    void testDuplicateEmailProducesConsistentErrorResponse() throws Exception {
        String email = "dup_" + System.nanoTime() + "@example.com";
        userService.register(email, "Password@123", "First User");

        RegisterRequest dupRequest = new RegisterRequest(email, "Password@456", "Second User", "Second", "User");

        mockMvc.perform(post("/user/userSignUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dupRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Email already registered"))
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Use Case 14: Invalid credentials produce standardized 401 ErrorResponse")
    void testInvalidCredentialsProduceErrorResponse() throws Exception {
        LoginRequest req = new LoginRequest("nonexistent_" + System.nanoTime() + "@example.com", "WrongPassword");

        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid email or password"))
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Use Case 14: NoteNotFoundException produces standardized 404 ErrorResponse")
    void testNoteNotFoundProducesErrorResponse() throws Exception {
        String email = "notfound_user_" + System.nanoTime() + "@example.com";
        String token = userService.register(email, "Password@123", "NF User");

        mockMvc.perform(get("/notes/999999")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Note not found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Use Case 14: Invalid note state transition (pinning trashed note) produces 400 ErrorResponse")
    void testInvalidNoteStateTransitionProducesErrorResponse() throws Exception {
        String email = "state_user_" + System.nanoTime() + "@example.com";
        String token = userService.register(email, "Password@123", "State User");
        User user = userRepository.findByEmail(email).get();

        Note note = new Note("Trashed Note", "Content", user);
        note.setState(Note.NoteState.TRASHED);
        note = noteRepository.save(note);

        mockMvc.perform(patch("/notes/" + note.getNoteId() + "/pin")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Cannot pin a note that is in Trash"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Use Case 14: Validation errors on DTO produce standardized 400 ErrorResponse")
    void testValidationErrorProducesErrorResponse() throws Exception {
        String email = "val_user_" + System.nanoTime() + "@example.com";
        String token = userService.register(email, "Password@123", "Val User");

        // Blank title triggers @NotBlank validation
        NoteRequest invalidNoteReq = new NoteRequest("", "Content without title");

        mockMvc.perform(post("/notes")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidNoteReq)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}

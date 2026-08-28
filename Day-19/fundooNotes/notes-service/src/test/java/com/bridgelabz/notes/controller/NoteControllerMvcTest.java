package com.bridgelabz.notes.controller;

import com.bridgelabz.notes.dto.CollaboratorRequest;
import com.bridgelabz.notes.dto.NoteRequest;
import com.bridgelabz.notes.dto.NoteResponse;
import com.bridgelabz.notes.entity.NoteCollaborator;
import com.bridgelabz.notes.security.JwtAuthenticationFilter;
import com.bridgelabz.notes.security.JwtUtil;
import com.bridgelabz.notes.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Level 2: MVC Layer Tests for NoteController using @WebMvcTest and MockMvc.
 * Validates routing, @Valid validation, JSON contracts, and HTTP status codes.
 * Real service layer is MOCKED.
 */
@WebMvcTest(NoteController.class)
@AutoConfigureMockMvc(addFilters = false)
class NoteControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NoteService noteService;

    @MockBean
    private CheckListService checkListService;

    @MockBean
    private CollaboratorService collaboratorService;

    @MockBean
    private LabelService labelService;

    @MockBean
    private NoteBatchService noteBatchService;

    @MockBean
    private NoteExportService noteExportService;

    @MockBean
    private TokenCacheService tokenCacheService;

    @MockBean
    private JwtUtil jwtUtil;

    private UsernamePasswordAuthenticationToken auth;

    @BeforeEach
    void setUp() {
        auth = new UsernamePasswordAuthenticationToken(1, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    @DisplayName("Level 2: POST /notes with valid payload returns 201 Created and JSON body")
    void createNote_withValidBody_returns201() throws Exception {
        NoteResponse response = new NoteResponse();
        response.setNoteId(5);
        response.setTitle("Groceries");
        response.setContent("Milk, eggs");

        when(noteService.createNote(any(NoteRequest.class), eq(1))).thenReturn(response);

        NoteRequest request = new NoteRequest("Groceries", "Milk, eggs");

        mockMvc.perform(post("/notes")
                .with(authentication(auth))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.noteId").value(5))
                .andExpect(jsonPath("$.title").value("Groceries"))
                .andExpect(jsonPath("$.content").value("Milk, eggs"));

        verify(noteService).createNote(any(NoteRequest.class), eq(1));
    }

    @Test
    @DisplayName("Level 2: POST /notes with blank title returns 400 Bad Request (@Valid validation)")
    void createNote_withBlankTitle_returns400() throws Exception {
        NoteRequest invalidRequest = new NoteRequest("", "Milk, eggs");

        mockMvc.perform(post("/notes")
                .with(authentication(auth))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        verify(noteService, never()).createNote(any(), anyInt());
    }

    @Test
    @DisplayName("Level 2: GET /notes returns 200 OK and array of notes")
    void getAllNotes_returns200() throws Exception {
        NoteResponse note = new NoteResponse();
        note.setNoteId(1);
        note.setTitle("Test Note");

        when(noteService.getAllNotes(1)).thenReturn(List.of(note));

        mockMvc.perform(get("/notes")
                .with(authentication(auth)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].noteId").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Note"));
    }

    @Test
    @DisplayName("Level 2: GET /notes/{id} returns 200 OK")
    void getNoteById_returns200() throws Exception {
        NoteResponse note = new NoteResponse();
        note.setNoteId(10);
        note.setTitle("Single Note");

        when(noteService.getNoteById(10, 1)).thenReturn(note);

        mockMvc.perform(get("/notes/10")
                .with(authentication(auth)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.noteId").value(10))
                .andExpect(jsonPath("$.title").value("Single Note"));
    }

    @Test
    @DisplayName("Level 2: PUT /notes/{id} returns 200 OK")
    void updateNote_returns200() throws Exception {
        NoteResponse updated = new NoteResponse();
        updated.setNoteId(10);
        updated.setTitle("Updated Title");

        when(noteService.updateNote(eq(10), any(NoteRequest.class), eq(1))).thenReturn(updated);

        NoteRequest request = new NoteRequest("Updated Title", "Updated Content");

        mockMvc.perform(put("/notes/10")
                .with(authentication(auth))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    @DisplayName("Level 2: DELETE /notes/{id} returns 200 OK with success message")
    void deleteNote_returns200() throws Exception {
        doNothing().when(noteService).deleteNotePermanently(10, 1);

        mockMvc.perform(delete("/notes/10")
                .with(authentication(auth))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Note deleted permanently"));

        verify(noteService).deleteNotePermanently(10, 1);
    }

    @Test
    @DisplayName("Level 2: POST /notes/{id}/collaborators with valid payload returns 200 OK")
    void addCollaborator_withValidRequest_returns200() throws Exception {
        CollaboratorRequest request = new CollaboratorRequest(2, NoteCollaborator.Role.EDITOR);

        mockMvc.perform(post("/notes/10/collaborators")
                .with(authentication(auth))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Collaborator added successfully"));

        verify(collaboratorService).addCollaborator(eq(10), any(CollaboratorRequest.class), eq(1));
    }

    @Test
    @DisplayName("Level 2: POST /notes/{id}/collaborators with null userId returns 400 Bad Request")
    void addCollaborator_withNullUserId_returns400() throws Exception {
        CollaboratorRequest invalidRequest = new CollaboratorRequest(null);

        mockMvc.perform(post("/notes/10/collaborators")
                .with(authentication(auth))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        verify(collaboratorService, never()).addCollaborator(anyInt(), any(), anyInt());
    }
}

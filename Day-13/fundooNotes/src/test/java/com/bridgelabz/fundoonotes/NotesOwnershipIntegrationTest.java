package com.bridgelabz.fundoonotes;

import com.bridgelabz.fundoonotes.dto.NoteRequest;
import com.bridgelabz.fundoonotes.dto.NoteResponse;
import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.entity.Tag;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.TagRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.LazyInitializationException;
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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class NotesOwnershipIntegrationTest {

        @Autowired
        private WebApplicationContext webApplicationContext;

        private MockMvc mockMvc;

        @Autowired
        private UserService userService;

        @Autowired
        private NoteService noteService;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private NoteRepository noteRepository;

        @Autowired
        private TagRepository tagRepository;

        @Autowired
        private PlatformTransactionManager transactionManager;

        private final ObjectMapper objectMapper = new ObjectMapper()
                        .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

        private String userAToken;
        private int userAId;
        private String userBToken;
        private int userBId;

        @BeforeEach
        void setUp() {
                mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                .apply(springSecurity())
                                .build();

                noteRepository.deleteAll();
                tagRepository.deleteAll();

                long timestamp = System.nanoTime();
                String emailA = "usera_" + timestamp + "@example.com";
                String emailB = "userb_" + timestamp + "@example.com";

                userAToken = userService.register(emailA, "Password123!", "User A");
                User userA = userRepository.findByEmail(emailA).orElseThrow();
                userAId = userA.getUserId();

                userBToken = userService.register(emailB, "Password123!", "User B");
                User userB = userRepository.findByEmail(emailB).orElseThrow();
                userBId = userB.getUserId();
        }

        @Test
        @DisplayName("Problem 1: Filter runs and validates No Token, Corrupted Token, and Valid Token")
        void problem1_proveJwtFilterExecutionBranches() throws Exception {
                // Branch 1: Protected endpoint with NO token -> 403 Forbidden / 401 Unauthenticated
                mockMvc.perform(get("/notes"))
                                .andExpect(status().isForbidden());

                // Branch 2: Protected endpoint with Deliberately Corrupted token -> 403 Forbidden
                mockMvc.perform(get("/notes")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer invalid.corrupted.token123"))
                                .andExpect(status().isForbidden());

                // Branch 3: Protected endpoint with VALID token -> 200 OK
                mockMvc.perform(get("/notes")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                                .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Problem 2: IDOR Prevention - Authenticated identity from SecurityContextHolder restricts access")
        void problem2_idorVulnerabilityPrevention() throws Exception {
                // User A creates Note A
                Note noteA = noteService.createNote(userAId, "User A Note", "Private thoughts of User A");
                // User B creates Note B
                Note noteB = noteService.createNote(userBId, "User B Note", "Private thoughts of User B");

                // User A requests GET /notes -> only Note A is returned, never Note B
                MvcResult resultA = mockMvc.perform(get("/notes")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                                .andExpect(status().isOk())
                                .andReturn();

                String responseJson = resultA.getResponse().getContentAsString();
                assertTrue(responseJson.contains("User A Note"));
                assertFalse(responseJson.contains("User B Note"));

                // Direct lookup of Note B by User A -> Returns 404 Not Found (no IDOR)
                mockMvc.perform(get("/notes/" + noteB.getNoteId())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                                .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Problem 3: Trigger LazyInitializationException and resolve via JOIN FETCH")
        void problem3_lazyInitializationAndJoinFetchResolution() {
                // Create notes for User A
                noteService.createNote(userAId, "Lazy Note 1", "Content 1");
                noteService.createNote(userAId, "Lazy Note 2", "Content 2");

                //1. Load detached User entity outside of open transaction
                // (open-in-view=false)
                TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);
                User detachedUser = txTemplate.execute(status -> userRepository.findById(userAId).orElseThrow());

                // Outside transaction, accessing lazy notes collection throws
                // LazyInitializationException
                assertNotNull(detachedUser);
                assertThrows(LazyInitializationException.class, () -> {
                        detachedUser.getNotes().size();
                }, "Accessing LAZY collection on detached entity after session closed must throw LazyInitializationException");

                //2. Fix using repository query with JOIN FETCH
                User userWithNotesEager = userRepository.findByIdWithNotes(userAId).orElseThrow();
                assertNotNull(userWithNotesEager);
                assertDoesNotThrow(() -> {
                        assertEquals(2, userWithNotesEager.getNotes().size());
                }, "JOIN FETCH must initialize collection eagerly within single query");
        }

        @Test
        @DisplayName("Problem 4: Note and Tag @ManyToMany Relationship with note_tags Join Table")
        void problem4_noteAndTagManyToManyRelationship() throws Exception {
                NoteRequest request = new NoteRequest("Architectural Guide", "Spring Security and JPA",
                                Set.of("Architecture", "Security", "JPA"));

                MvcResult result = mockMvc.perform(post("/notes")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isCreated())
                                .andReturn();

                NoteResponse createdNote = objectMapper.readValue(result.getResponse().getContentAsString(),
                                NoteResponse.class);
                assertEquals("Architectural Guide", createdNote.title());
                assertEquals(3, createdNote.tags().size());
                assertTrue(createdNote.tags().contains("Architecture"));
                assertTrue(createdNote.tags().contains("Security"));
                assertTrue(createdNote.tags().contains("JPA"));

                // Verify tags persisted in TagRepository and note_tags junction
                Optional<Tag> secTag = tagRepository.findByName("Security");
                assertTrue(secTag.isPresent());

                // Add an additional tag to the note via NoteService
                noteService.addTagToNote(createdNote.noteId(), userAId, "DevOps");

                Note noteWithTags = noteRepository.findByNoteIdAndOwnerWithTags(createdNote.noteId(), new User(userAId))
                                .orElseThrow();
                assertEquals(4, noteWithTags.getTags().size());
        }

        @Test
        @DisplayName("Problem 5: Someone Else's Note - User B cannot delete User A's note and gets 404")
        void problem5_someoneElsesNoteDeleteReturns404() throws Exception {
                // User A creates a note
                NoteRequest noteRequest = new NoteRequest("User A Classified", "Confidential Details");
                MvcResult createResult = mockMvc.perform(post("/notes")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(noteRequest)))
                                .andExpect(status().isCreated())
                                .andReturn();

                NoteResponse noteA = objectMapper.readValue(createResult.getResponse().getContentAsString(),
                                NoteResponse.class);
                int noteAId = noteA.noteId();

                // User B attempts to DELETE User A's note -> 404 Not Found (not 403 to prevent
                // probing)
                mockMvc.perform(delete("/notes/" + noteAId)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userBToken))
                                .andExpect(status().isNotFound());

                // As User A, verify note still exists in database and API
                mockMvc.perform(get("/notes/" + noteAId)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.title").value("User A Classified"));

                assertTrue(noteRepository.existsById(noteAId));
        }

        @Test
        @DisplayName("Problem 6: Extend Authorization to Update (PUT /notes/{id}) - 404 for unauthorized user")
        void problem6_extendAuthorizationToUpdate() throws Exception {
                // User A creates a note
                Note noteA = noteService.createNote(userAId, "Original Title", "Original Content");
                int noteId = noteA.getNoteId();

                // User B attempts to UPDATE User A's note via PUT -> 404 Not Found
                NoteRequest tamperRequest = new NoteRequest("Hacked Title", "Hacked Content");
                mockMvc.perform(put("/notes/" + noteId)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userBToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(tamperRequest)))
                                .andExpect(status().isNotFound());

                // Verify User A's note was NOT modified
                Note unchangedNote = noteRepository.findById(noteId).orElseThrow();
                assertEquals("Original Title", unchangedNote.getTitle());
                assertEquals("Original Content", unchangedNote.getContent());

                // User A updates their own note -> 200 OK
                NoteRequest validUpdateRequest = new NoteRequest("Updated Title", "Updated Content", Set.of("Updated"));
                mockMvc.perform(put("/notes/" + noteId)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validUpdateRequest)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.title").value("Updated Title"))
                                .andExpect(jsonPath("$.content").value("Updated Content"));

                Note updatedNote = noteRepository.findById(noteId).orElseThrow();
                assertEquals("Updated Title", updatedNote.getTitle());
                assertEquals("Updated Content", updatedNote.getContent());
        }

        @Test
        @DisplayName("Complete Note CRUD and User Cascade Deletion verification")
        void completeNoteCrudAndCascadeTest() {
                Note note1 = noteService.createNote(userAId, "Note 1", "Content 1");
                Note note2 = noteService.createNote(userAId, "Note 2", "Content 2");

                List<Note> userANotes = noteService.findByOwner(userAId);
                assertEquals(2, userANotes.size());

                boolean deleted = noteService.deleteNote(note1.getNoteId(), userAId);
                assertTrue(deleted);
                assertEquals(1, noteService.findByOwner(userAId).size());
        }
}

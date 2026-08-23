package com.bridgelabz.fundoonotes;

import com.bridgelabz.fundoonotes.dto.NoteRequest;
import com.bridgelabz.fundoonotes.dto.NoteResponse;
import com.bridgelabz.fundoonotes.dto.TagRequest;
import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.entity.Tag;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.TagRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.NoteService;
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
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class NoteOrganisationIntegrationTest {

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
        String emailA = "day15_usera_" + timestamp + "@example.com";
        String emailB = "day15_userb_" + timestamp + "@example.com";

        userAToken = userService.register(emailA, "Password123!", "Day15 User A");
        User userA = userRepository.findByEmail(emailA).orElseThrow();
        userAId = userA.getUserId();

        userBToken = userService.register(emailB, "Password123!", "Day15 User B");
        User userB = userRepository.findByEmail(emailB).orElseThrow();
        userBId = userB.getUserId();
    }

    @Test
    @DisplayName("Problem 1: Prove Invalid State Combinations Are Prevented (Trash -> Pin returns 400 Bad Request)")
    void problem1_proveInvalidStateCombinationsPrevented() throws Exception {
        // Create active note
        Note note = noteService.createNote(userAId, "Meeting Notes", "Discuss Q3 Roadmap");
        int noteId = note.getNoteId();

        // Call PATCH /notes/{id}/trash
        mockMvc.perform(patch("/notes/" + noteId + "/trash")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("TRASHED"))
                .andExpect(jsonPath("$.pinned").value(false));

        // Immediately call PATCH /notes/{id}/pin on the trashed note
        MvcResult pinResult = mockMvc.perform(patch("/notes/" + noteId + "/pin")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Verify the response status is 400 and body contains IllegalStateException message
        String responseBody = pinResult.getResponse().getContentAsString();
        assertTrue(responseBody.contains("Cannot pin a note that is in Trash"),
                "Response must contain sensible error message preventing invalid state");

        // Verify note in database remains TRASHED and unpinned
        Note persisted = noteRepository.findById(noteId).orElseThrow();
        assertEquals(Note.NoteState.TRASHED, persisted.getState());
        assertFalse(persisted.isPinned());
    }

    @Test
    @DisplayName("Problem 2: State Transition Rules (Pin -> Archive -> Restore lifecycle)")
    void problem2_stateTransitionLifecyclePinArchiveRestore() throws Exception {
        // Create a note
        Note note = noteService.createNote(userAId, "Important Ideas", "Key design ideas");
        int noteId = note.getNoteId();
        assertEquals(Note.NoteState.ACTIVE, note.getState());
        assertFalse(note.isPinned());

        // Pin the note -> pinned = true
        mockMvc.perform(patch("/notes/" + noteId + "/pin")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pinned").value(true));

        // Archive the note -> state = ARCHIVED, pinned automatically cleared to false
        mockMvc.perform(patch("/notes/" + noteId + "/archive")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("ARCHIVED"))
                .andExpect(jsonPath("$.pinned").value(false));

        // Restore the note -> state = ACTIVE, pinned remains false per defined transition rule
        mockMvc.perform(patch("/notes/" + noteId + "/restore")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("ACTIVE"))
                .andExpect(jsonPath("$.pinned").value(false));

        Note restoredNote = noteRepository.findById(noteId).orElseThrow();
        assertEquals(Note.NoteState.ACTIVE, restoredNote.getState());
        assertFalse(restoredNote.isPinned());

        // Re-pin explicitly to verify note is once again pinnable in ACTIVE state
        mockMvc.perform(patch("/notes/" + noteId + "/pin")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pinned").value(true));
    }

    @Test
    @DisplayName("Problem 3: Build Full Dynamic Search Endpoint with Specification")
    void problem3_buildFullSearchEndpoint() throws Exception {
        // Create test notes for User A
        Note note1 = noteService.createNote(userAId, "Grocery shopping list", "Milk, eggs, bread", Set.of("urgent", "home"));
        Note note2 = noteService.createNote(userAId, "Work project tasks", "Backend API implementation", Set.of("work", "urgent"));
        Note note3 = noteService.createNote(userAId, "Archived grocery coupons", "Discount vouchers", Set.of("home"));
        noteService.archiveNote(note3.getNoteId(), userAId);

        // Case 1: No filters supplied -> returns all notes belonging to user across all states
        MvcResult noFilterResult = mockMvc.perform(get("/notes/search")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                .andExpect(status().isOk())
                .andReturn();
        List<NoteResponse> allNotes = objectMapper.readValue(noFilterResult.getResponse().getContentAsString(),
                new TypeReference<List<NoteResponse>>() {});
        assertEquals(3, allNotes.size());

        // Case 2: Title only -> search "grocery"
        MvcResult titleResult = mockMvc.perform(get("/notes/search?title=grocery")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                .andExpect(status().isOk())
                .andReturn();
        List<NoteResponse> groceryNotes = objectMapper.readValue(titleResult.getResponse().getContentAsString(),
                new TypeReference<List<NoteResponse>>() {});
        assertEquals(2, groceryNotes.size());

        // Case 3: Tag only -> search "urgent"
        MvcResult tagResult = mockMvc.perform(get("/notes/search?tag=urgent")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                .andExpect(status().isOk())
                .andReturn();
        List<NoteResponse> urgentNotes = objectMapper.readValue(tagResult.getResponse().getContentAsString(),
                new TypeReference<List<NoteResponse>>() {});
        assertEquals(2, urgentNotes.size());

        // Case 4: Title + Tag -> search title="grocery" and tag="urgent"
        MvcResult titleAndTagResult = mockMvc.perform(get("/notes/search?title=grocery&tag=urgent")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                .andExpect(status().isOk())
                .andReturn();
        List<NoteResponse> groceryUrgentNotes = objectMapper.readValue(titleAndTagResult.getResponse().getContentAsString(),
                new TypeReference<List<NoteResponse>>() {});
        assertEquals(1, groceryUrgentNotes.size());
        assertEquals("Grocery shopping list", groceryUrgentNotes.get(0).title());

        // Case 5: Title + Tag + State -> search title="grocery", tag="home", state="archived"
        MvcResult allFiltersResult = mockMvc.perform(get("/notes/search?title=grocery&tag=home&state=archived")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                .andExpect(status().isOk())
                .andReturn();
        List<NoteResponse> archivedGroceryNotes = objectMapper.readValue(allFiltersResult.getResponse().getContentAsString(),
                new TypeReference<List<NoteResponse>>() {});
        assertEquals(1, archivedGroceryNotes.size());
        assertEquals("Archived grocery coupons", archivedGroceryNotes.get(0).title());
    }

    @Test
    @DisplayName("Problem 4: Prove Authorization Boundary Holds in Dynamic Specification")
    void problem4_proveAuthorizationBoundaryInSpecification() throws Exception {
        // User A and User B create notes with identical titles and tags
        noteService.createNote(userAId, "Confidential Strategy Plan", "User A Strategy Details", Set.of("strategy", "leadership"));
        noteService.createNote(userBId, "Confidential Strategy Plan", "User B Strategy Details", Set.of("strategy", "finance"));

        // User A searches by title "Strategy" -> ONLY User A's note is returned
        MvcResult userASearchResult = mockMvc.perform(get("/notes/search?title=Strategy")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                .andExpect(status().isOk())
                .andReturn();
        List<NoteResponse> userAResults = objectMapper.readValue(userASearchResult.getResponse().getContentAsString(),
                new TypeReference<List<NoteResponse>>() {});
        assertEquals(1, userAResults.size());
        assertEquals(userAId, userAResults.get(0).ownerId());
        assertEquals("User A Strategy Details", userAResults.get(0).content());

        // User B searches by tag "strategy" -> ONLY User B's note is returned
        MvcResult userBSearchResult = mockMvc.perform(get("/notes/search?tag=strategy")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userBToken))
                .andExpect(status().isOk())
                .andReturn();
        List<NoteResponse> userBResults = objectMapper.readValue(userBSearchResult.getResponse().getContentAsString(),
                new TypeReference<List<NoteResponse>>() {});
        assertEquals(1, userBResults.size());
        assertEquals(userBId, userBResults.get(0).ownerId());
        assertEquals("User B Strategy Details", userBResults.get(0).content());
    }

    @Test
    @DisplayName("Problem 5: Add and Query Tags End to End with note_tags Junction Table")
    void problem5_addAndQueryTagsEndToEnd() throws Exception {
        // Create 3 notes for User A
        Note note1 = noteService.createNote(userAId, "Server Deployment", "Deploy to AWS");
        Note note2 = noteService.createNote(userAId, "Database Migration", "Run Flyway scripts");
        Note note3 = noteService.createNote(userAId, "Personal Diary", "Weekend reflections");

        // Add tag "urgent" to Note 1 via POST /notes/{id}/tags
        mockMvc.perform(post("/notes/" + note1.getNoteId() + "/tags")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new TagRequest("urgent"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tags").isArray());

        // Add tag "urgent" to Note 2
        mockMvc.perform(post("/notes/" + note2.getNoteId() + "/tags")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new TagRequest("urgent"))))
                .andExpect(status().isOk());

        // Add tag "personal" to Note 3
        mockMvc.perform(post("/notes/" + note3.getNoteId() + "/tags")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new TagRequest("personal"))))
                .andExpect(status().isOk());

        // Query GET /notes?tag=urgent -> exactly Note 1 and Note 2
        MvcResult tagFilterResult = mockMvc.perform(get("/notes?tag=urgent")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                .andExpect(status().isOk())
                .andReturn();
        List<NoteResponse> urgentNotes = objectMapper.readValue(tagFilterResult.getResponse().getContentAsString(),
                new TypeReference<List<NoteResponse>>() {});
        assertEquals(2, urgentNotes.size());
        assertTrue(urgentNotes.stream().anyMatch(n -> n.title().equals("Server Deployment")));
        assertTrue(urgentNotes.stream().anyMatch(n -> n.title().equals("Database Migration")));

        // Verify Tag entity and note_tags relationships in database
        Optional<Tag> urgentTag = tagRepository.findByName("urgent");
        assertTrue(urgentTag.isPresent());
        assertEquals("urgent", urgentTag.get().getName());
    }

    @Test
    @DisplayName("Problem 6: Combine Organisation Features (Pin, Archive, Trash, Tag & Dynamic Filters)")
    void problem6_combineOrganisationFeatures() throws Exception {
        // Create 5 distinct notes
        Note n1 = noteService.createNote(userAId, "Work budget report", "Q4 financial analysis", Set.of("work", "finance"));
        noteService.pinNote(n1.getNoteId(), userAId);

        Note n2 = noteService.createNote(userAId, "Home budget plan", "Monthly groceries and savings", Set.of("home", "finance"));
        noteService.archiveNote(n2.getNoteId(), userAId);

        Note n3 = noteService.createNote(userAId, "Work sprint tasks", "Sprint 42 backlog grooming", Set.of("work", "dev"));

        Note n4 = noteService.createNote(userAId, "Old budget draft", "Deprecated draft 2025", Set.of("finance"));
        noteService.trashNote(n4.getNoteId(), userAId);

        Note n5 = noteService.createNote(userAId, "Pinned idea", "Brainstorm startup idea", Set.of("personal"));
        noteService.pinNote(n5.getNoteId(), userAId);

        //1. Default active view GET /notes -> returns only active notes (n1, n3, n5)
        MvcResult defaultResult = mockMvc.perform(get("/notes")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                .andExpect(status().isOk())
                .andReturn();
        List<NoteResponse> activeNotes = objectMapper.readValue(defaultResult.getResponse().getContentAsString(),
                new TypeReference<List<NoteResponse>>() {});
        assertEquals(3, activeNotes.size());
        assertTrue(activeNotes.stream().anyMatch(n -> n.noteId() == n1.getNoteId()));
        assertTrue(activeNotes.stream().anyMatch(n -> n.noteId() == n3.getNoteId()));
        assertTrue(activeNotes.stream().anyMatch(n -> n.noteId() == n5.getNoteId()));

        //2. State views GET /notes?state=archived -> n2
        mockMvc.perform(get("/notes?state=archived")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Home budget plan"));

        // GET /notes?state=trashed -> n4
        mockMvc.perform(get("/notes?state=trashed")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Old budget draft"));

        //3. Pinned active search GET /notes?pinned=true -> returns n1 and n5
        mockMvc.perform(get("/notes?pinned=true")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        // 4. Search active notes tagged "work" GET /notes/search?state=active&tag=work -> returns n1 and n3
        mockMvc.perform(get("/notes/search?state=active&tag=work")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        // 5. Search every note containing "budget" in title -> returns n1, n2, n4
        mockMvc.perform(get("/notes/search?title=budget")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }
}

package com.bridgelabz.fundoonotes;

import com.bridgelabz.fundoonotes.dto.CheckListRequest;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class CheckListIntegrationTest {

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

        private String ownerToken;
        private int ownerId;

        private String strangerToken;
        private int strangerId;

        @BeforeEach
        void setUp() {
                mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                .apply(springSecurity())
                                .build();

                long ts = System.nanoTime();
                String ownerEmail = "cl_owner_" + ts + "@example.com";
                ownerToken = userService.register(ownerEmail, "Password@123", "Checklist Owner", "Checklist", "Owner");
                ownerId = userRepository.findByEmail(ownerEmail).get().getUserId();

                String strangerEmail = "cl_stranger_" + ts + "@example.com";
                strangerToken = userService.register(strangerEmail, "Password@123", "Checklist Stranger", "Checklist",
                                "Stranger");
                strangerId = userRepository.findByEmail(strangerEmail).get().getUserId();
        }

        @Test
        @DisplayName("Use Case 12: Checklist CRUD, bulk complete, and ownership authorization")
        void testChecklistItemsLifecycleAndSecurity() throws Exception {
                // 1. Create a Note of type CHECKLIST
                User owner = userRepository.findById(ownerId).get();
                Note note = new Note("Grocery List", "Items to buy", owner);
                note.setTypeOfNote("CHECKLIST");
                note = noteRepository.save(note);
                int noteId = note.getNoteId();

                // 2. Add first checklist item
                CheckListRequest item1Req = new CheckListRequest("Buy Milk", "PENDING", false);
                MvcResult addResult1 = mockMvc.perform(post("/notes/" + noteId + "/noteCheckLists")
                                .header("Authorization", "Bearer " + ownerToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(item1Req)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.itemName").value("Buy Milk"))
                                .andExpect(jsonPath("$.status").value("PENDING"))
                                .andExpect(jsonPath("$.notesId").value(noteId))
                                .andReturn();

                int item1Id = objectMapper.readTree(addResult1.getResponse().getContentAsString()).get("id").asInt();

                // 3. Add second checklist item
                CheckListRequest item2Req = new CheckListRequest("Buy Bread", "PENDING", false);
                mockMvc.perform(post("/notes/" + noteId + "/noteCheckLists")
                                .header("Authorization", "Bearer " + ownerToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(item2Req)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.itemName").value("Buy Bread"));

                // 4. Stranger CANNOT add items to owner's note
                mockMvc.perform(post("/notes/" + noteId + "/noteCheckLists")
                                .header("Authorization", "Bearer " + strangerToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(item1Req)))
                                .andExpect(status().isNotFound());

                // 5. Get checklist items for the note
                mockMvc.perform(get("/notes/" + noteId + "/noteCheckLists")
                                .header("Authorization", "Bearer " + ownerToken))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(2));

                // 6. Update item1 to "DONE"
                CheckListRequest updateReq = new CheckListRequest("Buy Organic Milk", "DONE", false);
                mockMvc.perform(put("/notes/" + noteId + "/noteCheckLists/" + item1Id)
                                .header("Authorization", "Bearer " + ownerToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateReq)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.itemName").value("Buy Organic Milk"))
                                .andExpect(jsonPath("$.status").value("DONE"));

                // 7. Bulk complete all items
                mockMvc.perform(patch("/notes/" + noteId + "/noteCheckLists/completeAll")
                                .header("Authorization", "Bearer " + ownerToken))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].status").value("DONE"))
                                .andExpect(jsonPath("$[1].status").value("DONE"));

                // 8. Delete item1
                mockMvc.perform(delete("/notes/" + noteId + "/noteCheckLists/" + item1Id)
                                .header("Authorization", "Bearer " + ownerToken))
                                .andExpect(status().isOk());

                // 9. Verify only 1 active item remains
                mockMvc.perform(get("/notes/" + noteId + "/noteCheckLists")
                                .header("Authorization", "Bearer " + ownerToken))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(1));
        }
}

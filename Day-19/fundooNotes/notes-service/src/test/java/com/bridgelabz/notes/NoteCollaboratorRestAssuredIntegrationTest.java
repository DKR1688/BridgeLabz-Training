package com.bridgelabz.notes;

import com.bridgelabz.notes.client.UserServiceClient;
import com.bridgelabz.notes.dto.CollaboratorResponse;
import com.bridgelabz.notes.entity.NoteCollaborator;
import com.bridgelabz.notes.security.JwtUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

/**
 * Level 3: REST Assured Integration Tests (Full End-to-End Test).
 * Tests real HTTP calls, real database operations, real Spring security filter chain,
 * and verifies Use Case 21 Role-Based Permissions end-to-end.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteCollaboratorRestAssuredIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JwtUtil jwtUtil;

    @MockBean
    private UserServiceClient userServiceClient;

    private String ownerToken;
    private String collaboratorToken;
    private String unrelatedUserToken;

    private final int ownerId = 101;
    private final int collaboratorId = 102;
    private final int unrelatedUserId = 103;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        ownerToken = "Bearer " + jwtUtil.generateToken(ownerId, "owner@example.com");
        collaboratorToken = "Bearer " + jwtUtil.generateToken(collaboratorId, "collaborator@example.com");
        unrelatedUserToken = "Bearer " + jwtUtil.generateToken(unrelatedUserId, "unrelated@example.com");

        when(userServiceClient.userExists(collaboratorId)).thenReturn(true);
        when(userServiceClient.getUserDetails(collaboratorId))
                .thenReturn(new CollaboratorResponse(collaboratorId, "collaborator@example.com", "Collab User"));
    }

    @Test
    @DisplayName("Level 3 Use Case 21: Full Role-Based Collaborator Lifecycle (Viewer -> Editor -> Owner Delete)")
    void testCollaboratorRolesLifecycleEndToEnd() {
        // Step 1: Owner creates a new note
        int noteId = given()
                .header("Authorization", ownerToken)
                .contentType(ContentType.JSON)
                .body("{\"title\":\"Collaborative Project Plan\",\"content\":\"Initial draft content\"}")
                .when()
                .post("/notes")
                .then()
                .statusCode(201)
                .body("title", equalTo("Collaborative Project Plan"))
                .extract()
                .path("noteId");

        // Step 2: Owner adds User 102 as a VIEWER
        given()
                .header("Authorization", ownerToken)
                .contentType(ContentType.JSON)
                .body("{\"userId\":" + collaboratorId + ",\"role\":\"VIEWER\"}")
                .when()
                .post("/notes/" + noteId + "/collaborators")
                .then()
                .statusCode(200)
                .body("message", equalTo("Collaborator added successfully"));

        // Step 3: Viewer (User 102) can GET/view the shared note
        given()
                .header("Authorization", collaboratorToken)
                .when()
                .get("/notes/" + noteId)
                .then()
                .statusCode(200)
                .body("noteId", equalTo(noteId))
                .body("title", equalTo("Collaborative Project Plan"));

        // Step 4: Viewer (User 102) attempts PUT/modification -> MUST FAIL with 400 (InvalidNoteStateException)
        given()
                .header("Authorization", collaboratorToken)
                .contentType(ContentType.JSON)
                .body("{\"title\":\"Unauthorized Modification Attempt\"}")
                .when()
                .put("/notes/" + noteId)
                .then()
                .statusCode(400)
                .body("message", containsString("Viewer role cannot modify this note"));

        // Step 5: Owner promotes User 102 to EDITOR (takes effect immediately, no new token required)
        given()
                .header("Authorization", ownerToken)
                .contentType(ContentType.JSON)
                .body("{\"userId\":" + collaboratorId + ",\"role\":\"EDITOR\"}")
                .when()
                .post("/notes/" + noteId + "/collaborators")
                .then()
                .statusCode(200);

        // Step 6: Editor (User 102) updates note content -> SUCCEEDS with 200 OK
        given()
                .header("Authorization", collaboratorToken)
                .contentType(ContentType.JSON)
                .body("{\"title\":\"Collaborative Project Plan - Edited by Collab\",\"content\":\"Updated content by Editor\"}")
                .when()
                .put("/notes/" + noteId)
                .then()
                .statusCode(200)
                .body("title", equalTo("Collaborative Project Plan - Edited by Collab"))
                .body("content", equalTo("Updated content by Editor"));

        // Step 7: Editor (User 102) attempts DELETE note -> MUST FAIL with 403 Forbidden (Owner-only action)
        given()
                .header("Authorization", collaboratorToken)
                .when()
                .delete("/notes/" + noteId)
                .then()
                .statusCode(403);

        // Step 8: Unrelated User (User 103) cannot access note (404 Not Found - IDOR protection)
        given()
                .header("Authorization", unrelatedUserToken)
                .when()
                .get("/notes/" + noteId)
                .then()
                .statusCode(404);

        // Step 9: Owner deletes note permanently -> SUCCEEDS with 200 OK
        given()
                .header("Authorization", ownerToken)
                .when()
                .delete("/notes/" + noteId)
                .then()
                .statusCode(200)
                .body("message", equalTo("Note deleted permanently"));

        // Step 10: Verify note is gone
        given()
                .header("Authorization", ownerToken)
                .when()
                .get("/notes/" + noteId)
                .then()
                .statusCode(404);
    }
}

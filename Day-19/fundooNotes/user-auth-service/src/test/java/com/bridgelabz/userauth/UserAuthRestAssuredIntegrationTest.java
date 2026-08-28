package com.bridgelabz.userauth;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Level 3: REST Assured Integration Tests for user-auth-service.
 * Tests entire real user lifecycle: Registration -> Duplicate Detection -> Login -> Forgot & Reset Password -> Re-login.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserAuthRestAssuredIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    @DisplayName("Level 3 Use Case 2 & 8: Full User Auth, Duplicate Rejection, and Password Recovery Lifecycle")
    void testFullUserAuthAndRecoveryLifecycle() {
        String testEmail = "testuser" + System.currentTimeMillis() + "@fundoonotes.app";

        // Step 1: Register new user
        String token = given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"" + testEmail + "\",\"password\":\"StrongPass@123\",\"firstName\":\"John\",\"lastName\":\"Doe\"}")
                .when()
                .post("/auth/register")
                .then()
                .statusCode(201)
                .body("token", notNullValue())
                .extract()
                .path("token");

        // Step 2: Attempt duplicate registration -> MUST FAIL with 409 or 400
        given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"" + testEmail + "\",\"password\":\"StrongPass@123\",\"firstName\":\"Duplicate\",\"lastName\":\"User\"}")
                .when()
                .post("/auth/register")
                .then()
                .statusCode(anyOf(equalTo(400), equalTo(409)))
                .body("message", containsString("already in use"));

        // Step 3: Login with registered credentials -> returns 200 OK
        given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"" + testEmail + "\",\"password\":\"StrongPass@123\"}")
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue());

        // Step 4: Request Password Reset via /auth/forgot-password (Asynchronous JMS dispatch)
        String resetToken = given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"" + testEmail + "\"}")
                .when()
                .post("/auth/forgot-password")
                .then()
                .statusCode(200)
                .body("resetToken", notNullValue())
                .extract()
                .path("resetToken");

        // Step 5: Reset Password using generated reset token
        given()
                .contentType(ContentType.JSON)
                .body("{\"token\":\"" + resetToken + "\",\"newPassword\":\"NewStrongPass@456\"}")
                .when()
                .post("/auth/reset-password")
                .then()
                .statusCode(200)
                .body("message", equalTo("Password reset successful"));

        // Step 6: Login with old password fails (400 or 401)
        given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"" + testEmail + "\",\"password\":\"StrongPass@123\"}")
                .when()
                .post("/auth/login")
                .then()
                .statusCode(anyOf(equalTo(400), equalTo(401)));

        // Step 7: Login with new password succeeds (200 OK)
        given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"" + testEmail + "\",\"password\":\"NewStrongPass@456\"}")
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }
}

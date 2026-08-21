package com.bridgelabz.fundoonotes;

import com.bridgelabz.fundoonotes.dto.LoginRequest;
import com.bridgelabz.fundoonotes.dto.RegisterRequest;
import com.bridgelabz.fundoonotes.security.JwtUtil;
import com.bridgelabz.fundoonotes.service.PasswordRecoveryService;
import com.bridgelabz.fundoonotes.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AuthFlowIntegrationTest {

        @Autowired
        private WebApplicationContext webApplicationContext;

        private MockMvc mockMvc;

        @Autowired
        private UserService userService;

        @Autowired
        private PasswordRecoveryService passwordRecoveryService;

        @Autowired
        private JwtUtil jwtUtil;

        private final ObjectMapper objectMapper = new ObjectMapper();

        @BeforeEach
        void setUp() {
                mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                .apply(springSecurity())
                                .build();
        }

        @Test
        @DisplayName("Use Case 2: POST /user/userSignUp and /user/login endpoints return JWT tokens")
        void testUserSignUpAndLoginEndpoints() throws Exception {
                String email = "testuser_" + System.nanoTime() + "@example.com";
                RegisterRequest registerReq = new RegisterRequest(email, "Password123!", null, "John", "Doe");

                // 1. POST /user/userSignUp -> 201 Created with token
                mockMvc.perform(post("/user/userSignUp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(registerReq)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.token").isNotEmpty());

                // 2. Duplicate registration -> 409 Conflict
                mockMvc.perform(post("/user/userSignUp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(registerReq)))
                                .andExpect(status().isConflict());

                // 3. POST /user/login with correct credentials -> 200 OK with token
                LoginRequest loginReq = new LoginRequest(email, "Password123!");
                mockMvc.perform(post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginReq)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.token").isNotEmpty());

                // 4. POST /user/login with wrong password -> 401 Unauthorized
                LoginRequest wrongLogin = new LoginRequest(email, "WrongPassword!");
                mockMvc.perform(post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(wrongLogin)))
                                .andExpect(status().isUnauthorized());
        }

        @Test
        void registrationLoginJwtAndDuplicateEmailWork() {
                String email = "priya" + System.nanoTime() + "@example.com";
                String registrationToken = userService.register(email, "SecurePass123", "Priya Mehta");
                Claims claims = jwtUtil.parseSignedClaims(registrationToken);
                assertEquals(email, claims.get("email", String.class));
                assertNotNull(claims.getSubject());
                assertDoesNotThrow(() -> userService.login(email, "SecurePass123"));
                assertThrows(IllegalArgumentException.class,
                                () -> userService.register(email, "AnotherPass123", "Priya"));
        }

        @Test
        void recoveryTokenCanBeUsedOnlyOnce() {
                String email = "reset" + System.nanoTime() + "@example.com";
                userService.register(email, "SecurePass123", "Reset User");
                String resetToken = passwordRecoveryService.requestReset(email);
                passwordRecoveryService.resetPassword(resetToken, "ChangedPass123");
                assertDoesNotThrow(() -> userService.login(email, "ChangedPass123"));
                assertThrows(IllegalArgumentException.class,
                                () -> passwordRecoveryService.resetPassword(resetToken, "AnotherPass123"));
        }
}

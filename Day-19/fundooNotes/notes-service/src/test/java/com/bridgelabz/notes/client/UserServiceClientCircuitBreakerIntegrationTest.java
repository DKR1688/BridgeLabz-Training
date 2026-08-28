package com.bridgelabz.notes.client;

import com.bridgelabz.notes.dto.CollaboratorResponse;
import com.bridgelabz.notes.exception.UserServiceUnavailableException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Level 3 / Use Case 24: Tests Resilience4j Circuit Breaker around UserServiceClient.
 * Validates fallback method execution and fast failure when user-auth-service is unavailable.
 */
@SpringBootTest
class UserServiceClientCircuitBreakerIntegrationTest {

    @Autowired
    private UserServiceClient userServiceClient;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    @DisplayName("Use Case 24: UserServiceClient invokes fallback when user-auth-service throws RestClientException")
    void userExists_whenUserServiceDown_triggersFallbackAndThrowsServiceUnavailable() {
        when(restTemplate.getForObject(anyString(), eq(Object.class)))
                .thenThrow(new RestClientException("Connection refused"));

        UserServiceUnavailableException ex = assertThrows(UserServiceUnavailableException.class,
                () -> userServiceClient.userExists(999));

        assertTrue(ex.getMessage().contains("User service is currently unavailable") || ex.getMessage().contains("circuit breaker"));
    }

    @Test
    @DisplayName("Use Case 24: getUserDetails returns graceful fallback object when user-auth-service is down")
    void getUserDetails_whenUserServiceDown_returnsFallbackResponse() {
        when(restTemplate.getForObject(anyString(), eq(CollaboratorResponse.class)))
                .thenThrow(new RestClientException("Timeout connecting to user-auth-service"));

        CollaboratorResponse response = userServiceClient.getUserDetails(999);

        assertNotNull(response);
        assertEquals(999, response.getUserId());
        assertEquals("Unavailable User", response.getName());
    }
}

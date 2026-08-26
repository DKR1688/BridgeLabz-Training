package com.bridgelabz.notes.client;

import com.bridgelabz.notes.dto.CollaboratorResponse;
import com.bridgelabz.notes.exception.UserServiceUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class UserServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceClient.class);

    private final RestTemplate restTemplate;

    @Value("${app.user-service.base-url:http://user-auth-service}")
    private String userServiceBaseUrl;

    public UserServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean userExists(int userId) {
        try {
            restTemplate.getForObject(userServiceBaseUrl + "/users/" + userId, Object.class);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        } catch (RestClientException e) {
            logger.error("Inter-service call to user-auth-service failed: {}", e.getMessage());
            throw new UserServiceUnavailableException("User authentication service is currently unavailable: " + e.getMessage());
        }
    }

    public CollaboratorResponse getUserDetails(int userId) {
        try {
            return restTemplate.getForObject(userServiceBaseUrl + "/users/" + userId + "/details", CollaboratorResponse.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        } catch (RestClientException e) {
            logger.error("Inter-service call to user-auth-service failed: {}", e.getMessage());
            throw new UserServiceUnavailableException("User authentication service is currently unavailable: " + e.getMessage());
        }
    }
}

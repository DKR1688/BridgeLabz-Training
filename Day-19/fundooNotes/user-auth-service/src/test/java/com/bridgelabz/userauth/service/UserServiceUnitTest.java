package com.bridgelabz.userauth.service;

import com.bridgelabz.userauth.dto.AuthResponse;
import com.bridgelabz.userauth.dto.LoginRequest;
import com.bridgelabz.userauth.dto.RegisterRequest;
import com.bridgelabz.userauth.dto.UserResponse;
import com.bridgelabz.userauth.entity.User;
import com.bridgelabz.userauth.exception.DuplicateEmailException;
import com.bridgelabz.userauth.exception.InvalidCredentialsException;
import com.bridgelabz.userauth.exception.UserNotFoundException;
import com.bridgelabz.userauth.repository.UserRepository;
import com.bridgelabz.userauth.security.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Level 1: Business Logic Unit Tests for UserService (Use Case 2).
 */
@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Level 1: Registering with existing email throws DuplicateEmailException")
    void register_withDuplicateEmail_throwsDuplicateEmailException() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("existing@example.com");
        request.setPassword("password123");

        when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);

        assertThrows(DuplicateEmailException.class, () -> userService.register(request));
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Level 1: Registering with new email hashes password and returns JWT AuthResponse")
    void register_withNewEmail_succeedsAndHashesPassword() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("newuser@example.com");
        request.setPassword("plainPassword");
        request.setFirstName("John");
        request.setLastName("Doe");

        when(userRepository.existsByEmail("newuser@example.com")).thenReturn(false);
        when(passwordEncoder.encode("plainPassword")).thenReturn("hashedPassword123");

        User savedUser = new User();
        savedUser.setUserId(1);
        savedUser.setEmail("newuser@example.com");
        savedUser.setPasswordHash("hashedPassword123");
        savedUser.setFirstName("John");
        savedUser.setLastName("Doe");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtUtil.generateToken(1, "newuser@example.com")).thenReturn("mock-jwt-token");

        AuthResponse response = userService.register(request);

        assertNotNull(response);
        assertEquals("mock-jwt-token", response.getToken());
        verify(passwordEncoder).encode("plainPassword");
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Level 1: Login with valid credentials returns AuthResponse")
    void login_withValidCredentials_returnsAuthResponse() {
        User user = new User();
        user.setUserId(1);
        user.setEmail("user@example.com");
        user.setPasswordHash("hashedPassword");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "hashedPassword")).thenReturn(true);
        when(jwtUtil.generateToken(1, "user@example.com")).thenReturn("valid-jwt-token");

        LoginRequest request = new LoginRequest("user@example.com", "password123");
        AuthResponse response = userService.login(request);

        assertNotNull(response);
        assertEquals("valid-jwt-token", response.getToken());
    }

    @Test
    @DisplayName("Level 1: Login with invalid password throws InvalidCredentialsException")
    void login_withInvalidPassword_throwsInvalidCredentialsException() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setPasswordHash("hashedPassword");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "hashedPassword")).thenReturn(false);

        LoginRequest request = new LoginRequest("user@example.com", "wrongPassword");
        assertThrows(InvalidCredentialsException.class, () -> userService.login(request));
    }

    @Test
    @DisplayName("Level 1: Login with non-existent email throws InvalidCredentialsException")
    void login_withUnknownEmail_throwsInvalidCredentialsException() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        LoginRequest request = new LoginRequest("unknown@example.com", "anyPassword");
        assertThrows(InvalidCredentialsException.class, () -> userService.login(request));
    }

    @Test
    @DisplayName("Level 1: Get user by ID returns UserResponse")
    void getUserById_returnsUserResponse() {
        User user = new User();
        user.setUserId(5);
        user.setEmail("user5@example.com");
        user.setFirstName("Alice");
        user.setLastName("Smith");

        when(userRepository.findById(5)).thenReturn(Optional.of(user));

        UserResponse response = userService.getUserById(5);

        assertEquals(5, response.getUserId());
        assertEquals("user5@example.com", response.getEmail());
        assertEquals("Alice Smith", response.getName());
    }
}

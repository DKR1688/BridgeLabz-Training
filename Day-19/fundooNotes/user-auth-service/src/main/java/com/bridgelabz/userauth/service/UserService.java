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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email is already in use: " + request.getEmail());
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        User saved = userRepository.save(user);
        String token = jwtUtil.generateToken(saved.getUserId(), saved.getEmail());
        return new AuthResponse(token, "User registered successfully");
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getUserId(), user.getEmail());
        return new AuthResponse(token, "Login successful");
    }

    public UserResponse getUserById(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        return new UserResponse(user.getUserId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getName());
    }

    public boolean userExists(int userId) {
        return userRepository.existsById(userId);
    }

    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return new UserResponse(user.getUserId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getName());
    }
}

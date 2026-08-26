package com.bridgelabz.userauth.controller;

import com.bridgelabz.userauth.dto.UserResponse;
import com.bridgelabz.userauth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Minimal public endpoint for microservice inter-communication.
     * Returns 200 OK (no body) if user exists, 404 NOT_FOUND if not.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Void> checkUserExists(@PathVariable("id") int id) {
        if (userService.userExists(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<UserResponse> getUserDetails(@PathVariable("id") int id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            return ResponseEntity.status(401).build();
        }
        int userId = (int) authentication.getPrincipal();
        return ResponseEntity.ok(userService.getUserById(userId));
    }
}

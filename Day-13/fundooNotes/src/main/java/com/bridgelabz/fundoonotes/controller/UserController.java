package com.bridgelabz.fundoonotes.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class UserController {
    @GetMapping("/api/me")
    public Map<String, Object> currentUser(Authentication authentication) {
        return Map.of("userId", authentication.getName());
    }
}

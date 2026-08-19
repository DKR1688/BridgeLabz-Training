package com.bridgelabz.fundoonotes.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<Map<String, String>> illegalArgument(IllegalArgumentException exception) {
        HttpStatus status = exception.getMessage().equals("Email already registered") ? HttpStatus.CONFLICT
                : exception.getMessage().equals("Invalid email or password") ? HttpStatus.UNAUTHORIZED : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(Map.of("message", exception.getMessage()));
    }
}

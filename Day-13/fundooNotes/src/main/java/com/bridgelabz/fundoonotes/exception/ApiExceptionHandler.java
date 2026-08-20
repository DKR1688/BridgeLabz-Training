package com.bridgelabz.fundoonotes.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> illegalArgument(IllegalArgumentException exception) {
        String msg = exception.getMessage() != null ? exception.getMessage() : "Bad request";
        HttpStatus status;
        if ("Email already registered".equals(msg)) {
            status = HttpStatus.CONFLICT;
        } else if ("Invalid email or password".equals(msg)) {
            status = HttpStatus.UNAUTHORIZED;
        } else if ("Note not found".equals(msg) || "User not found".equals(msg)) {
            status = HttpStatus.NOT_FOUND;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(Map.of("message", msg));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> illegalState(IllegalStateException exception) {
        String msg = exception.getMessage() != null ? exception.getMessage() : "Invalid state transition";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", msg));
    }
}

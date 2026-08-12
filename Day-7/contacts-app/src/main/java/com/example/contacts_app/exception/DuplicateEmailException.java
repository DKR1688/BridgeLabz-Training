package com.example.contacts_app.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) {
        super("A contact with email '" + email + "' already exists");
    }
}

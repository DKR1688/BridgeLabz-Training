package com.example.contacts_app.exception;

public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException(Long id) {
        super("Contact with id " + id + " was not found");
    }
}

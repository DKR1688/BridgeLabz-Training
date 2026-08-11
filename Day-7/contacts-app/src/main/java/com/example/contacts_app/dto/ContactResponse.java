package com.example.contacts_app.dto;

import com.example.contacts_app.model.Contact;

public record ContactResponse(Long id, String firstName, String lastName, String email, String phoneNumber,
        String address) {
    public static ContactResponse from(Contact contact) {
        return new ContactResponse(contact.getId(), contact.getFirstName(), contact.getLastName(),
                contact.getEmail(), contact.getPhoneNumber(), contact.getAddress());
    }
}

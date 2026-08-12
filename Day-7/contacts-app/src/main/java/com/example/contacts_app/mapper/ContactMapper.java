package com.example.contacts_app.mapper;

import com.example.contacts_app.dto.request.ContactRequest;
import com.example.contacts_app.dto.response.ContactResponse;
import com.example.contacts_app.entity.Contact;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ContactMapper {
    public Contact toEntity(ContactRequest request) {
        return new Contact(clean(request.firstName()), clean(request.lastName()), normalizeEmail(request.email()),
                clean(request.phoneNumber()), cleanNullable(request.address()));
    }

    public void updateEntity(Contact contact, ContactRequest request) {
        contact.update(clean(request.firstName()), clean(request.lastName()), normalizeEmail(request.email()),
                clean(request.phoneNumber()), cleanNullable(request.address()));
    }

    public ContactResponse toResponse(Contact contact) {
        return new ContactResponse(contact.getId(), contact.getFirstName(), contact.getLastName(), contact.getEmail(),
                contact.getPhoneNumber(), contact.getAddress());
    }

    public String normalizeEmail(String email) { return clean(email).toLowerCase(Locale.ROOT); }
    private String clean(String value) { return value.trim(); }
    private String cleanNullable(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}

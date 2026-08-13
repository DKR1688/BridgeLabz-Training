package com.example.contacts_app.dao;

import com.example.contacts_app.entity.Contact;
import java.util.List;
import org.springframework.stereotype.Repository;

/** Deliberately separate hard-coded implementation for DI practice and tests. */
@Repository
public class InMemoryContactDAO implements ContactLookupDAO {
    @Override
    public List<Contact> getAllContacts() {
        return List.of(new Contact("Training", "Contact", "training@example.com", "9999999999", "In memory"));
    }
}

package com.example.contacts_app.controller;

import com.example.contacts_app.dao.ContactLookupDAO;
import com.example.contacts_app.entity.Contact;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** A specific injection point where @Qualifier deliberately overrides @Primary. */
@RestController
@RequestMapping("/api/contacts/training")
public class ContactDiTrainingController {
    private final ContactLookupDAO inMemoryContactDAO;

    public ContactDiTrainingController(@Qualifier("inMemoryContactDAO") ContactLookupDAO inMemoryContactDAO) {
        this.inMemoryContactDAO = inMemoryContactDAO;
    }

    @GetMapping("/in-memory")
    public List<Contact> inMemoryContacts() {
        return inMemoryContactDAO.getAllContacts();
    }
}

package com.example.contacts_app.service;

import com.example.contacts_app.dao.ContactLookupDAO;
import com.example.contacts_app.entity.Contact;
import java.util.List;
import org.springframework.stereotype.Service;

/** Uses the @Primary database DAO when no more specific choice is requested. */
@Service
public class ContactLookupService {
    private final ContactLookupDAO contactLookupDAO;

    public ContactLookupService(ContactLookupDAO contactLookupDAO) {
        this.contactLookupDAO = contactLookupDAO;
    }

    public List<Contact> getAllContacts() {
        return contactLookupDAO.getAllContacts();
    }
}

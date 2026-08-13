package com.example.contacts_app.dao;

import com.example.contacts_app.entity.Contact;
import com.example.contacts_app.repository.ContactRepository;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/** Normal lookup implementation, backed by the application's H2 database. */
@Repository
@Primary
public class DatabaseContactDAO implements ContactLookupDAO {
    private final ContactRepository contactRepository;

    public DatabaseContactDAO(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAllByOrderByFirstNameAscLastNameAsc();
    }
}

package com.example.contacts_app.dao;

import com.example.contacts_app.entity.Contact;
import java.util.List;

/** Training interface used to demonstrate multiple DI candidates. */
public interface ContactLookupDAO {
    List<Contact> getAllContacts();
}

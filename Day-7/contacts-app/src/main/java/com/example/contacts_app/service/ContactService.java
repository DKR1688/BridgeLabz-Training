package com.example.contacts_app.service;

import com.example.contacts_app.dto.ContactRequest;
import com.example.contacts_app.dto.ContactResponse;
import com.example.contacts_app.model.Contact;
import com.example.contacts_app.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public ContactResponse create(ContactRequest request) {
        Contact contact = new Contact(request.firstName(), request.lastName(), request.email(), request.phoneNumber(), request.address());
        return ContactResponse.from(contactRepository.save(contact));
    }

    public List<ContactResponse> findAll() {
        return contactRepository.findAll().stream().map(ContactResponse::from).toList();
    }

    public ContactResponse findById(Long id) {
        return ContactResponse.from(findContact(id));
    }

    public ContactResponse update(Long id, ContactRequest request) {
        Contact contact = findContact(id);
        contact.update(request.firstName(), request.lastName(), request.email(), request.phoneNumber(), request.address());
        return ContactResponse.from(contactRepository.save(contact));
    }

    public void delete(Long id) {
        contactRepository.delete(findContact(id));
    }

    private Contact findContact(Long id) {
        return contactRepository.findById(id).orElseThrow();
    }
}

package com.example.contacts_app.service;

import com.example.contacts_app.dto.ContactRequest;
import com.example.contacts_app.dto.ContactResponse;
import com.example.contacts_app.exception.ContactNotFoundException;
import com.example.contacts_app.exception.DuplicateEmailException;
import com.example.contacts_app.model.Contact;
import com.example.contacts_app.repository.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Transactional
    public ContactResponse create(ContactRequest request) {
        String email = normalizeEmail(request.email());
        if (contactRepository.existsByEmailIgnoreCase(email)) {
            throw new DuplicateEmailException(email);
        }
        Contact contact = new Contact(request.firstName().trim(), request.lastName().trim(), email,
                request.phoneNumber().trim(), normalizeAddress(request.address()));
        return ContactResponse.from(contactRepository.save(contact));
    }

    @Transactional(readOnly = true)
    public List<ContactResponse> findAll() {
        return contactRepository.findAllByOrderByFirstNameAscLastNameAsc().stream().map(ContactResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ContactResponse findById(Long id) {
        return ContactResponse.from(findContact(id));
    }

    @Transactional
    public ContactResponse update(Long id, ContactRequest request) {
        Contact contact = findContact(id);
        String email = normalizeEmail(request.email());
        if (contactRepository.existsByEmailIgnoreCaseAndIdNot(email, id)) {
            throw new DuplicateEmailException(email);
        }
        contact.update(request.firstName().trim(), request.lastName().trim(), email, request.phoneNumber().trim(),
                normalizeAddress(request.address()));
        return ContactResponse.from(contact);
    }

    @Transactional
    public void delete(Long id) {
        contactRepository.delete(findContact(id));
    }

    private Contact findContact(Long id) {
        return contactRepository.findById(id).orElseThrow(() -> new ContactNotFoundException(id));
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

    private String normalizeAddress(String address) {
        return address == null || address.isBlank() ? null : address.trim();
    }
}

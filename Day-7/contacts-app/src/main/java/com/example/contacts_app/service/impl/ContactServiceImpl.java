package com.example.contacts_app.service.impl;

import com.example.contacts_app.dto.request.ContactRequest;
import com.example.contacts_app.dto.response.ContactResponse;
import com.example.contacts_app.entity.Contact;
import com.example.contacts_app.exception.ContactNotFoundException;
import com.example.contacts_app.exception.DuplicateEmailException;
import com.example.contacts_app.mapper.ContactMapper;
import com.example.contacts_app.repository.ContactRepository;
import com.example.contacts_app.service.ContactService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public ContactServiceImpl(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    @Override @Transactional
    public ContactResponse create(ContactRequest request) {
        String email = contactMapper.normalizeEmail(request.email());
        if (contactRepository.existsByEmailIgnoreCase(email)) throw new DuplicateEmailException(email);
        return contactMapper.toResponse(contactRepository.save(contactMapper.toEntity(request)));
    }

    @Override @Transactional(readOnly = true)
    public List<ContactResponse> findAll() {
        return contactRepository.findAllByOrderByFirstNameAscLastNameAsc().stream().map(contactMapper::toResponse).toList();
    }

    @Override @Transactional(readOnly = true)
    public ContactResponse findById(Long id) { return contactMapper.toResponse(findContact(id)); }

    @Override @Transactional
    public ContactResponse update(Long id, ContactRequest request) {
        Contact contact = findContact(id);
        String email = contactMapper.normalizeEmail(request.email());
        if (contactRepository.existsByEmailIgnoreCaseAndIdNot(email, id)) throw new DuplicateEmailException(email);
        contactMapper.updateEntity(contact, request);
        return contactMapper.toResponse(contact);
    }

    @Override @Transactional
    public void delete(Long id) { contactRepository.delete(findContact(id)); }

    private Contact findContact(Long id) {
        return contactRepository.findById(id).orElseThrow(() -> new ContactNotFoundException(id));
    }
}

package com.example.contacts_app.service;

import com.example.contacts_app.dto.request.ContactRequest;
import com.example.contacts_app.dto.response.ContactResponse;

import java.util.List;

public interface ContactService {
    ContactResponse create(ContactRequest request);
    List<ContactResponse> findAll();
    ContactResponse findById(Long id);
    ContactResponse update(Long id, ContactRequest request);
    void delete(Long id);
}

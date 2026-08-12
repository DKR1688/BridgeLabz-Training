package com.example.contacts_app.controller;

import com.example.contacts_app.dto.ContactRequest;
import com.example.contacts_app.dto.ContactResponse;
import com.example.contacts_app.service.ContactService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.validation.annotation.Validated;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@Validated
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<ContactResponse> create(@Valid @RequestBody ContactRequest request) {
        ContactResponse contact = contactService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(contact.id()).toUri();
        return ResponseEntity.created(location).body(contact);
    }

    @GetMapping
    public List<ContactResponse> findAll() {
        return contactService.findAll();
    }

    @GetMapping("/{id}")
    public ContactResponse findById(@PathVariable @Positive(message = "Contact id must be positive") Long id) {
        return contactService.findById(id);
    }

    @PutMapping("/{id}")
    public ContactResponse update(@PathVariable @Positive(message = "Contact id must be positive") Long id, @Valid @RequestBody ContactRequest request) {
        return contactService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive(message = "Contact id must be positive") Long id) {
        contactService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

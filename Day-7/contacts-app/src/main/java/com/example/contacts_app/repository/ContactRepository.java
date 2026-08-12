package com.example.contacts_app.repository;

import com.example.contacts_app.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

    List<Contact> findAllByOrderByFirstNameAscLastNameAsc();
}

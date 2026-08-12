package com.example.contacts_app.service;

import com.example.contacts_app.dto.request.ContactRequest;
import com.example.contacts_app.entity.Contact;
import com.example.contacts_app.exception.ContactNotFoundException;
import com.example.contacts_app.mapper.ContactMapper;
import com.example.contacts_app.repository.ContactRepository;
import com.example.contacts_app.service.impl.ContactServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactServiceImplTest {
    @Mock private ContactRepository contactRepository;
    @InjectMocks private ContactServiceImpl contactService;

    @Test
    void findByIdMapsTheStoredContact() {
        Contact contact = new Contact("Ada", "Lovelace", "ada@example.com", "+91 9876543210", "London");
        when(contactRepository.findById(10L)).thenReturn(Optional.of(contact));

        var response = contactService.findById(10L);

        assertThat(response.firstName()).isEqualTo("Ada");
        assertThat(response.email()).isEqualTo("ada@example.com");
    }

    @Test
    void findByIdThrowsForAnUnknownContact() {
        when(contactRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> contactService.findById(99L))
                .isInstanceOf(ContactNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void createNormalizesEmailBeforeCheckingForDuplicates() {
        ContactRequest request = new ContactRequest(" Ada ", " Lovelace ", " ADA@EXAMPLE.COM ", "+91 9876543210", " London ");
        when(contactRepository.existsByEmailIgnoreCase("ada@example.com")).thenReturn(true);

        assertThatThrownBy(() -> contactService.create(request))
                .hasMessageContaining("ada@example.com");
    }
}

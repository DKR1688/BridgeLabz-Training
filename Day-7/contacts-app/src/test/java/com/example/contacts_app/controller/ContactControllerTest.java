package com.example.contacts_app.controller;

import com.example.contacts_app.dto.response.ContactResponse;
import com.example.contacts_app.exception.GlobalExceptionHandler;
import com.example.contacts_app.service.ContactService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ContactControllerTest {
    @Test
    void createReturnsCreatedContactAndLocationHeader() throws Exception {
        ContactService service = mock(ContactService.class);
        when(service.create(any())).thenReturn(new ContactResponse(7L, "Ada", "Lovelace", "ada@example.com", "+91 9876543210", "London"));
        MockMvc mvc = MockMvcBuilders.standaloneSetup(new ContactController(service))
                .setControllerAdvice(new GlobalExceptionHandler()).build();

        mvc.perform(post("/api/contacts").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Ada\",\"lastName\":\"Lovelace\",\"email\":\"ada@example.com\",\"phoneNumber\":\"+91 9876543210\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.endsWith("/api/contacts/7")))
                .andExpect(jsonPath("$.email").value("ada@example.com"));
    }
}

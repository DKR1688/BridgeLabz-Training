package com.example.contacts_app.dto.response;

public record ContactResponse(Long id, String firstName, String lastName, String email, String phoneNumber,
                              String address) { }

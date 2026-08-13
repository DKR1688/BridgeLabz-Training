package com.example.contacts_app.service.di;

import org.springframework.stereotype.Component;

/** Extracted shared work prevents ServiceA and ServiceB from depending on each other. */
@Component
public class SharedResponsibility {
    public String describe() {
        return "shared work completed";
    }
}

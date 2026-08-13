package com.example.contacts_app.service.di;

import org.springframework.stereotype.Service;

@Service
public class ServiceA {
    private final SharedResponsibility sharedResponsibility;
    public ServiceA(SharedResponsibility sharedResponsibility) { this.sharedResponsibility = sharedResponsibility; }
    public String work() { return sharedResponsibility.describe(); }
}

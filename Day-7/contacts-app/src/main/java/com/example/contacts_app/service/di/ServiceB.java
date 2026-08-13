package com.example.contacts_app.service.di;

import org.springframework.stereotype.Service;

@Service
public class ServiceB {
    private final SharedResponsibility sharedResponsibility;
    public ServiceB(SharedResponsibility sharedResponsibility) { this.sharedResponsibility = sharedResponsibility; }
    public String work() { return sharedResponsibility.describe(); }
}

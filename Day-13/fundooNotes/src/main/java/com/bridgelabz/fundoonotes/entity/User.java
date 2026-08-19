package com.bridgelabz.fundoonotes.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false, unique = true, length = 254)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(length = 100)
    private String name;

    public User() { }
    public int getUserId() { return userId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

package com.clinic.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Patient {
    private int patientId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String email;
    private boolean active = true;
    private LocalDateTime registeredOn;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int value) {
        patientId = value;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String value) {
        firstName = value;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String value) {
        lastName = value;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate value) {
        dateOfBirth = value;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String value) {
        gender = value;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String value) {
        phoneNumber = value;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String value) {
        email = value;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean value) {
        active = value;
    }

    public LocalDateTime getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDateTime value) {
        registeredOn = value;
    }

    @Override
    public String toString() {
        return "Patient{id=" + patientId + ", name='" + firstName + " " + lastName + "', dob=" + dateOfBirth
                + ", gender='" + gender + "', phone='" + phoneNumber + "', email='" + email + "', active=" + active
                + "}";
    }
}

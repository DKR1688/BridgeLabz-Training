package com.clinic.dto;

import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private int doctorId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private boolean active = true;
    private List<Specialization> specializations = new ArrayList<>();

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int value) {
        doctorId = value;
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

    public List<Specialization> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<Specialization> value) {
        specializations = value;
    }

    @Override
    public String toString() {
        return "Doctor{id=" + doctorId + ", name='" + firstName + " " + lastName + "', phone='" + phoneNumber
                + "', email='" + email + "', active=" + active + ", specializations=" + specializations + "}";
    }
}

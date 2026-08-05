package com.clinic.dto;

public class Specialization {
    private int specializationId;
    private String name;
    private String description;

    public int getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(int value) {
        specializationId = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        description = value;
    }

    @Override
    public String toString() {
        return name + " (id=" + specializationId + ", description='" + description + "')";
    }
}

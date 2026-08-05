package com.clinic.dto;

import java.time.LocalDateTime;

public class Appointment {
    private int appointmentId;
    private int patientId;
    private int doctorId;
    private LocalDateTime appointmentDate;
    private String status;

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int value) {
        appointmentId = value;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int value) {
        patientId = value;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int value) {
        doctorId = value;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime value) {
        appointmentDate = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String value) {
        status = value;
    }

    @Override
    public String toString() {
        return "Appointment{id=" + appointmentId + ", patientId=" + patientId + ", doctorId=" + doctorId + ", date="
                + appointmentDate + ", status='" + status + "'}";
    }
}

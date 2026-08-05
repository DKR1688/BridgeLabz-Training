package com.clinic.dto;

public class VisitHistory {
    private int visitId;
    private int appointmentId;
    private String diagnosis;
    private String prescription;
    private String visitNotes;

    public int getVisitId() {
        return visitId;
    }

    public void setVisitId(int value) {
        visitId = value;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int value) {
        appointmentId = value;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String value) {
        diagnosis = value;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String value) {
        prescription = value;
    }

    public String getVisitNotes() {
        return visitNotes;
    }

    public void setVisitNotes(String value) {
        visitNotes = value;
    }

    @Override
    public String toString() {
        return "VisitHistory{id=" + visitId + ", appointmentId=" + appointmentId + ", diagnosis='" + diagnosis
                + "', prescription='" + prescription + "', notes='" + visitNotes + "'}";
    }
}

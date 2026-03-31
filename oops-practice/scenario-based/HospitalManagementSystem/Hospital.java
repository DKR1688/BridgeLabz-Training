package HospitalManagementSystem;

import java.util.*;
public class Hospital implements HospitalService {
    private Map<Integer, Patient> patients = new HashMap<>();
    private Map<Integer, Doctor> doctors = new HashMap<>();
    private Map<Integer, Appointment> appointments = new HashMap<>();

    @Override
    public void addPatient(Patient p) {
        patients.put(p.id, p);
        System.out.println("Added patient name is- " + p.name);
    }

    @Override
    public void addDoctor(Doctor d) {
        doctors.put(d.id, d);
        System.out.println("Added doctor name is- " +d.name);
    }

    @Override
    public void bookAppointment(Appointment a) throws AppointmentNotAvailableException {
        if (appointments.containsKey(a.getAppointmentId())) {
            throw new AppointmentNotAvailableException("Appointment ID already booked!");
        }
        appointments.put(a.getAppointmentId(), a);
        System.out.println("Appointment booked successfully!");
    }

    @Override
    public void cancelAppointment(int appointmentId) {
        if (appointments.remove(appointmentId) != null) {
            System.out.println("Appointment cancelled.");
        } else {
            System.out.println("No appointment found with ID- " + appointmentId);
        }
    }

    @Override
    public void viewMedicalHistory(Patient p) {
        System.out.println("Medical history of " + p.name + "is: Disease- " + p.getDisease());
    }
}
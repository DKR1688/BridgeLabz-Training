package HospitalManagementSystem;

import java.util.*;
public class HospitalSystem {
	public static void main(String[] args) {
        Hospital hospital = new Hospital();

        //adding patient
        Patient p1 = new Patient(1, "Deepak", 30, "Fever");
        hospital.addPatient(p1);

        //adding doctor
        Doctor d1 = new Doctor(101, "Dr. Rajput", 25, "Cardiology", 200);
        hospital.addDoctor(d1);

        // Book appointment
        try {
            Appointment a1 = new Appointment(1001, p1, d1, "15-Jan-2026");
            hospital.bookAppointment(a1);
            a1.showAppointment();
        } catch (AppointmentNotAvailableException e) {
            System.out.println(e.getMessage());
        }

        // View medical history
        hospital.viewMedicalHistory(p1);

        // Cancel appointment
        hospital.cancelAppointment(1001);
    }

}

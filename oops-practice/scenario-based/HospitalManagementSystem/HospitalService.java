package HospitalManagementSystem;

public interface HospitalService {
    void addPatient(Patient p);
    void addDoctor(Doctor d);
    void bookAppointment(Appointment a) throws AppointmentNotAvailableException;
    void cancelAppointment(int appointmentId);
    void viewMedicalHistory(Patient p);
}
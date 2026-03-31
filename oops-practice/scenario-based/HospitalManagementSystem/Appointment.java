package HospitalManagementSystem;

public class Appointment {
    private int appointmentId;
    private Patient patient;
    private Doctor doctor;
    private String date;

    public Appointment(int appointmentId, Patient patient, Doctor doctor, String date) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
    }

    public void showAppointment() {
       System.out.println("Appointment ID is- " + appointmentId +
                           "\n Patient is- " + patient.name +
                           "\n Doctor is- " + doctor.name +
                           "\n Date is- " + date);
    }

    public int getAppointmentId() {
        return appointmentId;
    }
}
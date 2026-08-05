package com.clinic.ui;

import com.clinic.dto.*;
import com.clinic.service.AppointmentService;
import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner in = new Scanner(System.in);
    private final AppointmentService service = new AppointmentService();
    private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void start() {
        boolean run = true;
        while (run) {
            menu();
            try {
                switch (readInt("Choose an option: ")) {
                    case 1 -> addPatient();
                    case 2 -> list(service.patients());
                    case 3 -> editPatient();
                    case 4 -> result(service.deletePatient(readInt("Patient ID: ")));
                    case 5 -> addDoctor();
                    case 6 -> list(service.doctors());
                    case 7 -> editDoctor();
                    case 8 -> result(service.deleteDoctor(readInt("Doctor ID: ")));
                    case 9 ->
                        result(service.assignSpecialization(readInt("Doctor ID: "), readInt("Specialization ID: ")));
                    case 10 ->
                        result(service.removeSpecialization(readInt("Doctor ID: "), readInt("Specialization ID: ")));
                    case 11 -> addSpecialization();
                    case 12 -> list(service.specializations());
                    case 13 -> editSpecialization();
                    case 14 -> result(service.deleteSpecialization(readInt("Specialization ID: ")));
                    case 15 -> book();
                    case 16 -> list(service.appointments());
                    case 17 -> editAppointment();
                    case 18 -> result(service.deleteAppointment(readInt("Appointment ID: ")));
                    case 19 -> complete();
                    case 20 -> list(service.billings());
                    case 21 -> result(service.updatePaymentStatus(readInt("Bill ID: "),
                            choice("Payment status (Pending/Paid/Refunded): ", "Pending", "Paid", "Refunded")));
                    case 22 -> result(service.deleteBilling(readInt("Bill ID: ")));
                    case 23 -> list(service.visitHistory());
                    case 24 -> editVisit();
                    case 25 -> result(service.deleteVisit(readInt("Visit ID: ")));
                    case 0 -> {
                        run = false;
                        System.out.println("Goodbye!");
                    }
                    default -> System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
        }
    }

    private void menu() {
        System.out.println("""

                === Health Clinic Management ===
                1. Add patient
                2. List patients
                3. Edit patient
                4. Delete patient
                5. Add doctor
                6. List doctors
                7. Edit doctor
                8. Delete doctor
                9. Assign specialization
                10. Remove specialization
                11. Add specialization
                12. List specializations
                13. Edit specialization
                14. Delete specialization
                15. Book appointment
                16. List appointments
                17. Edit appointment
                18. Delete appointment
                19. Complete appointment
                20. List bills
                21. Update payment status
                22. Delete bill
                23. List visit history
                24. Edit visit history
                25. Delete visit history
                0. Exit
                """);
    }

    private void addPatient() throws Exception {
        Patient p = patient(new Patient());
        System.out.println("Registered with ID: " + service.registerPatient(p));
    }

    private void editPatient() throws Exception {
        Patient p = service.patient(readInt("Patient ID: "))
                .orElseThrow(() -> new IllegalArgumentException("Patient not found."));
        patient(p);
        result(service.updatePatient(p));
    }

    private Patient patient(Patient p) {
        p.setFirstName(text("First name: "));
        p.setLastName(text("Last name: "));
        p.setDateOfBirth(dateOptional("Date of birth yyyy-MM-dd (blank allowed): "));
        p.setGender(choice("Gender (Male/Female/Other): ", "Male", "Female", "Other"));
        p.setPhoneNumber(optional("Phone: "));
        p.setEmail(optional("Email: "));
        p.setActive(yesNo("Active? (y/n): "));
        return p;
    }

    private void addDoctor() throws Exception {
        Doctor d = doctor(new Doctor());
        System.out.println("Doctor created with ID: " + service.addDoctor(d));
    }

    private void editDoctor() throws Exception {
        Doctor d = service.doctor(readInt("Doctor ID: "))
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found."));
        doctor(d);
        result(service.updateDoctor(d));
    }

    private Doctor doctor(Doctor d) {
        d.setFirstName(text("First name: "));
        d.setLastName(text("Last name: "));
        d.setPhoneNumber(optional("Phone: "));
        d.setEmail(optional("Email: "));
        d.setActive(yesNo("Active? (y/n): "));
        return d;
    }

    private void addSpecialization() throws Exception {
        Specialization s = specialization(new Specialization());
        System.out.println("Specialization created with ID: " + service.addSpecialization(s));
    }

    private void editSpecialization() throws Exception {
        Specialization s = service.specialization(readInt("Specialization ID: "))
                .orElseThrow(() -> new IllegalArgumentException("Specialization not found."));
        specialization(s);
        result(service.updateSpecialization(s));
    }

    private Specialization specialization(Specialization s) {
        s.setName(text("Name: "));
        s.setDescription(optional("Description: "));
        return s;
    }

    private void book() throws Exception {
        System.out.println("Appointment created with ID: " + service.bookAppointment(readInt("Patient ID: "),
                readInt("Doctor ID: "), dateTime("Appointment date/time (yyyy-MM-dd HH:mm): ")));
    }

    private void editAppointment() throws Exception {
        Appointment a = service.appointment(readInt("Appointment ID: "))
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found."));
        a.setPatientId(readInt("Patient ID: "));
        a.setDoctorId(readInt("Doctor ID: "));
        a.setAppointmentDate(dateTime("Appointment date/time (yyyy-MM-dd HH:mm): "));
        a.setStatus(choice("Status (Scheduled/Completed/Cancelled): ", "Scheduled", "Completed", "Cancelled"));
        result(service.updateAppointment(a));
    }

    private void complete() throws Exception {
        boolean ok = service.completeAppointment(readInt("Appointment ID: "), new BigDecimal(text("Bill amount: ")),
                optional("Diagnosis: "), optional("Prescription: "), optional("Visit notes: "));
        System.out.println(ok ? "Appointment completed; bill and visit record saved." : "Completion failed.");
    }

    private void editVisit() throws Exception {
        int visitId = readInt("Visit ID: ");
        VisitHistory v = service.visitHistory().stream().filter(x -> x.getVisitId() == visitId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Visit not found."));
        v.setDiagnosis(optional("Diagnosis: "));
        v.setPrescription(optional("Prescription: "));
        v.setVisitNotes(optional("Visit notes: "));
        result(service.updateVisit(v));
    }

    private int readInt(String p) {
        while (true)
            try {
                return Integer.parseInt(text(p));
            } catch (NumberFormatException e) {
                System.out.println("Enter a whole number.");
            }
    }

    private String text(String p) {
        System.out.print(p);
        return in.nextLine().trim();
    }

    private String optional(String p) {
        return text(p);
    }

    private LocalDate dateOptional(String p) {
        String v = text(p);
        return v.isEmpty() ? null : LocalDate.parse(v);
    }

    private LocalDateTime dateTime(String p) {
        return LocalDateTime.parse(text(p), DATE_TIME);
    }

    private boolean yesNo(String p) {
        return text(p).equalsIgnoreCase("y");
    }

    private String choice(String p, String... values) {
        String v = text(p);
        for (String x : values)
            if (x.equalsIgnoreCase(v))
                return x;
        throw new IllegalArgumentException("Allowed values: " + String.join(", ", values));
    }

    private void result(boolean ok) {
        System.out.println(ok ? "Saved successfully." : "No record was changed.");
    }

    private void list(Iterable<?> values) {
        boolean any = false;
        for (Object value : values) {
            System.out.println(value);
            any = true;
        }
        if (!any)
            System.out.println("No records found.");
    }
}

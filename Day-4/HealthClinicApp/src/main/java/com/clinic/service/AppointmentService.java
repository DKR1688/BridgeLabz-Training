package com.clinic.service;

import com.clinic.config.HikariConnectionPool;
import com.clinic.dao.*;
import com.clinic.dto.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/** Application service: UI calls this class; DAOs exclusively contain SQL. */
public class AppointmentService {
    private final PatientDAO patients = new PatientDAOImpl();
    private final DoctorDAO doctors = new DoctorDAOImpl();
    private final SpecializationDAO specializations = new SpecializationDAOImpl();
    private final AppointmentDAO appointments = new AppointmentDAOImpl();
    private final BillingDAO billings = new BillingDAOImpl();
    private final VisitHistoryDAO visits = new VisitHistoryDAOImpl();

    public int registerPatient(Patient p) throws SQLException {
        return patients.create(p);
    }

    public List<Patient> patients() throws SQLException {
        return patients.findAll();
    }

    public Optional<Patient> patient(int id) throws SQLException {
        return patients.findById(id);
    }

    public boolean updatePatient(Patient p) throws SQLException {
        return patients.update(p);
    }

    public boolean deletePatient(int id) throws SQLException {
        return patients.delete(id);
    }

    public int addDoctor(Doctor d) throws SQLException {
        return doctors.create(d);
    }

    public List<Doctor> doctors() throws SQLException {
        return doctors.findAll();
    }

    public Optional<Doctor> doctor(int id) throws SQLException {
        return doctors.findById(id);
    }

    public boolean updateDoctor(Doctor d) throws SQLException {
        return doctors.update(d);
    }

    public boolean deleteDoctor(int id) throws SQLException {
        return doctors.delete(id);
    }

    public boolean assignSpecialization(int d, int s) throws SQLException {
        return doctors.assignSpecialization(d, s);
    }

    public boolean removeSpecialization(int d, int s) throws SQLException {
        return doctors.removeSpecialization(d, s);
    }

    public int addSpecialization(Specialization s) throws SQLException {
        return specializations.create(s);
    }

    public List<Specialization> specializations() throws SQLException {
        return specializations.findAll();
    }

    public Optional<Specialization> specialization(int id) throws SQLException {
        return specializations.findById(id);
    }

    public boolean updateSpecialization(Specialization s) throws SQLException {
        return specializations.update(s);
    }

    public boolean deleteSpecialization(int id) throws SQLException {
        return specializations.delete(id);
    }

    public int bookAppointment(int patientId, int doctorId, LocalDateTime date) throws SQLException {
        if (date == null)
            throw new IllegalArgumentException("Appointment date is required.");
        if (patients.findById(patientId).isEmpty() || doctors.findById(doctorId).isEmpty())
            throw new IllegalArgumentException("Patient or doctor does not exist.");
        Appointment a = new Appointment();
        a.setPatientId(patientId);
        a.setDoctorId(doctorId);
        a.setAppointmentDate(date);
        a.setStatus("Scheduled");
        return appointments.create(a);
    }

    public List<Appointment> appointments() throws SQLException {
        return appointments.findAll();
    }

    public Optional<Appointment> appointment(int id) throws SQLException {
        return appointments.findById(id);
    }

    public boolean updateAppointment(Appointment a) throws SQLException {
        return appointments.update(a);
    }

    public boolean deleteAppointment(int id) throws SQLException {
        return appointments.delete(id);
    }

    /**
     * Completes an appointment, creates its single bill and visit record
     * atomically.
     */
    public boolean completeAppointment(int id, BigDecimal amount, String diagnosis, String prescription, String notes)
            throws SQLException {
        if (amount == null || amount.signum() <= 0)
            throw new IllegalArgumentException("Amount must be positive.");
        Appointment a = appointments.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment does not exist."));
        if (!"Scheduled".equals(a.getStatus()))
            throw new IllegalArgumentException("Only scheduled appointments can be completed.");
        try (Connection c = HikariConnectionPool.getConnection()) {
            c.setAutoCommit(false);
            try {
                if (!appointments.updateStatus(c, id, "Completed"))
                    throw new SQLException("Appointment status was not updated.");
                Billing b = new Billing();
                b.setAppointmentId(id);
                b.setAmount(amount);
                b.setPaymentStatus("Pending");
                if (billings.create(c, b) < 1)
                    throw new SQLException("Bill was not created.");
                VisitHistory v = new VisitHistory();
                v.setAppointmentId(id);
                v.setDiagnosis(diagnosis);
                v.setPrescription(prescription);
                v.setVisitNotes(notes);
                if (visits.create(c, v) < 1)
                    throw new SQLException("Visit record was not created.");
                c.commit();
                return true;
            } catch (SQLException | RuntimeException e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);
            }
        }
    }

    public List<Billing> billings() throws SQLException {
        return billings.findAll();
    }

    public boolean updatePaymentStatus(int id, String status) throws SQLException {
        return billings.updatePaymentStatus(id, status);
    }

    public boolean deleteBilling(int id) throws SQLException {
        return billings.delete(id);
    }

    public List<VisitHistory> visitHistory() throws SQLException {
        return visits.findAll();
    }

    public boolean updateVisit(VisitHistory v) throws SQLException {
        return visits.update(v);
    }

    public boolean deleteVisit(int id) throws SQLException {
        return visits.delete(id);
    }
}

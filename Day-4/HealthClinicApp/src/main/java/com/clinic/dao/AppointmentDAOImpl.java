package com.clinic.dao;

import com.clinic.config.HikariConnectionPool;
import com.clinic.dto.Appointment;
import java.sql.*;
import java.util.*;

public class AppointmentDAOImpl implements AppointmentDAO {
    public int create(Appointment a) throws SQLException {
        String q = "INSERT INTO appointments(patient_id,doctor_id,appointment_date,status) VALUES (?,?,?,?)";
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement(q, Statement.RETURN_GENERATED_KEYS)) {
            set(s, a, false);
            s.executeUpdate();
            try (ResultSet k = s.getGeneratedKeys()) {
                return k.next() ? k.getInt(1) : -1;
            }
        }
    }

    public List<Appointment> findAll() throws SQLException {
        List<Appointment> r = new ArrayList<>();
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement("SELECT * FROM appointments ORDER BY appointment_date");
                ResultSet x = s.executeQuery()) {
            while (x.next())
                r.add(map(x));
        }
        return r;
    }

    public Optional<Appointment> findById(int id) throws SQLException {
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement("SELECT * FROM appointments WHERE appointment_id=?")) {
            s.setInt(1, id);
            try (ResultSet x = s.executeQuery()) {
                return x.next() ? Optional.of(map(x)) : Optional.empty();
            }
        }
    }

    public boolean update(Appointment a) throws SQLException {
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement(
                        "UPDATE appointments SET patient_id=?,doctor_id=?,appointment_date=?,status=? WHERE appointment_id=?")) {
            set(s, a, true);
            return s.executeUpdate() == 1;
        }
    }

    public boolean updateStatus(Connection c, int id, String status) throws SQLException {
        try (PreparedStatement s = c.prepareStatement("UPDATE appointments SET status=? WHERE appointment_id=?")) {
            s.setString(1, status);
            s.setInt(2, id);
            return s.executeUpdate() == 1;
        }
    }

    public boolean delete(int id) throws SQLException {
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement("DELETE FROM appointments WHERE appointment_id=?")) {
            s.setInt(1, id);
            return s.executeUpdate() == 1;
        }
    }

    private void set(PreparedStatement s, Appointment a, boolean id) throws SQLException {
        s.setInt(1, a.getPatientId());
        s.setInt(2, a.getDoctorId());
        s.setTimestamp(3, Timestamp.valueOf(a.getAppointmentDate()));
        s.setString(4, a.getStatus() == null ? "Scheduled" : a.getStatus());
        if (id)
            s.setInt(5, a.getAppointmentId());
    }

    private Appointment map(ResultSet x) throws SQLException {
        Appointment a = new Appointment();
        a.setAppointmentId(x.getInt("appointment_id"));
        a.setPatientId(x.getInt("patient_id"));
        a.setDoctorId(x.getInt("doctor_id"));
        a.setAppointmentDate(x.getTimestamp("appointment_date").toLocalDateTime());
        a.setStatus(x.getString("status"));
        return a;
    }
}

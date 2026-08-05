package com.clinic.dao;

import com.clinic.config.HikariConnectionPool;
import com.clinic.dto.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientDAOImpl implements PatientDAO {
    public int create(Patient p) throws SQLException {
        String q = "INSERT INTO patients (first_name,last_name,date_of_birth,gender,phone_number,email,is_active) VALUES (?,?,?,?,?,?,?)";
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement(q, Statement.RETURN_GENERATED_KEYS)) {
            set(s, p, false);
            s.executeUpdate();
            try (ResultSet k = s.getGeneratedKeys()) {
                return k.next() ? k.getInt(1) : -1;
            }
        }
    }

    public List<Patient> findAll() throws SQLException {
        List<Patient> r = new ArrayList<>();
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement("SELECT * FROM patients ORDER BY patient_id");
                ResultSet x = s.executeQuery()) {
            while (x.next())
                r.add(map(x));
        }
        return r;
    }

    public Optional<Patient> findById(int id) throws SQLException {
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement("SELECT * FROM patients WHERE patient_id=?")) {
            s.setInt(1, id);
            try (ResultSet x = s.executeQuery()) {
                return x.next() ? Optional.of(map(x)) : Optional.empty();
            }
        }
    }

    public boolean update(Patient p) throws SQLException {
        String q = "UPDATE patients SET first_name=?,last_name=?,date_of_birth=?,gender=?,phone_number=?,email=?,is_active=? WHERE patient_id=?";
        try (Connection c = HikariConnectionPool.getConnection(); PreparedStatement s = c.prepareStatement(q)) {
            set(s, p, true);
            return s.executeUpdate() == 1;
        }
    }

    public boolean delete(int id) throws SQLException {
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement("DELETE FROM patients WHERE patient_id=?")) {
            s.setInt(1, id);
            return s.executeUpdate() == 1;
        }
    }

    private void set(PreparedStatement s, Patient p, boolean id) throws SQLException {
        s.setString(1, p.getFirstName());
        s.setString(2, p.getLastName());
        if (p.getDateOfBirth() == null)
            s.setNull(3, Types.DATE);
        else
            s.setDate(3, Date.valueOf(p.getDateOfBirth()));
        s.setString(4, p.getGender());
        s.setString(5, p.getPhoneNumber());
        s.setString(6, p.getEmail());
        s.setBoolean(7, p.isActive());
        if (id)
            s.setInt(8, p.getPatientId());
    }

    private Patient map(ResultSet x) throws SQLException {
        Patient p = new Patient();
        p.setPatientId(x.getInt("patient_id"));
        p.setFirstName(x.getString("first_name"));
        p.setLastName(x.getString("last_name"));
        Date d = x.getDate("date_of_birth");
        p.setDateOfBirth(d == null ? null : d.toLocalDate());
        p.setGender(x.getString("gender"));
        p.setPhoneNumber(x.getString("phone_number"));
        p.setEmail(x.getString("email"));
        p.setActive(x.getBoolean("is_active"));
        Timestamp t = x.getTimestamp("registered_on");
        p.setRegisteredOn(t == null ? null : t.toLocalDateTime());
        return p;
    }
}

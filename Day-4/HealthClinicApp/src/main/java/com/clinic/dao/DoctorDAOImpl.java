package com.clinic.dao;

import com.clinic.config.HikariConnectionPool;
import com.clinic.dto.*;
import java.sql.*;
import java.util.*;

public class DoctorDAOImpl implements DoctorDAO {
    public int create(Doctor d) throws SQLException {
        String q = "INSERT INTO doctors(first_name,last_name,phone_number,email,is_active) VALUES (?,?,?,?,?)";
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement(q, Statement.RETURN_GENERATED_KEYS)) {
            set(s, d, false);
            s.executeUpdate();
            try (ResultSet k = s.getGeneratedKeys()) {
                return k.next() ? k.getInt(1) : -1;
            }
        }
    }

    public List<Doctor> findAll() throws SQLException {
        List<Doctor> r = new ArrayList<>();
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement("SELECT * FROM doctors ORDER BY doctor_id");
                ResultSet x = s.executeQuery()) {
            while (x.next())
                r.add(map(c, x));
        }
        return r;
    }

    public Optional<Doctor> findById(int id) throws SQLException {
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement("SELECT * FROM doctors WHERE doctor_id=?")) {
            s.setInt(1, id);
            try (ResultSet x = s.executeQuery()) {
                return x.next() ? Optional.of(map(c, x)) : Optional.empty();
            }
        }
    }

    public boolean update(Doctor d) throws SQLException {
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement(
                        "UPDATE doctors SET first_name=?,last_name=?,phone_number=?,email=?,is_active=? WHERE doctor_id=?")) {
            set(s, d, true);
            return s.executeUpdate() == 1;
        }
    }

    public boolean delete(int id) throws SQLException {
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement("DELETE FROM doctors WHERE doctor_id=?")) {
            s.setInt(1, id);
            return s.executeUpdate() == 1;
        }
    }

    public boolean assignSpecialization(int d, int sp) throws SQLException {
        return link(d, sp, "INSERT IGNORE INTO doctor_specializations(doctor_id,specialization_id) VALUES (?,?)");
    }

    public boolean removeSpecialization(int d, int sp) throws SQLException {
        return link(d, sp, "DELETE FROM doctor_specializations WHERE doctor_id=? AND specialization_id=?");
    }

    private boolean link(int d, int sp, String q) throws SQLException {
        try (Connection c = HikariConnectionPool.getConnection(); PreparedStatement s = c.prepareStatement(q)) {
            s.setInt(1, d);
            s.setInt(2, sp);
            return s.executeUpdate() > 0;
        }
    }

    private void set(PreparedStatement s, Doctor d, boolean id) throws SQLException {
        s.setString(1, d.getFirstName());
        s.setString(2, d.getLastName());
        s.setString(3, d.getPhoneNumber());
        s.setString(4, d.getEmail());
        s.setBoolean(5, d.isActive());
        if (id)
            s.setInt(6, d.getDoctorId());
    }

    private Doctor map(Connection c, ResultSet x) throws SQLException {
        Doctor d = new Doctor();
        d.setDoctorId(x.getInt("doctor_id"));
        d.setFirstName(x.getString("first_name"));
        d.setLastName(x.getString("last_name"));
        d.setPhoneNumber(x.getString("phone_number"));
        d.setEmail(x.getString("email"));
        d.setActive(x.getBoolean("is_active"));
        List<Specialization> ss = new ArrayList<>();
        try (PreparedStatement s = c.prepareStatement(
                "SELECT s.* FROM specializations s JOIN doctor_specializations ds ON s.specialization_id=ds.specialization_id WHERE ds.doctor_id=?")) {
            s.setInt(1, d.getDoctorId());
            try (ResultSet a = s.executeQuery()) {
                while (a.next()) {
                    Specialization p = new Specialization();
                    p.setSpecializationId(a.getInt("specialization_id"));
                    p.setName(a.getString("name"));
                    p.setDescription(a.getString("description"));
                    ss.add(p);
                }
            }
        }
        d.setSpecializations(ss);
        return d;
    }
}

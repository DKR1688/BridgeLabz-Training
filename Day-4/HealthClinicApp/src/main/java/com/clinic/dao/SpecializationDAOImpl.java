package com.clinic.dao;

import com.clinic.config.HikariConnectionPool;
import com.clinic.dto.Specialization;
import java.sql.*;
import java.util.*;

public class SpecializationDAOImpl implements SpecializationDAO {
    public int create(Specialization p) throws SQLException {
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement("INSERT INTO specializations(name,description) VALUES (?,?)",
                        Statement.RETURN_GENERATED_KEYS)) {
            s.setString(1, p.getName());
            s.setString(2, p.getDescription());
            s.executeUpdate();
            try (ResultSet k = s.getGeneratedKeys()) {
                return k.next() ? k.getInt(1) : -1;
            }
        }
    }

    public List<Specialization> findAll() throws SQLException {
        List<Specialization> r = new ArrayList<>();
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement("SELECT * FROM specializations ORDER BY specialization_id");
                ResultSet x = s.executeQuery()) {
            while (x.next())
                r.add(map(x));
        }
        return r;
    }

    public Optional<Specialization> findById(int id) throws SQLException {
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement("SELECT * FROM specializations WHERE specialization_id=?")) {
            s.setInt(1, id);
            try (ResultSet x = s.executeQuery()) {
                return x.next() ? Optional.of(map(x)) : Optional.empty();
            }
        }
    }

    public boolean update(Specialization p) throws SQLException {
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement(
                        "UPDATE specializations SET name=?,description=? WHERE specialization_id=?")) {
            s.setString(1, p.getName());
            s.setString(2, p.getDescription());
            s.setInt(3, p.getSpecializationId());
            return s.executeUpdate() == 1;
        }
    }

    public boolean delete(int id) throws SQLException {
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement("DELETE FROM specializations WHERE specialization_id=?")) {
            s.setInt(1, id);
            return s.executeUpdate() == 1;
        }
    }

    private Specialization map(ResultSet x) throws SQLException {
        Specialization p = new Specialization();
        p.setSpecializationId(x.getInt("specialization_id"));
        p.setName(x.getString("name"));
        p.setDescription(x.getString("description"));
        return p;
    }
}

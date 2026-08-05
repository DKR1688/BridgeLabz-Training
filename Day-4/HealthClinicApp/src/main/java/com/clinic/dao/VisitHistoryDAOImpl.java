package com.clinic.dao;

import com.clinic.config.HikariConnectionPool;
import com.clinic.dto.VisitHistory;
import java.sql.*;
import java.util.*;

public class VisitHistoryDAOImpl implements VisitHistoryDAO {
    public int create(Connection c, VisitHistory v) throws SQLException {
        try (PreparedStatement s = c.prepareStatement(
                "INSERT INTO visit_history(appointment_id,diagnosis,prescription,visit_notes) VALUES (?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
            set(s, v, false);
            s.executeUpdate();
            try (ResultSet k = s.getGeneratedKeys()) {
                return k.next() ? k.getInt(1) : -1;
            }
        }
    }

    public List<VisitHistory> findAll() throws SQLException {
        List<VisitHistory> r = new ArrayList<>();
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement("SELECT * FROM visit_history ORDER BY visit_id");
                ResultSet x = s.executeQuery()) {
            while (x.next())
                r.add(map(x));
        }
        return r;
    }

    public Optional<VisitHistory> findByAppointmentId(int id) throws SQLException {
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement("SELECT * FROM visit_history WHERE appointment_id=?")) {
            s.setInt(1, id);
            try (ResultSet x = s.executeQuery()) {
                return x.next() ? Optional.of(map(x)) : Optional.empty();
            }
        }
    }

    public boolean update(VisitHistory v) throws SQLException {
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement(
                        "UPDATE visit_history SET diagnosis=?,prescription=?,visit_notes=? WHERE visit_id=?")) {
            set(s, v, true);
            return s.executeUpdate() == 1;
        }
    }

    public boolean delete(int id) throws SQLException {
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement("DELETE FROM visit_history WHERE visit_id=?")) {
            s.setInt(1, id);
            return s.executeUpdate() == 1;
        }
    }

    private void set(PreparedStatement s, VisitHistory v, boolean id) throws SQLException {
        if (!id)
            s.setInt(1, v.getAppointmentId());
        s.setString(id ? 1 : 2, v.getDiagnosis());
        s.setString(id ? 2 : 3, v.getPrescription());
        s.setString(id ? 3 : 4, v.getVisitNotes());
        if (id)
            s.setInt(4, v.getVisitId());
    }

    private VisitHistory map(ResultSet x) throws SQLException {
        VisitHistory v = new VisitHistory();
        v.setVisitId(x.getInt("visit_id"));
        v.setAppointmentId(x.getInt("appointment_id"));
        v.setDiagnosis(x.getString("diagnosis"));
        v.setPrescription(x.getString("prescription"));
        v.setVisitNotes(x.getString("visit_notes"));
        return v;
    }
}

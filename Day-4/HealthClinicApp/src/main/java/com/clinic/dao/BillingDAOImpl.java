package com.clinic.dao;

import com.clinic.config.HikariConnectionPool;
import com.clinic.dto.Billing;
import java.sql.*;
import java.util.*;

public class BillingDAOImpl implements BillingDAO {
    public int create(Connection c, Billing b) throws SQLException {
        try (PreparedStatement s = c.prepareStatement(
                "INSERT INTO billing(appointment_id,amount,payment_status) VALUES (?,?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
            s.setInt(1, b.getAppointmentId());
            s.setBigDecimal(2, b.getAmount());
            s.setString(3, b.getPaymentStatus() == null ? "Pending" : b.getPaymentStatus());
            s.executeUpdate();
            try (ResultSet k = s.getGeneratedKeys()) {
                return k.next() ? k.getInt(1) : -1;
            }
        }
    }

    public List<Billing> findAll() throws SQLException {
        List<Billing> r = new ArrayList<>();
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement("SELECT * FROM billing ORDER BY bill_id");
                ResultSet x = s.executeQuery()) {
            while (x.next())
                r.add(map(x));
        }
        return r;
    }

    public Optional<Billing> findByAppointmentId(int id) throws SQLException {
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement("SELECT * FROM billing WHERE appointment_id=?")) {
            s.setInt(1, id);
            try (ResultSet x = s.executeQuery()) {
                return x.next() ? Optional.of(map(x)) : Optional.empty();
            }
        }
    }

    public boolean updatePaymentStatus(int id, String status) throws SQLException {
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement("UPDATE billing SET payment_status=? WHERE bill_id=?")) {
            s.setString(1, status);
            s.setInt(2, id);
            return s.executeUpdate() == 1;
        }
    }

    public boolean delete(int id) throws SQLException {
        try (Connection c = HikariConnectionPool.getConnection();
                PreparedStatement s = c.prepareStatement("DELETE FROM billing WHERE bill_id=?")) {
            s.setInt(1, id);
            return s.executeUpdate() == 1;
        }
    }

    private Billing map(ResultSet x) throws SQLException {
        Billing b = new Billing();
        b.setBillId(x.getInt("bill_id"));
        b.setAppointmentId(x.getInt("appointment_id"));
        b.setAmount(x.getBigDecimal("amount"));
        b.setPaymentStatus(x.getString("payment_status"));
        Timestamp t = x.getTimestamp("billing_date");
        b.setBillingDate(t == null ? null : t.toLocalDateTime());
        return b;
    }
}

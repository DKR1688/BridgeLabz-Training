package com.clinic.dao;

import com.clinic.dto.Billing;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BillingDAO {
    int create(Connection connection, Billing billing) throws SQLException;

    List<Billing> findAll() throws SQLException;

    Optional<Billing> findByAppointmentId(int appointmentId) throws SQLException;

    boolean updatePaymentStatus(int billId, String paymentStatus) throws SQLException;

    boolean delete(int billId) throws SQLException;
}

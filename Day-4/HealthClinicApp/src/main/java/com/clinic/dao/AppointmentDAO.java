package com.clinic.dao;

import com.clinic.dto.Appointment;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AppointmentDAO {
    int create(Appointment appointment) throws SQLException;

    List<Appointment> findAll() throws SQLException;

    Optional<Appointment> findById(int id) throws SQLException;

    boolean update(Appointment appointment) throws SQLException;

    boolean updateStatus(Connection connection, int appointmentId, String status) throws SQLException;

    boolean delete(int id) throws SQLException;
}

package com.clinic.dao;

import com.clinic.dto.VisitHistory;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface VisitHistoryDAO {
    int create(Connection connection, VisitHistory visit) throws SQLException;

    List<VisitHistory> findAll() throws SQLException;

    Optional<VisitHistory> findByAppointmentId(int appointmentId) throws SQLException;

    boolean update(VisitHistory visit) throws SQLException;

    boolean delete(int visitId) throws SQLException;
}

package com.clinic.dao;

import com.clinic.dto.Patient;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PatientDAO {
    int create(Patient patient) throws SQLException;

    List<Patient> findAll() throws SQLException;

    Optional<Patient> findById(int id) throws SQLException;

    boolean update(Patient patient) throws SQLException;

    boolean delete(int id) throws SQLException;
}

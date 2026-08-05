package com.clinic.dao;

import com.clinic.dto.Doctor;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DoctorDAO {
    int create(Doctor doctor) throws SQLException;

    List<Doctor> findAll() throws SQLException;

    Optional<Doctor> findById(int id) throws SQLException;

    boolean update(Doctor doctor) throws SQLException;

    boolean delete(int id) throws SQLException;

    boolean assignSpecialization(int doctorId, int specializationId) throws SQLException;

    boolean removeSpecialization(int doctorId, int specializationId) throws SQLException;
}

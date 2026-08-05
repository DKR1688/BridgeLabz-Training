package com.clinic.dao;

import com.clinic.dto.Specialization;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface SpecializationDAO {
    int create(Specialization specialization) throws SQLException;

    List<Specialization> findAll() throws SQLException;

    Optional<Specialization> findById(int id) throws SQLException;

    boolean update(Specialization specialization) throws SQLException;

    boolean delete(int id) throws SQLException;
}

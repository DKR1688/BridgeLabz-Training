package com.payroll.dao;

import com.payroll.dto.Employee;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * Alternate DAO deliberately selected by @Qualifier for the archived endpoint.
 * Uses Spring JDBC to query inactive employees.
 */
@Repository("archivedEmployeeDAO")
public class ArchivedEmployeeDAO implements EmployeeDAO {

    private static final Logger log = LoggerFactory.getLogger(ArchivedEmployeeDAO.class);

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Employee> mapper = (rs, row) -> new Employee(
            rs.getInt("employee_id"),
            rs.getString("name"),
            rs.getString("department"),
            rs.getBigDecimal("salary"),
            rs.getBoolean("active"));

    public ArchivedEmployeeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Employee> findAll() {
        log.debug("Fetching archived employees via JDBC");
        return jdbcTemplate.query(
                "SELECT * FROM employees WHERE active = FALSE ORDER BY employee_id", mapper);
    }

    @Override
    public Optional<Employee> findById(int id) {
        log.debug("Fetching archived employee id={} via JDBC", id);
        return jdbcTemplate.query(
                        "SELECT * FROM employees WHERE employee_id = ? AND active = FALSE", mapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public Employee save(Employee employee) {
        throw new UnsupportedOperationException("Archived DAO is read-only");
    }

    @Override
    public boolean update(int id, Employee employee) {
        throw new UnsupportedOperationException("Archived DAO is read-only");
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Archived DAO is read-only");
    }
}

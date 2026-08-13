package com.payroll.dao;

import com.payroll.dto.Employee;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * Alternate DAO deliberately selected by @Qualifier for the archived endpoint.
 */
@Repository
public class ArchivedEmployeeDAO implements EmployeeDAO {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Employee> mapper = (rs, row) -> new Employee(rs.getInt("employee_id"), rs.getString("name"),
            rs.getString("department"), rs.getBigDecimal("salary"), rs.getBoolean("active"));

    public ArchivedEmployeeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Employee> findAll() {
        return jdbcTemplate.query("SELECT * FROM employees WHERE active = FALSE ORDER BY employee_id", mapper);
    }

    public Optional<Employee> findById(int id) {
        return jdbcTemplate.query("SELECT * FROM employees WHERE employee_id = ? AND active = FALSE", mapper, id)
                .stream().findFirst();
    }

    public Employee save(Employee employee) {
        throw new UnsupportedOperationException("Archived DAO is read-only");
    }

    public boolean update(int id, Employee employee) {
        throw new UnsupportedOperationException("Archived DAO is read-only");
    }

    public boolean delete(int id) {
        throw new UnsupportedOperationException("Archived DAO is read-only");
    }
}

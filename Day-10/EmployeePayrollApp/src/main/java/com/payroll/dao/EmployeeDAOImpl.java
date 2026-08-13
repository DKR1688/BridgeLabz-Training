package com.payroll.dao;

import com.payroll.dto.Employee;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class EmployeeDAOImpl implements EmployeeDAO {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Employee> mapper = (rs, row) -> new Employee(rs.getInt("employee_id"), rs.getString("name"),
            rs.getString("department"), rs.getBigDecimal("salary"), rs.getBoolean("active"));

    public EmployeeDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Employee> findAll() {
        return jdbcTemplate.query("SELECT * FROM employees WHERE active = TRUE ORDER BY employee_id", mapper);
    }

    public Optional<Employee> findById(int id) {
        return jdbcTemplate.query("SELECT * FROM employees WHERE employee_id = ? AND active = TRUE", mapper, id)
                .stream().findFirst();
    }

    public Employee save(Employee employee) {
        GeneratedKeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO employees (name, department, salary, active) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getDepartment());
            ps.setBigDecimal(3, employee.getSalary());
            ps.setBoolean(4, employee.isActive());
            return ps;
        }, key);
        employee.setEmployeeId(key.getKey().intValue());
        return employee;
    }

    public boolean update(int id, Employee employee) {
        return jdbcTemplate.update(
                "UPDATE employees SET name = ?, department = ?, salary = ?, active = ? WHERE employee_id = ? AND active = TRUE",
                employee.getName(), employee.getDepartment(), employee.getSalary(), employee.isActive(), id) == 1;
    }

    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM employees WHERE employee_id = ? AND active = TRUE", id) == 1;
    }
}

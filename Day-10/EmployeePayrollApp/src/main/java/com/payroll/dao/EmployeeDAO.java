package com.payroll.dao;

import com.payroll.dto.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeDAO {
    List<Employee> findAll();

    Optional<Employee> findById(int id);

    Employee save(Employee employee);

    boolean update(int id, Employee employee);

    boolean delete(int id);
}

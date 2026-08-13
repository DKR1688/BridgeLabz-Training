package com.payroll.service;

import com.payroll.dao.EmployeeDAO;
import com.payroll.dto.Employee;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeDAO employeeDAO;
    private final EmployeeDAO archivedEmployeeDAO;

    // The unqualified argument receives EmployeeDAOImpl because it is @Primary.
    public EmployeeService(EmployeeDAO employeeDAO, @Qualifier("archivedEmployeeDAO") EmployeeDAO archivedEmployeeDAO) {
        this.employeeDAO = employeeDAO;
        this.archivedEmployeeDAO = archivedEmployeeDAO;
    }

    public List<Employee> findAll() {
        return employeeDAO.findAll();
    }

    public List<Employee> findArchived() {
        return archivedEmployeeDAO.findAll();
    }

    public Optional<Employee> findById(int id) {
        return employeeDAO.findById(id);
    }

    public Employee save(Employee employee) {
        return employeeDAO.save(employee);
    }

    public boolean update(int id, Employee employee) {
        return employeeDAO.update(id, employee);
    }

    public boolean delete(int id) {
        return employeeDAO.delete(id);
    }
}

package com.payroll.service;

import com.payroll.dao.EmployeeDAO;
import com.payroll.dto.Employee;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeDAO employeeDAO;
    private final EmployeeDAO archivedEmployeeDAO;

    public EmployeeService(EmployeeDAO employeeDAO, @Qualifier("archivedEmployeeDAO") EmployeeDAO archivedEmployeeDAO) {
        this.employeeDAO = employeeDAO;
        this.archivedEmployeeDAO = archivedEmployeeDAO;
    }

    public List<Employee> findAll() {
        log.debug("Service: listing all active employees");
        return employeeDAO.findAll();
    }

    public List<Employee> findArchived() {
        log.debug("Service: listing archived employees via JDBC DAO");
        return archivedEmployeeDAO.findAll();
    }

    public Optional<Employee> findById(int id) {
        log.debug("Service: finding employee id={}", id);
        return employeeDAO.findById(id);
    }

    public Employee save(Employee employee) {
        log.info("Service: creating employee name={}", employee.getName());
        return employeeDAO.save(employee);
    }

    public boolean update(int id, Employee employee) {
        log.info("Service: updating employee id={}", id);
        return employeeDAO.update(id, employee);
    }

    public boolean delete(int id) {
        log.info("Service: deleting employee id={}", id);
        return employeeDAO.delete(id);
    }
}

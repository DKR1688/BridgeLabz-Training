package com.payroll.dao;

import com.payroll.dto.Employee;
import com.payroll.entity.EmployeeEntity;
import com.payroll.repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class EmployeeJpaDAO implements EmployeeDAO {

    private static final Logger log = LoggerFactory.getLogger(EmployeeJpaDAO.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeJpaDAO(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        log.debug("Fetching all active employees via JPA");
        return employeeRepository.findByActiveTrueOrderByEmployeeIdAsc().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public Optional<Employee> findById(int id) {
        log.debug("Fetching employee id={} via JPA", id);
        return employeeRepository.findByEmployeeIdAndActiveTrue(id).map(this::toDto);
    }

    @Override
    public Employee save(Employee employee) {
        log.info("Saving employee name={} via JPA", employee.getName());
        EmployeeEntity saved = employeeRepository.save(toEntity(employee));
        return toDto(saved);
    }

    @Override
    public boolean update(int id, Employee employee) {
        log.info("Updating employee id={} via JPA", id);
        return employeeRepository.findByEmployeeIdAndActiveTrue(id)
                .map(existing -> {
                    existing.setName(employee.getName());
                    existing.setDepartment(employee.getDepartment());
                    existing.setSalary(employee.getSalary());
                    existing.setActive(employee.isActive());
                    employeeRepository.save(existing);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean delete(int id) {
        log.info("Deleting employee id={} via JPA", id);
        return employeeRepository.findByEmployeeIdAndActiveTrue(id)
                .map(existing -> {
                    employeeRepository.delete(existing);
                    return true;
                })
                .orElse(false);
    }

    private Employee toDto(EmployeeEntity entity) {
        return new Employee(
                entity.getEmployeeId(),
                entity.getName(),
                entity.getDepartment(),
                entity.getSalary(),
                entity.isActive());
    }

    private EmployeeEntity toEntity(Employee employee) {
        EmployeeEntity entity = new EmployeeEntity();
        if (employee.getEmployeeId() != null && employee.getEmployeeId() > 0) {
            entity.setEmployeeId(employee.getEmployeeId());
        }
        entity.setName(employee.getName());
        entity.setDepartment(employee.getDepartment());
        entity.setSalary(employee.getSalary());
        entity.setActive(employee.isActive());
        return entity;
    }
}

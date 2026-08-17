package com.payroll.controller;

import com.payroll.dto.Employee;
import com.payroll.service.EmployeeService;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        log.debug("GET /employees");
        return employeeService.findAll();
    }

    @GetMapping("/archived")
    public List<Employee> getArchivedEmployees() {
        log.debug("GET /employees/archived");
        return employeeService.findArchived();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable int id) {
        log.debug("GET /employees/{}", id);
        return employeeService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Employee id={} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee) {
        log.info("POST /employees name={}", employee.getName());
        Employee created = employeeService.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @Valid @RequestBody Employee employee) {
        log.info("PUT /employees/{}", id);
        if (!employeeService.update(id, employee)) {
            log.warn("Update failed: employee id={} not found", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employeeService.findById(id).orElseThrow());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
        log.info("DELETE /employees/{}", id);
        if (!employeeService.delete(id)) {
            log.warn("Delete failed: employee id={} not found", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

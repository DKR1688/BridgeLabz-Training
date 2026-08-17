package com.payroll.repository;

import com.payroll.entity.EmployeeEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

    List<EmployeeEntity> findByActiveTrueOrderByEmployeeIdAsc();

    Optional<EmployeeEntity> findByEmployeeIdAndActiveTrue(int employeeId);
}

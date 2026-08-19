# Day 11 — Spring Services, Spring JPA & Spring JDBC

Day 11 implements **Service-Layer Architecture**, **Spring Data JPA**, and **Spring JDBC Template** in the **Employee Payroll App** (`Day-10/EmployeePayrollApp`).

---

## Classes Implemented / Modified

### 1. Application Entry Point
* **`PayrollApplication.java`**: Main Spring Boot entry point starting embedded server and database connections.

### 2. Entity & DTO Layer
* **`EmployeeEntity.java`**: JPA Entity mapping database table `employees` with `@Entity`, `@Table`, and `@Id` generation.
* **`Employee.java`**: DTO (Data Transfer Object) with validation constraints for API requests and responses.

### 3. Data Access Layer (JPA & JDBC)
* **`EmployeeRepository.java`**: Spring Data JPA repository providing query methods (`findByActiveTrueOrderByEmployeeIdAsc`, etc.).
* **`EmployeeDAO.java`**: Common DAO interface defining data access contracts.
* **`EmployeeJpaDAO.java`**: `@Primary` DAO implementation using Spring Data JPA for active employee CRUD operations.
* **`ArchivedEmployeeDAO.java`**: Alternate DAO qualified by `@Qualifier("archivedEmployeeDAO")` using `JdbcTemplate` and `RowMapper` for archived employee queries.

### 4. Service Layer
* **`EmployeeService.java`**: Service layer orchestrating business logic and delegating calls between JPA DAO and JDBC DAO.

### 5. Controller Layer
* **`EmployeeController.java`**: REST Controller exposing CRUD endpoints (`GET /employees`, `GET /employees/archived`, `GET /employees/{id}`, `POST /employees`, `PUT /employees/{id}`, `DELETE /employees/{id}`).

### 6. Test Layer
* **`EmployeeCrudIntegrationTest.java`**: Integration test verifying complete CRUD operations, data persistence, and active/archived state transitions.

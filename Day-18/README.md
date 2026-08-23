# Day 18: DTO/Entity Separation, Global Exception Handling & AOP Logging

Day 18 implements **Strict DTO/Entity Separation**, **Standardized Global Exception Handling**, and **AOP Method Execution Timing & Error Logging** in the **Fundoo Notes App** (`Day-13/fundooNotes`).

---

## 🚀 Key Architectural Concepts Implemented

### 1. Use Case 14: DTO/Entity Separation & Global Exception Handling
- **Zero Raw Entity Exposure**: Every controller method returns dedicated DTOs (`NoteResponse`, `LabelResponse`, `CheckListResponse`, `CollaboratorResponse`, `AuthResponse`, `BatchJobResponse`, `ErrorResponse`).
- **Domain Exception Hierarchy**:
  - `NoteNotFoundException` (404)
  - `UserNotFoundException` (404)
  - `LabelNotFoundException` (404)
  - `CheckListItemNotFoundException` (404)
  - `DuplicateEmailException` (409)
  - `InvalidCredentialsException` (401)
  - `UnauthorizedActionException` (403)
  - `InvalidNoteStateException` (400)
- **Standardized `ErrorResponse` Shape**:
  ```json
  {
    "message": "Error description",
    "status": 404,
    "timestamp": "2026-08-23T10:00:00"
  }
  ```
- **Catch-All Exception Handler**: Returns 500 without leaking raw internal exception stack traces or implementation details to clients.

### 2. Use Case 15: AOP Execution Logging & Error Aspect
- **`ExecutionTimeAspect` (`@Around`)**:
  - Pointcut: `execution(* com.bridgelabz.fundoonotes.service.*.*(..))`
  - Automatically measures and logs service method execution times in milliseconds with zero code added to the service classes.
- **`ServiceExceptionLoggingAspect` (`@AfterThrowing`)**:
  - Automatically catches and logs errors thrown across all service methods.

---

## 🧪 Integration Tests
- `GlobalExceptionAndDtoHardeningIntegrationTest.java`: Verifies consistent error JSON formatting across duplicate emails (409), invalid credentials (401), not found (404), invalid state (400), and validation failures (400).
- `AopExecutionLoggingIntegrationTest.java`: Verifies `@Around` execution timer aspect intercepts service method invocations and `@AfterThrowing` logs exceptions.

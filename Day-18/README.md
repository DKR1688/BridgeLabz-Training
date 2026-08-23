# Day 18 — DTO Hardening, Global Exception Handling, AOP Logging & Regression

Day 18 implements **Strict DTO/Entity Separation**, **Standardized Global Exception Handling**, **AOP Execution Timing & Error Logging Aspects**, and **Use Case 16 Full Regression & Schema Validation** in the **Fundoo Notes App** (`Day-13/fundooNotes`).

---

## Classes Implemented / Modified

### 1. Exception Layer
* **`ApiExceptionHandler.java`**: Centralized `@RestControllerAdvice` mapping domain exceptions to HTTP status codes with standardized `ErrorResponse` JSON.
* **`NoteNotFoundException.java`**: Domain exception mapped to `404 Not Found`.
* **`UserNotFoundException.java`**: Domain exception mapped to `404 Not Found`.
* **`LabelNotFoundException.java`**: Domain exception mapped to `404 Not Found`.
* **`CheckListItemNotFoundException.java`**: Domain exception mapped to `404 Not Found`.
* **`DuplicateEmailException.java`**: Domain exception mapped to `409 Conflict`.
* **`InvalidCredentialsException.java`**: Domain exception mapped to `401 Unauthorized`.
* **`UnauthorizedActionException.java`**: Domain exception mapped to `403 Forbidden`.
* **`InvalidNoteStateException.java`**: Domain exception mapped to `400 Bad Request`.
* **`InvalidNoteRowException.java`**: Domain exception mapped to `400 Bad Request`.

### 2. DTO & Entity Layer
* **`ErrorResponse.java`**: Standardized error response DTO containing `message`, `status`, and formatted `timestamp`.
* **`NoteResponse.java`**: Clean response DTO encapsulating note details without exposing internal `@Entity` structures.
* **`LabelResponse.java`**: DTO returning label properties (`id`, `label`, `isDeleted`).
* **`User.java` / `Note.java` / `Tag.java` / `PasswordResetToken.java`**: Entities audited and hardened for zero raw entity exposure across all controllers.

### 3. Aspect (AOP) Layer
* **`ExecutionTimeAspect.java`**: AspectJ `@Aspect` using `@Around("execution(* com.bridgelabz.fundoonotes.service.*.*(..))")` to automatically log service execution time in milliseconds.
* **`ServiceExceptionLoggingAspect.java`**: AspectJ `@Aspect` using `@AfterThrowing` to catch and log any service exception across the service layer.

### 4. Configuration & Database Layer
* **`schema.sql`**: Complete database schema DDL defining tables, foreign keys, and indexes for all entities.
* **`application.properties`**: Configured `spring.jpa.hibernate.ddl-auto=validate` and `spring.sql.init.mode=always` to enforce strict schema validation.
* **`application-mysql.properties`**: Updated to `spring.jpa.hibernate.ddl-auto=validate`.

### 5. Controller Layer
* **`NoteController.java` / `UserController.java` / `LabelController.java` / `AuthController.java`**: Audited to guarantee that 100% of endpoints return response DTOs and never expose raw JPA entities.

### 6. Test Layer & Collections
* **`FullRegressionHardeningIntegrationTest.java`**: End-to-end regression test suite executing all Use Cases 2 through 13 sequentially.
* **`GlobalExceptionAndDtoHardeningIntegrationTest.java`**: Tests standardized `ErrorResponse` formatting across 400, 401, 403, 404, and 409 status codes.
* **`AopExecutionLoggingIntegrationTest.java`**: Tests `@Around` method execution timing and `@AfterThrowing` exception logging aspects.
* **`FundooNotes_UseCases_2_to_13.postman_collection.json`**: Complete Postman API collection covering all endpoints with parameterized requests and sample payloads.

---

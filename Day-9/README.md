# Day 9 — Spring Boot Fundamentals

Day 9 implements **Spring Boot Fundamentals** and REST API architecture in the **Contacts App** (`Day-7/contacts-app`).

---

## Classes Implemented / Modified

### 1. Application Entry Point
* **`ContactsAppApplication.java`**: Main Spring Boot entry point with `@SpringBootApplication` enabling auto-configuration, component scanning, and embedded server.

### 2. Entity & DTO Layer
* **`Contact.java`**: Entity mapping contacts data (ID, name, email, phone, address).
* **`ContactRequest.java`**: DTO for incoming request payload validation.
* **`ContactResponse.java`**: DTO for structured API responses.
* **`ContactMapper.java`**: Utility to map between `Contact` entity and DTOs.

### 3. Repository & Data Access Layer
* **`ContactRepository.java`**: Spring Data JPA repository for contact persistence.
* **`DatabaseContactDAO.java`**: Database DAO implementation for contact data operations.
* **`InMemoryContactDAO.java`**: In-memory DAO implementation for fast lookups/mocking.

### 4. Service Layer
* **`ContactService.java`**: Interface defining core business operations for contacts.
* **`ContactServiceImpl.java`**: Service implementation handling contact creation, retrieval, updates, deletions, and duplicate validations.
* **`ContactLookupService.java`**: Specialized service for lookup queries.

### 5. Controller Layer
* **`ContactController.java`**: REST controller exposing CRUD endpoints (`GET /api/contacts`, `GET /api/contacts/{id}`, `POST /api/contacts`, `PUT /api/contacts/{id}`, `DELETE /api/contacts/{id}`).
* **`ContactDiTrainingController.java`**: Controller demonstrating Spring dependency injection patterns.

### 6. Exception & Config Layer
* **`GlobalExceptionHandler.java`**: `@RestControllerAdvice` for centralized API error handling.
* **`ContactNotFoundException.java`** & **`DuplicateEmailException.java`**: Custom business exceptions.
* **`SecurityConfig.java`**, **`CorsConfig.java`**, **`OpenApiConfig.java`**: Application configurations.

### 7. Test Layer
* **`ContactControllerTest.java`**: Unit and integration tests for REST endpoints.
* **`ContactServiceImplTest.java`**: Service layer business logic tests.
* **`ContactsAppApplicationTests.java`**: Spring application context load tests.

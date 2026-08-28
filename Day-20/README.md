# Day 20 — FundooNotes Advanced Microservices, Resilience & 3-Level Testing

Today we extended the **Fundoo Notes Microservices Architecture** (`Day-19/fundooNotes`) with production-grade patterns: **Role-Based Collaborator Permissions**, **Real Email Dispatch via SMTP**, **Centralized Spring Cloud Config Server**, **Resilience4j Circuit Breaker**, **Docker Containerization**, **SonarQube Static Code Analysis**, and a comprehensive **3-Level Testing Framework** (Business Logic, MVC Layer, and REST Assured Integration Tests).

---

## 📁 Key Files Implemented & Modified

### `notes-service`
* `NoteCollaborator.java`: JPA entity storing `note`, `collaboratorId`, and `Role` (`VIEWER`, `EDITOR`).
* `NoteCollaboratorRepository.java`: Repository for finding collaborator relationships and roles.
* `Note.java`: Upgraded to map `@OneToMany` `NoteCollaborator` join entities.
* `NoteService.java`: Implemented `requireEditorAccess()` checks for all modifying endpoints.
* `CollaboratorService.java`: Added role assignment, role retrieval, and permission checks.
* `UserServiceClient.java`: Added `@CircuitBreaker` annotations and fallback methods.
* `NoteServiceUnitTest.java`: Level 1 Unit tests for pin/archive/trash and viewer/editor permissions.
* `CollaboratorServiceUnitTest.java`: Level 1 Unit tests for adding/removing collaborators with roles.
* `NoteControllerMvcTest.java`: Level 2 MVC tests for endpoint routing and validation.
* `NoteCollaboratorRestAssuredIntegrationTest.java`: Level 3 E2E test verifying full multi-user permission lifecycle.
* `UserServiceClientCircuitBreakerIntegrationTest.java`: Level 3 Integration test for Circuit Breaker fail-fast fallback.

### `user-auth-service`
* `EmailService.java`: Email dispatch service using `JavaMailSender`.
* `UserServiceUnitTest.java`: Level 1 Unit tests for duplicate email detection and password hashing.
* `PasswordRecoveryServiceUnitTest.java`: Level 1 Unit tests for password reset tokens and JMS dispatch.
* `AuthControllerMvcTest.java`: Level 2 MVC tests for registration, login, and forgot password endpoints.
* `UserAuthRestAssuredIntegrationTest.java`: Level 3 REST Assured E2E test for complete user auth lifecycle.

### `reminder-service`
* `EmailService.java`: Email dispatch service for note reminders and reset emails.
* `JmsConsumerService.java`: Asynchronous ActiveMQ Artemis consumer dispatching real emails with error handling.

### `config-server` (New Module)
* `ConfigServerApplication.java`: Spring Cloud Config Server application entry point.
* `application.properties`: Native search location pointing to `classpath:/config/`.
* `src/main/resources/config/`: Contains centralized properties for all services (`application.properties`, `notes-service.properties`, `user-auth-service.properties`, etc.).

### Deployment & Quality
* `Dockerfile` (in all 6 modules): JRE 21 container definitions.
* `docker-compose.yml`: Multi-container orchestration tying MySQL, Redis, RabbitMQ, SonarQube, and all 6 microservices.
* `pom.xml` (root & child modules): Configured `sonar-maven-plugin` and `sonar.projectKey`.

---

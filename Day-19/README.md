# Day 19 — Monolith → Microservices Decomposition (Use Cases 17–20 Capstone)

Day 19 implements the full **Microservices Decomposition** of the **Fundoo Notes App** into five independently runnable, fault-tolerant Spring Boot services located in [`Day-19/fundooNotes`](file:///d:/BridgeLabz-Training/Day-19/fundooNotes).

---

## Microservices & Classes Implemented / Modified

### 1. `eureka-server` (Port 8761)
* **`EurekaServerApplication.java`**: Configured with `@EnableEurekaServer` as the centralized Netflix Eureka Service Registry.
* **`application.yml`**: Configured on port `8761` with standalone mode (`register-with-eureka: false`, `fetch-registry: false`).

### 2. `user-auth-service` (Port 8081)
* **`UserController.java`**: Implemented minimal public existence check `GET /users/{id}` (HTTP 200 OK / 404 NOT_FOUND) without exposing sensitive body fields.
* **`AuthController.java`**: User registration, login, password recovery, and reset endpoints.
* **`User.java` / `PasswordResetToken.java`**: User entity and token models persisted in isolated `user_auth_db`.
* **`UserRepository.java` / `PasswordResetTokenRepository.java`**: Isolated Spring Data JPA repositories.
* **`UserService.java` / `PasswordRecoveryService.java`**: User business logic and recovery flows.
* **`JwtUtil.java` / `JwtAuthenticationFilter.java` / `SecurityConfig.java`**: JWT creation, claim parsing, and stateless Spring Security configuration.
* **`TokenCacheService.java`**: Redis token caching with in-memory resilient fallback.
* **`ApiExceptionHandler.java`**: Standardized domain exception handling returning `ErrorResponse`.

### 3. `notes-service` (Port 8082)
* **`Note.java`**: Decoupled `Note.owner` JPA `@ManyToOne` entity into a plain `ownerId` int; replaced collaborator entities with `@ElementCollection Set<Integer> collaboratorIds` to eliminate cross-database foreign key constraints.
* **`UserServiceClient.java`**: Inter-service HTTP client using `@LoadBalanced RestTemplate` calling `http://user-auth-service/users/{id}` to validate note owners and collaborators.
* **`UserServiceUnavailableException.java`**: Custom domain exception mapping remote service downtime to HTTP `503 Service Unavailable`.
* **`NoteService.java` / `CheckListService.java` / `CollaboratorService.java`**: Note lifecycle management, checklist sub-items, and shared note authorization.
* **`NoteBatchService.java` / `NoteExportService.java`**: Spring Batch Excel note import and Apache POI note export.
* **`JmsProducerService.java` / `RabbitProducerService.java`**: Asynchronous event dispatching to ActiveMQ Artemis JMS queues and RabbitMQ Topic exchanges.
* **`NoteController.java` / `LabelController.java`**: REST controllers exposing note actions, tags, reminders, and collaborations.
* **`ExecutionTimeAspect.java` / `ServiceExceptionLoggingAspect.java`**: AspectJ execution time tracking and error logging.

### 4. `reminder-service` (Port 8083)
* **`JmsConsumerService.java`**: Asynchronous consumer listening to `fundoonotes.reminders.queue` and `fundoonotes.recovery.queue`.
* **`RabbitConsumerService.java` / `ActivityLogConsumerService.java`**: Consumers listening to `fundoonotes.notes.exchange` with routing key bindings.
* **`ReminderHealthController.java`**: Health check endpoint for reminder worker status.
* **`JmsConfig.java`**: Configured `MappingJackson2MessageConverter` with `JavaTimeModule` for Jackson date-time serialization.

### 5. `api-gateway` (Port 8080)
* **`ApiGatewayApplication.java`**: Spring Cloud Gateway entry point with `@EnableDiscoveryClient`.
* **`application.yml`**: Configured dynamic Eureka route discovery (`spring.cloud.gateway.discovery.locator.enabled=true`) routing:
  * `/auth/**`, `/users/**` ➔ `lb://user-auth-service`
  * `/notes/**`, `/labels/**` ➔ `lb://notes-service`
  * `/reminders/**` ➔ `lb://reminder-service`

---

## Testing Layer

* **`FundooNotesDistributedSystemEndToEndTest.java`**: Validates the 10-step distributed capstone demo checklist exclusively routed through the Gateway.
* **`UserAuthFlowIntegrationTest.java`**: Tests isolated user authentication and existence check.
* **`NotesServiceInterServiceIntegrationTest.java`**: Tests cross-service validation and error handling.
* **`ReminderEventProcessingTest.java`**: Tests asynchronous message reception across JMS queues.
* **`ApiGatewayApplicationTests.java`**: Tests Gateway route discovery and configuration.

---

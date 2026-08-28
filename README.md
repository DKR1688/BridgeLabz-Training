# BridgeLabz-Training -- Daily Progress Log (Refresher-Training)
> This log documents the daily progress of tasks completed during the BridgeLabz training program, as recorded in the repository’s README. Tasks are grouped by week with thematic headings, detailing the work done on daily basis.

> **📂 Current Structure of GitHub :-**
```text
BridgeLabz-Training/
│
├── core-java-practice/            (Branch & Folder Name)
│
├── oops-practice/                 (Branch & Folder Name)
│
├── dsa-practice/                 (Branch & Folder Name)
│
├── collection-practice/                 (Branch & Folder Name)
│
├── io-programming-practice/ 				(Branch & Folder Name)
│
├── java-eight-features-practice/                 (Branch & Folder Name)
│
├── multi-threading-practice/                 (Branch & Folder Name)
│
├── jdbc-dbms-practice/                 (Branch & Folder Name)
│
├── solution-designing-practice/                  (Branch & Folder Name)
│
├── reviews/                       (Branch & Folder Name)
│
│
│
├── Refresher-Training/                  (Branch & Folder Name)
│   │ 
│   ├── Day-1/
│   ├── Day-2/
│   ├── Day-3/
│   ├── Day-4/
│   ├── Day-5/
│   │   .
│   │   .
│   │   .
│   │
│   └── README.md
│
│
│
+-- README.md
```


> 🗓️ 31-July-2026 (DBMS fundamentals & RDBMS basics):
- Completed different types of DBMS – Relational vs Non Relational - when to use what; Introduction to MySQL and RDBMS concepts; DDL (Data Definition Language) and DML (Data Manipulation Language); Completed set up MySQL environment and begin ER Diagram sketch for the Health Clinic app (patients, doctors, appointments); Completed Day 1 assignment;


## 🚀 Week 01
> 🗓️ 02-August-2026 (ER Diagrams, Indexing and Normalization):
- Completed ER Diagram design principles – entities, relationships, cardinality; Table Indexing – purpose and performance impact; Normalization Forms (1NF, 2NF, 3NF, BCNF); Completed ER Diagram for the Health Clinic App; Normalize the patient/doctor/appointment schema; Completed Day 2 assignment;

> 🗓️ 03-August-2026 (Joins, Stored procedures and Triggers):
- Completed Inner, Left, Right and Full outer join; creation and usage procedures; Triggers to sue case for automated database actions; Completed practice with joins, stored procedures and triggers for the Health clinic app;

> 🗓️ 04-August-2026 (DB Programming, JDBC & Health Clinic App):
- Learned about JDBC - connecting a Java application to MySQL; Completed CRUD operations via JDBC; connection pooling basics; Developed Console-based Health Clinic App to register patients, manage doctors & specialties, schedule appointments, track visit history & basic billing - persisted via JDBC (MySQL);

> 🗓️ 05-August-2026 (Tomcat, Servlet and Spring introduction):
- Completed Tomcat as a web/application server; Servlet lifecycle and deployment, Introduction to the Spring framework; Spring core concepts: IoC (Inversion of Control) and dependency injection; Spring MVC architecture: DispatcherServlet, controllers, views; Prepared the structure for MyGreetingsApp as a Spring MVC application.
This includes the layered package layout and JSP view placeholders without business implementation.

> 🗓️ 06-August-2026 (Spring MVC):
- Completed Spring MVC archetecture with dispatcherServlet, controller, views; Learned about request mapping and handling in Spring MVC; Builted My Greetings App using spring MVC with CRUD operations; Created or deployed a basic myFirstServlet on Tomcat;


## 🚀 Week 02
> 🗓️ 10-August-2026 (Spring REST Api and Request Handling):
- Completed Spring REST api programming with building RESTful endpoints to contact-app; Completed Request handling patterns; Completed H2 in memory databases basics; Learned about distributed architectures; Created a contects-app with absic REST endpints;

> 🗓️ 11-August-2026 (SQL quries and spring boot practice):
- Practiced with SQL quries and spring boot project with curd operation and validation annotation; Continuing contacts-app implementation with new features;

> 🗓️ 12-August-2026 (Spring Boot Fundamentals):
- Completed coding review above gla_practice_project with gla_db; Completed Spring Boot with auto configuration, starters and embedded server; Learned and performed Spring controller and REST api basic; Complete gla_practice_project app to gla_db with all curd operations;

> 🗓️ 13-August-2026 (Dependency Injection and H2 Database):
- Learned above dependency injection in spring boot and h2 database integration and configuration; Wried H2 databases into contacts-app; Developed Employee payroll app in spring boot with CURD oprations;

> 🗓️ 14-August-2026 (Spring Services, Spring JPA and Spring JDBC):
- Completed service layer design with REST API, repositories and entity mapping with JPA and template-based data access with spring JDBC;


## 🚀 Week 03
> 🗓️ 17-August-2026 (Spring scopes, logging, postman and maven):
- Completed above spring bean scopes like singleton, prototype, request and session; Completed postman api testing for Employee payroll app and address book app; Completed logging and dependency management;

> 🗓️ 18-August-2026 (Spring Security and JWT authentication):
- Recaped above spring security fundamentals; Completed JSON Web Token in spring security; Applied security fundamentals and JWT above contacts_app to user authentication; Prepared spring boot project strcture to fundooNotes app to user management;

> 🗓️ 19-August-2026 (Authorization & JPA for Notes Management):
- Implemented per-request JWT validation via OncePerRequestFilter and populated SecurityContextHolder; Implemented ownership authorization and IDOR prevention for Fundoo Notes app; Established bidirectional JPA relationships: @OneToMany / @ManyToOne between User and Note, and @ManyToMany between Note and Tag via note_tags junction table; Demonstrated and resolved LazyInitializationException with JPA JOIN FETCH queries; Created and executed comprehensive integration test suite covering all Day 14 problems.

> 🗓️ 20-August-2026 (Note Organisation: Pin/Archive/Trash, Search & Tags):
- Implemented Note state management (ACTIVE, ARCHIVED, TRASHED) with @Enumerated(EnumType.STRING) and independent pinned attribute in Fundoo Notes app; Built PATCH action endpoints; Implemented dynamic multi-filter search using Spring Data JPA Specification and CriteriaBuilder scoped to authenticated user; Managed @ManyToMany Note-Tag relationships with junction table queries; Created NoteOrganisationIntegrationTest covering all 6 Day 15 problems.

> 🗓️ 21-August-2026 (JMS Asynchronous Reminders, Async Recovery & Redis Token Caching):
- Configured Apache Artemis embedded JMS broker for asynchronous reminder publishing and consumption in Fundoo Notes app; Implemented sub-50ms instant return for reminder endpoints while processing background jobs via @JmsListener; Implemented asynchronous password recovery event dispatch via JMS; Integrated Redis token validation caching via TokenCacheService and RedisTemplate with TTL enforcement strictly derived from the JWT's remaining expiration;

> 🗓️ 23-August-2026 (RabbitMQ, Spring Batch & Global Exceptions, AOP Logging ):
- Configured RabbitMQ Topic Exchange with multi-queue bindings for decoupled note events; Implemented Spring Batch Excel note import with chunk processing and skip counting for invalid rows; Implemented checklist sub-items with parent note authorization; Established @ManyToMany collaborator relationships allowing shared view/edit permissions while protecting owner-only delete;
- Implemented strict DTO/Entity separation across all controllers with zero raw @Entity exposure; Implemented domain exception hierarchy and global ErrorResponse standard shape; Created AspectJ AOP @Around ExecutionTimeAspect and @AfterThrowing ServiceExceptionLoggingAspect across the service layer; Verified entire 33-test integration suite with 100% build success.


## 🚀 Week 04
> 🗓️ 24-August-2026 (Monolith to Microservices - Auth Splitting, Inter-Service Communication & Gateway):
- Extracted user authentication domain into an independently runnable user-auth-service with isolated JWT issuance and minimal public existence check GET /users/{id}; Decoupled Note.owner @ManyToOne reference to plain ownerId int to eliminate cross-database foreign key constraints;

> 🗓️ 25-August-2026 (Inter-Service Communication & Gateway, Eureka Service Registry):
- Implemented UserServiceClient in notes-service using @LoadBalanced RestTemplate for inter-service collaborator validation; Implemented fault tolerance returning HTTP 503 Service Unavailable when user-auth-service is down; Configured Spring Cloud API Gateway for unified edge routing and dynamic load balancing; Configured Eureka Service Registry on Port 8761; 

> 🗓️ 26-August-2026 (Async Reminder Extraction & Full Distributed System Capstone):
- Registered all microservices (user-auth-service, notes-service, reminder-service, api-gateway) dynamically; Extracted reminder-service consuming ActiveMQ Artemis JMS and RabbitMQ events; Complete distributed workflow exclusively through Gateway: register, login, create note, add label, add collaborator, set reminder, archive, search by label, export to Excel, graceful 503 failure on auth downtime, and self-healing restoration with 100% test success across all 5 microservices.

> 🗓️ 27-August-2026 (Role-Based Collaborators, Email Delivery, Config Server):
- Implemented Use Case 21 Role-Based Collaborators (NoteCollaborator join entity with VIEWER/EDITOR roles, immediate role reflection, owner-only delete/collaborator management, 400 rejection for viewers attempting edits); Implemented Use Case 22 Real Email Delivery via JavaMailSender in user-auth-service and reminder-service with asynchronous JMS processing and error logging; Implemented Use Case 23 Centralized Spring Cloud Config Server on Port 8888 serving unified properties across all microservices;

🗓️ 28-August-2026 (Circuit Breaker, Docker, SonarQube & 3-Level Testing):
- Implemented Use Case 24 Resilience4j Circuit Breaker around UserServiceClient for fail-fast fault tolerance during inter-service outages; Implemented Use Case 25 Dockerizing each microservice with Dockerfiles and a unified docker-compose.yml orchestrating MySQL, Redis, RabbitMQ, SonarQube, and all 6 microservices; Implemented Use Case 26 SonarQube static code analysis plugin integration with zero hardcoded secrets; Established comprehensive 3-Level Testing Suite (Level 1: Business Logic Mockito unit tests, Level 2: @WebMvcTest/MockMvc controller tests, Level 3: REST Assured full end-to-end integration tests) with 100% build and test success.

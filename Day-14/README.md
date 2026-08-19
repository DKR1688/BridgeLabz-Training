# Day 14 — Authorization & JPA for Notes Management

Day 14 implements **JWT authorization**, **ownership security**, and **JPA entity relationships** in the **Fundoo Notes App** (`Day-13/fundooNotes`).

---

## Classes Implemented / Modified

### 1. Security Layer
* **`JwtAuthenticationFilter.java`**: Intercepts every HTTP request, validates the Bearer token, and stores the user identity in `SecurityContextHolder`.
* **`JwtUtil.java`**: Added `isTokenValid()` and `extractUserId()` helper methods.
* **`SecurityConfig.java`**: Configured `/notes/**` endpoints to require authentication.

### 2. Entity Layer (JPA Relationships)
* **`User.java`**: Added `@OneToMany` relationship to hold user's notes (`mappedBy = "owner"`).
* **`Note.java`**: Created Note entity with `@ManyToOne` (owning `user_id` foreign key) and `@ManyToMany` with `Tag`.
* **`Tag.java`**: Created Tag entity with `@ManyToMany` mapped to notes via `note_tags` table.

### 3. Repository Layer
* **`NoteRepository.java`**: Added ownership query methods (`findByOwner`, `findByNoteIdAndOwner`).
* **`UserRepository.java`**: Added `findByIdWithNotes()` with `JOIN FETCH` to prevent `LazyInitializationException`.
* **`TagRepository.java`**: Repository for managing tags (`findByName`).

### 4. Service Layer
* **`NoteService.java`**: Implements Note CRUD logic strictly scoped to the logged-in user to prevent IDOR vulnerabilities (returns `404 Not Found` for unauthorized access).

### 5. Controller & DTO Layer
* **`NoteController.java`**: Exposes REST endpoints (`POST`, `GET`, `PUT`, `DELETE` at `/notes`) reading identity from `SecurityContextHolder`.
* **`NoteRequest.java`**: DTO for creating and updating notes (`title`, `content`, `tags`).
* **`NoteResponse.java`**: DTO for sending clean note responses to the client.

### 6. Test Layer
* **`NotesOwnershipIntegrationTest.java`**: Automated test suite verifying all 6 Day 14 scenarios (JWT filter branches, IDOR prevention, lazy loading with `JOIN FETCH`, `@ManyToMany` tags, and 404 security on delete/update).

---

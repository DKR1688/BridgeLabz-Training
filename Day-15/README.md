# Day 15 — Organisation Modules: Pin/Archive/Trash, Search & Tags

Day 15 implements **Note State Management** (`ACTIVE`, `ARCHIVED`, `TRASHED`), **Pinned Notes**, **Dynamic Search with Spring Data JPA Specification**, and **ManyToMany Tag Management** in the **Fundoo Notes App** (`Day-13/fundooNotes`).

---

## Classes Implemented / Modified

### 1. Entity Layer
* **`Note.java`**: Added `Note.NoteState` enum (`ACTIVE`, `ARCHIVED`, `TRASHED`) with `@Enumerated(EnumType.STRING)` and independent boolean `pinned` attribute.
* **`Tag.java`**: Maintained `@ManyToMany` mapping with `Note` via `note_tags` junction table.

### 2. DTO Layer
* **`NoteResponse.java`**: Added `state` and `pinned` attributes to note response record to expose complete organisation state.
* **`TagRequest.java`**: DTO for creating and adding tags to notes (`@NotBlank String name`).

### 3. Repository Layer
* **`NoteRepository.java`**: Extended `JpaSpecificationExecutor<Note>` and added derived query methods (`findByOwnerAndState`, `findByOwnerAndPinnedTrueAndStateNot`, `findByOwnerAndTagsName`) with `@EntityGraph(attributePaths = {"tags"})`.
* **`NoteSpecifications.java`**: Specification factory dynamically combining `title`, `state`, `tag`, and `pinned` predicates while strictly enforcing the unconditional `owner` authorization predicate.
* **`TagRepository.java`**: Repository for managing tag persistence (`findByName`).

### 4. Service Layer
* **`NoteService.java`**: Implemented state transitions and business rules:
  * `archiveNote()`: sets `state = ARCHIVED`, clears `pinned = false`.
  * `trashNote()`: sets `state = TRASHED`, clears `pinned = false`.
  * `restoreNote()`: restores note to `state = ACTIVE` (pinned remains `false`).
  * `pinNote()`: validates state; throws `IllegalStateException` if note is trashed.
  * `unpinNote()`: sets `pinned = false`.
  * `search()`: executes dynamic queries via `NoteSpecifications`.
  * `addTagToNote()`: attaches tags and updates `note_tags` association.

### 5. Controller Layer
* **`NoteController.java`**: Exposes organization REST endpoints:
  * State actions: `PATCH /notes/{id}/archive`, `PATCH /notes/{id}/trash`, `PATCH /notes/{id}/restore`, `PATCH /notes/{id}/pin`, `PATCH /notes/{id}/unpin`.
  * Tag management: `POST /notes/{id}/tags`.
  * State filtering: `GET /notes` (defaults to `ACTIVE`; filters by `?state=`, `?pinned=`, `?tag=`).
  * Dynamic search: `GET /notes/search` (multi-filter search with `?title=`, `?state=`, `?tag=`, `?pinned=`).

### 6. Exception Layer
* **`ApiExceptionHandler.java`**: Handles `IllegalStateException` and returns a clean `400 Bad Request` JSON response (`{"message": "Cannot pin a note that is in Trash"}`).

---

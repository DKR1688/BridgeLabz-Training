# Day 17 — Advanced Messaging, Batch Processing, Checklists & Collaborators

Day 17 implements **RabbitMQ Topic Exchange Event Routing**, **Spring Batch & Apache POI Excel Import/Export**, **Checklist Sub-items (`NoteCheckList`)**, and **Collaborator Note Sharing (`@ManyToMany`)** in the **Fundoo Notes App** (`Day-13/fundooNotes`).

---

## Classes Implemented / Modified

### 1. Configuration Layer
* **`RabbitMQConfig.java`**: Configures RabbitMQ `TopicExchange` (`notes-exchange`), declares queues (`collaborator-notify-queue`, `activity-log-queue`), and sets up bindings with routing keys (`note.shared`, `note.#`).
* **`BatchConfig.java`**: Configures Spring Batch job (`importNotesJob`) and chunk-based step (`importNotesStep`) for processing note spreadsheet records with fault-tolerant skip support.

### 2. Entity & DTO Layer
* **`NoteCheckList.java`**: Entity representing checklist sub-items with fields `id`, `itemName`, `status` (`PENDING`/`DONE`), `isDeleted`, and `@ManyToOne` association with parent `Note`.
* **`Note.java`**: Extended with `@ManyToMany` `collaborators` mapping to `users` via `note_collaborators` junction table and `@OneToMany` `checkLists` collection.
* **`CheckListRequest.java`**: DTO for creating and updating checklist items (`itemName`, `status`, `isDeleted`).
* **`CheckListResponse.java`**: DTO returning checklist sub-item details without exposing internal entity properties.
* **`CollaboratorRequest.java`**: DTO containing collaborator email and user ID.
* **`CollaboratorResponse.java`**: DTO returning collaborator user profile details (`userId`, `email`, `name`).
* **`NoteSharedMessage.java`**: RabbitMQ event payload for note sharing containing `noteId`, `ownerId`, and `collaboratorEmail`.
* **`BatchJobResponse.java`**: DTO returning batch execution metrics (`readCount`, `writeCount`, `skipCount`, `status`).
* **`NoteImportRow.java`**: Spreadsheet row mapping DTO used by Spring Batch reader.

### 3. Service Layer
* **`RabbitProducerService.java`**: Publishes asynchronous note events (`note.shared`) to RabbitMQ Topic Exchange via `RabbitTemplate`.
* **`RabbitConsumerService.java`**: Listens on `collaborator-notify-queue` using `@RabbitListener` to notify collaborators.
* **`ActivityLogConsumerService.java`**: Independent second consumer listening on `activity-log-queue` (`note.#`) proving decoupled event consumption without modifying producer code.
* **`CheckListService.java`**: Manages checklist CRUD operations, bulk status updates (`bulkCompleteAll`), and parent note ownership validation.
* **`CollaboratorService.java`**: Handles adding/removing collaborators, enforces read/write permissions for collaborators, and restricts note deletion to owners only.
* **`NoteBatchService.java`**: Executes Spring Batch Excel import pipeline, parses spreadsheet rows, and tracks skip counts for invalid rows.
* **`NoteExportService.java`**: Generates styled `.xlsx` spreadsheets using Apache POI for authenticated user notes.

### 4. Controller Layer
* **`NoteController.java`**: Exposes checklist, collaborator, and batch REST endpoints:
  * `GET /notes/{id}/noteCheckLists` — Lists all checklist items for a note.
  * `POST /notes/{id}/noteCheckLists` — Adds a checklist item to a note.
  * `PUT /notes/{id}/noteCheckLists/{fk}` — Updates checklist item status or text.
  * `PATCH /notes/{id}/noteCheckLists/completeAll` — Marks all checklist items as completed.
  * `DELETE /notes/{id}/noteCheckLists/{fk}` — Deletes a checklist item.
  * `POST /notes/{id}/AddcollaboratorsNotes` — Adds a collaborator and fires RabbitMQ event.
  * `GET /notes/{id}/collaborators` — Lists collaborators for a note.
  * `DELETE /notes/{id}/removeCollaboratorsNotes/{collaboratorUserId}` — Removes a collaborator (owner only).
  * `POST /notes/import` — Uploads and imports Excel spreadsheet notes via Spring Batch.
  * `GET /notes/export` — Downloads user notes as an Excel file (`fundoo_notes.xlsx`).

### 5. Test Layer
* **`CollaboratorsAndRabbitMQIntegrationTest.java`**: Verifies note sharing, collaborator editing, unauthorized delete rejection (403), and RabbitMQ topic exchange routing.
* **`SpringBatchExcelIntegrationTest.java`**: Verifies 50-row batch import with skip count verification and Apache POI Excel export generation.
* **`CheckListIntegrationTest.java`**: Verifies checklist sub-item CRUD operations, bulk toggle, and parent note security isolation.

---

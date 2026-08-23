# Day 17: RabbitMQ Messaging, Spring Batch Excel Processing, Checklists & Collaborators

Day 17 implements **Advanced Messaging with RabbitMQ Topic Exchange**, **Spring Batch & Apache POI Excel Import/Export**, **Checklist Sub-items (`NoteCheckList`)**, and **Collaborator Note Sharing (`@ManyToMany`)** in the **Fundoo Notes App** (`Day-13/fundooNotes`).

---

## 🚀 Key Architectural Concepts Implemented

### 1. Use Case 10: RabbitMQ Topic Exchange Messaging Path
- **Topic Exchange (`notes-exchange`)**: Provides decoupled, multi-consumer event distribution via routing keys (`note.shared`, `note.deleted`, `note.#`).
- **Decoupled Consumers**:
  - `RabbitConsumerService`: Listens to `collaborator-notify-queue` bound with routing key `note.shared`.
  - `ActivityLogConsumerService`: Listens to `activity-log-queue` bound with wildcard routing key `note.#`, proving consumer addition without modifying producer logic.
- **Why RabbitMQ vs JMS**:
  - JMS Reminders (Use Case 8) use a Point-to-Point direct queue model for strict single-consumer delivery.
  - RabbitMQ Note Sharing (Use Case 10) uses an Exchange model where 1 event can simultaneously route to notification, audit logging, and activity streaming queues based on patterns.

### 2. Use Case 11: Spring Batch & Apache POI Excel Pipeline
- **Spring Batch Excel Import**:
  - Chunk-based processing (`chunk(100)`).
  - Validates row fields; skips invalid rows (e.g. blank titles) via `faultTolerant().skip(InvalidNoteRowException.class)` with accurate `readCount`, `writeCount`, and `skipCount`.
- **Apache POI Excel Export**:
  - Direct POI `XSSFWorkbook` generator creating styled, openable `.xlsx` spreadsheets for single-user scoped datasets.

### 3. Use Case 12: Checklist Items on Notes
- **`NoteCheckList` Entity**: Sub-item to-do tracking (`id`, `itemName`, `status`, `isDeleted`, `note`).
- **REST Endpoints**:
  - `GET /notes/{id}/noteCheckLists`
  - `POST /notes/{id}/noteCheckLists`
  - `PUT /notes/{id}/noteCheckLists/{fk}`
  - `DELETE /notes/{id}/noteCheckLists/{fk}`
  - `PATCH /notes/{id}/noteCheckLists/completeAll`
- **Ownership/Access Security**: Enforces parent note authorization.

### 4. Use Case 13: Collaborators on Notes
- **`@ManyToMany` Note Collaborators**: `note_collaborators (note_id, user_id)`.
- **Authorization Extension**:
  - READ & EDIT: Note Owner OR Collaborators.
  - DELETE & COLLABORATOR MANAGEMENT: Note Owner ONLY.
  - Non-owner & non-collaborator receives `404 Not Found`.
- **RabbitMQ Event Trigger**: Automatically publishes `note.shared` event when a collaborator is added.

---

## 🧪 Integration Tests
- `CollaboratorsAndRabbitMQIntegrationTest.java`: Validates note sharing, collaborator editing, unauthorized delete rejection, and RabbitMQ topic exchange routing.
- `SpringBatchExcelIntegrationTest.java`: Validates 50-row batch import (45 valid, 5 invalid) with exact skip counting, and `.xlsx` export parsing.
- `CheckListIntegrationTest.java`: Validates checklist CRUD, bulk complete, and parent note security.

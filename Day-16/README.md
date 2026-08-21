# Day 16 — Advanced Messaging & Caching: JMS Reminders & Redis Token Caching

Day 16 implements **Asynchronous Messaging with Spring JMS (Apache Artemis)** for sub-50ms reminders & password recovery, and **Redis Token Validation Caching** for optimized JWT authentication in the **Fundoo Notes App** (`Day-13/fundooNotes`).

---

## Classes Implemented / Modified

### 1. Configuration Layer
* **`JmsConfig.java`**: Configures embedded Apache Artemis broker (`spring.artemis.mode=embedded`), JMS connection factory, and `MappingJackson2MessageConverter` for JSON serialization.
* **`RedisConfig.java`**: Configures `RedisTemplate<String, Object>` with `StringRedisSerializer` for keys and `GenericJackson2JsonRedisSerializer` for values.

### 2. DTO Layer
* **`ReminderMessage.java`**: JMS payload record containing `noteId`, `userId`, `userEmail`, and `reminderTime`.
* **`PasswordResetMessage.java`**: JMS payload record containing `email`, `resetToken`, and `expiryTime`.
* **`ReminderRequest.java`**: DTO for setting/updating note reminders (`@NotNull LocalDateTime reminderTime`).
* **`ForgotPasswordRequest.java`**: DTO containing user email for password reset requests.
* **`ResetPasswordRequest.java`**: DTO containing reset token and new password.

### 3. Service Layer
* **`JmsProducerService.java`**: Dispatches asynchronous messages to Artemis queues (`fundoo.reminders` and `fundoo.password-reset`) via `JmsTemplate`.
* **`JmsConsumerService.java`**: Asynchronously listens on JMS queues using `@JmsListener` to process reminders and password reset notifications in the background.
* **`TokenCacheService.java`**: Implements Redis-backed JWT validation caching (`jwt:valid:<token>`) with dynamic TTL matching token expiration, bypassing redundant cryptographic checks on cache hits.
* **`PasswordRecoveryService.java`**: Manages secure password reset tokens and delegates email dispatch to JMS queue.
* **`NoteService.java`**: Updated to handle reminder scheduling (`addUpdateReminder`), listing notes with active reminders (`getReminderList`), and reminder removal (`removeReminder`).

### 4. Security Layer
* **`JwtAuthenticationFilter.java`**: Integrated with `TokenCacheService` to perform ultra-fast cache-assisted JWT validation.
* **`SecurityConfig.java`**: Configured security filter chain and public access rules for password recovery endpoints (`/user/reset`, `/user/resetPassword`).

### 5. Controller Layer
* **`NoteController.java`**: Exposes reminder management REST endpoints:
  * `POST /notes/addUpdateReminderNotes` — Sets reminder and triggers async JMS event.
  * `GET /notes/getReminderNotesList` — Retrieves all active reminder notes for the authenticated user.
  * `POST /notes/removeReminderNotes` — Removes reminder from a note.
* **`UserController.java`**: Exposes password recovery REST endpoints:
  * `POST /user/reset` — Requests password reset and enqueues async email notification.
  * `POST /user/resetPassword` — Validates reset token and updates user password.

### 6. Test Layer
* **`JmsRemindersIntegrationTest.java`**: Automated test suite verifying sub-50ms reminder dispatch, background listener consumption, reminder listing/removal, and password reset messaging.
* **`RedisTokenCachingIntegrationTest.java`**: Automated test suite verifying token cache MISS vs HIT, remaining TTL alignment, and rejection of tampered tokens.

---

# Contacts App API

Spring Boot CRUD backend for managing contacts. It uses an in-memory H2 database, JPA, DTO validation, a service layer, and centralized API error handling.

Run it with:

```powershell
mvn spring-boot:run
```

The API base URL is `http://localhost:8080/api/contacts`.

| Method | Endpoint | Purpose |
| --- | --- | --- |
| POST | `/api/contacts` | Create a contact |
| GET | `/api/contacts` | List contacts ordered by name |
| GET | `/api/contacts/{id}` | Get one contact |
| PUT | `/api/contacts/{id}` | Replace a contact |
| DELETE | `/api/contacts/{id}` | Delete a contact |

Example request body:

```json
{
  "firstName": "Ada",
  "lastName": "Lovelace",
  "email": "ada@example.com",
  "phoneNumber": "+91 9876543210",
  "address": "London"
}
```

The H2 console is available at `http://localhost:8080/h2-console` while the app is running. Use JDBC URL `jdbc:h2:mem:contactsdb`.

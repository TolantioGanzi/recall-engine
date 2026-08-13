# Recall Engine

A full-stack spaced-repetition tracking engine that prioritizes LeetCode practice problems using adaptive scheduling algorithms based on problem difficulty, historical confidence, and failure rates.

![Java](https://img.shields.io/badge/Java-26-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1.0-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)
![Status](https://img.shields.io/badge/Status-In_Active_Development-yellow)

---

## 📌 Architecture & Design

Recall Engine is built on a layered **Controller–Service–Repository** architecture:

* **Presentation Layer (REST Controllers):** Handles HTTP requests, enforces DTO request/response boundaries, and maps responses to JSON.
* **Business Logic Layer (Services & Orchestrators):** Coordinates problem validation, calculates next recall dates based on user confidence scores, and executes scoring algorithms.
* **Data Access Layer (Spring Data JPA Repositories):** Interfaces with PostgreSQL to handle persistence across separate official problem reference tables (`problems`) and user-specific tracking logs (`user_problems`).
* **Database Migrations:** Managed through versioned Flyway scripts (`db/migration`) for consistent schema evolution across development environments.

---

## 🛠️ Tech Stack

* **Backend:** Java, Spring Boot, Spring Data JPA, Hibernate
* **Database:** PostgreSQL, Flyway
* **Testing & Tools:** JUnit 5, Postman, Maven, Lombok
* **Frontend (In Progress):** React, CSS

---

## 🔌 API Endpoints (`/api/logs`)

| Method | Endpoint | Description | Query / Request Body |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/logs/validate` | Validates if a LeetCode problem exists in the official catalog | `?id={problemId}` |
| `POST` | `/api/logs/record` | Logs a user attempt and calculates the next recall schedule | JSON `RecordRequest` payload |

### Request Body Example (`POST /api/logs/record`):

```json
{
  "problemNumber": 1,
  "confidence": 3,
  "pattern": "Two Pointers",
  "failLog": "Forgot edge case where array length is less than 2"
}

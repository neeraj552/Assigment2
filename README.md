# Global Class Offering Booking System

## Overview

This project is a backend service for a global live-learning platform where teachers conduct online classes and parents/students can book course offerings.

The system supports:

- Course management
- Offering creation
- Session scheduling
- Parent bookings
- Time conflict detection
- Timezone conversion
- Concurrent booking protection

---

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Lombok
- Swagger/OpenAPI
- JUnit 5

---

## Database Design

### Teacher

Stores teacher information and timezone.

| Field | Type |
|---------|---------|
| id | Long |
| name | String |
| timezone | String |

### Parent

Stores parent information and timezone.

| Field | Type |
|---------|---------|
| id | Long |
| name | String |
| timezone | String |

### Course

Represents a course.

Examples:
- Python Coding
- Art Drawing
- Public Speaking

| Field | Type |
|---------|---------|
| id | Long |
| name | String |

### Offering

A schedulable version of a course.

Examples:
- Saturday Batch
- Summer Camp
- Evening Batch

| Field | Type |
|---------|---------|
| id | Long |
| title | String |
| course_id | Long |
| teacher_id | Long |

### Session

Stores actual class timings.

| Field | Type |
|---------|---------|
| id | Long |
| offering_id | Long |
| teacher_id | Long |
| start_time | Instant |
| end_time | Instant |

### Booking

Represents a parent's enrollment in an offering.

| Field | Type |
|---------|---------|
| id | Long |
| parent_id | Long |
| offering_id | Long |
| created_at | Instant |

---

## Timezone Handling

Teachers create sessions using their local timezone.

Example:

Teacher Timezone: Asia/Kolkata

Teacher creates:

2026-06-07 18:00 - 19:00

The application converts the session time to UTC before storing it in the database.

Stored value:

2026-06-07T12:30:00Z

Parents can view schedules in their own timezone by converting UTC timestamps to their configured timezone.

---

## Booking Rules

### Rule 1 - Booking at Offering Level

Parents book an entire offering.

Booking one offering automatically includes all sessions belonging to that offering.

### Rule 2 - Duplicate Booking Prevention

A parent cannot book the same offering multiple times.

Validation is performed using:

- Parent ID
- Offering ID

### Rule 3 - Time Conflict Detection

A parent cannot book another offering if any session overlaps with already booked sessions.

Overlap formula:

```text
existingStart < newEnd
AND
existingEnd > newStart
```

Example:

Offering A:
18:00 - 19:00

Offering B:
18:30 - 19:30

Result:
Booking rejected with HTTP 409 Conflict.

### Rule 4 - Concurrent Booking Handling

Booking operations are executed inside a database transaction.

Pessimistic row-level locking is applied on the parent record to prevent race conditions when multiple booking requests occur simultaneously.

Annotations used:

```java
@Transactional
@Lock(LockModeType.PESSIMISTIC_WRITE)
```

---

## APIs

### Teacher APIs

#### Create Offering

```http
POST /teacher/offerings
```

#### Add Session

```http
POST /teacher/offerings/{offeringId}/sessions
```

#### View Offerings

```http
GET /teacher/offerings
```

### Parent APIs

#### View Available Offerings

```http
GET /parent/offerings
```

#### Book Offering

```http
POST /parent/book
```

#### View Bookings

```http
GET /parent/{parentId}/bookings
```

---

## Exception Handling

Custom exception handling is implemented using:

- TimeConflictException
- GlobalExceptionHandler

Example Response:

```json
{
  "message": "Time conflict detected"
}
```

HTTP Status:

```text
409 CONFLICT
```

---

## Testing

Basic API tests are implemented using:

- JUnit 5
- Spring Boot Test
- MockMvc

Covered scenarios:

- Get available offerings
- Get parent bookings

---

## Swagger Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## How to Run

### Create PostgreSQL Database

```sql
CREATE DATABASE class_booking;
```

### Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/class_booking
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Run Application

```bash
mvn spring-boot:run
```

---

## Key Engineering Decisions

- Session times are stored in UTC using `Instant`.
- Teacher timezone is converted to UTC during session creation.
- Parent bookings are validated against existing booked sessions.
- Duplicate bookings are prevented.
- Transactions ensure consistency.
- Pessimistic locking protects against concurrent booking conflicts.
- Custom exception handling provides meaningful API responses.
- Swagger is provided for easy API testing.

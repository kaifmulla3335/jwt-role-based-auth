# JWT Role Based Authentication

Full-stack authentication system with Spring Boot 3 + React + PostgreSQL.

---

## Tech Stack

| Layer | Technologies |
|-------|-------------|
| Backend | Java 17, Spring Boot 3, Spring Security, JWT, PostgreSQL, Maven, Lombok |
| Frontend | React 18 (Vite), React Router DOM, Axios, Tailwind CSS |

---

## Features

- User Registration (always assigned USER role)
- Login with JWT Token
- Role Based Authorization (USER / ADMIN)
- Protected Routes in React
- BCrypt Password Hashing
- Stateless Authentication
- CORS Configuration

---

## Project Structure

```
jwt-role-based-auth/
├── auth-backend/
│   └── src/main/java/com/example/authdemo/
│       ├── config/        → Security configuration
│       ├── controller/    → REST API endpoints
│       ├── dto/           → Request/Response objects
│       ├── entity/        → Database models
│       ├── repository/    → Database queries
│       ├── security/      → JWT filter and service
│       └── service/       → Business logic
├── auth-frontend/
│   └── src/
│       ├── pages/         → Login, Register, Dashboards
│       ├── services/      → Axios API calls
│       ├── routes/        → Protected route logic
│       └── components/    → Reusable UI components
├── .gitignore
└── README.md
```

---

## Getting Started

### 1. Database Setup

```sql
CREATE DATABASE auth_db;
```

Update `auth-backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/auth_db
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

jwt.secret=your-very-secret-key-must-be-at-least-32-characters-long
jwt.expiration=86400000
```

### 2. Run Backend

```bash
cd auth-backend
./mvnw spring-boot:run
```

Runs at: `http://localhost:8080`

### 3. Run Frontend

```bash
cd auth-frontend
npm install
npm run dev
```

Runs at: `http://localhost:5173`

---

## API Endpoints

| Method | Endpoint | Access |
|--------|----------|--------|
| POST | `/api/auth/register` | Public |
| POST | `/api/auth/login` | Public |
| GET | `/api/dashboard/user` | ROLE_USER only |
| GET | `/api/dashboard/admin` | ROLE_ADMIN only |

---

## How to Create Admin

Registration always assigns USER role (security best practice).
To promote a user to ADMIN, run in pgAdmin:

```sql
UPDATE users SET role = 'ADMIN' WHERE email = 'admin@gmail.com';
```

---

## Authentication Flow

```
Register  →  BCrypt hash password  →  Save as USER
Login     →  Verify credentials   →  Generate JWT token
Request   →  Send Bearer token    →  Filter validates
Response  →  Role checked         →  Allow or 403 Forbidden
```

---

## Author

**Kaif Mulla** — Full Stack Developer (Spring Boot + React)

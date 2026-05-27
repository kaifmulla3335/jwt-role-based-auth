# 🔐 JWT Role Based Authentication

<p align="center">
  <img alt="Spring Boot" src="https://img.shields.io/badge/Spring_Boot-3.2.5-6DB33F?logo=springboot&logoColor=white&style=flat-square" />
  <img alt="React" src="https://img.shields.io/badge/React-19-61DAFB?logo=react&logoColor=white&style=flat-square" />
  <img alt="PostgreSQL" src="https://img.shields.io/badge/PostgreSQL-16-4169E1?logo=postgresql&logoColor=white&style=flat-square" />
  <img alt="JWT" src="https://img.shields.io/badge/JWT-jjwt_0.12.3-000000?logo=jsonwebtokens&logoColor=white&style=flat-square" />
  <img alt="Tailwind CSS" src="https://img.shields.io/badge/Tailwind_CSS-4-38BDF8?logo=tailwindcss&logoColor=white&style=flat-square" />
  <img alt="License" src="https://img.shields.io/badge/license-MIT-green?style=flat-square" />
</p>

A full-stack **JWT authentication system** with role-based authorization — built with Spring Boot 3 + Spring Security (backend) and React 19 + React Router (frontend), backed by PostgreSQL.

---

## ✨ Features

- 📝 **User Registration** — always assigned `USER` role (security best practice)
- 🔑 **JWT Login** — returns signed token + role on successful login
- 🛡️ **Role-Based Access Control** — `USER` and `ADMIN` roles with separate protected routes
- 🔒 **BCrypt Password Hashing** — plain text passwords never stored
- 📡 **Stateless Authentication** — no server-side sessions; every request carries a Bearer token
- 🌐 **CORS Configured** — backend allows requests from `localhost:5173`
- ⚛️ **Protected Frontend Routes** — `PrivateRoute` component blocks unauthorized access
- 🔄 **Auto Redirect** — login redirects to USER or ADMIN dashboard based on role
- 🚪 **Logout** — clears token and role from `localStorage`

---

## 📦 Tech Stack

| Layer | Technologies |
|---|---|
| Backend | Java 17, Spring Boot 3.2.5, Spring Security, Spring Data JPA, jjwt 0.12.3, PostgreSQL, Lombok, Maven |
| Frontend | React 19, Vite 8, React Router DOM 7, Axios, Tailwind CSS 4 |

---

## 📁 Project Structure

```
jwt-role-based-auth/
├── auth-backend/
│   └── src/main/java/com/example/authdemo/
│       ├── config/
│       │   └── SecurityConfig.java          → Spring Security + CORS + filter chain
│       ├── controller/
│       │   ├── AuthController.java          → POST /register, POST /login
│       │   └── DashboardController.java     → GET /dashboard/user, /dashboard/admin
│       ├── dto/
│       │   ├── RegisterRequest.java         → name, email, password
│       │   ├── LoginRequest.java            → email, password
│       │   └── AuthResponse.java            → token, role
│       ├── entity/
│       │   ├── User.java                    → JPA entity (id, name, email, password, role)
│       │   └── Role.java                    → Enum: USER | ADMIN
│       ├── repository/
│       │   └── UserRepository.java          → findByEmail, existsByEmail
│       ├── security/
│       │   ├── JwtService.java              → generate / validate / extract JWT
│       │   ├── JwtAuthenticationFilter.java → intercepts every request, validates token
│       │   └── CustomUserDetailsService.java → loads user from DB for Spring Security
│       └── service/
│           └── AuthService.java             → register + login business logic
└── auth-frontend/
    └── src/
        ├── pages/
        │   ├── Login.jsx           → Login form with error handling
        │   ├── Register.jsx        → Registration form
        │   ├── UserDashboard.jsx   → Protected page for ROLE_USER
        │   └── AdminDashboard.jsx  → Protected page for ROLE_ADMIN
        ├── routes/
        │   └── PrivateRoute.jsx    → Guards routes by token + role
        ├── services/
        │   └── api.js              → Axios calls (register, login, dashboards)
        ├── components/
        │   └── Navbar.jsx          → Navigation with logout button
        └── App.jsx                 → Router setup with public + protected routes
```

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Node.js 18+ & npm 9+
- PostgreSQL 14+

### 1. Database Setup

```sql
CREATE DATABASE auth_db;
```

### 2. Configure Backend

Update `auth-backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/auth_db
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# JWT Secret — must be at least 32 characters (256 bits)
jwt.secret=your-very-secret-key-must-be-at-least-32-characters-long
jwt.expiration=86400000
```

### 3. Run Backend

```bash
cd auth-backend
./mvnw spring-boot:run
```

Runs at: `http://localhost:8080`

### 4. Run Frontend

```bash
cd auth-frontend
npm install
npm run dev
```

Runs at: `http://localhost:5173`

---

## 🔌 API Endpoints

| Method | Endpoint | Access | Description |
|---|---|---|---|
| `POST` | `/api/auth/register` | Public | Register a new user |
| `POST` | `/api/auth/login` | Public | Login and receive JWT token |
| `GET` | `/api/dashboard/user` | `ROLE_USER` only | User dashboard |
| `GET` | `/api/dashboard/admin` | `ROLE_ADMIN` only | Admin dashboard |

### Register — Request Body

```json
{
  "name": "Mohammadkaif Mulla",
  "email": "kaif@example.com",
  "password": "password123"
}
```

### Login — Request Body

```json
{
  "email": "kaif@example.com",
  "password": "password123"
}
```

### Login — Response

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "role": "USER"
}
```

### Dashboard Request (Authenticated)

```
GET /api/dashboard/user
Authorization: Bearer <your_jwt_token>
```

---

## 🗄️ Database Schema

Table: `users` (auto-created by Hibernate on startup)

| Column | Type | Constraint |
|---|---|---|
| `id` | `BIGINT` | Primary Key, Auto Increment |
| `name` | `VARCHAR` | Not Null |
| `email` | `VARCHAR` | Not Null, Unique |
| `password` | `VARCHAR` | Not Null (BCrypt hash) |
| `role` | `VARCHAR` | Not Null — `USER` or `ADMIN` |

---

## 👑 How to Create an Admin

Registration always assigns `USER` role. To promote a user to `ADMIN`, run this in pgAdmin or psql:

```sql
UPDATE users SET role = 'ADMIN' WHERE email = 'admin@example.com';
```

---

## 🔄 Authentication Flow

```
Register  →  BCrypt hash password  →  Save as ROLE_USER
Login     →  Verify credentials   →  Generate JWT (contains role)
Request   →  Send Bearer token    →  JwtFilter validates signature
Response  →  Role checked         →  Allow or 403 Forbidden
```

---

## 🌐 Frontend Routes

| Route | Protection | Description |
|---|---|---|
| `/login` | Public | Login page |
| `/register` | Public | Registration page |
| `/user-dashboard` | `ROLE_USER` only | User dashboard |
| `/admin-dashboard` | `ROLE_ADMIN` only | Admin dashboard |

Token and role are stored in `localStorage` after login. `PrivateRoute` reads them on every navigation to guard protected pages.

---

## 📄 License

MIT © [Mohammadkaif Mulla](https://github.com/kaifmulla3335)

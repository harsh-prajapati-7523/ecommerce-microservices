# AuthService (User Service) Documentation

## 1. Overview

**Purpose:**
AuthService is a dedicated microservice for **user registration, login, and authentication**.
It handles JWT generation and role-based access control for other microservices (e.g., ProductService, OrderService).

**Responsibilities:**

* User registration & login
* Password encryption
* JWT generation
* Role-based authentication

**Technology Stack:**

* Java 17+ / Spring Boot 3
* Spring Security
* H2 database (for development)
* JWT (io.jsonwebtoken library)

---

## 2. Directory Structure

```
auth-service/
├─ src/main/java/com/ecom/authservice
│  ├─ controller
│  │  └─ AuthController.java
│  ├─ model
│  │  ├─ User.java
│  │  └─ Role.java
│  ├─ repository
│  │  └─ UserRepository.java
│  ├─ security
│  │  ├─ JwtUtil.java
│  │  └─ SecurityConfig.java
│  └─ AuthServiceApplication.java
├─ src/main/resources
│  └─ application.properties
└─ pom.xml
```

---

## 3. Database Model

**User Entity**

| Field    | Type   | Description                  |
| -------- | ------ | ---------------------------- |
| id       | Long   | Auto-generated primary key   |
| username | String | Unique username              |
| password | String | Encrypted password           |
| role     | Role   | User role (`USER` / `ADMIN`) |

**Role Enum**

```java
public enum Role {
    USER,
    ADMIN
}
```

---

## 4. API Endpoints

| Method | Endpoint       | Description                | Request Body Example                                       |
| ------ | -------------- | -------------------------- | ---------------------------------------------------------- |
| POST   | /auth/register | Register new user          | `{ "username":"harsh", "password":"1234", "role":"USER" }` |
| POST   | /auth/login    | Login user & get JWT token | `{ "username":"harsh", "password":"1234" }`                |

**Response Example (Login)**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

## 5. Key Components & Annotations

| Component                  | Purpose                                              |
| -------------------------- | ---------------------------------------------------- |
| `@Entity`                  | Marks User class as JPA entity                       |
| `@Enumerated`              | Specifies that `role` is an enum                     |
| `@RestController`          | Handles HTTP requests for authentication             |
| `@RequestMapping("/auth")` | Base path for all auth endpoints                     |
| `@PostMapping`             | Maps HTTP POST requests                              |
| `@Autowired`               | Injects beans (repository, jwtUtil, passwordEncoder) |
| `PasswordEncoder`          | Encodes passwords before storing them in DB          |
| `JwtUtil`                  | Generates and validates JWT tokens                   |

---

## 6. JWT Overview

* JWT is **self-contained**: contains username, role, issued time, expiration.
* **Signature** ensures token integrity: ProductService can trust it.
* **Claims Example:**

```json
{
  "sub": "harsh",
  "role": "ADMIN",
  "iat": 1694070000,
  "exp": 1694073600
}
```

---

## 7. Security Plan

* **Passwords** stored encrypted using `BCryptPasswordEncoder`.
* **Roles:** `USER` / `ADMIN`.
* **JWT expiration:** 1 hour (configurable).
* **Secret key:** stored in environment variables or `application.properties`.
* **ProductService** validates JWT signature & role without querying AuthService DB.

---

## 8. Development Plan (Step-by-Step)

1. **Setup project**

   * Create Spring Boot project `auth-service`.
   * Add dependencies: Spring Security, Spring Data JPA, H2, JWT (`io.jsonwebtoken`).

2. **Create User entity & Role enum**

   * Add fields: `id`, `username`, `password`, `role`.

3. **Setup H2 Database**

   * `application.properties`:

     ```properties
     spring.datasource.url=jdbc:h2:mem:authdb
     spring.datasource.driverClassName=org.h2.Driver
     spring.datasource.username=sa
     spring.datasource.password=
     spring.jpa.hibernate.ddl-auto=update
     spring.h2.console.enabled=true
     spring.h2.console.path=/h2-console
     ```

4. **Password Encoding**

   * Configure `BCryptPasswordEncoder` bean.

5. **Implement JWT Utility**

   * Generate token with `username` and `role`.
   * Validate token signature & expiration.

6. **AuthController**

   * `/register` → save user with encrypted password.
   * `/login` → validate credentials & return JWT.

7. **Test AuthService**

   * Use Postman to register and login users.
   * Confirm JWT is returned and can be decoded.

8. **Integrate with ProductService**

   * Share secret key (or public key for asymmetric).
   * Configure JWT filter to secure endpoints.

---

## 9. Notes

* **Stateless Authentication:** ProductService does not store user info.
* **Security Best Practices:**

  * Use HTTPS in production
  * Strong secret keys
  * Short-lived tokens + optional refresh tokens
* **Extensibility:** Add refresh tokens, password reset, email verification in future.

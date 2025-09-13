# 🛍️ E-commerce Microservices (Spring Boot + JWT)

This project demonstrates a simple **microservices architecture** using Spring Boot.  
It includes two services: **Auth Service** and **Product Service**, communicating securely with **JWT tokens**.

---

## ✨ Features

### 🔐 Auth Service
- User registration & login
- Secure password storage with `PasswordEncoder`
- Generates JWT tokens for authentication
- Stateless authentication using Spring Security

### 📦 Product Service
- CRUD operations for products
- DTO mapping using **MapStruct**
- Input validation with `javax.validation`
- Caching support with `@Cacheable`
- Secured endpoints (only accessible with valid JWT)
- In-memory database with **H2** for easy testing

---

## 🛠️ Tech Stack
- **Java 17**
- **Spring Boot 3**
- **Spring Security + JWT**
- **Spring Data JPA + H2 Database**
- **MapStruct** for DTO mapping
- **Lombok** for boilerplate reduction
- **Maven**

---

## 🚀 Getting Started

### 1. Clone the repository
```bash
git clone https://github.com/<your-username>/<your-repo-name>.git
cd <your-repo-name>

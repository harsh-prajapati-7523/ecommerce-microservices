
---

## 🧾 Data Model
**Entity: `Product`**

| Field         | Type     | Description                   |
|---------------|----------|-------------------------------|
| id            | Long     | Unique identifier (PK)        |
| name          | String   | Product name                  |
| description   | String   | Product details               |
| price         | Double   | Price of the product          |
| stockQuantity | Integer  | Available quantity in stock   |
| createdAt     | DateTime | Record creation timestamp     |
| updatedAt     | DateTime | Last update timestamp         |

---

## 🔗 API Endpoints

| Method | Endpoint          | Description                |
|--------|-------------------|----------------------------|
| GET    | `/products`       | Get all products           |
| GET    | `/products/{id}`  | Get product by ID          |
| POST   | `/products`       | Create a new product       |
| PUT    | `/products/{id}`  | Update an existing product |
| DELETE | `/products/{id}`  | Delete a product           |

---

## ⚙️ Configurations
**application.properties (Dev setup)**

```properties
spring.application.name=product-service
server.port=8081

spring.datasource.url=jdbc:h2:mem:productdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

# 🧩 Product Service Components Documentation

This document explains the **main components** of the `product-service` in detail.  
We will build each layer step by step.

---

## 1️⃣ Entity Layer (`model`)
- **Purpose**: Represents the database tables as Java objects.  
- **Class**: `Product`  
- **Fields**:  

| Field         | Type     | Description                   |
|---------------|----------|-------------------------------|
| id            | Long     | Unique identifier (PK)        |
| name          | String   | Product name                  |
| description   | String   | Product details               |
| price         | Double   | Price of the product          |
| stockQuantity | Integer  | Available quantity in stock   |
| createdAt     | DateTime | Record creation timestamp     |
| updatedAt     | DateTime | Last update timestamp         |

---

## 2️⃣ Repository Layer (`repository`)
- **Purpose**: Communicates with the database.  
- **Class**: `ProductRepository`  
- **Base**: Extends `JpaRepository<Product, Long>`  
- **Responsibilities**:  
  - Fetch all products  
  - Fetch product by ID  
  - Save a new product  
  - Update product  
  - Delete product  

---

## 3️⃣ Service Layer (`service`)
- **Purpose**: Contains business logic.  
- **Class**: `ProductService`  
- **Methods**:  
  - `getAllProducts()` → returns all products  
  - `getProductById(Long id)` → returns product by ID  
  - `createProduct(Product product)` → adds a new product  
  - `updateProduct(Long id, Product product)` → updates product details  
  - `deleteProduct(Long id)` → removes product  

---

## 4️⃣ Controller Layer (`controller`)
- **Purpose**: Exposes REST APIs to outside world.  
- **Class**: `ProductController`  
- **Endpoints**:  

| Method | Endpoint          | Description                |
|--------|-------------------|----------------------------|
| GET    | `/products`       | Get all products           |
| GET    | `/products/{id}`  | Get product by ID          |
| POST   | `/products`       | Create a new product       |
| PUT    | `/products/{id}`  | Update an existing product |
| DELETE | `/products/{id}`  | Delete a product           |

---

## 5️⃣ Configuration Layer (`resources`)
- **File**: `application.properties`  
- **Responsibilities**:  
  - Define DB connection (H2 for demo).  
  - Set service name and port.  
  - Enable H2 console.  

---

## 6️⃣ Exception Handling (Future Enhancement)
- **Purpose**: Handle errors gracefully.  
- **Classes**:  
  - `ProductNotFoundException`  
  - `GlobalExceptionHandler` (`@ControllerAdvice`)  

---

## 7️⃣ DTOs / Mappers (Future Enhancement)
- **Purpose**: Create request/response objects separate from entity.  
- **Benefit**: Clean API responses, no DB exposure.  

---

## 8️⃣ Testing (Future Enhancement)
- **Frameworks**: JUnit + Mockito + Spring Boot Test  
- **Types**:  
  - Unit tests (service, repository)  
  - Integration tests (controller + DB)  

---

✅ **Next Action**: Start with **Entity Layer → `Product.java`** implementation.

# 📝 Product Service Study Plan

## Index
1. [Introduction](#1-introduction)
2. [Project Structure](#2-project-structure)
3. [Entity Layer](#3-entity-layer)
4. [Repository Layer](#4-repository-layer)
5. [Service Layer](#5-service-layer)
6. [Controller Layer](#6-controller-layer)
7. [Exception Handling](#7-exception-handling)
8. [Validation](#8-validation)
9. [Database Configuration](#9-database-configuration)
10. [Swagger / API Documentation](#10-swagger--api-documentation)
11. [Testing](#11-testing)
12. [Next Steps / Advanced Concepts](#12-next-steps--advanced-concepts)

---

## 1. Introduction
### Purpose of Product Service
The Product Service is a microservice in the e-commerce application responsible for managing all product-related operations. It allows clients to:
- Create new products
- Retrieve product details
- Update existing products
- Delete products

This service ensures that product data is consistent, validated, and easily accessible to other microservices like Order Service or Inventory Service.

### Overview of Microservice Architecture
- Each functionality of the e-commerce application (Products, Orders, Users, Payments) is implemented as a **separate microservice**.
- Product Service focuses solely on **product management**, making the system modular, scalable, and easier to maintain.
- Microservices communicate via REST APIs or messaging systems when needed.

### Why Spring Boot is Used
- **Rapid development**: Spring Boot provides auto-configuration and starter dependencies, making setup fast.
- **Embedded server**: Comes with Tomcat by default, no need to install or configure external server.
- **Dependency injection**: Makes it easy to manage components like controllers, services, and repositories.
- **Spring Data JPA**: Simplifies database interactions.
- **Integration with Swagger/OpenAPI**: Provides API documentation automatically.
- **Supports Profiles**: Makes it easy to configure separate environments (dev, prod).

---

## 2. Project Structure

The Product Service follows a standard **Spring Boot + Microservices structure**. Each folder/package has a specific responsibility.

### Folder Hierarchy
````text
product-service/
└── src/
    └── main/
        ├── java/
        │   └── com/ecom/harsh/productservice/
        │       ├── controller/       # REST controllers
        │       ├── service/          # Business logic
        │       ├── repository/       # JPA repositories
        │       ├── model/            # JPA entities
        │       └── exception/        # Global exception handlers
        │       ProductServiceApplication.java  # Main Spring Boot app
        └── resources/
            ├── application.properties
            └── application-dev.properties
├── pom.xml
└── README.md
````

---

### Package Explanation

1. **`controller/`**  
   - Contains REST controllers (`ProductController.java`).  
   - Handles HTTP requests (`GET`, `POST`, `PUT`, `DELETE`).  
   - Uses annotations like `@RestController`, `@RequestMapping`, `@GetMapping`, `@PostMapping`, etc.

2. **`service/`**  
   - Contains business logic (`ProductService.java`).  
   - Communicates with the repository layer.  
   - Annotated with `@Service`.  

3. **`repository/`**  
   - Contains interfaces extending `JpaRepository` (`ProductRepository.java`).  
   - Handles database operations without writing SQL manually.  
   - Annotated with `@Repository`.

4. **`model/`** (or `entity/`)  
   - Contains JPA entities (`Product.java`).  
   - Maps Java objects to database tables.  
   - Annotated with `@Entity`, `@Id`, `@GeneratedValue`, etc.

5. **`exception/`**  
   - Contains global exception handling classes (`GlobalExceptionHandler.java`).  
   - Uses `@ControllerAdvice` and `@ExceptionHandler`.

6. **Root `ProductServiceApplication.java`**  
   - Main Spring Boot application entry point.  
   - Annotated with `@SpringBootApplication`.

7. **`resources/`**  
   - `application.properties` → default configuration (port, DB, logging).  
   - `application-dev.properties` → development profile configuration.  
   - Can add `application-prod.properties` for production.

8. **`pom.xml`**  
   - Maven project file containing dependencies, plugins, and build configuration.

---

### Notes
- The structure is **layered**: Controller → Service → Repository → Database.  
- Makes the code **modular, readable, and easy to maintain**.  
- Easy to expand in the future with additional packages (e.g., `config/` for security or messaging).


## 3. Entity Layer

The **Entity Layer** represents the data structure of your microservice. In Product Service, this is the `Product` entity.

### Product Entity (`Product.java`)

```java
package com.ecom.harsh.productservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Primary key

    private String name;        // Product name
    private String description; // Product description
    private double price;       // Product price
    private int stockQuantity;  // Quantity in stock

    private LocalDateTime createdAt;  // Timestamp when created
    private LocalDateTime updatedAt;  // Timestamp when updated

    // Automatically set timestamps
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters (or use Lombok @Getter/@Setter)
}
```
### Key Annotations Explained

- `@Entity` → Marks the class as a JPA entity, mapped to a database table.
- `@Table(name = "products")` → Defines the table name in the database.
- `@Id` → Marks the primary key field.
- `@GeneratedValue(strategy = GenerationType.IDENTITY)` → Auto-generates the primary key using the database’s identity column.
- `@PrePersist` → Executes before inserting a new record, used here to set `createdAt` and `updatedAt`.
- `@PreUpdate` → Executes before updating a record, used to update `updatedAt`.

---

### Fields & Purpose

| Field          | Type           | Description                                |
|----------------|----------------|--------------------------------------------|
| `id`           | `Long`         | Unique identifier, auto-generated         |
| `name`         | `String`       | Name of the product                        |
| `description`  | `String`       | Product description                        |
| `price`        | `double`       | Price of the product                        |
| `stockQuantity`| `int`          | Number of items in stock                   |
| `createdAt`    | `LocalDateTime`| Automatically set when product is created |
| `updatedAt`    | `LocalDateTime`| Automatically updated on modification     |

---

### Notes

- Entities represent **database tables**; each instance is a row.  
- Auto timestamps (`createdAt` and `updatedAt`) make it easy to track **when records are created or updated**.  
- In the future, you can add **relationships** (e.g., `@ManyToOne` with Category or `@OneToMany` with Orders).  

## 4. Repository Layer

The **Repository Layer** is responsible for **database operations**. In Spring Boot, we use **Spring Data JPA** to simplify CRUD operations without writing SQL.

### ProductRepository (`ProductRepository.java`)

```java
package com.ecom.harsh.productservice.repository;

import com.ecom.harsh.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // No additional code needed for basic CRUD
}
```
### Key Annotations Explained

- `@Repository` → Marks the interface as a Spring **repository bean** and enables exception translation.  
- `JpaRepository<Product, Long>` → Provides **built-in CRUD methods** like:
  - `save()` → Insert or update a record
  - `findById()` → Find a record by ID
  - `findAll()` → Retrieve all records
  - `deleteById()` → Delete a record by ID
  - `existsById()` → Check if a record exists

---

### Notes

- Using `JpaRepository` eliminates the need to write boilerplate SQL for basic CRUD.  
- You can **add custom query methods** in the future using Spring Data method naming conventions, for example:

```java
List<Product> findByName(String name);
List<Product> findByPriceGreaterThan(double price);
```

## 5. Service Layer

The **Service Layer** contains the **business logic** of the Product Service. It interacts with the repository layer to perform CRUD operations and can include additional validation or processing before returning data to the controller.

### ProductService (`ProductService.java`)

```java
package com.ecom.harsh.productservice.service;

import com.ecom.harsh.productservice.model.Product;
import com.ecom.harsh.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get product by ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Create a new product
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Update an existing product
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStockQuantity(productDetails.getStockQuantity());

        return productRepository.save(product);
    }

    // Delete a product
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
```

### Key Annotations Explained

- `@Service` → Marks the class as a **service bean**, making it a candidate for Spring’s dependency injection.  
- Constructor injection (`ProductRepository productRepository`) → Ensures the repository is available in the service.  

---

### Notes

- The service layer **separates business logic from controller logic**, keeping the code modular.  
- It handles operations like retrieving, creating, updating, and deleting products.  
- Exceptions for invalid operations (like updating a non-existent product) can be handled here or in the **GlobalExceptionHandler**.  
- Future improvements:
  - Add caching for frequently accessed products.  
  - Add business rules (e.g., stock quantity checks before updates).  
  - Integrate with other microservices (like Inventory or Order Service).  

## 6. Controller Layer

The **Controller Layer** handles **HTTP requests** from clients and maps them to service layer methods. It exposes REST APIs for the Product Service.

### ProductController (`ProductController.java`)

```java
package com.ecom.harsh.productservice.controller;

import com.ecom.harsh.productservice.model.Product;
import com.ecom.harsh.productservice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Get all products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new product
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    // Update an existing product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    // Delete a product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
```

### Key Annotations Explained

- `@RestController` → Marks the class as a REST controller and combines `@Controller` + `@ResponseBody`.  
- `@RequestMapping("/products")` → Base path for all endpoints in this controller.  
- `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping` → Map HTTP methods to specific methods.  
- `@PathVariable` → Binds URI path variables to method parameters.  
- `@RequestBody` → Binds HTTP request body to a method parameter.  
- `@Valid` → Enables validation of request body fields based on entity annotations.  

---

### Notes

- The controller **delegates all business logic to the service layer**, keeping the controller lightweight.  
- Handles HTTP response codes (`200 OK`, `204 No Content`, `404 Not Found`) properly.  
- Can be extended to support pagination, filtering, or sorting in the future.  
- Integrates with **Swagger/OpenAPI** for auto-generated API documentation.  

## 7. Exception Handling

The **Exception Handling Layer** manages application errors and ensures consistent responses for the client. In Spring Boot, we use `@ControllerAdvice` and `@ExceptionHandler` for global exception handling.

### GlobalExceptionHandler (`GlobalExceptionHandler.java`)

```java
package com.ecom.harsh.productservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("status", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // You can add more handlers for specific exceptions like:
    // @ExceptionHandler(EntityNotFoundException.class)
    // @ExceptionHandler(MethodArgumentNotValidException.class)
}
```
### Key Annotations Explained

- `@ControllerAdvice` → Marks the class as a global exception handler for all controllers.  
- `@ExceptionHandler(ExceptionType.class)` → Handles a specific exception type and returns a custom response.  

---

### Notes

- Centralizes error handling, avoiding repetitive try-catch blocks in controllers.  
- Provides **consistent error responses** for clients with HTTP status, message, and timestamp.  
- Can be extended to handle **validation errors, database errors, or custom exceptions**.  
- Makes the API more **user-friendly and easier to debug**.  

## 8. Validation

Validation ensures that incoming data meets specific rules before it is processed by the service. Spring Boot uses **Jakarta Bean Validation** (`jakarta.validation`) annotations combined with `@Valid` in controllers.

### Product Entity Validation Example

```java
package com.ecom.harsh.productservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is mandatory")
    private String name;

    private String description;

    @NotNull(message = "Price is mandatory")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Double price;

    @Min(value = 0, message = "Stock quantity must be greater than or equal to 0")
    private int stockQuantity;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
}
```

**Using @Valid in Controller**
@PostMapping
public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
    Product createdProduct = productService.createProduct(product);
    return ResponseEntity.ok(createdProduct);
}

### Key Annotations Explained

- `@NotBlank` → Ensures a string is not null or empty.  
- `@NotNull` → Ensures a field is not null.  
- `@Min(value)` → Ensures a numeric field is greater than or equal to the specified value.  
- `@Valid` → Tells Spring to validate the request body based on the entity’s annotations.  

---

### Notes

- Validation prevents **invalid data** from reaching the service or database.  
- Error responses for validation failures are usually handled via **GlobalExceptionHandler**.  
- You can combine multiple constraints for advanced validation (e.g., `@Size`, `@Max`, `@Pattern`).  
- Helps maintain **data integrity** and reduces runtime errors.  


## 9. Database Configuration

Product Service uses an **in-memory H2 database** for development and testing. Configuration is done in `application.properties`.

### `application.properties`

```properties
# Application name and port
spring.application.name=product-service
server.port=8081

# H2 Database configuration
spring.datasource.url=jdbc:h2:mem:productdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```
### Key Properties Explained

- `spring.datasource.url` → JDBC URL for H2 database in memory mode.  
- `spring.datasource.driverClassName` → JDBC driver class for H2.  
- `spring.datasource.username` & `spring.datasource.password` → Database credentials.  
- `spring.jpa.hibernate.ddl-auto=update` → Automatically updates the database schema based on entity classes.  
- `spring.jpa.database-platform` → Hibernate dialect for H2.  
- `spring.h2.console.enabled` → Enables the H2 web console for querying the database.  
- `spring.h2.console.path` → URL path to access H2 console in the browser.  

---

### Notes

- H2 is **in-memory**, so data will be lost when the application stops.  
- Ideal for development and testing; production usually uses MySQL, PostgreSQL, or another RDBMS.  
- You can access the H2 console at `http://localhost:8081/h2-console` to view tables and data.  
- Hibernate auto schema update helps **quickly sync entities with the database** during development.  

## 10. Swagger / API Documentation

Swagger (OpenAPI) provides **interactive API documentation** for the Product Service. It allows developers to test endpoints directly from a browser.

### Setup

1. **Add dependency in `pom.xml`:**

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.1.0</version>
</dependency>
```
### Swagger / API Documentation

No extra configuration is needed for default setup; Spring Boot automatically exposes Swagger UI.

#### Accessing Swagger UI

- URL: `http://localhost:8081/swagger-ui.html` or `http://localhost:8081/swagger-ui/index.html`  
- API JSON: `http://localhost:8081/v3/api-docs`  

---

#### Example API Endpoints

| Method | Endpoint        | Description               |
|--------|----------------|---------------------------|
| GET    | /products      | Get all products          |
| GET    | /products/{id} | Get product by ID         |
| POST   | /products      | Create a new product      |
| PUT    | /products/{id} | Update an existing product|
| DELETE | /products/{id} | Delete a product          |

---

### Key Annotations Explained

- `@Operation(summary = "...")` → Provides a description for a REST endpoint in Swagger.  
- `@Parameter(...)` → Adds metadata to parameters in Swagger documentation.  
- `@ApiResponses` → Describes possible HTTP responses for the endpoint.  

---

### Notes

- Swagger UI **enables interactive testing** without Postman or curl.  
- Makes it easier for front-end developers or other microservices to understand and consume APIs.  
- Can be enhanced with **security, custom models, and request/response examples**.  


## 11. Testing
- Testing POST, GET, PUT, DELETE
- Using Swagger UI
- Using Postman
- Using cURL

## 12. Next Steps / Advanced Concepts
- Move to persistent DB (MySQL/Postgres)
- Dockerize Product Service
- Unit Testing (JUnit + Mockito)
- Integration Testing
- Security (Spring Security, JWT)

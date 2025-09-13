# 📦 Product Service

## 📌 Overview
The **Product Service** is a standalone microservice responsible for managing the **product catalog** in the e-commerce platform. It exposes REST APIs for **CRUD operations** on products and stores product data in its own database.  

## 🎯 Responsibilities
- Create, read, update, and delete products.  
- Store product details such as:
  - **Name**
  - **Description**
  - **Price**
  - **Quantity (stock)**  
- Provide APIs to other services like **Order Service** and **API Gateway**.  

## 🛠️ Tech Stack
- **Java 24**  
- **Spring Boot**  
- **Spring Data JPA (Hibernate)**  
- **H2 Database (in-memory, for demo)**  
- **Lombok** (to reduce boilerplate)  
- **Maven**  

## 📂 Project Structure
product-service/
├── src/main/java/com/harsh/ecom/productservice/
│ ├── controller/ # REST APIs
│ ├── model/ # Product entity
│ ├── repository/ # Database access
│ ├── service/ # Business logic
│ └── ProductServiceApplication.java
├── src/main/resources/
│ ├── application.properties
│ └── data.sql # (optional) sample products
└── pom.xml

## ⚙️ Configuration
**File:** `application.properties`

```properties
spring.application.name=product-service
server.port=8081

# H2 Database
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


## 🚀 Running Locally
```bash
cd product-service
./mvnw spring-boot:run
```
Once running:
App available at → http://localhost:8081
H2 Console → http://localhost:8081/h2-console

## 🔗 API Endpoints

| Method   | Endpoint           | Description                |
|----------|--------------------|----------------------------|
| GET      | `/products`        | Get all products           |
| GET      | `/products/{id}`   | Get product by ID          |
| POST     | `/products`        | Create a new product       |
| PUT      | `/products/{id}`   | Update an existing product |
| DELETE   | `/products/{id}`   | Delete a product           |


##🧪 Example Product JSON
{
  "name": "iPhone 15",
  "description": "Latest Apple iPhone",
  "price": 1200.00,
  "quantity": 10
}
# DistroCart Microservices

A Spring Boot microservices-based e-commerce backend implementing distributed communication using Eureka Service Discovery, API Gateway, OpenFeign, Apache Kafka, Redis caching, JWT Authentication, and MySQL.

---

## Architecture

```text
                           Client
                              |
                              v
                     +----------------+
                     |  API Gateway   |
                     +----------------+
                              |
                              v
                     +----------------+
                     | Eureka Server  |
                     +----------------+
                              |
     -------------------------------------------------
     |                |                |             |
     v                v                v             v
+-----------+  +-------------+  +-----------+  +------------+
| Auth      |  | Product     |  | Inventory |  | Payment    |
| Service   |  | Service     |  | Service   |  | Service    |
+-----------+  +-------------+  +-----------+  +------------+
                                      ^
                                      |
                                 OpenFeign
                                      |
                                      v
                                +-----------+
                                | Order     |
                                | Service   |
                                +-----------+

Kafka Event Flow:

Order Service
      |
      v
OrderCreatedEvent
      |
      v
Payment Service

Inventory Service
      |
      v
InventoryReservedEvent

Payment Service
      |
      v
PaymentCompletedEvent
```

---

## Technology Stack

* Java 21
* Spring Boot 3
* Spring Cloud
* Spring Data JPA
* Spring Security
* JWT Authentication
* Spring Cloud Gateway
* Eureka Discovery Server
* OpenFeign
* Apache Kafka
* Redis Cache
* MySQL
* Docker
* Maven
* Swagger / OpenAPI

---

## Microservices

### Auth Service

Responsible for authentication and authorization.

Features:

* User Registration
* User Login
* JWT Token Generation
* JWT Validation
* Password Encryption using BCrypt

---

### API Gateway

Acts as the single entry point for all client requests.

Features:

* Request Routing
* Service Discovery Integration
* Authentication Filter Support
* Load Balanced Routing

---

### Product Service

Responsible for product management.

Features:

* Create Product
* Update Product
* Delete Product
* Product Retrieval
* Pagination Support
* Redis Caching

---

### Inventory Service

Responsible for inventory management.

Features:

* Check Stock Availability
* Reserve Stock
* Release Stock
* Deduct Stock
* Inventory Updates

---

### Order Service

Responsible for order creation and tracking.

Features:

* Create Order
* Order Status Tracking
* Inventory Validation using OpenFeign
* Kafka Event Publishing

---

### Payment Service

Responsible for payment processing.

Features:

* Payment Processing
* Payment Status Updates
* Kafka Event Consumption
* Event-Based Order Updates

---

## Database Architecture

Each microservice owns its own database.

| Service           | Database    |
| ----------------- | ----------- |
| Auth Service      | authdb      |
| Product Service   | productdb   |
| Inventory Service | inventorydb |
| Order Service     | orderdb     |
| Payment Service   | paymentdb   |

This follows the Database-per-Service pattern, ensuring loose coupling between services.

---

## Event Flow

### Order Creation

```text
Client
   |
   v
Order Service
   |
   +---- OpenFeign ----> Inventory Service
   |
   v
OrderCreatedEvent (Kafka)
```

### Inventory Reservation

```text
Inventory Service
        |
        v
InventoryReservedEvent
```

### Payment Completion

```text
Payment Service
        |
        v
PaymentCompletedEvent
```

---

## Service Ports

| Service           | Port |
| ----------------- | ---- |
| API Gateway       | 8080 |
| Inventory Service | 8081 |
| Payment Service   | 8082 |
| Order Service     | 8083 |
| Auth Service      | 8084 |
| Product Service   | 8085 |
| Eureka Server     | 8761 |

---

## Running Locally

### Prerequisites

* Java 21
* Maven
* MySQL
* Docker Desktop

### Start Infrastructure

```bash
docker-compose up -d
```

This starts:

* Kafka
* Zookeeper
* Redis

### Start Services

Run the services in the following order:

1. Eureka Server
2. API Gateway
3. Auth Service
4. Product Service
5. Inventory Service
6. Payment Service
7. Order Service

---

## API Documentation

Swagger UI is available for each service.

Example:

```text
http://localhost:8080/swagger-ui.html
```

---

## Future Improvements

* Resilience4j Circuit Breakers
* Distributed Tracing (Zipkin)
* Kubernetes Deployment
* Saga Pattern Implementation
* CI/CD Pipeline
* ELK Stack Logging
* Prometheus & Grafana Monitoring

---

## Author

**Rehan Khan**

Backend Software Engineer focused on Java, Spring Boot, Microservices, Distributed Systems, and Event-Driven Architecture.

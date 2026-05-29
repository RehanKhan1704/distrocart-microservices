# DistroCart Microservices Architecture

DistroCart is a scalable e-commerce backend system built using Spring Boot microservices architecture.

## Architecture Overview

The system is composed of independent microservices communicating using REST APIs and service discovery.

### Services

| Service                   | Port | Responsibility                       |
| ------------------------- | ---- | ------------------------------------ |
| API Gateway               | 8080 | Central entry point and routing      |
| Discovery Server (Eureka) | 8761 | Service registry and discovery       |
| Auth Service              | 8084 | JWT authentication and authorization |
| Product Service           | 8082 | Product management                   |
| Inventory Service         | 8083 | Inventory management                 |
| Order Service             | 8081 | Order processing                     |

---

# Tech Stack

* Java 21
* Spring Boot 3
* Spring Cloud
* Spring Security
* JWT Authentication
* Spring Data JPA
* MySQL
* Eureka Discovery Server
* OpenFeign
* API Gateway
* Redis
* Docker
* Maven

---

# Microservices Architecture

```text
Client
   |
   v
API Gateway
   |
------------------------------------------------
|              |              |                |
v              v              v                v
Auth       Product       Inventory         Order
Service    Service       Service           Service
   |
   v
Eureka Discovery Server
```

---

# Features

* Microservices architecture
* JWT authentication
* API Gateway routing
* Eureka service discovery
* Inter-service communication using OpenFeign
* Independent databases per service
* Dockerized services
* Centralized entry point
* Scalable architecture

---

# Project Structure

```text
DistroCart/
│
├── api-gateway/
├── auth-service/
├── product-service/
├── inventory-service/
├── order-service/
├── discovery-server/
├── docker-compose.yml
└── README.md
```

---

# Running the Project

## Prerequisites

Install:

* Java 21
* Maven
* Docker Desktop
* MySQL

---

# Step 1 — Clone Repository

```bash
git clone https://github.com/yourusername/distrocart-microservices.git
```

```bash
cd distrocart-microservices
```

---

# Step 2 — Build All Services

Inside each service:

```bash
mvn clean package
```

---

# Step 3 — Run Discovery Server

```bash
cd discovery-server
mvn spring-boot:run
```

Open:

```text
http://localhost:8761
```

---

# Step 4 — Run Other Services

Run:

* auth-service
* product-service
* inventory-service
* order-service
* api-gateway

Each service registers itself with Eureka.

---

# API Gateway

Access all APIs through gateway:

Example:

```text
http://localhost:8080/product-service/products
```

---

# JWT Authentication Flow

1. User logs in
2. Auth service generates JWT token
3. Gateway validates token
4. Request forwarded to microservices

---

# Docker Support

## Build Containers

```bash
docker compose up --build
```

## Stop Containers

```bash
docker compose down
```

---

# Dockerfile Example

```dockerfile
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
```

---

# Future Improvements

* Kafka event-driven communication
* Centralized Config Server
* Distributed tracing
* Resilience4j
* Prometheus + Grafana monitoring
* Kubernetes deployment
* CI/CD pipelines
* ELK logging stack

---

# Author

Rehan Khan

---

# License

This project is for educational and learning purposes.

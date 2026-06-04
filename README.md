Here is your comprehensive, enterprise-ready **README.md**. It covers every single architectural layer, security detail, data flow, and environment setup instructions so that any recruiter or senior engineer looking at your GitHub repository instantly recognizes high-level system design execution.

---

# DistroCart Microservices

DistroCart is a high-performance, resilient, and horizontally scalable e-commerce backend architecture built using **Java 21** and **Spring Boot 3.x**. The project transitions away from monolithic dependencies by breaking domains into decoupled microservices that synchronize state asynchronously using an event-driven choreography architecture, while ensuring fault tolerance through distributed circuit breakers.

---

## 🛠️ System Architecture & Network Topology

The architecture uses **Spring Cloud Gateway** as a secure, single entry point to abstract the underlying topology. Microservices dynamically register their network locations with a **Eureka Discovery Server** to allow client-side load balancing without hardcoded routing rules.

```text
                        +-------------------+
                        |   Client/Postman  |
                        +---------+---------+
                                  | (HTTPS Request via Port 8080)
                                  v
                        +-------------------+
                        |  Spring Cloud GW  | <--- [Global JWT Auth Filter]
                        +---------+---------+
                                  |
         +------------------------+------------------------+
         | (Dynamic Load-Balanced Routing via Eureka)      |
         v                                                 v
+------------------+                              +------------------+
|  Order Service   | ---(Synchronous Inter-svc)--->| Inventory Service|
|    (Port 8083)   | <---(OpenFeign + Resilence4j)-|    (Port 8081)   |
+--------+---------+                              +--------+---------+
         |                                                 |
         |                                                 |
         +-----------------------+-------------------------+
                                 |
                                 v [Apache Kafka Event Bus]
         +-------------------------------------------------+
         |  - OrderCreatedEvent                            |
         |  - InventoryReservedEvent                       |
         |  - PaymentCompletedEvent                        |
         +-----------------------+-------------------------+
                                 |
                                 v
                        +-------------------+
                        |  Payment Service  |
                        |    (Port 8082)    |
                        +-------------------+

```

---

## 🚀 Distributed Transaction Model: Choreographed Saga

To maintain business logic invariants without introducing blocking distributed locks (like 2-Phase Commit), this system utilizes an asynchronous **Choreographed Saga Pattern** managed by an Apache Kafka messaging backbone.

### The Happy Path Workflow

1. **Order Initiation:** The `Order Service` receives an `OrderRequest`, validates parameters, maps data using MapStruct, sets state to `PENDING`, saves it to MySQL, and publishes an `OrderCreatedEvent` to Kafka.
2. **Stock Allocation:** The `Inventory Service` consumes the event, checks atomic product levels, isolates and flags the quantity, and publishes an `InventoryReservedEvent`.
3. **Financial Execution:** The `Payment Service` consumes the allocation event, processes the debit record, and dispatches a `PaymentCompletedEvent`.
4. **Finalization:** The `Order Service` consumes the success payload and transitions the state from `PENDING` to `COMPLETED`.

### Distributed Rollback (Compensating Transactions)

If any downstream microservice encounters an error (e.g., card declined during payment or a service timeout), a reverse cascade triggers:

* A failure event is broadcasted.
* `Inventory Service` intercepts the failure and executes a compensating transaction to release the specific reserved stock back into the pool.
* `Order Service` updates the order record status to `FAILED` or `REJECTED`, maintaining eventual consistency across independent databases.

---

## 🧰 Technology Stack & Structural Design

* **Core Framework:** Java 21 / Spring Boot 3.x / Spring Cloud (2023.x)
* **API Routing & Load Balancing:** Spring Cloud Gateway, OpenFeign (with explicit variable compilation mappings)
* **Fault Tolerance & Resilience:** Resilience4j Circuit Breakers (configured with smart exception mapping to isolate business validation faults from network timeouts)
* **Persistence & Caching:** Spring Data JPA, Hibernate, MySQL 8 (Isolated schema-per-service paradigm), Redis (Read/Write-Through caching for low latency catalog lookups)
* **Event Streaming & Topology:** Apache Kafka (Configured with custom Jackson serializers and explicit `trusted.packages` payload deserialization blocks)
* **Security Layer:** Spring Security 6, Stateless JWT validation handling asymmetric signatures
* **Testing & Mappings:** MapStruct (Compile-time DTO-Entity conversion), WireMock (Isolated inter-service integration testing)

---

## 🏛️ Microservices Deep-Dive

### 🔐 Auth Service (Authentication & Identity)

* Emits secure, cryptographically signed JSON Web Tokens (JWT) upon successful validation.
* Manages secure identity enrollment with one-way password hashing using `BCryptPasswordEncoder`.
* Interfaces natively with Spring Cloud Gateway to provide decentralized stateless authentication filters.

### 🛍️ Product Service (Catalog Management)

* Implements read-optimized **Redis Caching** structures to prevent database strain from heavy query spikes.
* Uses Spring Data pagination wrappers configured with `VIA_DTO` serialization models to maintain stable API collection contracts across downstream entities.

### 📋 Order Service (State & Choreography Orchestration)

* Handles entry points for transaction logs.
* Wraps out-of-process RPC requests (`InventoryClient`) within **Resilience4j Circuit Breakers** to handle network degradations gracefully and instantly fall back to safe error payloads rather than cascading failures.

### 🏭 Inventory Service (Atomic Resource Allocation)

* Protects resource levels by isolating checking operations (`check-stock`) from application reservation states (`reserve`).
* Provides endpoints matching precise Path Variable contracts (`/{productId}/{quantity}/check-stock`) configured to survive compiled-parameter erasure environments.

### 💳 Payment Service (Financial Processing Settlement)

* Operates reactively as an event-driven data consumer.
* Decoupled completely from client request blocks, guaranteeing high-throughput availability.

---

## ⚙️ Configuration & Environment Setup

To run this microservices cluster locally without automated orchestration utilities, follow these network alignment steps to avoid common naming and interface routing problems (such as Link-Local `169.254.x.x` configuration overrides).

### 1. Network & Service Discovery Alignment (`application.yml`)

Ensure both your services and your local service discovery setup speed up lease renewals and target precise internal loopback adapters:

```yaml
# Add to both Order Service and Inventory Service configurations
eureka:
  client:
    registry-fetch-interval-seconds: 5
  instance:
    prefer-ip-address: true
    ip-address: 127.0.0.1
    lease-renewal-interval-in-seconds: 5

```

### 2. Kafka Serialization Setup

Ensure your consumers have explicit authorization definitions to reconstruct Jackson transaction events:

```properties
spring.kafka.consumer.properties.spring.json.trusted.packages=*

```

### 3. Compilation Target Execution Requirement

Because this architecture uses explicit annotation parameters on OpenFeign client interfaces, ensure your build environment retains parameter meta-names. Update your `pom.xml` configuration block if you compile manually:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <compilerArgs>
            <arg>-parameters</arg>
        </compilerArgs>
    </configuration>
</plugin>

```

---

## 🔄 Execution Sequence & Boot Order

To allow network caches to initialize and coordinate correctly, always start the infrastructure components and services in this specific order:

1. **Start Storage & Streaming Broker Backends:** Run your active local instances of **MySQL**, **Redis**, and **Apache Kafka**.
2. **Start Discovery Engine:** Initialize `Eureka Server Service` and verify the management interface has loaded at `http://localhost:8761`.
3. **Start API Entry Point:** Boot the `API Gateway Service` (Port `8080`).
4. **Boot Business Domains:** Spin up `Inventory Service`, `Product Service`, `Payment Service`, and `Auth Service`. Wait for their registration blocks to appear as `UP` inside the Eureka console.
5. **Boot Primary Consumer Engine:** Start `Order Service` (Port `8083`).

---

## 🗺️ Architectural Roadmap & Upcoming Milestones

* [ ] **Distributed Tracing:** Integration of **Micrometer Tracing** and **Zipkin** exporters to visualize cross-network spans.
* [ ] **Centralized Logging:** Deployment of the **ELK/PLG Stack** (Elasticsearch/Loki) via custom logback appenders.
* [ ] **Containerization:** Conversion of system topologies into unified structures using multi-stage `Dockerfiles` and orchestration using **Docker Compose**.
* [ ] **Infrastructure Observability:** SRE visualization deployment using **Prometheus** target-scraping links mapped to **Grafana** dashboard engines.

---

**Author:** Rehan Khan

*Software Engineer specializing in High-Availability Backend Architecture and Distributed Distributed Execution Ecosystems.*

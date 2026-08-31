![Java](https://img.shields.io/badge/JAVA-E65100?style=for-the-badge&labelColor=FFFFFF)
![Spring](https://img.shields.io/badge/SPRING-4CAF50?style=for-the-badge&labelColor=FFFFFF)
![Redis](https://img.shields.io/badge/REDIS-D32F2F?style=for-the-badge&labelColor=FFFFFF)
![Docker](https://img.shields.io/badge/DOCKER-0074D9?style=for-the-badge&labelColor=FFFFFF)
![MySQL](https://img.shields.io/badge/MYSQL-3A6599?style=for-the-badge&labelColor=FFFFFF)
![Flyway](https://img.shields.io/badge/FLYWAY-C92626?style=for-the-badge&labelColor=FFFFFF)
![Maven](https://img.shields.io/badge/MAVEN-0074D9?style=for-the-badge&labelColor=FFFFFF)

# iot-sensor-data-platform

This project is a distributed platform based on microservices for managing IoT devices and sensors.

The platform provides a centralized API Gateway for accessing the available services and uses service discovery to manage communication between microservices.

Currently, the platform includes a microservice responsible for managing devices and sensors, allowing users to register, retrieve, update, delete, activate, and disable devices.

The **API Gateway** acts as the main entry point for external clients, while **Eureka** is responsible for service discovery.

---

## Tech Stack

**Language:** Java 21

**Frameworks:** Spring Boot, Spring Web, Spring Data JPA, Spring Validation, Spring Cloud Gateway, Netflix Eureka

**Database:** MySQL

**Cache:** Redis

**Migrations:** Flyway

**Observability:** Spring Boot Actuator, Prometheus

**Containerization:** Docker, Docker Compose

**Dependency Manager:** Maven

---

## Application Endpoints

The main API entry point is provided through the **API Gateway**.

**Gateway:** `http://localhost:8080`

### Device

`GET /device` Returns all registered devices.  
`GET /device/{name}` Returns a device using its name.  
`POST /device` Registers a new device.  
`PATCH /device/{name}` Updates an existing device.  
`DELETE /device/{name}` Deletes a device using its name.  
`PATCH /device/active/{name}` Activates the specified device.  
`PATCH /device/disable/{name}` Disables the specified device.

---

## Services

### Discovery

The Discovery service is responsible for service discovery using Netflix Eureka.

All other microservices connect to this service to register themselves and discover other services.

Port: `8761`

### Device

The Device microservice is responsible for managing IoT devices and sensors.

Port: `8081`

### Gateway

The API Gateway is the main entry point for external clients and routes requests to the appropriate microservice.

Port:`8080`

---

## Service Startup Order

To ensure that all services can register and communicate correctly, start them in the following order:

### 1 - Start the infrastructure

Start the required infrastructure services:MySQL, Redis

### 2. Start Discovery

Start the Discovery service first. Wait until Eureka is fully available.

### 3. Start Device

Start the Device microservice. The service will register itself with Eureka.

### 4. Start Gateway

Finally, start the Gateway. The Gateway will use Eureka to discover the available microservices.
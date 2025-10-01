# Product API Documentation

## 1. Overview

This project is a reactive web application built using:

*   **Framework:** Spring Boot 3.5.6, Spring WebFlux
*   **Language:** Java 21

## 2. Dependencies

The project utilizes the following dependencies:

*   **`org.springframework.boot:spring-boot-starter-webflux`**: Provides support for building reactive web applications using Spring WebFlux.
*   **`org.springframework.boot:spring-boot-starter-validation`**:  Provides support for Bean Validation API.
*   **`org.projectlombok:lombok`**:  A library to reduce boilerplate code.
*   **`org.springdoc:springdoc-openapi-starter-webflux-ui`**:  Automatically generates API documentation using OpenAPI 3.0 specification and provides a Swagger UI for interacting with the API. Version 2.3.0 is used.
*   **`org.springframework.boot:spring-boot-starter-test`**: Provides core testing functionality for Spring Boot applications.
*   **`io.projectreactor:reactor-test`**: Provides utilities for testing reactive streams.
*   **`org.mockito:mockito-inline`**: Allows mocking of final classes and methods for testing. Version 5.2.0 is used.

## 3. Build Tool Configuration

The project is built using **Maven**.

Key plugins configured:

*   **`org.springframework.boot:spring-boot-maven-plugin`**:  Provides Spring Boot support in Maven. The configuration excludes Lombok from the repackaged archive.
*   **`org.jacoco:jacoco-maven-plugin`**: Used for generating code coverage reports. JaCoCo version is 0.8.11. The configuration enforces a minimum line coverage ratio of 1.00 at the package level.

## 4. Deployment Configuration

The project includes a **Dockerfile** and a **docker-compose.yml** file for containerized deployment.

### 4.1 Dockerfile

The Dockerfile uses a multi-stage build:

*   **Stage 1 (build):** Uses a JDK 21 base image to build the application. It copies the Maven wrapper, `pom.xml`, and source code, downloads dependencies, and builds the application using `./mvnw clean package -DskipTests`.
*   **Stage 2 (runtime):** Uses a JRE 21 base image. It creates a non-root user named `spring`, copies the built JAR file from the build stage, exposes port 8080, and defines a health check that probes the `/api/v1/products` endpoint. The entrypoint is set to run the application using `java -jar app.jar`.

### 4.2 docker-compose.yml

The docker-compose.yml file defines a single service `product-api`:

*   Builds the image using the `Dockerfile` in the current directory (`.`).
*   Sets the container name to `product-api`.
*   Maps port 8080 on the host to port 8080 in the container.
*   Sets the environment variables `SPRING_PROFILES_ACTIVE` to `prod` and `JAVA_OPTS` to `-Xmx512m -Xms256m`.
*   Defines a health check that probes the `/api/v1/products` endpoint.
*   Restarts the container unless it's explicitly stopped.
*   Attaches the container to a network named `product-network`. A bridge network named `product-network` is created to connect the services.

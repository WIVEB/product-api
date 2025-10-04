# Product API Documentation

## 1. Overview

This document describes the technology stack of the `product-api` project. This is a Spring Boot reactive web application built with Java.

*   **Framework:** Spring Boot
*   **Reactive Framework:** WebFlux
*   **Language:** Java
*   **Spring Boot Version:** 3.5.6
*   **Java Version:** 21

## 2. Dependencies

The project uses the following dependencies:

*   **`org.springframework.boot:spring-boot-starter-webflux`:** Provides the foundation for building reactive web applications using Spring WebFlux.
*   **`org.springframework.boot:spring-boot-starter-validation`:** Enables bean validation using annotations for request and response objects.
*   **`org.projectlombok:lombok`:**  Provides annotations to reduce boilerplate code (e.g., getters, setters, constructors).  The `optional` tag is set to `true`.
*   **`org.springdoc:springdoc-openapi-starter-webflux-ui`:** Provides automatic OpenAPI 3 documentation generation and Swagger UI integration for WebFlux applications.  Version: 2.3.0
*   **`org.springframework.boot:spring-boot-starter-test`:** Provides core testing support for Spring Boot applications. Scope: `test`.
*   **`io.projectreactor:reactor-test`:** Provides utilities for testing reactive streams using Reactor. Scope: `test`.
*   **`org.mockito:mockito-inline`:**  Allows mocking of final classes and methods.  Version: 5.2.0.  Scope: `test`.

## 3. Build Tool Configuration

The project uses Maven as its build tool.

*   **Maven:**
    *   The `pom.xml` defines the project dependencies, plugins, and build configuration.
    *   The `spring-boot-maven-plugin` is used to package the application as an executable JAR.  It excludes Lombok.
    *   The `jacoco-maven-plugin` is configured for code coverage analysis.  It is configured to enforce a code coverage rule of 100% on the line coverage ratio for packages. Jacoco version: 0.8.11

## 4. Deployment Configuration

The project includes a `Dockerfile` and `docker-compose.yml` for containerized deployment.

*   **Dockerfile:**
    *   A multi-stage build is used:
        *   Stage 1 (build): Uses `eclipse-temurin:21-jdk-alpine` to build the application using Maven.
        *   Stage 2 (runtime): Uses `eclipse-temurin:21-jre-alpine` to run the application.
    *   A non-root user "spring" is created for security purposes.
    *   The application JAR is copied from the build stage.
    *   Port 8080 is exposed.
    *   A health check is defined to verify the application's availability. It checks endpoint `/api/v1/products`.
    *   The application is started using `java -jar app.jar`.
*   **docker-compose.yml:**
    *   Defines a service named `product-api`.
    *   Builds the image using the `Dockerfile` in the current directory.
    *   Maps port 8080 of the container to port 8080 of the host.
    *   Sets the `SPRING_PROFILES_ACTIVE` environment variable to `prod`.
    *   Sets the `JAVA_OPTS` environment variable for JVM memory settings.
    *   Configures a health check similar to the one in the `Dockerfile`, again against endpoint `/api/v1/products`.
    *   Configures the container to restart unless explicitly stopped.
    *   Defines a network named `product-network` using the bridge driver.  The service is attached to this network.

## 5. Application Configuration

*   **application.properties:**
    *   `spring.application.name=product-api`: Sets the application name.
    *   `springdoc.api-docs.path=/api-docs`: Configures the path for OpenAPI documentation.
    *   `springdoc.swagger-ui.path=/`: Configures the path for the Swagger UI.
    *   `springdoc.swagger-ui.operationsSorter=method`: Configures method sorting in Swagger UI.
    *   `springdoc.swagger-ui.tagsSorter=alpha`: Configures tag sorting in Swagger UI alphabetically.

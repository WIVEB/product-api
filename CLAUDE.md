# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot 3.5.6 reactive web application using WebFlux for building reactive RESTful services. The project uses Java 21 and Maven for build management.

**Package structure:** `com.example.product.api.produc`

## Build and Development Commands

**Build the project:**
```bash
./mvnw clean install
```

**Run the application:**
```bash
./mvnw spring-boot:run
```

**Run all tests:**
```bash
./mvnw test
```

**Run a single test class:**
```bash
./mvnw test -Dtest=ClassName
```

**Run a single test method:**
```bash
./mvnw test -Dtest=ClassName#methodName
```

**Package the application:**
```bash
./mvnw package
```

## Architecture

This is a reactive Spring Boot application built on Spring WebFlux, which uses Project Reactor for reactive programming. The application follows a non-blocking, event-driven architecture.

**Key dependencies:**
- `spring-boot-starter-webflux` - Reactive web framework
- `reactor-test` - Testing utilities for reactive streams
- `spring-boot-starter-test` - Standard testing framework

**Note on reactive programming:** All controllers should return reactive types (Mono<T> for single values, Flux<T> for streams). Use WebClient for external HTTP calls instead of RestTemplate.

# Quora-like Backend System

A backend system inspired by Quora’s core Q&A platform, focused on scalable backend architecture rather than feature parity.

This project demonstrates clean backend design using reactive APIs, asynchronous event processing, and search indexing.

---

## 🚀 Features
- Reactive REST APIs using **Spring WebFlux**
- Question & Answer management
- Asynchronous view count handling using **Kafka**
- Full-text search using **Elasticsearch**
- Clean layered architecture (Controller, Service, Repository, DTO, Adapter)
- API-first development with **Swagger UI**

---

## 🛠️ Tech Stack
- Java
- Spring Boot (WebFlux)
- Apache Kafka
- Elasticsearch
- SQL (JPA/Hibernate)
- Gradle
- Swagger / OpenAPI

---

## 📐 Architecture Overview
- **Controller Layer** – Handles HTTP requests
- **Service Layer** – Business logic
- **Repository Layer** – Database & Elasticsearch access
- **Kafka Producer/Consumer** – Async event processing (view counts)
- **DTOs & Adapters** – Clean data transformation

---

## 📄 API Documentation
All APIs are documented using **Swagger UI**.

Once the application is running, access Swagger at:
http://localhost:8080/webjars/swagger-ui/index.html
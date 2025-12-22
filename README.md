[![CodeFactor](https://www.codefactor.io/repository/github/dmitriy-iliyov/aid-compass-backend/badge)](https://www.codefactor.io/repository/github/dmitriy-iliyov/aid-compass-backend)

![Java](https://img.shields.io/badge/Java-17-007396?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-6DB33F?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16+-336791?logo=postgresql)
![Redis](https://img.shields.io/badge/Redis-7+-DC382D?logo=redis)
![Prometheus](https://img.shields.io/badge/Prometheus-Metrics-E6522C?logo=prometheus)
![Grafana](https://img.shields.io/badge/Grafana-Dashboards-F46800?logo=grafana)

## Overview

This repository contains the main backend implementation for a platform designed to help users find specialists and manage appointment scheduling.  
It supports user and specialist account management, personal schedule configuration, and booking workflows.

Project components:
- frontend – https://github.com/dmitriy-iliyov/aid-compass-frontend  
- task scheduler service – https://github.com/dmitriy-iliyov/schedule-tasks-service  

## Functions

- account creation and registration;
- account management for users and specialists;
- specialist search by specialization;
- appointment booking and cancellation;
- personal schedule configuration for specialists;
- management of booking and reminder records;
- automated appointment reminders;
- notifications about appointment updates and cancellations.

## Architecture

The project is implemented as a **modular monolithic system** with support for horizontal scaling.  
Each module encapsulates a specific business domain while sharing common infrastructure and contracts.

### Modules

- **aggregation-module**  
  Acts as an API aggregation layer for the frontend.  
  It combines data from multiple internal modules and exposes frontend-oriented endpoints, reducing the number of client-server round trips.

- **aidcompass-contract**  
  Contains shared DTOs, enums, and API contracts used across modules and external services.  
  This module ensures consistency of data structures and minimizes duplication.

- **aidcompass-core**  
  Provides core infrastructure such as:
  - common configuration;
  - security configuration;
  - shared utilities;
  - persistence abstractions;
  - contract storage and management.

- **avatar-module**  
  Responsible for managing user avatars stored in Azure Blob Storage.  
  Handles upload, update, deletion, and access control for media resources.

- **message-module**  
  Implements notification and messaging logic, including:
  - appointment reminders;
  - cancellation notifications;
  - system-generated messages.

- **schedule-module**  
  Manages scheduling logic and appointment planning:
  - specialist availability;
  - time slot management;
  - conflict detection;
  - booking lifecycle.

- **user-module**  
  Handles user and specialist accounts:
  - registration and profile management;
  - role-based access control;
  - authentication-related user data.

## Non-functional requirements

### Authentication & Security

- **Web authentication** is based on JWT tokens stored in HTTP cookies, providing stateless authentication while remaining browser-friendly.
- CSRF protection is enabled for all state-changing HTTP requests.  
  CSRF tokens are validated on write operations to prevent cross-site request forgery attacks.
- XSS protection is implemented via request filtering and input sanitization.  
  All incoming data used in state-changing operations is validated and escaped to prevent injection of malicious scripts.
- **Internal services** (such as the task scheduler service and Prometheus scraper) authenticate using Basic Authentication, simplifying secure service-to-service communication.
- Role-based access control (RBAC) is enforced at the API and service levels.

### Caching & Performance

- **Redis** is used as an in-memory data store to cache frequently accessed data and intermediate results.
- Caching significantly reduces the load on the PostgreSQL database, improving overall system throughput and response times.
- Redis is also used for short-lived data such as temporary states, counters, and performance-critical lookups.

### Observability

- **Prometheus** is used to collect application metrics.
- **Grafana** provides dashboards for monitoring system health, performance, and load characteristics.


## Run

The project is fully containerized using Docker.

### Option 1: Automatic build (frontend + backend)

To build and run the project locally **when the frontend repository is available locally**, execute:

```bash
bash local-build-and-run.sh
```
This script performs the following steps:
- builds the Angular frontend application in production mode;
- copies the generated static resources into app/src/main/resources/static;
- rebuilds the backend Docker image;
- starts the full infrastructure using Docker Compose.
This option is recommended for local development and quick setup.

### Option 2: Manual frontend build + Docker Compose

If you prefer to build the Angular frontend manually and then run the application using Docker Compose:
Build the frontend application:

```bash
cd aid-compass-frontend
npm install
npm run build -- --configuration production
```

Copy the generated static files to the backend:

```bash
cp -r dist/aid-compass-frontend/browser/* aid-compass-backend/app/src/main/resources/static/
Build and start the backend and infrastructure:
docker compose build
docker compose up -d
```

This option is useful when you want full control over the frontend build process or already have prebuilt frontend artifacts.

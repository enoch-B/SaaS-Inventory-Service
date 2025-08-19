# SaaS Inventory Service

**A modular Java microservice for managing inventory operations built during my INSA internship.**

---

##  Project Structure

- `src/` – Java source code modules.
- `pom.xml` – Root Maven build configuration.
- `pom.xml` (module) – Maven configuration for secondary module (e.g., "form" or "api").
- `Dockerfile` – Defines how to build the service’s Docker image.
- `docker-compose.yml` – Orchestrates local development and testing environments.
- `Jenkinsfile` – CI/CD pipeline for builds, tests, and deployments.

---

##  Tech Stack

- **Java** – Core backend development  
- **Maven** – Build and dependency management :contentReference[oaicite:3]{index=3}  
- **Docker & Docker Compose** – Containerization for development & deployment  
- **Jenkins** – Automated CI/CD workflows

---

##  Getting Started

### Prerequisites

Ensure you have installed:
- Java JDK 11+
- Maven
- Docker & Docker Compose
- Jenkins (for CI/CD pipeline)

### Build & Run Locally

```bash
# Build with Maven
mvn clean package

# Build Docker image
docker build -t saas-inventory-service:latest .

# Run via Docker Compose
docker-compose up

```
## Possible Extensions

- Implement REST APIs for inventory management (e.g., items, stock operations).

- Integrate with a database ( MySQL).

- Add authentication and authorization.

- Support multi-tenant SaaS operations .

- Enhance observability with logging and metrics.


## Acknowledgments

This project was developed as part of my internship at INSA, focusing on cloud-native practices, containerization, and CI/CD.

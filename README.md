🟦 Shopping Delivery API
==========================

This project is a **Spring Boot** application developed in **Java 21**, using **Maven** as the build system. It integrates with **PostgreSQL** as the database, supports **Docker build & run**, and provides **API documentation with Swagger / OpenAPI**.

Main Technologies
-------------------

*   **Java 21**

*   **Spring Boot**

*   **Maven**

*   **Spotless** → automatic code formatting

*   **JUnit** → testing

*   **PostgreSQL**

*   **Docker & Docker Compose**

*   **Swagger / OpenAPI** → API documentation


⚙️ Local Setup
--------------------
---

### 1\. Clone the repository

```git clone git@github.com:ranieri-vannozzi/shopping-delivery-api.git```

### 2\. Start PostgreSQL with Docker Compose

The file postgres-compose.yml launches a local PostgreSQL instance for development.

```docker-compose -f postgres-compose.yml up -d```

Default database configuration:

*   **Host**: localhost

*   **Port**: 54320

*   **Database**: shopping

*   **User**: user

*   **Password**: password


▶️ Build and Run
--------------------
---

### 1\. Build with Maven

```mvn clean install```

### 2\. Run the application

```mvn spring-boot:run```

### 3\. Run the application in debug mode - Remote JVM

```mvn spring-boot:run -Dspring-boot.run.fork=true -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005"```

Application will be available at:👉 [http://localhost:8080](http://localhost:8080)

🧪 Testing
--------------------
---

Tests are written with **JUnit**. Run them with:

```mvn test```

🐳 Docker
--------------------
---

### Build Docker image

The project includes a Dockerfile. To build the image:

```docker build -t shopping-delivery-api:latest .```

### Run container

```docker run -p 8080:8080 --env-file .env shopping-delivery-api:latest```

⚠️ Make sure the PostgreSQL container (started with postgres-compose.yml) is running.

📖 API Documentation
--------------------
---

*   **Swagger UI** → [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

*   **OpenAPI Descriptor** → [http://localhost:8080/open-api](http://localhost:8080/open-api)


🧹 Code Formatting
------------------
---

The project uses **Spotless** to keep the code style consistent.

Format the code automatically:

```mvn spotless:apply```

Check formatting without applying changes:

```mvn spotless:check```

🚀 Continuous integration
------------------
---

This project uses GitHub Actions for automated CI/CD. 
The backend pipeline automatically runs on every push and pull request to the main branch, performing the following steps:

- Environment Setup: configures JDK 21 and Docker Compose
- Database: starts PostgreSQL container and waits for readiness
- Build & Test: compiles and tests the application using Maven
- Artifact Storage: saves the generated JAR file for deployment

The CI ensures code quality and build integrity before merging changes.

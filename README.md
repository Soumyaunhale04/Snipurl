# Snipurl - URL Shortener Service

A Spring Boot-based REST API for shortening long URLs into compact, shareable codes.

## Overview

Snipurl is a simple yet powerful URL shortening service that converts lengthy URLs into short codes. It provides endpoints to create shortened URLs and retrieve the original URLs using the generated shortcodes.

## Features

- **URL Shortening**: Convert long URLs into short, unique codes
- **URL Retrieval**: Redirect from shortcode to original URL with automatic 302 redirects
- **Expiry Support**: URLs can have optional expiry times
- **Persistent Storage**: All URLs stored in PostgreSQL database
- **Rate Limiting**: Basic request throttling on `/api/**`
- **Statistics Endpoint**: View stored URL metadata for a shortcode
- **RESTful API**: Clean and intuitive REST endpoints

## Technology Stack

- **Framework**: Spring Boot 4.0.7
- **Language**: Java 17
- **Database**: PostgreSQL
- **Caching**: Redis
- **ORM**: Spring Data JPA
- **Build Tool**: Maven
- **Additional Libraries**: Lombok (for reducing boilerplate code)

## Project Structure

```
snipurl/
├── backend/                    # Spring Boot application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/snipurl/
│   │   │   │       ├── SnipurlApplication.java     # Entry point
│   │   │   │       ├── controller/
│   │   │   │       │   └── UrlController.java      # REST endpoints
│   │   │   │       ├── service/
│   │   │   │       │   └── UrlService.java         # Business logic
│   │   │   │       ├── repository/
│   │   │   │       │   └── UrlRepository.java      # Data access layer
│   │   │   │       └── entity/
│   │   │   │           └── Url.java                # Domain model
│   │   │   └── resources/
│   │   │       └── application.yml                 # Application configuration
│   │   └── test/
│   │       └── java/com/snipurl/...               # Test classes
│   ├── pom.xml                # Maven dependencies
│   └── mvnw                   # Maven wrapper scripts
└── database/
    └── Schema.sql             # Database initialization script
```

## Installation & Setup

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12 or higher

### Database Setup

1. Create a PostgreSQL database:
```bash
CREATE DATABASE Snipurl;
```

2. Run the schema initialization script:
```bash
psql -U postgres -d Snipurl -f database/Schema.sql
```

### Application Configuration

Update `backend/src/main/resources/application.yml` with your PostgreSQL credentials:

```yaml
spring:
  application:
    name: Snipurl
  datasource:
    driverClassName: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5432/snipurl
    username: postgres 
    password: YOUR_PASSWORD
  jpa:
    hibernate:
      ddl-auto: create
```

When running with Docker Compose, the service host names are `postgres` and `redis`.

### Build & Run

Option 1: Run with Docker Compose

```bash
cd backend
docker compose up --build
```

This starts the application with PostgreSQL and Redis.

Option 2: Run locally with Maven

```bash
cd backend
./mvnw clean package
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

### Shorten a URL

**Endpoint**: `POST /api/shorten`
<!--  -->
**Request**:
```bash
curl -X POST http://localhost:8080/api/shorten \
  -H "Content-Type: application/json" \
  -d "https://example.com/very/long/url/path"
```

**Response** (201 Created):
```json
{
  "id": 1,
  "originalurl": "https://example.com/very/long/url/path",
  "shortcode": "abc123",
  "createdTime": "2026-05-09T10:30:00",
  "expiryTime": null
}
```

### Retrieve Original URL

**Endpoint**: `GET /api/{shortcode}`

**Request**:
```bash
curl -X GET http://localhost:8080/api/abc123 -L
```

**Response** (302 Found):
- Automatically redirects to the original URL
- Returns the URL object in response body

**Error Response** (404 Not Found):
- Returns 404 if shortcode does not exist

### URL Metadata

**Endpoint**: `GET /api/stats/{shortCode}`

Returns the stored URL object for the shortcode.

## Data Model

### Url Entity

| Field | Type | Description |
|-------|------|-------------|
| id | Long | Primary key (auto-generated) |
| originalurl | String | The full original URL |
| shortcode | String | The generated short code |
| createdTime | LocalDateTime | Timestamp when URL was shortened |
| expiryTime | LocalDateTime | Optional expiry time for the shortened URL |
| clickCount | Long | Click count / metadata field |

## Development

### Running Tests

```bash
cd backend
./mvnw test
```

### Build Outputs

- **Classes**: `backend/target/classes/`
- **JAR**: `backend/target/snipurl-0.0.1-SNAPSHOT.jar`

## Future Enhancements

- [ ] Custom shortcode support
- [ ] Analytics dashboard (click tracking)
- [ ] URL expiration enforcement
- [ ] Rate limiting
- [ ] User authentication and management
- [ ] Admin panel for URL management
- [ ] API key support
- [ ] QR code generation

## Contributing

Feel free to fork this project and submit pull requests for any improvements.

---

**Version**: 0.0.1-SNAPSHOT  
**Status**: In Development  
**Last Updated**: May 2026

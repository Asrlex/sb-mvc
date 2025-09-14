# Spring Boot API Scaffolding

A robust, production-ready Spring Boot API scaffolding project with modular authentication, standardized responses, and developer-friendly features.

## Features

### 1. Basic Controllers
- **User Management**: CRUD endpoints for users (`/users`), including search by email, username, and ID.
- **Self Endpoint**: `/users/me` returns the currently authenticated user.
- **Healthcheck**: Simple endpoint for service health monitoring.

### 2. Auth Module
- **Login & Registration**: `/auth/login` and `/auth/register` endpoints.
- **JWT Authentication**: Secure token-based authentication with custom claims.
- **API Key Authentication**: Supports API key-based access for service-to-service or admin use cases.
- **Composite Authentication Pipeline**: Tries JWT first, then API key, ensuring flexible authentication strategies.

### 3. Security Configuration
- **Spring Security Integration**: Centralized config for authentication and authorization.
- **Custom Entry Point & Access Denied Handler**: Standardized error responses for unauthorized and forbidden access.
- **Endpoint Whitelisting**: Public access for `/auth/**`, `/healthcheck`, and Swagger docs; all other endpoints require authentication.

### 4. Access Points
- **RESTful Endpoints**: Well-structured, discoverable API routes for all resources.
- **Swagger/OpenAPI**: Auto-generated API documentation at `/swagger-ui.html` and `/v3/api-docs`.

### 5. Request Logging Filter
- **Comprehensive Logging**: Logs every request and response with unique request IDs, method, URI, status, and processing time.
- **Traceability**: Adds `X-Request-Id` header to all responses for easy tracing.

### 6. Response Wrapper
- **Standardized API Responses**: All responses are wrapped in a consistent envelope (`ApiEnvelope`), including metadata and error codes.
- **Automatic Wrapping**: Applied globally, with opt-out via annotation.

### 7. JPA Integration
- **Spring Data JPA**: Repository pattern for data access.
- **User Entity**: Example entity with email, username, password, and role fields.
- **Validation & Error Handling**: Automatic validation and clear error messages for invalid data.
- **Pagination & Sorting**: Built-in support for paginated and sorted responses.
- **Database Migrations**: Flyway integration for managing schema changes.
- **DTO Mapping**: Use of MapStruct for clean separation between entities and API models.
- **Auditable Entities**: Automatic tracking of creation and modification timestamps.
- **Soft Deletion**: Logical deletion of records without physical removal.

### 8. Standardized Error Handling
- **Global Exception Handler**: Catches and formats all exceptions into a unified error structure.
- **Detailed Validation Errors**: Field-level error reporting for request validation failures.
- **Consistent Error Codes**: Uses a fixed set of response codes for all error scenarios.

---

## Getting Started

1. **Clone the repository**
2. **Configure your database and JWT secret in `application.properties`**
3. **Run with Maven:**
4. **Explore the API at** `http://localhost:8080/swagger-ui.html`

---

## License

MIT

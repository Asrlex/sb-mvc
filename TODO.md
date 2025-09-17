# TODO

## GENERIC

## PHASE 1

- [x] **Basic Controllers**
- [x] **Auth Module**
  - [x] JWT Authentication
  - [x] API Key Authentication
  - [x] Composite Authentication Pipeline
- [x] **Security Configuration**
  - [x] Access Points
  - [x] Custom Entry Point & Access Denied Handler
- [x] **Request Logging Filter**
- [x] **Response Wrapper**
- [x] **JPA Integration**
  - [x] User Entity
  - [x] Validation & Error Handling
  - [x] Pagination & Sorting
  - [x] Database Migrations
  - [x] DTO Mapping
  - [x] Repository Pattern
  - [x] Audit Fields
  - [x] Soft Deletion
  - [x] Custom Repositories with Generics and Specifications
- [x] **Standardized Error Handling**
- [x] **README.md**
- [x] **OpenAPI/Swagger Integration**

## PHASE 2

- [x] **Docker**
  - [x] Dockerfile
  - [x] Environment Variables
  - [x] Docker Compose with SQLite (for local dev)
  - [x] Docker Compose with Postgres
  - [x] Multi-stage builds for smaller image size
  - [x] Flyway migrations in Docker
- [x] **RBAC/ABAC**
  - [x] Role-Based Access Control (RBAC)
  - [x] Hierarchical Roles
- [x] **Caching**
  - [x] Spring Cache Abstraction
  - [x] In-Memory Caffeine Cache for Local Dev
  - [x] Redis Integration in Production
  - [x] Cache Eviction Strategies
- [x] **Rate Limiting with Bucket4j**
  - [x] Role-based Rate Limits
- [ ] Internationalization (i18n)
- [ ] RESPONSE ENTITY

## PHASE 3

- [ ] Extra Support
  - [ ] OAuth2 Support
  - [ ] GraphQL Support
  - [ ] WebSocket Support
- [ ] Observability Stack
  - [ ] Micrometer/Prometheus Metrics
  - [ ] Tracing with OpenTelemetry
  - [ ] JSON structured logs for ELK/Loki
- [ ] Resilience & Messaging
  - [ ] Add Resilience4j (retry, circuit breaker).
  - [ ] Add Kafka/RabbitMQ integration for async flows.
- [ ] CI/CD Pipeline (GitHub Actions)
- 

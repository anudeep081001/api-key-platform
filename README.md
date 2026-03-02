# Rate-Limited API Key Platform (Java + SDE)
**Goal:** Issue/rotate API keys, enforce quotas with Redis token bucket, store usage analytics, and provide admin APIs.

## Tech
- Java 21 + Spring Boot
- PostgreSQL (data)
- Redis (rate limiting + counters)
- Docker Compose

## Run
```bash
docker compose up --build
```
Open:
- Swagger: http://localhost:8082/swagger-ui/index.html

## Key Endpoints
- `POST /auth/register` → create customer
- `POST /keys` → issue key for customer
- `POST /keys/rotate` → rotate key
- `GET /protected/ping` → test rate-limited auth (requires `X-API-Key`)

Generated: 2026-03-02

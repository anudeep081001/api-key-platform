# Secure API Key Management & Rate-Limited Access Platform

A production-oriented backend system for secure API key management with distributed rate limiting using Redis.

---

## 🚀 What This System Does

This platform simulates how real-world APIs (Stripe, AWS, etc.):

- Issue and manage API keys securely
- Enforce per-client rate limits
- Protect backend services from abuse

---

## 🔥 Key Features

- API key issuance and rotation
- SHA-256 hashing (no raw key storage)
- Redis-based token bucket rate limiting
- Per-key request throttling
- Dockerized multi-service setup (PostgreSQL + Redis)
- Swagger API interface for testing

---

## 🧠 Request Flow (How It Works)

1. Client sends request with `X-API-Key`
2. System hashes and validates API key
3. Redis checks token bucket for rate limit
4. If allowed → request proceeds
5. If exceeded → request rejected
6. Usage events optionally recorded

---

## 🏗 Architecture

```mermaid
graph TD
    Client -->|X-API-Key| SpringBootAPI
    SpringBootAPI --> PostgreSQL[(PostgreSQL)]
    SpringBootAPI --> Redis[(Redis)]
    Redis -->|Token Bucket| RateLimiter
    SpringBootAPI --> UsageEvents

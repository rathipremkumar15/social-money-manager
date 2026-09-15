# Phase 1 — Project Foundation

## Scope

Phase 1 establishes a runnable full-stack foundation without paid infrastructure.

### Included
- Java 21 + Spring Boot backend
- Next.js + TypeScript frontend
- PostgreSQL persistence foundation
- Flyway migration foundation
- Redis local development service
- Backend health endpoint
- Shared API error/response conventions
- Dockerized local infrastructure
- Initial project configuration

### Not yet included
- Production authentication
- Social OAuth integrations
- AI provider credentials
- Video generation providers
- Payments
- Public deployment

Those belong to later phases.

## Local development

Start infrastructure with:

```bash
docker compose up -d
```

Backend:

```bash
cd backend
mvn spring-boot:run
```

Frontend:

```bash
cd frontend
npm install
npm run dev
```

Backend health endpoint:

`GET /api/v1/system/health`

## Phase 1 acceptance criteria

- Repository has independent frontend/backend foundations.
- Backend is buildable with Java 21 and Maven.
- PostgreSQL is configured through environment variables.
- Flyway owns schema migrations.
- Redis is available locally without a paid service.
- Frontend is TypeScript strict-mode configured.
- No secrets are committed.
- Health endpoint returns service status.
- Foundation is ready for Phase 2 authentication work.

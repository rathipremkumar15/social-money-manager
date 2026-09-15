# API Contracts

Phase 0 logical contract. Exact DTO validation and OpenAPI generation are Phase 1.

## Conventions

- Base path: `/api/v1`
- JSON request/response bodies
- UTC timestamps in ISO-8601
- UUID identifiers
- Authentication through secure session/token mechanism
- Pagination uses `page`, `pageSize`, `nextCursor` where cursor pagination is appropriate
- Mutating endpoints support an idempotency key when retries could duplicate work
- Errors use one stable envelope

## Error envelope

```json
{
  "error": {
    "code": "CONTENT_VALIDATION_FAILED",
    "message": "The content could not be published.",
    "requestId": "uuid",
    "details": []
  }
}
```

## Authentication

```text
POST /api/v1/auth/register
POST /api/v1/auth/login
POST /api/v1/auth/logout
POST /api/v1/auth/refresh
POST /api/v1/auth/forgot-password
POST /api/v1/auth/reset-password
GET  /api/v1/me
```

## AI

```text
POST /api/v1/ai/tasks
GET  /api/v1/ai/tasks/{taskId}
POST /api/v1/ai/tasks/{taskId}/cancel
GET  /api/v1/ai/runs/{runId}
```

Task request concept:

```json
{
  "objective": "Find five realistic ways to monetize my social audience",
  "agent": "MONEY_RESEARCH",
  "constraints": {"budget": 5000, "currency": "INR"},
  "requireApproval": true
}
```

## Opportunities

```text
POST /api/v1/opportunities/search
GET  /api/v1/opportunities
GET  /api/v1/opportunities/{id}
POST /api/v1/opportunities/{id}/save
DELETE /api/v1/opportunities/{id}/save
```

Search results must include evidence/source references and uncertainty where applicable.

## Research

```text
POST /api/v1/research/jobs
GET  /api/v1/research/jobs/{jobId}
GET  /api/v1/research/jobs/{jobId}/sources
```

## Content

```text
POST /api/v1/content
GET  /api/v1/content
GET  /api/v1/content/{id}
PATCH /api/v1/content/{id}
DELETE /api/v1/content/{id}
POST /api/v1/content/{id}/variants
POST /api/v1/content/{id}/generate
POST /api/v1/content/{id}/privacy-check
POST /api/v1/content/{id}/request-approval
```

## Media

```text
POST /api/v1/media/generations
GET  /api/v1/media/generations/{id}
GET  /api/v1/media/assets/{id}
```

Media generation is asynchronous. API responses return a task/generation ID rather than blocking until video generation completes.

## Social accounts

```text
GET  /api/v1/social/accounts
POST /api/v1/social/{platform}/connect
GET  /api/v1/social/{platform}/callback
DELETE /api/v1/social/accounts/{id}
GET  /api/v1/social/accounts/{id}/capabilities
POST /api/v1/social/publications
GET  /api/v1/social/publications/{id}
```

Platform-specific capabilities must be checked before exposing an action in the UI.

## Analytics

```text
POST /api/v1/analytics/sync
GET  /api/v1/analytics/accounts/{accountId}
GET  /api/v1/analytics/content/{contentId}
GET  /api/v1/insights
```

## Privacy & approvals

```text
POST /api/v1/privacy/check
GET  /api/v1/privacy/findings/{id}
GET  /api/v1/approvals
POST /api/v1/approvals/{id}/approve
POST /api/v1/approvals/{id}/reject
```

## Billing

```text
GET  /api/v1/billing/plans
GET  /api/v1/billing/subscription
POST /api/v1/billing/checkout
POST /api/v1/billing/portal
POST /api/v1/billing/webhooks/{provider}
```

Webhook endpoints must authenticate provider signatures and be idempotent.

## API design rules

1. Controllers remain thin; business logic belongs in application/domain services.
2. External provider models never leak directly into public API DTOs.
3. Every response that starts asynchronous work returns a durable ID and status.
4. Authorization is checked at the service boundary, not only in controllers.
5. Never return OAuth access/refresh tokens, password hashes, or secret material.
6. All external callbacks validate state/signatures before processing.

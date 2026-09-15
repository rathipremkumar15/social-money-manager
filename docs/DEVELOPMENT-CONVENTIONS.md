# Development Conventions

## Repository

Use a monorepo with independent frontend and backend applications plus shared documentation/infrastructure.

```text
frontend/   Next.js + TypeScript
backend/    Java + Spring Boot
infra/      deployment/local infrastructure
packages/   future shared contracts if needed
docs/       architecture and product documentation
```

## Java / Spring Boot

- Java LTS release selected during Phase 1 setup.
- Package by domain/feature rather than by technical layer alone.
- Controllers handle transport concerns only.
- Application services coordinate use cases.
- Domain objects contain business rules.
- Repositories abstract persistence.
- DTOs are separate from persistence entities.
- Use constructor injection.
- Validate inputs at boundaries.
- Transactions belong at application service boundaries.
- Avoid exposing JPA entities directly through APIs.

Example:

```text
com.socialmoneymanager
  identity/
  ai/
  content/
  social/
  opportunity/
  research/
  privacy/
  analytics/
  billing/
  shared/
```

## TypeScript / Next.js

- TypeScript strict mode.
- Server-side logic remains server-side; browser code receives only required data.
- API contracts are typed.
- Components should be small and feature-oriented.
- Avoid business logic duplicated between UI and backend.
- Never expose secrets in client bundles.

## Naming

- Java classes: `PascalCase`.
- Java methods/variables: `camelCase`.
- Database tables/columns: `snake_case`.
- TypeScript variables/functions: `camelCase`.
- TypeScript components/types: `PascalCase`.
- API paths: lowercase plural nouns.

## Git

Use small, meaningful commits:

```text
feat: add opportunity domain
fix: prevent duplicate publication jobs
refactor: isolate social provider adapter
docs: define privacy policy
chore: update build tooling
```

Do not commit generated build output or secrets.

## Testing

- Unit tests for domain/application logic.
- Integration tests for database and provider adapters.
- API tests for contract behavior.
- End-to-end tests for critical user journeys.
- Security tests for authorization and secret handling.

A feature is not complete until appropriate tests exist.

## Observability

Use structured logs with correlation/request IDs. Record useful operational metadata but never raw credentials, tokens, passwords or sensitive user content.

Metrics should cover API latency, job throughput, failures, provider latency, AI cost/usage and queue depth.

## API compatibility

Breaking API changes require an explicit versioning decision. Provider-specific response formats must be normalized at the integration boundary.

## AI development rules

- Prompts are versioned artifacts.
- Tool schemas are explicit.
- Model output is treated as untrusted until validated.
- Important claims from research require provenance.
- Agent actions are permission-checked outside the model.
- No guaranteed-income language for speculative opportunities.

## Definition of done

A change is complete when code, tests, documentation, configuration, error handling, observability and security implications have been considered and the relevant checks pass.

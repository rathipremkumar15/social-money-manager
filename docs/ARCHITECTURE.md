# Architecture — Social Money Manager

## Architectural goals

- Production-grade backend for heavy, asynchronous workloads
- Modular domain boundaries
- Provider-agnostic AI and media integrations
- Secure handling of social OAuth credentials and user data
- Human approval for sensitive/high-impact automation
- Observable and testable services
- Ability to scale workers independently from the API

## High-level architecture

```text
                    Next.js Web App
                           |
                           v
                  Spring Boot API
                           |
          +----------------+----------------+
          |                |                |
          v                v                v
       AI Core        Social Core      Opportunity Core
          |                |                |
          v                v                v
     AI Providers      Platform APIs     Research Sources
          |                |                |
          +----------------+----------------+
                           |
                    Job / Event Layer
                      Redis + Workers
                           |
                 +---------+---------+
                 |                   |
                 v                   v
            PostgreSQL         Object Storage
```

## Backend domains

### Identity
Users, sessions, roles, permissions, account security.

### AI Core
Model abstraction, prompts, agent orchestration, tools, context, usage and cost tracking.

### Social Core
Connected accounts, OAuth lifecycle, platform capabilities, content publishing and scheduling.

### Opportunity Core
Money-making opportunities, sources, evidence, scoring, ranking and user goals.

### Research Core
Search requests, sources, extracted evidence, resource catalogues and research jobs.

### Content Core
Ideas, scripts, media assets, variants, publishing state and approval state.

### Privacy Core
PII/secret detection, policy checks, redaction decisions and security events.

### Analytics Core
Platform metrics, content performance, derived insights and recommendations.

### Billing Core
Plans, subscriptions, entitlements, usage and payment-provider webhooks.

## Processing model

Synchronous API requests should remain lightweight. Heavy work such as research, media generation, analytics processing and scheduled automation should execute as asynchronous jobs.

## Data principle

User-owned social credentials and sensitive data must never be stored in plain text. Secrets must remain outside source control and be handled through a dedicated secrets/configuration mechanism.

## AI principle

Business opportunities must be presented as researched possibilities with evidence, assumptions, costs, risks and uncertainty. The system must not represent speculative income as guaranteed.

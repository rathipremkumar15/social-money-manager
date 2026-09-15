# Database & Domain Model

Phase 0 design. This document defines the logical model; physical migrations are Phase 1.

## Design principles

- PostgreSQL is the source of truth for transactional product data.
- Every tenant-owned record is scoped by `user_id` or future `workspace_id`.
- IDs use UUIDs.
- Timestamps are stored in UTC.
- External provider IDs are stored alongside internal IDs.
- Secrets/tokens are encrypted and never returned by normal API responses.
- Soft deletion is used where recovery/audit requirements justify it.
- JSONB is used only for provider-specific payloads and evolving metadata, not core relational relationships.

## Core entities

### users
`id`, `email`, `password_hash`/identity-provider reference, `display_name`, `status`, `created_at`, `updated_at`.

### user_settings
`user_id`, timezone, locale, notification preferences, automation defaults, privacy preferences.

### roles / user_roles
Authorization roles and assignments. Start with `USER`, `ADMIN`; design for future workspace roles.

### social_accounts
`id`, `user_id`, `platform`, `external_account_id`, `handle`, `display_name`, `status`, `scopes`, encrypted token reference, token expiry, connected_at, disconnected_at.

### social_account_capabilities
Platform/account capability snapshot: publishing, scheduling, analytics, comments, messages, media types and API version metadata.

### content_items
`id`, `user_id`, type, title, body/script, status, approval_status, scheduled_at, published_at, created_at, updated_at.

### media_assets
`id`, `user_id`, content_id, provider, external_asset_id, asset_type, storage_uri, mime_type, duration, dimensions, generation_status, metadata.

### content_variants
Alternative hooks, captions, scripts, thumbnails or media versions linked to one content item.

### publication_jobs
`id`, `user_id`, content_id, social_account_id, status, scheduled_at, provider_job_id, attempts, error_code, error_message, published_external_id.

### opportunities
`id`, title, category, description, business_model, difficulty, startup_cost, revenue_potential, scalability, risk_level, score, status, created_at, updated_at.

### opportunity_sources
`id`, `opportunity_id`, source_url, source_name, source_type, retrieved_at, evidence_summary, reliability_score.

### opportunity_requirements
Skills, tools, platforms, capital, time and prerequisites associated with an opportunity.

### research_jobs
`id`, `user_id`, query, objective, status, started_at, completed_at, result_summary, usage_metadata.

### research_sources
Sources discovered by research jobs with URL, title, source type, retrieved time, relevance and evidence.

### ai_runs
`id`, `user_id`, agent_type, task_type, provider, model, status, input_reference, output_reference, token_usage, estimated_cost, latency_ms, created_at.

### agent_tasks
Durable tasks created by the AI orchestrator: objective, state, priority, parent_task_id, tool calls, approval requirement, deadlines and result reference.

### privacy_findings
`id`, `user_id`, `content_id`/asset reference, finding_type, severity, confidence, matched_reference, action, resolved_at. Never persist raw secrets unnecessarily.

### approvals
`id`, `user_id`, target_type, target_id, requested_action, risk_level, status, requested_at, decided_at, decision_reason.

### analytics_snapshots
`id`, `user_id`, social_account_id, content_external_id, metric_date, views, likes, comments, shares, saves, watch_time, clicks, followers_delta and provider metadata.

### insights
Derived observations tied to a user/content/account, including evidence, confidence, recommendation and expiry.

### subscriptions
`id`, `user_id`, provider, external_customer_id, external_subscription_id, plan, status, current_period_start, current_period_end, cancel_at_period_end.

### usage_records
Metered usage for AI calls, media generation, research and other billable features.

### audit_events
Security and business audit trail: actor, action, resource, outcome, timestamp, request/correlation ID, non-sensitive metadata.

## Main relationships

```text
User
 ├── Settings
 ├── SocialAccounts ──< PublicationJobs >── ContentItem
 ├── ContentItem ──< MediaAssets
 │               └──< ContentVariants
 ├── ResearchJobs ──< ResearchSources
 ├── AI Runs / AgentTasks
 ├── PrivacyFindings / Approvals
 ├── AnalyticsSnapshots ──> Insights
 ├── Subscription ──< UsageRecords
 └── AuditEvents

Opportunity ──< OpportunitySources
            └──< OpportunityRequirements
```

## Initial indexes

- `users(email)` unique
- `social_accounts(user_id, platform, external_account_id)` unique
- `content_items(user_id, status, scheduled_at)`
- `publication_jobs(status, scheduled_at)`
- `opportunities(category, score)`
- `opportunity_sources(opportunity_id)`
- `research_jobs(user_id, status, created_at)`
- `ai_runs(user_id, created_at)`
- `analytics_snapshots(social_account_id, metric_date)`
- `audit_events(user_id, created_at)`

## Future multi-tenant extension

Introduce `workspaces`, `workspace_members`, and `workspace_id` on tenant-owned tables without redesigning domain semantics. Personal accounts can initially map one user to one default workspace.

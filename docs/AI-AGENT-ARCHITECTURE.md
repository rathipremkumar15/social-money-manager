# AI Agent Architecture

## Goal

Build a controlled, tool-using AI system that can research opportunities, create content, analyze performance and coordinate authorized actions without turning into an unrestricted autonomous actor.

## Architecture

```text
User Request
    |
    v
AI Gateway
    |
    v
Planner / Orchestrator
    |
    +----> Context Manager
    |
    +----> Policy & Permission Guard
    |
    +----> Agent Registry
               |
       +-------+--------+---------+---------+
       v       v        v         v         v
    Research  Money   Content   Social   Analytics
     Agent    Agent    Agent     Agent     Agent
       |       |        |         |         |
       +-------+--------+---------+---------+
                       |
                    Tool Layer
                       |
        +--------------+----------------+
        |              |                |
      Search       Social APIs       Media APIs
        |              |                |
        +--------------+----------------+
                       |
                 Evidence / Results
                       |
                Approval / Policy
                       |
                     User
```

## Agents

### Orchestrator
Breaks a user objective into bounded tasks, selects agents/tools, tracks task state and composes results.

### Research Agent
Finds sources, extracts evidence, evaluates relevance and returns cited research.

### Money Agent
Transforms research into opportunity candidates, estimates requirements/cost/risk, scores them and clearly separates evidence from assumptions.

### Content Agent
Creates hooks, scripts, captions, content plans and variants based on user goals and available platform constraints.

### Social Agent
Reads permitted account data and prepares or executes platform actions only within granted scopes and policy rules.

### Media Agent
Coordinates image/video/animation generation providers and tracks asynchronous generation jobs.

### Analytics Agent
Analyzes social metrics and generates evidence-backed performance insights.

### Privacy Agent
Scans content and outbound payloads for PII, secrets and other privacy risks before external transmission/publication.

## Tool contract

Every tool should declare:

- name and version
- input schema
- output schema
- required permission/scope
- risk level
- side-effect classification
- timeout/retry policy
- audit requirements

Tools are divided into:

**READ** — search, fetch analytics, inspect account metadata.

**WRITE** — create drafts, schedule content, update settings.

**EXTERNAL SIDE EFFECT** — publish, send, delete, purchase, change account state.

External side effects require explicit policy authorization and, initially, user approval.

## Memory model

Use three layers:

1. **Task context** — temporary information for one agent task.
2. **User preferences** — durable user-approved settings and goals.
3. **Evidence store** — research sources and generated conclusions with timestamps.

Do not treat model output as authoritative memory. Store important facts with provenance.

## Prompt-injection defense

External web pages, social posts, comments and documents are untrusted data. They must never be allowed to redefine system instructions or permissions. Tool arguments are validated independently of model output.

## Reliability

Agent tasks are durable and resumable. Each tool call receives a correlation ID. Failed calls use bounded retries with exponential backoff. Irreversible actions are idempotent where the provider supports it.

## Cost controls

Track model/provider, tokens, latency and estimated cost per AI run. Support per-user and system-level budgets. Prefer cheaper models for classification/routing and stronger models for complex planning/reasoning.

## Human-in-the-loop policy

Default:

- Research: automatic
- Draft creation: automatic
- Privacy checks: automatic
- Scheduling: approval by default
- Publishing: approval by default
- Account/security changes: approval required
- Financial purchases or commitments: approval required

These policies can later become configurable after the safety system is mature.

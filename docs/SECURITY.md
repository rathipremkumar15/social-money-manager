# Security & Privacy Baseline

## Core rules

1. Never commit secrets, API keys, OAuth tokens, passwords, or private credentials.
2. Store social access tokens encrypted at rest.
3. Use least-privilege OAuth scopes.
4. Separate authentication, authorization, and integration credentials.
5. Log security events without logging sensitive values.
6. Treat user-generated content as untrusted input.
7. Validate and sanitize all external API data.
8. Use rate limits for authentication, AI, research, and publishing endpoints.
9. Require explicit user approval for high-impact or irreversible actions until a policy engine establishes safe automation rules.
10. Provide account disconnect/revocation flows.

## Privacy Guard

Before publication or external transmission, content should be checked for:

- Personal phone numbers
- Email addresses
- Physical addresses
- Government/identity numbers
- Financial/account information
- Passwords and secrets
- API keys and tokens
- Private conversations or documents
- Unintended location information

Detection should produce a structured finding with severity and recommended action. The system should support blocking or requiring approval rather than silently publishing risky content.

## Data minimization

Only collect data required for a defined product capability. Retention and deletion policies will be implemented before public launch.

## Threat model

Phase 0 will explicitly consider account takeover, token theft, prompt injection, malicious external content, data leakage, unauthorized publishing, abusive automation, and cross-user data access.

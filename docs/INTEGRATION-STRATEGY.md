# Integration Strategy

## Principles

1. Integrations are isolated behind provider adapters.
2. Domain code depends on internal interfaces, not vendor SDK models.
3. OAuth credentials are encrypted and scoped to the minimum required permissions.
4. Every integration declares supported capabilities and API version.
5. Provider failures must not corrupt domain state.
6. Webhooks are signature-verified and idempotent.
7. All asynchronous provider work is represented by durable jobs.

## Integration categories

### AI providers
Internal interface:

```text
TextGenerationProvider
EmbeddingProvider
ImageGenerationProvider
VideoGenerationProvider
SpeechProvider
```

The application should be able to switch providers without changing domain logic. Provider/model selection will later consider quality, latency, availability and cost.

### Social platforms
Internal interface concepts:

```text
SocialProvider
AccountConnector
Publisher
AnalyticsProvider
CapabilityProvider
```

Initial targets: Instagram/Facebook, YouTube, TikTok, LinkedIn and X, subject to each platform's official API permissions and policies.

Each adapter handles OAuth, token refresh, media requirements, publishing, analytics and provider-specific errors.

### Research/search
Use a provider abstraction for web/search retrieval. Store source metadata and evidence rather than relying on transient model knowledge.

### Media/storage
Generated media is tracked internally and stored through an object-storage abstraction. Provider job IDs are separate from internal asset IDs.

### Payments
Use a payment-provider abstraction with customer, subscription, checkout and webhook operations. Subscription state is controlled by verified webhook events rather than frontend claims.

### Notifications
Provider abstraction for email and in-app notifications. SMS/push can be added later.

## Provider adapter structure

```text
integrations/
  ai/
    provider-api/
    adapters/
  social/
    instagram/
    youtube/
    tiktok/
    linkedin/
    x/
  research/
  media/
  payments/
  notifications/
```

## Secrets

Development secrets live in local environment configuration and are excluded from Git. Production secrets must use the deployment platform's secret management facility. Never put provider tokens in database logs, source files, URLs or normal API responses.

## Failure strategy

- Timeouts on all external calls
- Bounded retries only for safe/idempotent operations
- Circuit breaking for repeatedly failing providers
- Dead-letter handling for unrecoverable asynchronous jobs
- Provider-specific error mapping to stable internal error codes
- Reconciliation jobs for eventual consistency

## API capability principle

The UI should not assume that every social platform supports the same actions. Capabilities are discovered/stored per integration and the application exposes only supported operations.

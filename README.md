# Mock Debt Project

A sample Java project with self-admitted technical debt (SATD) comments referencing real Jira tickets.

## Purpose

This repository is used to validate the **Debt Detection & Test Generation Pipeline** with realistic code examples.

## Project Structure

- **LoginService**: MD5 hashing deprecation (DEBT2TEST-1)
- **PaymentProcessor**: Hardcoded configuration (DEBT2TEST-2)
- **ConnectionPool**: Race condition in concurrency (DEBT2TEST-3)
- **CacheManager**: Unbounded cache issue (DEBT2TEST-4)
- **AuditLogger**: Sensitive data logged in plaintext (DEBT2TEST-6)
- **OrderRepository**: N+1 query pattern (DEBT2TEST-7)

## Jira Project Reference

Debt tickets tracked at: **https://debt2test.atlassian.net**

Project Key: **DEBT2TEST**

Example ticket: https://debt2test.atlassian.net/browse/DEBT2TEST-1

## Building

```bash
mvn clean install
```

## Testing

```bash
mvn test
```

## Debt Comments

All debt comments follow the pattern:
```
// TODO|FIXME|BUG: DEBT2TEST-N
// Explanation of the debt
```

These comments are parsed by the debt detection pipeline and correlated with Jira tickets.

## Integration

This repository is cloned by the debt pipeline and analyzed with:
1. SATD extraction from comments
2. Jira ticket lookup (when token available)
3. Context enrichment from external issues
4. Test case generation with context awareness

## License

MIT

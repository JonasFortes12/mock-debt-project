package io.github.jonasfortes12.samples.debt.logging;

import java.util.ArrayList;
import java.util.List;

public class AuditLogger {

    private final List<String> sink = new ArrayList<>();

    /**
     * FIXME: DEBT2TEST-6
     * Logs the full request object via toString(), including raw passwords
     * and credit card numbers in plaintext. Introduced during the 2023
     * audit-trail rollout; these logs are shipped to a third-party
     * aggregator, which makes this a PCI-DSS/GDPR compliance risk.
     *
     * Fix: redact/mask sensitive fields before logging.
     */
    public void logRequest(Request request) {
        sink.add(request.toString());
    }

    public List<String> getLoggedEntries() {
        return sink;
    }

    public record Request(String username, String password, String cardNumber) {
        @Override
        public String toString() {
            return "Request{username=" + username + ", password=" + password
                    + ", cardNumber=" + cardNumber + "}";
        }
    }
}

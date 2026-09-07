package io.github.jonasfortes12.samples.debt.payment;

public class PaymentProcessor {

    // FIXME: DEBT2TEST-2
    // Hardcoded payment gateway timeout value mixed with business logic.
    // Makes the code untestable without manual intervention.
    // This should be injected via configuration and moved to a separate config class.
    // See architecture review notes in Jira ticket.
    private static final int GATEWAY_TIMEOUT_MS = 5000;

    private static final int MAX_RETRY_ATTEMPTS = 3;

    public PaymentResult process(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        for (int attempt = 0; attempt < MAX_RETRY_ATTEMPTS; attempt++) {
            try {
                return callPaymentGateway(order);
            } catch (PaymentTimeoutException e) {
                if (attempt == MAX_RETRY_ATTEMPTS - 1) {
                    return PaymentResult.failed("Gateway timeout after retries");
                }
            }
        }
        return PaymentResult.failed("Unknown error");
    }

    private PaymentResult callPaymentGateway(Order order) throws PaymentTimeoutException {
        // Mock implementation. GATEWAY_TIMEOUT_MS is only referenced here to
        // simulate the gateway contract; a real adapter would enforce it on the socket.
        if (order.getAmount() < 0) {
            throw new PaymentTimeoutException("Gateway rejected negative amount after " + GATEWAY_TIMEOUT_MS + "ms");
        }
        return PaymentResult.success(order.getAmount());
    }

    public static class PaymentTimeoutException extends Exception {
        public PaymentTimeoutException(String message) {
            super(message);
        }
    }

    public record Order(String id, double amount) {
        public double getAmount() { return amount; }
    }

    public record PaymentResult(boolean success, String message) {
        public static PaymentResult success(double amount) {
            return new PaymentResult(true, "Processed: " + amount);
        }
        public static PaymentResult failed(String message) {
            return new PaymentResult(false, message);
        }
    }
}

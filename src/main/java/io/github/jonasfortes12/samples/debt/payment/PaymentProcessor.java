package io.github.jonasfortes12.samples.debt.payment;

public class PaymentProcessor {

    // TODO DEBT2TEST-2: configurable timeout is not implemented yet; the gateway timeout is
    // hardcoded here instead of being injected via configuration.
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

    public static final class Order {
        private final String id;
        private final double amount;

        public Order(String id, double amount) {
            this.id = id;
            this.amount = amount;
        }

        public String id() { return id; }
        public double amount() { return amount; }
        public double getAmount() { return amount; }
    }

    public static final class PaymentResult {
        private final boolean success;
        private final String message;

        public PaymentResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean success() { return success; }
        public String message() { return message; }

        public static PaymentResult success(double amount) {
            return new PaymentResult(true, "Processed: " + amount);
        }
        public static PaymentResult failed(String message) {
            return new PaymentResult(false, message);
        }
    }
}

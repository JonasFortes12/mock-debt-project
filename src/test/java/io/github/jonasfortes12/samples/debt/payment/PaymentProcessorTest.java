package io.github.jonasfortes12.samples.debt.payment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentProcessorTest {

    private final PaymentProcessor paymentProcessor = new PaymentProcessor();

    @Test
    void shouldProcessValidOrderSuccessfully() {
        PaymentProcessor.Order order = new PaymentProcessor.Order("order-1", 100.0);
        PaymentProcessor.PaymentResult result = paymentProcessor.process(order);
        assertTrue(result.success());
    }

    @Test
    void shouldThrowExceptionForNullOrder() {
        assertThrows(IllegalArgumentException.class, () -> paymentProcessor.process(null));
    }

    // TODO DEBT2TEST-2: add tests for the retry path; it is untested directly because the
    // hardcoded gateway timeout cannot be injected.
    @Test
    void shouldFailOnNegativeAmountAfterRetries() {
        PaymentProcessor.Order order = new PaymentProcessor.Order("order-2", -50.0);
        PaymentProcessor.PaymentResult result = paymentProcessor.process(order);
        assertFalse(result.success());
    }
}

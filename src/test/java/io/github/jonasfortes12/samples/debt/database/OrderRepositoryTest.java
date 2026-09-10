package io.github.jonasfortes12.samples.debt.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class OrderRepositoryTest {

    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository = new OrderRepository();
    }

    @Test
    void shouldReturnOrdersForEachCustomer() {
        List<String> orders = orderRepository.findOrdersForCustomers(List.of("c1", "c2"));
        assertEquals(4, orders.size());
    }

    // DEBT2TEST-7: one query is issued per customer ID instead of a single
    // batched lookup, so the query count scales linearly with input size.
    @Test
    void currentlyIssuesOneQueryPerCustomer() {
        orderRepository.findOrdersForCustomers(List.of("c1", "c2", "c3"));
        assertEquals(3, orderRepository.getQueryCount());
    }
}

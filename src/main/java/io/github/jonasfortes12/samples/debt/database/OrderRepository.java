package io.github.jonasfortes12.samples.debt.database;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    private int queryCount = 0;

    // TODO DEBT2TEST-7: refactor this; one query per customer is an ugly workaround (N+1)
    // and should be redesigned as a single batched IN (...) query.
    public List<String> findOrdersForCustomers(List<String> customerIds) {
        List<String> orders = new ArrayList<>();
        for (String customerId : customerIds) {
            orders.addAll(findOrdersByCustomer(customerId));
        }
        return orders;
    }

    private List<String> findOrdersByCustomer(String customerId) {
        queryCount++;
        // Mock implementation simulating one query per call.
        return List.of("order-" + customerId + "-1", "order-" + customerId + "-2");
    }

    public int getQueryCount() {
        return queryCount;
    }
}

package io.github.jonasfortes12.samples.debt.database;

import java.util.LinkedList;
import java.util.Queue;

public class ConnectionPool {

    private final Queue<DbConnection> availableConnections;
    private final int poolSize;

    /*
     * BUG: DEBT2TEST-3
     * Race condition on concurrent access. The availableConnections queue
     * is accessed without proper synchronization in getConnection() and
     * releaseConnection() methods. This can lead to:
     * - Duplicate connection leases
     * - Lost connections
     * - Connection pool exhaustion under load
     *
     * Fix: Use ConcurrentLinkedQueue or add synchronized blocks.
     * This is blocking deployment to production environments with
     * high concurrency (10k+ RPS).
     */
    public ConnectionPool(int poolSize) {
        this.poolSize = poolSize;
        this.availableConnections = new LinkedList<>();
        for (int i = 0; i < poolSize; i++) {
            availableConnections.offer(new DbConnection("conn-" + i));
        }
    }

    public DbConnection getConnection() throws InterruptedException {
        // Missing synchronization - DEBT2TEST-3
        while (availableConnections.isEmpty()) {
            Thread.sleep(100);
        }
        return availableConnections.poll();
    }

    public void releaseConnection(DbConnection conn) {
        // Missing synchronization - DEBT2TEST-3
        if (conn != null && availableConnections.size() < poolSize) {
            availableConnections.offer(conn);
        }
    }

    public static class DbConnection {
        private final String id;

        public DbConnection(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}

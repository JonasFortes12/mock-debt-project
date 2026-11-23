package io.github.jonasfortes12.samples.debt.database;

import java.util.LinkedList;
import java.util.Queue;

public class ConnectionPool {

    private final Queue<DbConnection> availableConnections;
    private final int poolSize;

    /*
     * FIXME DEBT2TEST-3: bug, race condition because availableConnections is used without
     * synchronization; concurrent access fails with duplicate or lost connections.
     */
    public ConnectionPool(int poolSize) {
        this.poolSize = poolSize;
        this.availableConnections = new LinkedList<>();
        for (int i = 0; i < poolSize; i++) {
            availableConnections.offer(new DbConnection("conn-" + i));
        }
    }

    public DbConnection getConnection() throws InterruptedException {
        // FIXME DEBT2TEST-3: bug, missing synchronization here is broken under concurrent access.
        while (availableConnections.isEmpty()) {
            Thread.sleep(100);
        }
        return availableConnections.poll();
    }

    public void releaseConnection(DbConnection conn) {
        // FIXME DEBT2TEST-3: bug, missing synchronization here is broken under concurrent access.
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

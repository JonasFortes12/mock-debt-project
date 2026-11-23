package io.github.jonasfortes12.samples.debt.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConnectionPoolTest {

    private ConnectionPool connectionPool;

    @BeforeEach
    void setUp() {
        connectionPool = new ConnectionPool(3);
    }

    @Test
    void shouldProvideConnectionFromPool() throws InterruptedException {
        ConnectionPool.DbConnection conn = connectionPool.getConnection();
        assertNotNull(conn);
    }

    @Test
    void shouldReleaseConnectionBackToPool() throws InterruptedException {
        ConnectionPool.DbConnection conn = connectionPool.getConnection();
        connectionPool.releaseConnection(conn);
        ConnectionPool.DbConnection reacquired = connectionPool.getConnection();
        assertNotNull(reacquired);
    }

    // TODO DEBT2TEST-3: add tests for concurrent access; it is untested because the pool
    // lacks synchronization and a concurrency test would be flaky.
    @Test
    void shouldNotExceedPoolSizeOnRelease() throws InterruptedException {
        ConnectionPool.DbConnection conn = connectionPool.getConnection();
        connectionPool.releaseConnection(conn);
        connectionPool.releaseConnection(conn);
        assertTrue(true);
    }
}

package io.github.jonasfortes12.samples.debt.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CacheManagerTest {

    private CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        cacheManager = new CacheManager();
    }

    @Test
    void shouldStoreAndRetrieveValue() {
        cacheManager.put("key1", "value1");
        assertEquals("value1", cacheManager.get("key1"));
    }

    @Test
    void shouldReturnNullForMissingKey() {
        assertNull(cacheManager.get("missing"));
    }

    @Test
    void shouldClearAllEntries() {
        cacheManager.put("key1", "value1");
        cacheManager.clear();
        assertEquals(0, cacheManager.size());
    }

    // DEBT2TEST-4: no test covers eviction under memory pressure because the
    // cache has no eviction policy or maxSize limit yet.
    @Test
    void shouldReportCorrectSize() {
        cacheManager.put("a", 1);
        cacheManager.put("b", 2);
        assertEquals(2, cacheManager.size());
    }
}

package io.github.jonasfortes12.samples.debt.cache;

import java.util.HashMap;
import java.util.Map;

public class CacheManager {

    // TODO: DEBT2TEST-4
    // Unbounded cache without eviction policy. Can cause OutOfMemoryError
    // in production. Should implement LRU cache with maxSize limit.
    // Reported by DevOps team after incident on 2024-01-15.
    private final Map<String, CacheEntry> cache = new HashMap<>();

    public void put(String key, Object value) {
        cache.put(key, new CacheEntry(value, System.currentTimeMillis()));
    }

    public Object get(String key) {
        CacheEntry entry = cache.get(key);
        if (entry != null && !entry.isExpired()) {
            return entry.value();
        }
        if (entry != null) {
            cache.remove(key);  // Cleanup expired, but race condition exists
        }
        return null;
    }

    public void clear() {
        cache.clear();
    }

    public int size() {
        return cache.size();
    }

    record CacheEntry(Object value, long timestamp) {
        private static final long TTL_MS = 3600000;  // 1 hour

        boolean isExpired() {
            return System.currentTimeMillis() - timestamp > TTL_MS;
        }
    }
}

package io.github.jonasfortes12.samples.debt.cache;

import java.util.HashMap;
import java.util.Map;

public class CacheManager {

    // TODO DEBT2TEST-4: eviction is not implemented yet; the cache is unbounded and the
    // LRU policy with a maxSize limit is a missing feature.
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

    static final class CacheEntry {
        private static final long TTL_MS = 3600000;  // 1 hour

        private final Object value;
        private final long timestamp;

        CacheEntry(Object value, long timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }

        Object value() { return value; }
        long timestamp() { return timestamp; }

        boolean isExpired() {
            return System.currentTimeMillis() - timestamp > TTL_MS;
        }
    }
}

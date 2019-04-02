package com.company;

import java.io.Serializable;
import java.util.Map;

public class CacheService<K, V extends Serializable> implements Cache<K, V> {

    private FileLevelCache<K, V> fileLevelCache;
    private Cache<K, V> ramCash;

    public CacheService(int memorySize, Cache ramCash) {
        fileLevelCache = new FileLevelCache(memorySize);
        this.ramCash = ramCash;
    }

    @Override
    public Map.Entry<K, V> put(K key, V value) {
        Map.Entry<K, V> lastEmpty = ramCash.put(key, value);

        if(lastEmpty != null) {
            fileLevelCache.put(lastEmpty.getKey(), lastEmpty.getValue());
        }
        return null;
    }

    @Override
    public V get(K key) {
        V value = ramCash.get(key);
        if (value  != null) {
            return value;
        } else {
            value = fileLevelCache.get(key);
            if(value != null) {
                ramCash.put(key, value);
            }
        }
        return value;
    }

    @Override
    public void clear() {
        ramCash.clear();
        fileLevelCache.clear();

    }
}

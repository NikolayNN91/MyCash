package com.company;

import java.io.Serializable;
import java.util.Map;

public class CacheService<K, V extends Serializable> implements Cache<K, V> {

    private FileLevelCache<K, V> fileLevelCache;
    private Cache<K, V> ramCache;

    public CacheService(int memorySize, Cache ramCash) {
        fileLevelCache = new FileLevelCache(memorySize);
        this.ramCache = ramCash;
    }

    @Override
    public Map.Entry<K, V> put(K key, V value) {
        Map.Entry<K, V> lastEmpty = ramCache.put(key, value);

        if(lastEmpty != null) {
            fileLevelCache.put(lastEmpty.getKey(), lastEmpty.getValue());
        }
        return lastEmpty;
    }

    @Override
    public V get(K key) {
        V value = ramCache.get(key);
        if (value  != null) {
            return value;
        } else {
            value = fileLevelCache.get(key);
            if(value != null) {
                //todo - закинуть во 2-й уровень
                ramCache.put(key, value);
            }
        }
        return value;
    }

    @Override
    public void clear() {
        ramCache.clear();
        fileLevelCache.clear();

    }
}

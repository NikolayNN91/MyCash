package com.company;

import java.util.LinkedHashMap;
import java.util.Map;

public class CashLFU<K, V> {

    private final int MAX_SIZE;
    private Map<K, V> linkedHashMap;

    public CashLFU(int size) {
        MAX_SIZE = size;
        linkedHashMap = new LinkedHashMap<>(MAX_SIZE) {

            
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return linkedHashMap.size() > MAX_SIZE;
            }
        };

    }

    public void put(K key, V value) {
        System.out.println(key + ": " + value);
        for (Map.Entry<K, V> entry : linkedHashMap.entrySet()) {
            if (entry.getKey().equals(key)) {
                linkedHashMap.remove(entry.getKey(), entry.getValue());
                linkedHashMap.put(key, value);

            } else {
                linkedHashMap.put(key, value);
            }
        }

    }

    public V get(K key) {

        return linkedHashMap.get(key);
    }

    public void clear() {

        linkedHashMap.clear();
    }
}

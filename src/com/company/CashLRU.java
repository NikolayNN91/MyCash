package com.company;


import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class CashLRU<K, V> implements Cash<K, V>{

    private final int MAX_SIZE;
    private Map<K, V> linkedHashMap;

    public CashLRU(int size) {
        MAX_SIZE = size;
        linkedHashMap = new LinkedHashMap<>(MAX_SIZE) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return linkedHashMap.size() > MAX_SIZE;
            }
        };

    }

    @Override
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

    @Override
    public V get(K key) {

        V value = linkedHashMap.get(key);

        if(value != null) {

            linkedHashMap.remove(key, value);
            linkedHashMap.put(key, value);
        }

        return value;
    }

    @Override
    public void clear() {

        linkedHashMap.clear();
    }


}

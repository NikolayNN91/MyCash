package com.company;


import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class CashLRU<K, V> implements Cash<K, V> {

    private final int MAX_SIZE;
    private Map<K, V> linkedHashMap;

    public CashLRU(int size) {
        MAX_SIZE = size;
        linkedHashMap = new LinkedHashMap<K, V>(MAX_SIZE) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return linkedHashMap.size() > MAX_SIZE;
            }
        };

    }

    //возвращает последний элемент, который был вытеснен из Мар при добавлении нового элемента, если элемент не удалялся возвращает null
    @Override
    public Map.Entry<K, V> put(K key, V value) {
        Map.Entry<K, V> lastEntry = null;
        System.out.println(key + ": " + value);

        for (Map.Entry<K, V> entry : linkedHashMap.entrySet()) {
            if (entry.getKey().equals(key)) {
                linkedHashMap.remove(entry.getKey(), entry.getValue());
            }
            if (linkedHashMap.size() > MAX_SIZE) {
                for (Map.Entry<K, V> removeEntry : linkedHashMap.entrySet()) {
                    lastEntry = removeEntry;
                }
            }
            linkedHashMap.put(key, value);
            return lastEntry;

        }
        return null;

    }

    @Override
    public V get(K key) {

        V value = linkedHashMap.get(key);

        if (value != null) {

            linkedHashMap.remove(key, value);
            put(key, value);
        }

        return value;
    }

    @Override
    public void clear() {

        linkedHashMap.clear();
    }


}

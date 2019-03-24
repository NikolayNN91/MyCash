package com.company;

import java.util.Map;

public class CashService<K, V> implements Cash<K, V> {

    private MemoryCash memoryCash;
    private Cash ramCash;
    private Map.Entry lastEmpty;

    public CashService(int memorySize, Cash ramCash) {
        memoryCash = new MemoryCash(memorySize);
        this.ramCash = ramCash;

    }


    @Override
    public Map.Entry<K, V> put(K key, V value) {
        lastEmpty = ramCash.put(key, value);
        if(lastEmpty != null) {
            memoryCash.put(lastEmpty.getKey(), lastEmpty.getValue());
        }
        return null;
    }

    @Override
    public V get(K key) {
        if (ramCash.get(key) != null) {
            return (V) ramCash.get(key);
        } else {
            return (V) memoryCash.get(key);
        }
    }

    @Override
    public void clear() {
        ramCash.clear();
        memoryCash.clear();

    }
}

package com.company;

import java.io.Serializable;
import java.util.Map;

public class CashService<K, V extends Serializable> implements Cash<K, V> {

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
            //todo  -убрать каст
            memoryCash.put(lastEmpty.getKey(), (V)lastEmpty.getValue());
        }
        return null;
    }

    @Override
    public V get(K key) {
        V value = (V) ramCash.get(key);
        if (value  != null) {
            return value;
        } else {
            value = (V) memoryCash.get(key);
            if(value != null) {
                ramCash.put(key, value);
            }
        }
        return value;
    }

    @Override
    public void clear() {
        ramCash.clear();
        memoryCash.clear();

    }
}

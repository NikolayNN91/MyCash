package com.company;

import java.util.Map;

public interface Cash<K, V> {

    Map.Entry<K, V> put(K key, V value);

    V get(K key);

    void clear();
}

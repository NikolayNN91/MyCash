package com.company;

import java.io.Serializable;
import java.util.Map;

public interface Cache<K, V extends Serializable> {

    Map.Entry<K, V> put(K key, V value);

    V get(K key);

    void clear();
}

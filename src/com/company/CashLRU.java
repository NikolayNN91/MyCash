package com.company;


import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class CashLRU<K, V extends Serializable> implements Cash<K, V> {

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

    //возвращает последний элемент, который был вытеснен из Мар при добавлении нового элемента, если элемент не удалялся возвращает null

    @Override
    public Map.Entry<K, V> put(K key, V value) {
        Map.Entry<K, V> lastEntry = null;
        System.out.println("ram put");
        System.out.println(key + ": " + value);

        //при добавлении сущности с ключом, который уже содержится в коллекции, удаляет старую запись и добавляет новую в начало списка
        Iterator iterator = linkedHashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            //todo -убрать каст
            Map.Entry<K, V> entry = (Map.Entry<K, V>) iterator.next();
            if (entry.getKey().equals(key)) {
                System.out.println("remove exist element");
                linkedHashMap.remove(entry);
                break;
            }

        }


        if (linkedHashMap.size() >= MAX_SIZE) {
            for (Map.Entry<K, V> removeEntry : linkedHashMap.entrySet()) {
                lastEntry = removeEntry;
            }
        }
        linkedHashMap.put(key, value);
        return lastEntry;

    }

    @Override
    public V get(K key) {

        V value = linkedHashMap.get(key);

        put(key, value);

        return value;
    }

    @Override
    public void clear() {

        linkedHashMap.clear();
    }


}

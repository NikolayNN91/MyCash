package com.company;


import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class CacheLRU<K, V extends Serializable> implements Cache<K, V> {

    private final int MAX_SIZE;
    private Map<K, V> linkedHashMap;

    public CacheLRU(int size) {
        MAX_SIZE = size;

        linkedHashMap = new LinkedHashMap<>(MAX_SIZE) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return linkedHashMap.size() > MAX_SIZE;
            }
        };
    }

    /**
     * возвращает последний элемент, который был вытеснен из Мар при добавлении нового элемента,
     * если элемент не удалялся возвращает null
     */
    @Override
    public Map.Entry<K, V> put(K key, V value) {
        Map.Entry<K, V> lastEntry = null;

        System.out.println("Put in ram: key=" + key + ", value=" + value);

        /**   при добавлении сущности с ключом, который уже содержится в коллекции,
         *   удаляет старую запись и добавляет новую в начало списка
         */
//        Iterator<Map.Entry<K, V>> iterator = linkedHashMap.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<K, V> entry = iterator.next();
//            if (entry.getKey().equals(key)) {
//                linkedHashMap.remove(entry.getKey());
//                break;
//            }
//        }
        linkedHashMap.remove(key);


        if (linkedHashMap.size() >= MAX_SIZE) {
//            for (Map.Entry<K, V> removeEntry : linkedHashMap.entrySet()) {
//                lastEntry = removeEntry;
//            }
            lastEntry=linkedHashMap.entrySet().iterator().next();
            System.out.println("Remove last element from ram: key=" + lastEntry.getKey() + ", value=" + lastEntry.getValue() + ", size=" + linkedHashMap.size());
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

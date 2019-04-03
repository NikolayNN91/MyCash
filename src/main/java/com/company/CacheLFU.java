package com.company;

import java.io.Serializable;
import java.util.*;

public class CacheLFU<K, V extends Serializable> implements Cache<K, V> {

    private final int MAX_SIZE;
    private Map<K, Node<V>> linkedHashMap;

    public CacheLFU(int size) {

        MAX_SIZE = size;
        linkedHashMap = new LinkedHashMap<>(MAX_SIZE);
    }

    /**
     * возвращает элемент, который был вытеснен при добавлении нового элемента,
     * либо null если элементы не удалялись
     */
    @Override
    public Map.Entry<K, V> put(K key, V value) {
        Map.Entry<K, V> removeEntry = null;
        Node<V> node = new Node<>(value, System.currentTimeMillis());

        if (linkedHashMap.size() >= MAX_SIZE && !linkedHashMap.containsKey(key)) {
            removeEntry = remove();
            System.out.println("Remove last element from ram: key=" + removeEntry.getKey() + ", value=" + removeEntry.getValue() + ", size=" + linkedHashMap.size());
        }

        linkedHashMap.put(key, node);
        System.out.println("Element is added: key=" + key + ", value=" + node.getValue());
        return removeEntry;
    }

    /**
     * удаляет и возвращает элемент с наименьшим числом вызовов,
     * либо null если элементы не удалялись
     */
    private Map.Entry<K, V> remove() {

        long min_count = linkedHashMap.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .map(Node::getCount)
                .min(Comparator.naturalOrder())
                .orElse(-1L);

        Map.Entry<K, Node<V>> removeEntry = linkedHashMap.entrySet()
                .stream()
                .filter(e -> e.getValue() != null)
                .filter(n -> n.getValue().getCount() <= min_count)
                .min(Comparator.comparing(n -> n.getValue().getDate()))
                .orElse(null);

        if (removeEntry != null) {
            Node<V> node = removeEntry.getValue();
            K key = removeEntry.getKey();

            System.out.println("Element is removed: key=" + key + ", value=" + node.getValue() + ", count=" + node.getCount() + ", date=" + node.getDate());

            linkedHashMap.remove(key, node);
        }

        return Optional.ofNullable(removeEntry)
                .map(x -> new AbstractMap.SimpleEntry<>(x.getKey(), x.getValue().getValue()))
                .orElse(null);
    }

    @Override
    public V get(K key) {

        Node<V> node = linkedHashMap.get(key);

        if (node == null) {
            return null;
        } else {
            node.incrementCount();
            return node.getValue();
        }
    }

    @Override
    public void clear() {
        linkedHashMap.clear();
    }

    /**
     * класс инкапсулирует значение, частоту вызова и дату вызова хранимого элемента
     */
    private class Node<V> implements Serializable {

        private final V value;
        private long date;
        private long count;

        public Node(V value, long date) {
            this.value = value;
            this.count = 1;
            this.date = date;
        }

        public V getValue() {
            return value;
        }

        public long getCount() {
            return count;
        }

        public long getDate() {
            return date;
        }

        public Node incrementCount() {
            count++;
            return this;
        }
    }
}

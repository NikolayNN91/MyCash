package com.company;

import java.util.*;

public class CashARC<K, V> implements Cash<K, V>{

    private final int MAX_SIZE;
    private Map<K, Node<V>> linkedHashMap;

    public CashARC(int size) {

        MAX_SIZE = size;
        linkedHashMap = new LinkedHashMap<>(MAX_SIZE);

    }

    @Override
    public void put(K key, V value) {

        Node<V> node = new Node<>(value, System.currentTimeMillis());

        if(linkedHashMap.size() >= MAX_SIZE) {
            System.out.println("size : " + linkedHashMap.size());
            remove();
        }
            linkedHashMap.put(key, node);
        System.out.println("Элемент добавлен: " + key + "/ " + node.getValue());
    }

    private void remove() {

        Node<V> node = null;
        K key = null;
        long minDate = -1;
        long minCount = -1;

        for(Map.Entry<K, Node<V>> entry : linkedHashMap.entrySet()) {
            Node<V> currentNode = entry.getValue();
            K currentKey = entry.getKey();
            if((currentNode.getCount() < minCount || minCount < 0) || ((currentNode.getCount() == minCount & currentNode.getDate() < minDate) || minDate < 0)) {
                node = currentNode;
                key = currentKey;
                minCount = currentNode.getCount();
                minDate = currentNode.getDate();

            }

        }
        if(node != null) {
            System.out.println("Элемент remove: " + key + "/ " + node.getValue() + " count: " + node.getCount() + " date: " + node.getDate());
            linkedHashMap.remove(key, node);
        }

    }

    @Override
    public V get(K key) {

        Node<V> node = linkedHashMap.get(key);

        if(node == null) {

          //  put(key, node); получить ключ и значение и вызвать put(key, values)
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

    //class stores invocation date, invocation frequency and values
    private class Node<V> {

        private final V value;
        private long date;
        private long count;

        public Node (V value, long date) {
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

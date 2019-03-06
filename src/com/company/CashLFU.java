package com.company;

import java.util.*;

public class CashLFU<K, V> implements Cash<K, V>{

    private final int MAX_SIZE;
    private Map<K, Node<V>> linkedHashMap;

    public CashLFU(int size) {

        MAX_SIZE = size;
        linkedHashMap = new LinkedHashMap<>(MAX_SIZE);

    }

    @Override
    public void put(K key, V value) {

        Node<V> node = new Node<>(value);

        if(linkedHashMap.size() > MAX_SIZE) {
            remove();
        }
            linkedHashMap.put(key, node);
    }

    private void remove() {
        Node<V> node;
        K key;
        Date maxDate;
        long minCount;

        for(Map.Entry<K, Node<V>> entry : linkedHashMap.entrySet()) {
            Node<V> currentNode = entry.getValue();
            K currentKey = entry.getKey();
            if(currentNode.getCount() < minCount && currentNode.getDate() > maxDate) {
                node = currentNode;
                key = currentKey;
                minCount = currentNode.getCount();
                maxDate = currentNode.getDate();

            }

        }

        for(Map.Entry<K, Node<V>> entry : linkedHashMap.entrySet()) {
            node = entry.getValue();
            if(node.count == minCount) {
                list.add(node);
            }

        }

        for(Node<V> minCountNode : list) {
            maxDate = minCountNode.getDate();
            if(minCountNode.getDate())
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
        private Date date;
        private long count;

        public Node (V value) {
            this.value = value;
            this.count = 1;
        }

        public V getValue() {
            return value;
        }

        public long getCount() {
            return count;
        }

        public Date getDate() {
            return date;
        }

        public Node incrementCount() {
            count++;
            return this;
        }
    }
}

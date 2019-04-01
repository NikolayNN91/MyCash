package com.company;

import java.io.Serializable;
import java.util.*;

public class CashLFU<K, V extends Serializable> implements Cash<K, V>{

    private final int MAX_SIZE;
    private Map<K, Node<V>> linkedHashMap;

    public CashLFU(int size) {

        MAX_SIZE = size;
        linkedHashMap = new LinkedHashMap<>(MAX_SIZE);

    }

    //возвращает элемент, который был вытеснен при добавлении нового элемента, либо null если элементы не удалялись
    @Override
    public Map.Entry<K, V> put(K key, V value) {
        Map.Entry<K, V> removeEntry = null;
        Node<V> node = new Node<>(value, System.currentTimeMillis());

        if(linkedHashMap.size() >= MAX_SIZE) {
            System.out.println("size : " + linkedHashMap.size());
            removeEntry = remove();
        }
            linkedHashMap.put(key, node);
        System.out.println("Элемент добавлен: " + key + "/ " + node.getValue());
        return removeEntry;
    }

    //удаляет и возвращает элемент с наименьшим числом вызовов
    private Map.Entry<K, V> remove() {

//        Node<V> node = null;
//        Map.Entry<K, V> removeEntry = null;
//        K key = null;
//        long minDate = -1;
//        long minCount = -1;

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



//        for(Map.Entry<K, Node<V>> entry : linkedHashMap.entrySet()) {
//            Node<V> currentNode = entry.getValue();
//            K currentKey = entry.getKey();
//            if((currentNode.getCount() < minCount || minCount < 0) || ((currentNode.getCount() == minCount && currentNode.getDate() < minDate) || minDate < 0)) {
//                node = currentNode;
//                key = currentKey;
//                minCount = currentNode.getCount();
//                minDate = currentNode.getDate();
//
//            }

//        }


        if(removeEntry!= null) {
            Node<V> node = removeEntry.getValue();
            K key = removeEntry.getKey();

//            removeEntry = new AbstractMap.SimpleEntry<> (key, node.getValue());
            System.out.println("Элемент remove: " + key + "/ " + node.getValue() + " count: " + node.getCount() + " date: " + node.getDate());
            linkedHashMap.remove(key, node);
        }


        return Optional.ofNullable(removeEntry)
                .map(x -> new AbstractMap.SimpleEntry<> (x.getKey(), x.getValue().getValue()))
                .orElse(null);
//        return removeEntry;

    }

    @Override
    public V get(K key) {

        Node<V> node = linkedHashMap.get(key);

        if(node == null) {
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
    private class Node<V> implements Serializable{

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

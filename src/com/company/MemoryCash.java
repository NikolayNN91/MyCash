package com.company;

import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryCash<K, V extends Serializable> implements Cash<K, V> {

    private int MAX_SIZE;
    private Map<K, String> map;
    private String filePath;
    private FileRW fileRW;
    private File directory;

    public MemoryCash(int size) {
        MAX_SIZE = size;
        map = new LinkedHashMap<K, String>(MAX_SIZE) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, String> eldest) {
                return map.size() > MAX_SIZE;
            }
        };
        fileRW = new FileRW();
        directory = new File("resource");

    }

    @Override
    public Map.Entry<K, V> put(K key, V value) {

        System.out.println("memory put");
        System.out.println(key + ": " + value);

        Map.Entry<K, V> lastEntry;

        filePath = "resource" + UUID.randomUUID().toString() + ".txt";

        //удаляет элемент с идентичным ключом из коллекции (затем положим ключ в начало списка)
        Iterator iterator = map.entrySet().iterator();
        Map.Entry<K, V> entry;
        while(iterator.hasNext()) {
            //todo -убрать каст
            entry = (Map.Entry<K, V>) iterator.next();
            if (entry.getKey().equals(key)) {
                map.remove(entry.getKey(), entry.getValue());
                break;
            }
        }
        //если размер коллекции превышает максимум, запоминает последний элемент в качестве возвращаемого значения для записи в файл
        if(map.size() >= MAX_SIZE) {
            for(Map.Entry removeEntry : map.entrySet()) {
                lastEntry = removeEntry;
                fileRemove(lastEntry.getKey());
            }
        }
        map.put(key, filePath);
        fileRW.cashWrite(key, value, directory);

        return null;
    }

    @Override
    public V get(K key) {
        filePath = map.get(key);
        V value = (V) fileRW.cashRead(filePath);
        return value;
    }

    @Override
    public void clear() {
        File[] fileArray = directory.listFiles();
        for (File file : fileArray) {
            file.delete();
        }
    }

    public void fileRemove(K key) {
        String fileName = map.get(key);
        File[] fileArray = directory.listFiles();
        for (File file : fileArray) {
            if(file.getName().equals(fileName)) {
                file.delete();
            }
        }


    }
}

package com.company;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryCash<K, V> implements Cash<K, V> {

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
        directory = new File("resource\\");

    }

    @Override
    public Map.Entry<K, V> put(K key, V value) {

        filePath = "resource\\" + UUID.randomUUID().toString() + ".txt";
        for (Map.Entry<K, String> entry : map.entrySet()) {
            if (entry.getKey().equals(key)) {
                map.remove(entry.getKey(), entry.getValue());
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

    public void remove(K key) {
        String fileName = map.get(key);
        map.remove(key);
        File[] fileArray = directory.listFiles();
        for (File file : fileArray) {
            if(file.getName().equals(fileName)) {
                file.delete();
            }
        }


    }
}

package com.company;

import java.io.*;
import java.util.*;

public class FileLevelCache<K, V extends Serializable> implements Cache<K, V> {

    private int MAX_SIZE;
    private File directory;
    private String filePath;
    private Map<K, String> pathMap;

    public FileLevelCache(int size) {

        MAX_SIZE = size;
        directory = new File("resource");

        pathMap = new LinkedHashMap<>(MAX_SIZE) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, String> eldest) {
                return pathMap.size() > MAX_SIZE;
            }
        };
    }

    @Override
    public Map.Entry<K, V> put(K key, V value) {

        System.out.println("Put in file: key=" + key + ", value=" + value);

        Map.Entry<K, V> lastEntry;

        filePath = "resource" + UUID.randomUUID().toString() + ".txt";

        /**
         * удаляет элемент с идентичным ключом из коллекции
         * (затем положим ключ в начало списка)
         */
        Iterator<Map.Entry<K, String>> iterator = pathMap.entrySet().iterator();
        Map.Entry<K, String> entry;
        while (iterator.hasNext()) {
            entry = iterator.next();
            if (entry.getKey().equals(key)) {
                pathMap.remove(entry.getKey(), entry.getValue());
                break;
            }
        }

        /**
         * если размер коллекции превышает максимум,
         * запоминает последний элемент в качестве возвращаемого значения для записи в файл
         */
        if (pathMap.size() >= MAX_SIZE) {
            for (Map.Entry removeEntry : pathMap.entrySet()) {
                lastEntry = removeEntry;
                fileRemove(lastEntry.getKey());
            }
        }

        pathMap.put(key, filePath);
        cacheWrite(value, filePath);

        return null;
    }

    @Override
    public V get(K key) {
        filePath = pathMap.get(key);
        V value = cacheRead(filePath);
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
        String fileName = pathMap.get(key);
        File[] fileArray = directory.listFiles();
        for (File file : fileArray) {
            if (file.getName().equals(fileName)) {
                file.delete();
            }
        }
    }

    private void cacheWrite(V value, String filePath) {

        if (!directory.exists()) {
            directory.mkdir();
        }

        String path = filePath;

        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
             ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)) {

            out.writeObject(value);
            out.flush();

        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }

    private V cacheRead(String fileName) {

//        if(fileName == null) {
//            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//        }

        try (FileInputStream fileInputStream = new FileInputStream(new File(fileName));
             ObjectInputStream in = new ObjectInputStream(fileInputStream)) {

            V value = (V) in.readObject();

            System.out.println("Read value from file: " + value);

            return value;

        } catch (FileNotFoundException e) {
            System.out.println("Cache-file not found");
            e.getStackTrace();
        } catch (IOException ex) {
            ex.getStackTrace();
        } catch (ClassNotFoundException exc) {
            exc.getStackTrace();
        }
        return null;
    }
}


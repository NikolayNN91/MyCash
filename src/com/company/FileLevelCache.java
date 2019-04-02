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

        Map.Entry<K, String> firstEntry;

        filePath = "resource/" + UUID.randomUUID().toString() + ".txt";

        /**
         * удаляет элемент с идентичным ключом из коллекции
         * (затем положим ключ в начало списка)
         */

//        Iterator<Map.Entry<K, String>> iterator = pathMap.entrySet().iterator();
//        Map.Entry<K, String> entry;
//        while (iterator.hasNext()) {
//            entry = iterator.next();
//
//            System.out.println("entry.getKey =" + entry.getKey());
//            System.out.println("key =" + key);
//            System.out.println();
//
//            if (entry.getKey().equals(key)) {
//                pathMap.remove(entry.getKey(), entry.getValue());
//                fileRemove(entry.getKey());
//                break;
//            }
//        }
        pathMap.remove(key);
        fileRemove(key);

        /**
         * если размер коллекции превышает максимум,
         * запоминает последний элемент и удаляет его из файловой директории,
         * затем последний элемент из Map будет также удален методом put()
         */
        if (pathMap.size() >= MAX_SIZE) {

            firstEntry = pathMap.entrySet().iterator().next();
            fileRemove(firstEntry.getKey());
            System.out.println("Remove last element from directory: key=" + firstEntry.getKey() + ", value=" + firstEntry.getValue() + ", size=" + pathMap.size());
        }

        pathMap.put(key, filePath);
        cacheWrite(value, filePath);

        return null;
    }

    /**
     * при получении элемента, удаляем его из Map и файловой директории,
     * т.к. в дальнейшем он будет добавлен в Cache 1-го уровня
     *
     * @param key
     * @return значение прочитанное из файла
     */
    @Override
    public V get(K key) {
        String fileName = pathMap.get(key);
        V value = cacheRead(fileName);

        if (fileName != null) {
            fileRemove(key);
            pathMap.remove(key);
        }
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
            if (("resource/" + file.getName()).equals(fileName)) {
                System.out.println("file is removed");
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

            System.out.println("Put in file: value=" + value);
            out.writeObject(value);
            out.flush();

        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }

    private V cacheRead(String fileName) {

        if (fileName == null) {
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        }

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


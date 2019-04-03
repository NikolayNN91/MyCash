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

        if (!directory.exists()) {
            directory.mkdir();
        }

        pathMap = new LinkedHashMap<K, String>(MAX_SIZE) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, String> eldest) {
                return pathMap.size() > MAX_SIZE;
            }
        };
    }

    @Override
    public Map.Entry<K, V> put(K key, V value) {

        Map.Entry<K, String> firstEntry;

        filePath = "resource/" + UUID.randomUUID().toString() + ".txt";

        /**
         * удаляет элемент с идентичным ключом из коллекции
         * (затем положим ключ в начало списка)
         */
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
        filePath = null;
        filePath = pathMap.get(key);
        V value = null;

        if (filePath != null) {
            value = cacheRead(filePath);
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
                System.out.println("File is removed from directory: key=" + key  + ", file_name=" + fileName);
                file.delete();
            }
        }
    }

    private void cacheWrite(V value, String filePath) {

        String path = filePath;

        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
             ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)) {

            System.out.println("Put in file: value=" + value  + ", file_name=" + path);
            out.writeObject(value);
            out.flush();

        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }

    private V cacheRead(String fileName) {

        try (FileInputStream fileInputStream = new FileInputStream(new File(fileName));
             ObjectInputStream in = new ObjectInputStream(fileInputStream)) {

            V value = (V) in.readObject();

            System.out.println("Read value from file: value=" + value + ", file_name=" + fileName);

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


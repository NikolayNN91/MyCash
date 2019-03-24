package com.company;

import java.io.*;
import java.util.Map;
import java.util.UUID;

public class FileRW<K, V> {

    private String path;

    public void cashWrite(K key, V value, File directory) {

        if (!directory.exists()) {
            directory.mkdir();
        }

        path = "resource\\" + UUID.randomUUID().toString() + ".txt";

        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
             ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)) {

            out.writeObject(value);
            out.flush();

        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }

    public V cashRead(String fileName) {

        try (FileInputStream fileInputStream = new FileInputStream(new File("fileName"));
             ObjectInputStream in = new ObjectInputStream(fileInputStream)) {

            V value = (V) in.readObject();
            return value;

        } catch (FileNotFoundException e) {
            //System.out.println("Cash-file not found");
            e.getStackTrace();
        } catch (IOException ex) {
            ex.getStackTrace();
        } catch (ClassNotFoundException exc) {
            exc.getStackTrace();
        }
        return null;
    }
}

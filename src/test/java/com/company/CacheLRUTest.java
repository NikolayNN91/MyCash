package com.company;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CacheLRUTest {

    private static CacheLRU cacheLRU;

    @BeforeAll
    public static void init() {
        cacheLRU = new CacheLRU(10);
    }

    @AfterEach
    public void end() {
        cacheLRU.clear();
    }

    @Test
    public void put() {

        Integer extend = 1;

        cacheLRU.put(1, 1);
        cacheLRU.put(2, 2);
        cacheLRU.put(3, 3);
        cacheLRU.put(4, 4);
        cacheLRU.put(5, 5);
        cacheLRU.put(6, 6);
        cacheLRU.put(7, 7);
        cacheLRU.put(8, 8);
        cacheLRU.put(9, 9);
        cacheLRU.put(10, 10);
        Map.Entry entry = cacheLRU.put(11, 11);

        assertEquals(extend, entry.getValue());


    }

    @Test
    void get() {

        cacheLRU.put(1, 1);
        cacheLRU.put(2, 2);
        cacheLRU.put(3, 3);
        cacheLRU.put(4, 4);
        cacheLRU.put(5, 5);
        cacheLRU.put(6, 6);
        cacheLRU.put(7, 7);
        cacheLRU.put(8, 8);
        cacheLRU.put(9, 9);
        cacheLRU.put(10, 10);

        Integer extend = 5;

        assertEquals(extend, cacheLRU.get(5));
    }

    @Test
    void clear() {

        cacheLRU.put(1, 1);

        cacheLRU.clear();

        assertTrue(cacheLRU.get(1) == null);


    }
}
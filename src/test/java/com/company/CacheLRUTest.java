package com.company;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class CacheLRUTest {

    private CacheLRU cacheLRU;

    @BeforeEach
    public void init() {

        cacheLRU = new CacheLRU(10);

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
    }

    @AfterEach
    public void end() {
        cacheLRU.clear();
    }

    @Test
    public void put() {

        int result = (int)cacheLRU.put(11, 11).getValue();

        assertEquals(1, result);
    }

    @Test
    public void get() {

        assertEquals(5, cacheLRU.get(5));
    }

    @Test
    public void clear() {

        cacheLRU.clear();
        assertTrue(cacheLRU.get(1) == null);
    }
}
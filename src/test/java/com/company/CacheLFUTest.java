package com.company;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheLFUTest {

    private CacheLFU cacheLFU;

    @BeforeEach
    public void init() {

        cacheLFU = new CacheLFU(10);

        cacheLFU.put(1, 1);
        cacheLFU.put(2, 2);
        cacheLFU.put(3, 3);
        cacheLFU.put(4, 4);
        cacheLFU.put(5, 5);
        cacheLFU.put(6, 6);
        cacheLFU.put(7, 7);
        cacheLFU.put(8, 8);
        cacheLFU.put(9, 9);
        cacheLFU.put(10, 10);
    }

    @AfterEach
    public void end() {
        cacheLFU.clear();
    }

    @Test
    public void put() {

        cacheLFU.put(1, 1);
        cacheLFU.put(2, 2);
        cacheLFU.put(3, 3);
        cacheLFU.put(6, 6);
        cacheLFU.put(7, 7);
        cacheLFU.put(8, 8);
        cacheLFU.put(9, 9);
        cacheLFU.put(10, 10);

        int result = (int)cacheLFU.put(11, 11).getValue();
        assertEquals(4, result);
    }

    @Test
    public void get() {

        assertEquals(5, cacheLFU.get(5));
    }

    @Test
    public void clear() {

        cacheLFU.clear();
        assertTrue(cacheLFU.get(1) == null);
    }
}
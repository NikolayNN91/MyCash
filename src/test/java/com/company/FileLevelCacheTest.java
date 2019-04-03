package com.company;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileLevelCacheTest {

    private FileLevelCache fileLevelCache;

    @BeforeEach
    public void init() {
        fileLevelCache = new FileLevelCache(10);

        fileLevelCache.put(1, 1);
        fileLevelCache.put(2, 2);
        fileLevelCache.put(3, 3);
        fileLevelCache.put(4, 4);
        fileLevelCache.put(5, 5);
        fileLevelCache.put(6, 6);
        fileLevelCache.put(7, 7);
        fileLevelCache.put(8, 8);
        fileLevelCache.put(9, 9);
        fileLevelCache.put(10, 10);
    }

    @AfterEach
    public void end() {
        fileLevelCache.clear();
    }

    @Test
    void put() {
        assertEquals(1, fileLevelCache.get(fileLevelCache.put(11, 11).getKey()));
        assertEquals(11, fileLevelCache.get(11));

    }

    @Test
    void get() {
        assertEquals(5, fileLevelCache.get(5));
    }

    @Test
    void clear() {

        fileLevelCache.clear();
        assertTrue(fileLevelCache.get(1) == null);
    }
}